package ai.dm;

import com.google.common.collect.Streams;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.lang.Math.min;
import static java.util.stream.Collectors.toMap;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
public class ProductResource {

    //TODO move to config
    private static final String DEFAULT_LIMIT = "20";
    private static final int MAX_LIMIT = 100;

    private final static Logger LOG = LoggerFactory.getLogger(ProductResource.class);

    private final ProductDAO dao;

    public ProductResource(ProductDAO dao) {
        this.dao = dao;
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public ProductWithOrders updateProduct(@PathParam("id") String id, Product product) {
        LOG.trace("id={}, product={}",id,product);
        dao.updateProduct(id, product);
        var orders = dao.productOrders(id);
        return new ProductWithOrders(product, orders);
    }

    @GET
    @Path("/{id}")
    public ProductWithOrders getProduct(@PathParam("id") String id) {
        var p = dao.getProduct(id);
        var orders = dao.productOrders(id);
        var po = new ProductWithOrders(p, orders);
        LOG.trace("po={}",po);
        return po;
    }

    @GET
    public List<ProductWithOrders> productsWithOrders(
            @QueryParam("limit") @DefaultValue(DEFAULT_LIMIT) int limit,
            @QueryParam("offset") int offset
    ) {
        var products = dao.products(min(limit, MAX_LIMIT), offset);
        Map<String, Product> productById = products.stream().collect(toMap(Product::id, Function.identity()));
        var orders = dao.ordersForProducts(productById.keySet());

        var ordersByProductId = orders.stream().collect(Collectors.groupingBy(Order::productId));
        var productsWithOrders = products.stream().map( p -> new ProductWithOrders(p, ordersByProductId.get(p.id()))).toList();
        LOG.trace("Returning all the products {}", productsWithOrders);
        return productsWithOrders;
    }
}
