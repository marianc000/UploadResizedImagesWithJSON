package service;

import entity.IlllustratedData;
import java.util.Arrays;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author mcaikovs
 */
@Path("/data")
public class IllustratedDataResource {
    
    @GET
    @Path("test")
    @Produces(MediaType.APPLICATION_JSON)
    public IlllustratedData getTestData() {
        return new IlllustratedData("John", "Smith", Arrays.asList("url1", "url2"));
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
