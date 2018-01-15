package service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author mcaikovs
 */
@Path("/test")
public class MyResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public MyBean getBook() {
        return new MyBean("John", "Smith");
    }
    /*
    both jaxb and jackson annotations are considered, output is:
{
  "jaxbFirstName" : "John",
  "jacksonLastName" : "Smith",
  "jaxbFullName" : "John Smith"
}
     */
}
