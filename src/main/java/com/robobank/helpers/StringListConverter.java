package com.robobank.helpers;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.List;

/**
 * @author mzeeshan
 * Created By Zeeshan on August 11, 2021 - 10:17 AM
 */
@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {

    private static final String SPLIT_CHAR = ";";

    @Override
    public String convertToDatabaseColumn(List<String> strings) {
        if(strings != null){
            return String.join(SPLIT_CHAR, strings);
        }
        return null;
    }

    @Override
    public List<String> convertToEntityAttribute(String s) {
        if(s !=null){
            return Arrays.asList(s.split(SPLIT_CHAR));
        }
        return null;
    }
}
