package Util;

import Model.Order;
import Policy.DiscountPolicy;
import VO.Money;

public class PriceCalculator {

    public static Money calculate(Money totalPrice, DiscountPolicy policy) {
        return policy.apply(totalPrice);
    }
}
