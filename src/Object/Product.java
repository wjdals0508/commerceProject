package Object;

public class Product {
    private String name;
    private int price;
    private String contents;
    private int stock;

    public Product(String name, int price, String contents, int stock) {
        this.name = name;
        this.price = price;
        this.contents = contents;
        this.stock = stock;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getContents() {
        return contents;
    }

    public int getStock() {
        return stock;
    }


}
