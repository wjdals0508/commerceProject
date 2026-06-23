package Manager;

import Model.Customer;
import Model.Product;
import Model.ShoppingCart;

import java.util.*;

public class Category {

    //// 싱글톤 ////
    private static Category instance;

    private Category() {}

    public static Category getInstance() {
        if (instance == null) {
            instance = new Category(); // 싱글톤
        }
        return instance;
    }

    //// 비즈니스 로직 ////

    // 카테고리 Enum
    public enum Categories {
        NONE("분류 없음", 0),
        ELECTRONIC_DEVICES("전자제품", 1),
        CLOTHES("의류", 2),
        FOOD("식품", 3);

        private final String string;
        private final int number;

        Categories(String string, int number) {
            this.string = string;
            this.number = number;
        }

        public String getString() {
            return string;
        }

        public int getNumber() {
            return number;
        }

        // 유저가 입력 받은 값을 Enum으로 변경해주기 위한 함수
        public static Categories getCategoryByNumber(int number) {

            for (Categories category : Categories.values()) {
                if (number == category.getNumber())
                    return category;
            }
            return NONE;
        }
    }

    // 카테고리를 키로 하는 상품 목록
    private final Map<Categories, List<Product>> categoryProducts = new HashMap<>();
    // ID를 키로 하는 상품 목록
    private final Map<Integer, Product> idProducts = new HashMap<>();


    // 상품 추가
    public void addProduct(Categories category, Product product) {

        categoryProducts.computeIfAbsent(category, key -> new ArrayList<>())
                .add(product);

        idProducts.put(product.getId(), product);
    }

    // 상품 제거
    public void deleteProduct(Product product) {
        categoryProducts.get(getCategory(product)).remove(product);
        idProducts.remove(product.getId());

        // 장바구니 모든 상품 제거
        Map<String, Customer> customers = CustomerManager.getInstance().getCustomers();
        for (Customer customer : customers.values()) {
            ShoppingCart shoppingCart = customer.getShoppingCart();
            if (shoppingCart.getProducts().containsKey(product)) {
                shoppingCart.removeProducts(product);
            }
        }
    }

    public Map<Categories, List<Product>> getCategoryProducts() {
        return Map.copyOf(categoryProducts); // 방어적 복사
    }

    // 카테고리로 상품 목록 검색
    public List<Product> getProductsByCategory(Categories category) {

        if (!categoryProducts.containsKey(category))
            return new ArrayList<>();

        return  List.copyOf(categoryProducts.get(category)); // 방어적 복사
    }

    // 상품 id로 상품 검색
    public Product getProductsById(int id) {
        return  idProducts.get(id);
    }

    // 상품 이름으로 상품 검색
    public List<Product> getProductsByName(String name) {

        return idProducts.values().stream()
                .filter(product -> product.getName().equals(name))
                .toList();
    }

    // 특정 상품의 카테고리 검색
    public Categories getCategory(Product product) {

        if (product == null) {
            throw new IllegalArgumentException("존재하지 않는 상품입니다.");
        }

        return categoryProducts.entrySet().stream()
                .filter(entry -> entry.getValue().contains(product))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
    }

    // 장바구니 내 상품의 재고 체크
    public void checkStock(Map<Product, Integer> products) {
        for (Map.Entry<Product,Integer> entry  : products.entrySet()) {
            if (entry.getKey().getStock() < entry.getValue()) {
                throw new IllegalArgumentException("[ " + entry.getKey().getName() + " ]의 재고가 부족합니다.");
            }
        }
    }

    // 재고 감소
    public void reduceStock(Product product, int count) {

        Product target = idProducts.get(product.getId());

        if (target == null) {
            throw new IllegalArgumentException("존재하지 않는 상품입니다.");
        }

        target.reduceStock(count);
    }

    // 장바구니 목록에 있는 삼품들의 재고 감소 (다형성)
    public void reduceStock(ShoppingCart shoppingCart) {

        if (shoppingCart == null) {
            throw new IllegalArgumentException("유효하지 않은 요청입니다.");
        }

        for (Map.Entry<Product, Integer> entry : shoppingCart.getProducts().entrySet()) {
            Product product = this.getProductsById(entry.getKey().getId());
            this.reduceStock(product, entry.getValue());
        }
    }

}
