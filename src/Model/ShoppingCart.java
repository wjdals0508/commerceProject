package Model;

import VO.Money;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {

    private final Map<Product, Integer> products = new HashMap<>();

    public ShoppingCart() {
    }

    public Map<Product, Integer> getProducts() {
        return Map.copyOf(products);
    }

    public void addProducts(Product product, int quantity) {
        if (product.getStock() < quantity) {
            throw new IllegalArgumentException("재고가 부족합니다.");
        }
        products.merge(product, quantity, Integer::sum);
    }

    public void removeProducts(Product product) {
        products.remove(product);
    }

    public void clearProducts() {
        products.clear();
    }

    public Money getTotalPrice() {
        Money totalPrice = new Money(0);

        for (Map.Entry<Product,Integer> entry  : products.entrySet()) {
            totalPrice = totalPrice.add(entry.getKey().getPrice().multi(entry.getValue()));
        }
        return totalPrice;
    }
}
