package ai.dm;


import java.util.List;

public record ProductWithOrders(Product product, List<Order> orders) {
}
