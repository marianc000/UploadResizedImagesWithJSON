package service;

import entity.IlllustratedData;
import entity.Photo;
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
        return new IlllustratedData("John", "Smith", Arrays.asList(new Photo("http://cdn.cnn.com/cnnnext/dam/assets/171106015854-trump-texas-church-shooting-mental-health-problem-acosta-bpr-00011118-medium-plus-169.jpg","http://cdn.cnn.com/cnnnext/dam/assets/180112064001-george-bush-richard-tubb-2003-medium-plus-169.jpg")));
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
