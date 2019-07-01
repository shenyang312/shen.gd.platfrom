package shen.gd.platform.Interface;

public interface ZuulFilter {
    // 只有一个方法， 其他方法要有默认的实现
    String filterName(String name);

    default int filterOrder() {
        return 0;
    }

}
