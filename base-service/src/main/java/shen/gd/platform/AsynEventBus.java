package shen.gd.platform;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author Xiep
 * @Date 27/03/2018
 */
@Component
public class AsynEventBus {

    @Autowired
    @Qualifier("taskExecutor")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    private EventBus eventBus;

    @PostConstruct
    public void init() {
        AsyncEventBus asyncEventBus = new AsyncEventBus(threadPoolTaskExecutor);
        this.eventBus = asyncEventBus;
    }

    /**
     * post object to subscribe
     * @param object Try to use a custom object To avoid conflict
     */
    public void post(Object object) {
        eventBus.post(object);
    }

    public void register(Object object) {
        eventBus.register(object);
    }
}
