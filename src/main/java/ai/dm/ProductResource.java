package ai.dm;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
public class ProductResource {

    private final static Logger LOG = LoggerFactory.getLogger(ProductResource.class);

    public ProductResource() {
    }

    @GET
    public Collection<Product> products() {
        LOG.info("Returning all the products");
        return List.of(
                new Product("1", "boom"),
                new Product("2", "bam")
        );
    }
}
