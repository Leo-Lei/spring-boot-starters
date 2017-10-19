package com.leibangzhu.starters.common.util;

import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.Field;
import java.util.Map;

public class TestUtil {

    // 设置类的字段，支持设置父类的字段
    public static void setField(Object obj, String fieldName, Object fieldValue) throws Exception {
        Field field = null;

        Class tempClass = obj.getClass();
        while ( null != tempClass){
            try {
                field = tempClass.getDeclaredField(fieldName);
            }catch (Exception e){

            }

            if (null != field){
                break;
            }
            tempClass = tempClass.getSuperclass();
        }
        if(null != field){
            field.setAccessible(true);
            field.set(obj,fieldValue);
        }
    }

    // 感知当前对象的属性是否发生变化（增加，减少或更名）
    public static boolean checkPropertiesStrictMatched(Object target, String[] expectedProperties){

        try {

            Map<String, String> actualProperties = BeanUtils.describe(target);
            actualProperties.remove("class");

            if(expectedProperties.length != actualProperties.size()){

                return false;
            }

            for(String expectedProperty : expectedProperties){

                if(!actualProperties.containsKey(expectedProperty)){

                    return false;
                }

                actualProperties.remove(expectedProperty);
            }

            if(!actualProperties.isEmpty()){

                return false;
            }
        } catch (Exception e) {

            return false;
        }

        return true;
    }
}
