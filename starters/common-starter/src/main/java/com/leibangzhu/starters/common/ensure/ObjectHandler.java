package com.leibangzhu.starters.common.ensure;

public class ObjectHandler {

    private Object obj;

    public ObjectHandler(Object obj) {
        this.obj = obj;
    }

    public EnsureResult isNotNull(){

        if(null != obj){

            return EnsureResult.pass();
        }else{

            return EnsureResult.fail();
        }
    }
}
