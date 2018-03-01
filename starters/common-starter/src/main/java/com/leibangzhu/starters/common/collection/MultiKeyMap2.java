package com.leibangzhu.starters.common.collection;

import java.util.LinkedHashMap;
import java.util.Map;

public class MultiKeyMap2<T1,T2,T4> {

    private Map<Key2<T1,T2>,T4> map = new LinkedHashMap<Key2<T1, T2>, T4>();

    public void put(T1 t1, T2 t2,T4 t4){
        map.put(new Key2<>(t1,t2),t4);
    }

    public T4 get(T1 t1,T2 t2){
        return map.get(new Key2<>(t1,t2));
    }

    public boolean containsKey(T1 t1,T2 t2){
        Key2<T1,T2> key = new Key2<T1,T2>(t1,t2);
        return map.containsKey(key);
    }

    public void remove(T1 t1,T2 t2){
        Key2<T1,T2> key = new Key2<T1,T2>(t1,t2);
        map.remove(key);
    }

    private class Key2<T1,T2>{
        private T1 t1;
        private T2 t2;

        public Key2(T1 t1,T2 t2){
            this.t1 = t1;
            this.t2 = t2;
        }

        @Override
        public boolean equals(Object o){
            if (this == o){return true;}
            if (!(o instanceof Key2)){return false;}
            Key2 key = (Key2) o;
            return t1.equals(key.t1) && t2.equals(key.t2);
        }

        public int hashCode(){
            int result = t1.hashCode() + t2.hashCode();
            return result;
        }
    }
}
