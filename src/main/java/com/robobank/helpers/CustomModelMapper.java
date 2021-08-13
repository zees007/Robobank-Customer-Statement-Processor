package com.robobank.helpers;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class CustomModelMapper extends ModelMapper {

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new CustomModelMapper();
        modelMapper.getConfiguration().setFieldMatchingEnabled(true).setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }
}
