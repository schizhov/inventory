package ai.dm;

import static java.util.Objects.requireNonNull;

//productId,name,quantity,category,subCategory
//prod1548#prod104001000080,High-top sneakers,8,Shoes,Sneakers
public class Product {
    private String id;

    private String name;

    public Product(String id, String name) {
        this.id = requireNonNull(id);
        this.name = requireNonNull(name);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
