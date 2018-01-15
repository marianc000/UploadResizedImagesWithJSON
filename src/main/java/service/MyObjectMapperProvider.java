package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import javax.ws.rs.ext.ContextResolver;

//@Provider
//By default a single instance of each provider class is instantiated for each JAX-RS application
public class MyObjectMapperProvider implements ContextResolver<ObjectMapper> {
    
    ObjectMapper objectMapper;  // create once, reuse
    
    public MyObjectMapperProvider() {
        objectMapper = createObjectMapper();
    }
    
    @Override
    public ObjectMapper getContext(final Class<?> type) {
        return objectMapper;
    }
    
    ObjectMapper createObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT).registerModule(new JaxbAnnotationModule());
        return objectMapper;
    }
}
