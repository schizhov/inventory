package ai.dm;

import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindList;
import org.jdbi.v3.sqlobject.customizer.BindMethods;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.Collection;
import java.util.List;

public interface ProductDAO {

    @SqlQuery("select * from products order by name limit :limit offset :offset")
    @RegisterConstructorMapper(Product.class)
    List<Product> products(@Bind("limit") int limit, @Bind("offset") int offset);

    @SqlQuery("select * from orders where product_id in (<productIds>)")
    @RegisterConstructorMapper(Order.class)
    List<Order> ordersForProducts(@BindList("productIds") Collection<String> productIds);

    @SqlUpdate("update products set name = :p.name, quantity = :p.quantity, category = :p.category, sub_category =:p.subCategory where id = :id")
    void updateProduct(@Bind("id") String id, @BindMethods("p") Product product);

    @SqlQuery("select * from products where id = :id")
    @RegisterConstructorMapper(Product.class)
    Product getProduct(@Bind("id") String id);

    @SqlQuery("select * from orders where product_id = :productId")
    @RegisterConstructorMapper(Order.class)
    List<Order> productOrders(@Bind("productId") String productId);

}
