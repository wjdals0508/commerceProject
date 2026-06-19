package Model;

import VO.Money;

public record Discount(String name, int rate, Money discountMoney) {

    public Discount(String name, int rate, Money discountMoney) {
        this.name = name;
        this.rate = rate;
        this.discountMoney = new Money(discountMoney.getAmount());
    }
}
