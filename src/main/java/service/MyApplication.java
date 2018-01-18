package service;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/api")
public class MyApplication extends Application {

    public MyApplication() {
        singletons = new HashSet<Object>() {
            {
                add(new MyObjectMapperProvider());
            }
        };
        resources = new HashSet<Class<?>>() {
            {
                add(MultiPartResource.class);
            }
        };
    }

    Set<Object> singletons;
    Set<Class<?>> resources;

    /**
     * The default lifecycle for resource class instances is per-request. The
     * default lifecycle for providers is singleton.
     */
    @Override
  
    public Set<Class<?>> getClasses() {
        System.out.println(">getClasses()");  // note, it is called twice during initialization, 
        return resources;
    }

    @Override
   
    public Set<Object> getSingletons() {
        System.out.println(">getSingletons()"); // note, it is called twice during initialization, 
        return singletons;
    }
}
