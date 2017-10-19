package com.leibangzhu.starters.common.mapper;

import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Component;

/**
 * Created by reinhard on 01/08/2017.
 */
@Component
public class ModelMapper {

    private DozerBeanMapper mapper = new DozerBeanMapper();

    public <T> T map(Object source, Class<T> destinationClass){

        return mapper.map(source, destinationClass);
    }
}
