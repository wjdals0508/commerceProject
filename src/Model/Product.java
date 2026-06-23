package Model;

import VO.Money;

public class Product {
    private static int idCounter = 0;
    private int id = 0;
    private String name;
    private Money price;
    private String contents;
    private int stock;



    public Product(String name, int price, String contents, int stock) {
        this.id = idCounter;
        this.name = name;
        this.price = new Money(price);
        this.contents = contents;
        this.stock = stock;
        idCounter++;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Money price) {
        if (price.getAmount() < 0) {
            throw new IllegalArgumentException("상품 가격은 0보다 작을 수 없습니다.");
        }
        this.price = new Money(price.getAmount());
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void increaseStock(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("재고 증가 값은 0보다 작을 수 없습니다.");
        }
        this.stock += count;
    }

    public void reduceStock(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("재고 감소 값은 0보다 작을 수 없습니다.");
        }
        this.stock -= count;
    }

    public void setStock(int stock) {
        if (stock < 0) {
            throw new IllegalArgumentException("재고 값은 0보다 작을 수 없습니다.");
        }
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Money getPrice() {
        return price;
    }

    public String getContents() {
        return contents;
    }

    public int getStock() {
        return stock;
    }


}
