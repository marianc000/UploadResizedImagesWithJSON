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
                add(MyResource.class);
                add(IllustratedDataResource.class);
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
    // note, it is called twice during initialization, 
    public Set<Class<?>> getClasses() {
        System.out.println(">getClasses()");
        return resources;
    }

    @Override
    // note, it is called twice during initialization, 
    public Set<Object> getSingletons() {
        System.out.println(">getSingletons()");
        return singletons;
    }
}
