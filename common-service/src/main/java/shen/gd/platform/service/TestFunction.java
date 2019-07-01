package shen.gd.platform.service;

import org.apache.poi.ss.formula.functions.T;
import shen.gd.platform.Interface.ZuulFilter;
import shen.gd.platform.entity.GdOrderMerch;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class TestFunction {
    public static void main(String[] args) {

//        ZuulFilter filter = String::toLowerCase;
//
//        filter.filterName("JAY");
//
//        Map<Class<?>, Function<String, Object>> map = new HashMap<Class<?>, Function<String, Object>>();
//
//        map.put(Boolean.class, Boolean::valueOf);
//        map.put(Boolean.TYPE, Boolean::valueOf);
//
//        // 相当于这种函数的写法
//        map.put(Byte.class, (t) -> {
//
//            return Byte.valueOf(t);
//
//        });
//
//        Function<String, Object> put = map.put(Byte.TYPE, (t) -> {
//
//            return Byte.valueOf(t) + 1;
//
//        });
        Integer x = 0;
        Integer y = 1;

        Map<Integer,Function<Object, Boolean>> mapTwo = new HashMap<>();
        Function<Object,Boolean> function = (b)->{
            GdOrderMerch sd = (GdOrderMerch)b;
            if(sd.getId()==1)return true;
            return false;
        };
        mapTwo.put(0,function);
        if(mapTwo.get(x).apply(GdOrderMerch.builder().id(1).build()))
            System.out.println("成功了");
        Boolean flag = false;
        switch(x){
            case 1:
                if(x==y)flag = true;
                break;
            case 2:
                if(x==y)flag = true;
                break;
        }
        System.out.println(flag);
//
//        System.out.println(map.get(Boolean.class).apply("true"));
//
//        System.out.println(map.get(Byte.class).apply("47"));
//        System.out.println(map.get(Byte.TYPE).apply("47"));
    }

}
