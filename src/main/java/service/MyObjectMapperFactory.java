/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 *
 * @author mcaikovs
 */
public class MyObjectMapperFactory {

    private static ObjectMapper defaultObjectMapper;

    public static ObjectMapper getObjectMapper() {

        if (defaultObjectMapper == null) {
            defaultObjectMapper = new ObjectMapper();
            defaultObjectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        }

        return defaultObjectMapper;
    }
}
