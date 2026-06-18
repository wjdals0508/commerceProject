package System;

import Manager.OrderManager;
import Model.Product;
import Manager.Category;
import Model.Customer;
import Model.Order;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CommerceSystem {

    //// 싱글톤 ////
    private static CommerceSystem instance;

    private CommerceSystem() {}

    public static CommerceSystem getInstance() {
        if (instance == null) {
            instance = new CommerceSystem(); // 싱글톤
        }
        return instance;
    }

    //// 비즈니스 로직 ////
    public void start(Customer customer) {
        Scanner sc = new Scanner(System.in);
        DecimalFormat df = new DecimalFormat("###,###");
        Category category = Category.getInstance();
        OrderManager orderManager = OrderManager.getInstance();

        final int SCAN_CATEGORY_MAX = 3;

        //// 콘솔 OUT 시작 ////
        while (true) {
            try {

                //// Main ////
                System.out.println("[ 실시간 커머스 플랫폼 ]");
                System.out.println("1.전자제품");
                System.out.println("2.의류");
                System.out.println("3.식품");
                System.out.println("0.종료");

                System.out.println("\n[ 주문 관리 ]");
                System.out.println("4.장바구니 확인");
                System.out.println("5.주문 확인");

                int mainFunctionScan = sc.nextInt();

                if (mainFunctionScan == 0) break;

                else if (mainFunctionScan <= SCAN_CATEGORY_MAX) {

                    //// Category ////
                    Category.Categories categoryEnum = Category.Categories.getCategoryByNumber(mainFunctionScan);
                    List<Product> products = category.getProductsByCategory(categoryEnum);

                    System.out.println("[ " + categoryEnum.getString() + " 카테고리 ]");
                    for (int i = 0; i < products.size(); i++) {
                        Product product = products.get(i);
                        System.out.println((i + 1) + ". "
                                + product.getName() + "  /  "
                                + df.format(product.getPrice()) + "원  /  "
                                + product.getContents() + "  /  재고 : "
                                + product.getStock() + "개");
                    }
                    System.out.println("0. 뒤로가기");

                    int functionScan = sc.nextInt();
                    if (functionScan == 0) continue;

                    //// Product ////
                    int productNumber = functionScan;
                    Product product = products.get(productNumber - 1);

                    System.out.println("\n선택한 상품 : "
                            + product.getName() + "  /  "
                            + df.format(product.getPrice()) + "원  /  "
                            + product.getContents() + "  /  재고 : "
                            + product.getStock() + "개 \n");

                    System.out.println("위 상품을 장바구니에 추가하시겠습니까?");
                    System.out.println("1.확인        2.취소" );

                    functionScan = sc.nextInt();

                    if (functionScan == 1) {
                        customer.getShoppingCart().addProducts(product, 1);
                    }
                }
                else if (mainFunctionScan == 4) {

                    //// SHOPPING_CART ////
                    Map<Product, Integer> shoppingCartMap = customer.getShoppingCart().getProducts();
                    if (shoppingCartMap.isEmpty()) {
                        System.out.println("장바구니가 비었습니다. 메인화면으로 돌아갑니다.");
                        continue;
                    } else {
                        System.out.println("[ 장바구니 내역 ]");
                        for (Map.Entry<Product, Integer> entry : shoppingCartMap.entrySet()) {
                            System.out.println(entry.getKey().getName() + "  /  "
                                    + df.format(entry.getKey().getPrice()) + "원  /  "
                                    + entry.getKey().getContents() + "  /  수량: "
                                    + entry.getValue() + "개  /  재고: "
                                    + entry.getKey().getStock() + "개");
                        }
                        System.out.println("\n[ 총 주문 금액 ]");
                        System.out.println(df.format(customer.getShoppingCart().getTotalPrice()) + "원");
                        System.out.println("\n1. 주문 확정    2. 장바구니 비우기    3. 메인으로 돌아가기");

                        int functionScan = sc.nextInt();
                        switch (functionScan) {
                            case 1 -> orderManager.order(customer);
                            case 2 -> customer.getShoppingCart().clearProducts();
                            case 3 -> {continue;}
                        }
                    }
                }
                else if (mainFunctionScan == 5){

                    //// ORDER ////
                    List<Order> orderList = orderManager.getOrderListByUserId(customer.getId());
                    System.out.println("[ 주문 내역 ]");
                    for (int i = 0; i < orderList.size(); i++){

                        Order order = orderList.get(i);
                        System.out.println(i+1 + "번 주문 : " + order.getDateString() + " ");
                        System.out.println("--------------------------------------------------");
                        for (Map.Entry<Product, Integer> entry : order.getProducts().entrySet()) {
                            System.out.println(" - " + entry.getKey().getName() + "  /  "
                                    + df.format(entry.getKey().getPrice()) + "원  /  "
                                    + entry.getKey().getContents() + "  /  수량: "
                                    + entry.getValue() + "개  /  재고: "
                                    + entry.getKey().getStock() + "개");
                        }
                        System.out.println("--------------------------------------------------");
                        System.out.println("총 금액 : " + df.format(order.getTotalPrice()) + "원");
                        System.out.println("주문 상태 : " + order.getState().getString() + "\n");
                    }
                    System.out.println("1. 주문 취소    2. 메인으로 돌아가기");
                    int functionScan = sc.nextInt();
                    switch (functionScan) {
                        case 1 -> {
                            System.out.println("몇 번 주문을 취소할까요?");
                            functionScan = sc.nextInt();
                            orderManager.cancelOrder(customer.getId(), functionScan-1);
                            System.out.println("주문이 취소되었습니다.");
                            continue;
                        }
                        case 2 -> {continue;}
                    }
                }

            } catch (Exception e) {
                System.out.println("\n ****" + e.getMessage() + "**** \n");
            }
        }
    }
}
