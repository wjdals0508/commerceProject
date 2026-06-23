package Policy;

import VO.Money;

// 전략 패턴을 위한 인터페이스
@FunctionalInterface
public interface DiscountPolicy{
    Money apply(Money price);
}