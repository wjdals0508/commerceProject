package Model;

public class Customer {

    private final String id;
    private String name;
    private String eMail;
    private String grade;

    private final ShoppingCart shoppingCart = new ShoppingCart(); // 합성


    public Customer(String id, String name, String eMail, String grade) {
        this.id = id;
        this.name = name;
        this.eMail = eMail;
        this.grade = grade;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEMail(String eMail) {
        this.eMail = eMail;
    }

    public void setGrade(String grade) {
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

    public String getGrade() {
        return grade;
    }

    public ShoppingCart getShoppingCart() { return shoppingCart; }
}
