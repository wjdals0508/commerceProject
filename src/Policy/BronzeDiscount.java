package Policy;

import Model.Customer;
import VO.Money;

public class BronzeDiscount implements DiscountPolicy{
    @Override
    public Money apply(Money price) {
        return price.multi(Customer.Grade.BRONZE.getDiscountRate());
    }
}
