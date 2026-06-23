package Model;

import VO.Money;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {

    private final Map<Product, Integer> products = new HashMap<>();

    public ShoppingCart() {
    }

    // 장바구니 목록
    public Map<Product, Integer> getProducts() {
        return Map.copyOf(products);
    }

    // 장바구니에 상품 추가
    public void addProducts(Product product, int quantity) {
        if (product.getStock() < quantity) {
            throw new IllegalArgumentException("재고가 부족합니다.");
        }
        products.merge(product, quantity, Integer::sum);
    }

    // 이름으로 검색 후 제거
    public void removeProducts(String name) {

        Product product = products.keySet().stream()
                .filter(x -> x.getName().equals(name))
                .findFirst().orElseThrow(() -> new IllegalStateException("상품을 찾을 수 없습니다."));

        products.remove(product);
    }

    // 특정 상품 제거
    public void removeProducts(Product product) {
        products.remove(product);
    }

    // 장바구니 비우기
    public void clearProducts() {
        products.clear();
    }

    // 총 상품 가격
    public Money getTotalPrice() {
        Money totalPrice = new Money(0);

        for (Map.Entry<Product,Integer> entry  : products.entrySet()) {
            totalPrice = totalPrice.add(entry.getKey().getPrice().multi(entry.getValue()));
        }
        return totalPrice;
    }
}
