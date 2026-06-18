package Manager;

import Model.Product;

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


    private final Map<Categories, List<Product>> categoryProducts = new HashMap<>();
    private final Map<Integer, Product> idProducts = new HashMap<>();

    public void addProduct(Categories category, Product product) {

        categoryProducts.computeIfAbsent(category, key -> new ArrayList<>())
                .add(product);

        idProducts.put(product.getId(), product);
    }

    public void deleteProduct(Product product) {
        categoryProducts.get(getCategory(product)).remove(product);
        idProducts.remove(product.getId());
    }

    public Map<Categories, List<Product>> getCategoryProducts() {
        return Map.copyOf(categoryProducts);
    }

    public List<Product> getProductsByCategory(Categories category) {

        if (!categoryProducts.containsKey(category))
            return new ArrayList<>();

        return  List.copyOf(categoryProducts.get(category)); // 방어적 복사
    }

    public Product getProductsById(int id) {
        return  idProducts.get(id);
    }

    public List<Product> getProductsByName(String name) {

        return idProducts.values().stream()
                .filter(product -> product.getName().equals(name))
                .toList();
    }

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

    public void reduceStock(Product product, int count) {

        Product target = idProducts.get(product.getId());

        if (target == null) {
            throw new IllegalArgumentException("존재하지 않는 상품입니다.");
        }

        target.reduceStock(count);
    }

}
