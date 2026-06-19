package Policy;

import Model.Customer;
import VO.Money;

public class SilverDiscount implements DiscountPolicy{
    @Override
    public Money apply(Money price) {
        return price.multi(Customer.Grade.SILVER.getDiscountRate());
    }
}
