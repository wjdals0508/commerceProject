package Model;

public class Product {
    private static int idCounter = 0;
    private int id = 0;
    private String name;
    private int price;
    private String contents;
    private int stock;

    public Product(String name, int price, String contents, int stock) {
        this.id = idCounter;
        this.name = name;
        this.price = price;
        this.contents = contents;
        this.stock = stock;
        idCounter++;
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

    public void reduceStock(int count) {
        this.stock -= count;
    }

    public int getId() {
        return id;
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
