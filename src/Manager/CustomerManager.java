package Manager;

import Model.Customer;

import java.util.HashMap;
import java.util.Map;

public class CustomerManager {

    //// 싱글톤 ////
    private static CustomerManager instance;

    private CustomerManager() {}

    public static CustomerManager getInstance() {
        if (instance == null) {
            instance = new CustomerManager(); // 싱글톤
        }
        return instance;
    }


    //// 비즈니스 로직 ////

    // 유저 목록
    private final Map<String, Customer> customers = new HashMap<>();

    // 유저 추가
    public void addCustomer(Customer customer) {

        Customer previous = customers.putIfAbsent(customer.getId(), customer);
        if (previous != null) {
            throw new IllegalArgumentException("이미 존재하는 유저입니다.");
        }
    }

    // 유저 정보
    public Map<String, Customer> getCustomers() {
        return Map.copyOf(customers);
    }
}
