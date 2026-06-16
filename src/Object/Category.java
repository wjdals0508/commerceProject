package Object;

import java.util.*;

public class Category {

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

        public static Categories getCategoryByNumber(int number) {

            for (Categories category : Categories.values()) {
                if (number == category.getNumber())
                    return category;
            }
            return NONE;
        }
    }

    private final Map<Categories, List<Product>> products = new HashMap<>();

    public void addProduct(Categories category, Product product) {

        products.computeIfAbsent(category, key -> new ArrayList<>())
                .add(product);
    }

    public List<Product> getProducts(Categories category) {

        if (products.get(category) == null)
            return new ArrayList<>();

        return  List.copyOf(products.get(category)); // 방어적 복사
    }
}
