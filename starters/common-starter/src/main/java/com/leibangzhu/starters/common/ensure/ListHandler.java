package com.leibangzhu.starters.common.ensure;

import java.util.List;

public class ListHandler<T> {

    private final List<T> list;

    public ListHandler(List<T> list){

        this.list = list;
    }

    public EnsureResult isNotEmpty(){

        if(null != list && !list.isEmpty()){

            return EnsureResult.pass();
        }else{

            return EnsureResult.fail();
        }
    }
}
