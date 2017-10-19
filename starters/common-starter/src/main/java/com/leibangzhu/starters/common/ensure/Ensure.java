package com.leibangzhu.starters.common.ensure;

import java.util.List;
import java.util.Map;

public class Ensure {

    public static IntegerHandler that(int a){
        return new IntegerHandler(a);
    }

    public static <T> ListHandler<T> that(List<T> a){
        return new ListHandler<T>(a);
    }

    public static StringHandler that(String a) {
        return new StringHandler(a);
    }

    public static <K,V> MapHandler<K,V> that(Map<K,V> a){
        return new MapHandler<>(a);
    }

    public static ObjectHandler that(Object a){
        return new ObjectHandler(a);
    }

}
