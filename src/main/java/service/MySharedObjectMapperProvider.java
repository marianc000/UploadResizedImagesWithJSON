package service;

import com.fasterxml.jackson.databind.ObjectMapper;

//By default a single instance of each provider class is instantiated for each JAX-RS application
public class MySharedObjectMapperProvider extends MyObjectMapperProvider {

    @Override
    ObjectMapper createObjectMapper() {
        return MyObjectMapperFactory.getObjectMapper();
    }
}
