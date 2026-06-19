package Policy;

import VO.Money;

@FunctionalInterface
public interface DiscountPolicy{
    Money apply(Money price);
}