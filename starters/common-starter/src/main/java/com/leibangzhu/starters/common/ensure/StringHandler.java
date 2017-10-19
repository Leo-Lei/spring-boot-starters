package com.leibangzhu.starters.common.ensure;

import org.apache.commons.lang3.StringUtils;


public class StringHandler {

    private String str;

    public StringHandler(String str) {

        this.str = str;
    }

    public EnsureResult isNotEmpty() {

        if(StringUtils.isNoneEmpty(str)){

            return EnsureResult.pass();
        }else{

            return EnsureResult.fail();
        }
    }

    public EnsureResult contains(String subString){

        if(0 <= str.indexOf(subString)){

            return EnsureResult.pass();
        }else{

            return EnsureResult.fail();
        }
    }

    public EnsureResult beginsWith(String prefix){

        if(str.startsWith(prefix)){

            return EnsureResult.pass();
        }else{

            return EnsureResult.fail();
        }
    }

    public EnsureResult endsWith(String suffix){

        if(str.endsWith(suffix)){

            return EnsureResult.pass();
        }else{

            return EnsureResult.fail();
        }
    }




}
