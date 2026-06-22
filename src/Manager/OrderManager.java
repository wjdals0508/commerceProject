package Manager;

import Model.*;
import Policy.GoldDiscount;
import Policy.PlatinumDiscount;
import Policy.SilverDiscount;
import Policy.BronzeDiscount;
import Util.Util;
import VO.Money;

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

    public Order order(Customer customer) {

        ShoppingCart shoppingCart = customer.getShoppingCart();

        Category category = Category.getInstance();
        category.checkStock(shoppingCart.getProducts());
        category.reduceStock(shoppingCart);

        List<Discount> discountList = new ArrayList<>();
        Money totalPrice = discount(shoppingCart.getTotalPrice(), customer, discountList);

        Order order = new Order(shoppingCart.getProducts(), customer.getId(), totalPrice, shoppingCart.getTotalPrice(), discountList);
        orderList.add(order);

        shoppingCart.clearProducts();

        return order;
    }

    private Money discount(Money totalPrice, Customer customer, List<Discount> discountList) {

        Money discountPrice = gradeDiscount(totalPrice, customer, discountList);
        // TO DO : 기타 할인, 쿠폰 등 추가

        return discountPrice;
    }

    private Money gradeDiscount(Money totalPrice, Customer customer, List<Discount> discountList) {

        Money discountMoney = new Money(0);

        switch (customer.getGrade()) {
            case BRONZE -> { discountMoney = Util.calculate(totalPrice, new BronzeDiscount()); }
            case SILVER -> { discountMoney = Util.calculate(totalPrice, new SilverDiscount()); }
            case GOLD -> { discountMoney = Util.calculate(totalPrice, new GoldDiscount()); }
            case PLATINUM -> { discountMoney = Util.calculate(totalPrice, new PlatinumDiscount()); }
        }

        String discountName = customer.getGrade() + " 등급 할인";
        discountList.add(new Discount(discountName, customer.getGrade().getDiscount(), discountMoney));

        return totalPrice.sub(discountMoney);
    }

    public List<Order> getOrderList() {
        return List.copyOf(orderList);
    }

    public List<Order> getOrderListByUserId(String id) {
        return List.copyOf(orderList.stream()
                .filter(x -> x.getCustomerId().equals(id))
                .toList());
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
