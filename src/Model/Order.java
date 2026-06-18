package Model;

import java.text.SimpleDateFormat;
import java.util.Date;
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

    private final String customerId;
    private final Map<Product, Integer> products;
    private final int totalPrice;
    private State state;

    private final Date date = new Date();

    public Order(Map<Product, Integer> products, String customerId, int totalPrice) {

        if (products.isEmpty() || customerId.isEmpty() || totalPrice < 0) {
            throw new IllegalArgumentException("옳바르지 않은 요청 입니다.");
        }

        this.products = Map.copyOf(products);
        this.customerId = customerId;
        this.totalPrice = totalPrice;
        this.state = State.REQUEST;
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

    public int getTotalPrice() {
        return totalPrice;
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
}
