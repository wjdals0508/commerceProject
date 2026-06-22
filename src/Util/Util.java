package Util;

import Policy.DiscountPolicy;
import VO.Money;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Util {

    public static <T> List<T> filter(List<T> items, Predicate<T> condition) {
        List<T> result = new ArrayList<>();
        for (T item : items) {
            if (condition.test(item)) {
                result.add(item);
            }
        }
        return result;
    }

    public static Money calculate(Money totalPrice, DiscountPolicy policy) {
        return policy.apply(totalPrice);
    }
}
