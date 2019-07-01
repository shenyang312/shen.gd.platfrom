package shen.gd.platform.controller;

import com.google.common.eventbus.Subscribe;
import org.apache.commons.lang.ObjectUtils;
import org.assertj.core.util.Lists;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shen.gd.platform.AsynEventBus;
import shen.gd.platform.SyUtil;
import shen.gd.platform.entity.GdOrderMerch;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RestController
public class JsoupMovieController {
    @Autowired
    private static volatile Instrumentation instru;

    @Autowired
    private AsynEventBus asynEventBus;

    @PostConstruct
    public void init() {
        asynEventBus.register(this);
    }


    @RequestMapping("jsoupMovie")
    public void jsoupMovie() throws IOException {
        //设置需要下载多少页
        int page = 10;//先爬取10页的内容
        int result = 0;
        for (int i = 1; i <= page; i++) {
            result = pachong_page("http://www.dytt8.net/html/gndy/dyzz/list_23_" + i + ".html");
        }
        System.out.println("爬取结束！一共爬取内容为:" + result * page + "条！");
    }


    public int pachong_page(String url) throws IOException {
        //String url="http://www.ygdy8.net/html/gndy/dyzz/list_23_1.html";
        Document doc = null;
        try {
            doc = Jsoup.connect(url).userAgent("Mozilla").get();//模拟火狐浏览器
        } catch (IOException e) {
            e.printStackTrace();
        }
        //这里根据在网页中分析的类选择器来获取电影列表所在的节点
        Elements div = doc.getElementsByClass("co_content8");
        //获取电影列表
        Elements table = div.select("table");//查找table标签
        //获取电影列表总数
        int result = table.size();
        //System.out.println("电影列表数为:"+result);
        for (Element tb : table) {
            try {
                Thread.sleep(1000);//让线程操作不要太快 1秒一次 时间自己设置，主要是模拟人在点击
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //获取所有电影详情的链接所在的节点
            Elements tr = tb.select("tr");
            //System.out.println(tr.size());
            //获取电影列表链接和标题
            String videos = tr.get(1).select("a").attr("abs:href");
            System.out.println(tr.get(1).select("a").attr("abs:href") + "\t" + tr.get(1).select("a").text());
            //这里要跳过这个首页页面 否则会抛出异常
            if ("http://www.dytt8.net/html/gndy/jddy/index.html".equals(videos)) continue;
            //进如电影列表详情页面
            doc = Jsoup.connect(videos).userAgent("Mozilla").get();
            //获取到电影详情页面所在的节点
            Element div1 = doc.getElementById("Zoom");
            //获取电影描述
            //String des=div1.select("p").text();
            //System.out.println(div1.select("p").text());
            //获取封面图地址
            Elements select = div1.select("img[src$=.jpg]");
            String imgUrl = select.get(0).attr("abs:src");
            System.out.println(imgUrl);
            //获取下载地址
            System.out.println(div1.select("td").text());

            //存入数据库
//                                .set("type", "最新电影")
//                                .set("title", tr.get(1).select("a").text())
//                                .set("imagesUrl", imgUrl)
//                                .set("videoUrl", div1.select("td").text())
//                                .set("date", new Date())
//                                .set("describe", div1.select("p").text())

        }
        return result;

    }

    @RequestMapping("evenBus")
    public String evenBus(Integer integer) throws IOException {
        List<GdOrderMerch> list = Lists.newArrayList();
        for (int i = 0; i < 30000; i++) {
            list.add(GdOrderMerch.builder().id(i).gdsName("asdfasfdasfdadfsasdf").gmtCreate(new Date()).build());
        }
        Predicate<GdOrderMerch> p1 = i -> i.getId() > 5;
        Predicate<GdOrderMerch> p2 = i -> i.getId() < 20;
        list.stream().filter(p1.and(p2)).collect(Collectors.toList());
        asynEventBus.post(11111);
        System.out.println("触发通知");
        return "111111";
    }

    @Subscribe
    private void pdmEvent(Integer integer) {
        System.out.println("看一下优先级-1");
    }

//    @Subscribe
//    public void lister(Integer integer) {
//        try {
//            Thread.sleep(10000l);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.printf("%s from int%n", integer);
//    }

    @Subscribe
    public void listerTwo(GdOrderMerch integer) {
        System.out.printf("这个是不想你走的 的 ");
        if (SyUtil.isEmpty(integer)) {
            System.out.printf("在这终止");
            Thread.currentThread().isInterrupted();
        }
        ;
        System.out.printf("这就不会走了");

    }

    public static void main(String[] str){
        Function<Integer, Integer> f = x -> x + 1;
        Function<Integer, Integer> g = x -> x * 2;
        Function<Integer, Integer> h = f.compose(g);
        int result = h.apply(6);
        System.out.println(result);
    }
}
