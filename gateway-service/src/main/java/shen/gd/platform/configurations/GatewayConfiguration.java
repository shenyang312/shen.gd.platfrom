package shen.gd.platform.configurations;

import com.alibaba.csp.sentinel.adapter.gateway.common.SentinelGatewayConstants;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiDefinition;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPathPredicateItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPredicateItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.GatewayApiDefinitionManager;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.SentinelGatewayFilter;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.exception.SentinelGatewayBlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.util.function.Supplier;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
public class GatewayConfiguration {

    private final List<ViewResolver> viewResolvers;
    private final ServerCodecConfigurer serverCodecConfigurer;

    public GatewayConfiguration(ObjectProvider<List<ViewResolver>> viewResolversProvider,
                                ServerCodecConfigurer serverCodecConfigurer) {
        this.viewResolvers = viewResolversProvider.getIfAvailable(Collections::emptyList);
        this.serverCodecConfigurer = serverCodecConfigurer;
    }


    /**
     * 配置SentinelGatewayBlockExceptionHandler，限流后异常处理
     * @return
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public JsonSentinelGatewayBlockExceptionHandler sentinelGatewayBlockExceptionHandler() {
        return new JsonSentinelGatewayBlockExceptionHandler(viewResolvers, serverCodecConfigurer);
    }

    class JsonSentinelGatewayBlockExceptionHandler implements WebExceptionHandler {

        private List<ViewResolver> viewResolvers;
        private List<HttpMessageWriter<?>> messageWriters;

        public JsonSentinelGatewayBlockExceptionHandler(List<ViewResolver> viewResolvers, ServerCodecConfigurer serverCodecConfigurer) {
            this.viewResolvers = viewResolvers;
            this.messageWriters = serverCodecConfigurer.getWriters();
        }

        @Override
        public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
            if (exchange.getResponse().isCommitted()) {
                return Mono.error(ex);
            }
            // This exception handler only handles rejection by Sentinel.
            if (!BlockException.isBlockException(ex)) {
                return Mono.error(ex);
            }
            return handleBlockedRequest(exchange, ex)
                    .flatMap(response -> writeResponse(response, exchange));
        }

        private Mono<ServerResponse> handleBlockedRequest(ServerWebExchange exchange, Throwable throwable) {
            return GatewayCallbackManager.getBlockHandler().handleRequest(exchange, throwable);
        }

        private final Supplier<ServerResponse.Context> contextSupplier = () -> new ServerResponse.Context() {
            @Override
            public List<HttpMessageWriter<?>> messageWriters() {
                return JsonSentinelGatewayBlockExceptionHandler.this.messageWriters;
            }

            @Override
            public List<ViewResolver> viewResolvers() {
                return JsonSentinelGatewayBlockExceptionHandler.this.viewResolvers;
            }
        };

        private Mono<Void> writeResponse(ServerResponse response, ServerWebExchange exchange) {
            ServerHttpResponse serverHttpResponse = exchange.getResponse();
            serverHttpResponse.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
            byte[] datas = "{\"code\":403,\"msg\":\"限流了\"}".getBytes(StandardCharsets.UTF_8);
            DataBuffer buffer = serverHttpResponse.bufferFactory().wrap(datas);
            return serverHttpResponse.writeWith(Mono.just(buffer));
        }
    }

    /**
     * 配置SentinelGatewayFilter
     * @return
     */
    @Bean
    @Order(-1)
    public GlobalFilter sentinelGatewayFilter() {
        return new SentinelGatewayFilter();
    }

    @PostConstruct
    public void doInit() {

        initGatewayRules();
    }

    /**
     * 配置限流规则
     */
    private void initGatewayRules() {
        Set<GatewayFlowRule> rules = new HashSet<>();
        rules.add(new GatewayFlowRule("path2_route")
                .setCount(1) // 限流阈值
                .setIntervalSec(1) // 统计时间窗口，单位是秒，默认是 1 秒
        );
        rules.add(new GatewayFlowRule("path_route")
                .setCount(20) // 限流阈值
                .setIntervalSec(1) // 统计时间窗口，单位是秒，默认是 1 秒
        );
        GatewayRuleManager.loadRules(rules);
    }

    private void initCustomizedApis() {
        Set<ApiDefinition> definitions = new HashSet<>();
        ApiDefinition api1 = new ApiDefinition("customized_api")
                .setPredicateItems(new HashSet<ApiPredicateItem>() {{
                    // article完全匹配
                    add(new ApiPathPredicateItem().setPattern("/local/**"));
                    // blog/开头的
                    add(new ApiPathPredicateItem().setPattern("/java/**")
                            .setMatchStrategy(SentinelGatewayConstants.PARAM_MATCH_STRATEGY_PREFIX));
                }});
        definitions.add(api1);
        GatewayApiDefinitionManager.loadApiDefinitions(definitions);
    }
}
