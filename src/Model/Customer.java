package Model;

public class Customer {

    public enum Grade {
        BRONZE("BRONZE", 0),
        SILVER("SILVER", 5),
        GOLD("GOLD", 10),
        PLATINUM("PLATINUM", 15);

        private final String string;
        private final int discount;

        Grade(String string, int discountRate) {
            this.string = string;
            this.discount = discountRate;
        }
        public String getString() {
            return string;
        }

        public int getDiscount() {
            return discount;
        }

        public double getDiscountRate() {
            return discount * 0.01;
        }
    }

    private final String id;
    private String name;
    private String eMail;
    private Grade grade;

    private final ShoppingCart shoppingCart = new ShoppingCart(); // 합성

    public Customer(String id, String name, String eMail, String grade) {
        this.id = id;
        this.name = name;
        this.eMail = eMail;
        this.grade = Grade.BRONZE;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEMail(String eMail) {
        this.eMail = eMail;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEMail() {
        return eMail;
    }

    public Grade getGrade() {
        return grade;
    }

    public ShoppingCart getShoppingCart() { return shoppingCart; }
}
