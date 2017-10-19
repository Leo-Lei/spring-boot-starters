package com.leibangzhu.starters.common.ensure;

public class IntegerHandler {

    private int number;

    public IntegerHandler(int number) {
        this.number = number;
    }

    public EnsureResult isPositive(){

        if(0 <= number){

            return EnsureResult.pass();
        }else{

            return EnsureResult.fail();
        }
    }

    public EnsureResult isNegative(){

        if(0 > number){

            return EnsureResult.pass();
        }else{

            return EnsureResult.fail();
        }
    }

    public EnsureResult inRange(int lower, int upper){

        if(lower <= number && number < upper){

            return EnsureResult.pass();
        }else{

            return EnsureResult.fail();
        }
    }

    public EnsureResult greaterThan(int limit){

        if(limit < number){

            return EnsureResult.pass();
        }else{

            return EnsureResult.fail();
        }
    }

    public EnsureResult lessThan(int limit){

        if(limit > number){

            return EnsureResult.pass();
        }else{

            return EnsureResult.fail();
        }
    }

}
