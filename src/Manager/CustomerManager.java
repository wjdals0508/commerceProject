package Manager;

import Model.Customer;
import Model.Order;

import java.util.HashMap;
import java.util.List;
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
    private final Map<String, Customer> customers = new HashMap<>();

    public void addCustomer(Customer customer) {

        Customer previous = customers.putIfAbsent(customer.getId(), customer);
        if (previous != null) {
            throw new IllegalArgumentException("이미 존재하는 유저입니다.");
        }
    }

    public Map<String, Customer> getCustomers() {
        return Map.copyOf(customers);
    }




}
