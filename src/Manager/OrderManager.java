package Manager;

import Model.Order;
import Model.Customer;
import Model.Product;
import Model.ShoppingCart;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderManager {

    //// 싱글톤 ////
    private static OrderManager instance;

    private OrderManager() {}

    public static OrderManager getInstance() {
        if (instance == null) {
            instance = new OrderManager(); // 싱글톤
        }
        return instance;
    }

    //// 비즈니스 로직 ////
    private final List<Order> orderList = new ArrayList<>();

    public void order(Customer customer) {

        ShoppingCart shoppingCart = customer.getShoppingCart();

        shoppingCart.checkStock();
        reduceStock(shoppingCart);

        orderList.add(new Order(shoppingCart.getProducts(), customer.getId(), shoppingCart.getTotalPrice()));

        shoppingCart.clearProducts();
    }

    public List<Order> getOrderList() {
        return List.copyOf(orderList);
    }

    public List<Order> getOrderListByUserId(String id) {
        return List.copyOf(orderList.stream()
                .filter(x -> x.getCustomerId().equals(id))
                .toList());
    }

    public void reduceStock(ShoppingCart shoppingCart) {

        if (shoppingCart == null) {
            throw new IllegalArgumentException("유효하지 않은 요청입니다.");
        }

        Category category = Category.getInstance();

        for (Map.Entry<Product, Integer> entry : shoppingCart.getProducts().entrySet()) {
            Product product = category.getProductsById(entry.getKey().getId());
            category.reduceStock(product, entry.getValue());
        }
    }

    public void cancelOrder(Order order) {

        Order targetOrder =  orderList.stream()
                .filter(x -> x.getId() == order.getId())
                .findFirst().orElseThrow(() -> new IllegalArgumentException("유효하지 않은 주문입니다."));

        Category category = Category.getInstance();
        Map<Product, Integer> productMap = targetOrder.getProducts();

        for (Map.Entry<Product, Integer> entry : productMap.entrySet()) {
            category.getProductsById(entry.getKey().getId()).increaseStock(entry.getValue());
        }

        targetOrder.cancelState();
    }

}
