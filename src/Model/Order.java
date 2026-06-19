package Model;

import VO.Money;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Order {

    public enum State {
        CANCEL("취소됨"),
        REQUEST("접수중"),
        DELIVERY("배송중"),
        COMPLETE("배송완료");

        private final String string;

        State(String string) {
            this.string = string;
        }
        public String getString() {
            return string;
        }
    }

    private static int idCounter = 0;
    private final int id;
    private final String customerId;
    private final Map<Product, Integer> products;
    private final Money totalPrice;
    private final Money originPrice;
    private State state;

    private final Date date = new Date();

    List<Discount> discountList;

    public Order(Map<Product, Integer> products, String customerId, Money totalPrice, Money originPrice, List<Discount> discountList) {

        if (products.isEmpty() || customerId.isEmpty() || totalPrice.getAmount() < 0) {
            throw new IllegalArgumentException("옳바르지 않은 요청 입니다.");
        }
        this.id = idCounter;
        this.products = Map.copyOf(products);
        this.customerId = customerId;
        this.totalPrice = new Money(totalPrice.getAmount());
        this.originPrice = new Money(originPrice.getAmount());
        this.state = State.REQUEST;
        this.discountList = List.copyOf(discountList);
        idCounter++;
    }

    public int getId() {
        return id;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getCustomerId() {
        return customerId;
    }

    public Map<Product, Integer> getProducts() {
        return Map.copyOf(products);
    }

    public Money getTotalPrice() {
        return totalPrice;
    }

    public Money getOriginPrice() {
        return originPrice;
    }

    public State getState() {
        return state;
    }

    public String getDateString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
        return simpleDateFormat.format(date);
    }

    public void cancelState() {
        if (state != State.REQUEST) {
            throw new IllegalArgumentException("접수 중인 주문만 취소할 수 있습니다.");
        }
        this.state = State.CANCEL;
    }

    public List<Discount> getDiscountList() {
        return discountList;
    }
}
