package System;

import Manager.OrderManager;
import Model.Discount;
import Model.Product;
import Manager.Category;
import Model.Customer;
import Model.Order;
import VO.Money;

import java.text.DecimalFormat;
import java.util.ArrayList;
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

    //// 내부 클래스 ////
    public static class UiController {

        public enum Screen {
            MAIN,
            CATEGORY_FILTER,
            CATEGORY,
            PRODUCT,
            SHOPPING_CART,
            ORDER,
            ADMIN,
            ADMIN_PRODUCT_ADD,
            ADMIN_PRODUCT_UPDATE,
            ADMIN_PRODUCT_DELETE,
            ADMIN_ALL_PRODUCT_VIEW,
        }

        private Screen screen;
        private boolean exit;
        private int category;
        private int scanInt;
        private String scanString;
        private boolean isAdmin = false;

        private Money filterPrice = new Money(0);
        private boolean filterOver = true;

        public UiController() {
            this.screen = Screen.MAIN;
            this.exit = false;
            this.category = 0;
            this.scanString = "";
        }

        public void setScreen(Screen screen) {
            this.screen = screen;
        }

        public Screen getScreen() {
            return screen;
        }

        public boolean getExit() {
            return exit;
        }

        public void setExit() {
            exit = true;
        }

        public int getCategory() {
            return category;
        }

        public void setCategory(int category) {
            this.category = category;
        }

        public int getScanInt() {
            return scanInt;
        }

        public void setScanInt(int scanInt) {
            this.scanInt = scanInt;
        }

        public String getScanString() {
            return scanString;
        }

        public void setScanString(String scanString) {
            this.scanString = scanString;
        }

        public boolean getAdmin() {
            return isAdmin;
        }

        public void setAdmin(boolean admin) {
            isAdmin = admin;
        }

        public Money getFilterPrice() {
            return filterPrice;
        }

        public void setFilterPrice(int filterPrice) {
            this.filterPrice = new Money(filterPrice);
        }

        public boolean getFilterOver() {
            return filterOver;
        }

        public void setFilterOver(boolean filterOver) {
            this.filterOver = filterOver;
        }
    }

    //// 비즈니스 로직 ////
    final int SCAN_CATEGORY_MAX = 3;

    public void mainScreen(Scanner sc, UiController ui) {

        System.out.println("\n--[ 실시간 커머스 플랫폼 ]--");
        System.out.println("1.전자제품");
        System.out.println("2.의류");
        System.out.println("3.식품");
        System.out.println("0.종료");
        System.out.println("----[ 주문 관리 ]----");
        System.out.println("4.장바구니 확인");
        System.out.println("5.주문 확인");
        System.out.println("----[ 관리자 모드 ]----");
        System.out.println("6.관리자 모드");
        System.out.println("----------------------");
        int scan = sc.nextInt();

        if (scan == 0) ui.setExit();

        else if (scan <= SCAN_CATEGORY_MAX) {
            ui.setScreen(UiController.Screen.CATEGORY_FILTER);
            ui.setCategory(scan);
        }

        else if (scan == 4) {
            ui.setScreen(UiController.Screen.SHOPPING_CART);
        }

        else if (scan == 5) {
            ui.setScreen(UiController.Screen.ORDER);
        }

        else if (scan == 6) {
            ui.setScreen(UiController.Screen.ADMIN);
        }
    }

    public void categoryFilterScreen(Scanner sc, UiController ui) {

        Category.Categories categoryEnum = Category.Categories.getCategoryByNumber(ui.getCategory());

        System.out.println("\n--[ " + categoryEnum.getString() + " 카테고리 ]--------");
        System.out.println("1. 전체 상품 보기");
        System.out.println("2. 가격대 별 필터링");
        System.out.println("0. 뒤로 가기");

        int scan = sc.nextInt();

        if (scan == 0) {
            ui.setScreen(UiController.Screen.MAIN);
        }
        else if (scan == 2) {
            System.out.println("\n가격 기준을 입력해주세요.");
            scan = sc.nextInt();
            ui.setFilterPrice(scan);

            System.out.println("\n1.초과    2.이하");
            scan = sc.nextInt();
            ui.setFilterOver(scan == 1);

            ui.setScreen(UiController.Screen.CATEGORY);
            ui.setScanInt(2);
        }
        else {
            ui.setScreen(UiController.Screen.CATEGORY);
            ui.setScanInt(scan);
        }
    }

    public void categoryScreen(Scanner sc, UiController ui) {

        Category category = Category.getInstance();

        Category.Categories categoryEnum = Category.Categories.getCategoryByNumber(ui.getCategory());

        List<Product> products = new ArrayList<>();
        if (ui.getScanInt() == 2 ) {
            products = category.getProductsByCategoryWithFilter(categoryEnum, ui.getFilterPrice(), ui.getFilterOver());
        }
        else {
            products = category.getProductsByCategory(categoryEnum);
        }

        System.out.println("\n--[ " + categoryEnum.getString() + " 카테고리 ]--------");
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            System.out.println((i + 1) + ". "
                    + product.getName() + "  /  "
                    + product.getPrice().getAmountString() + "원  /  "
                    + product.getContents() + "  /  재고 : "
                    + product.getStock() + "개");
        }
        System.out.println("------------------------------");
        System.out.println("0. 뒤로가기");

        int scan = sc.nextInt();

        if (scan == 0) {
            ui.setScreen(UiController.Screen.MAIN);
        }
        else {
            ui.setScreen(UiController.Screen.PRODUCT);
            ui.setScanInt(scan);
        }
    }

    public void productScreen(Scanner sc, UiController ui, Customer customer) {

        Category category = Category.getInstance();
        Category.Categories categoryEnum = Category.Categories.getCategoryByNumber(ui.getCategory());
        List<Product> products = category.getProductsByCategory(categoryEnum);

        Product product = products.get(ui.getScanInt() - 1);

        System.out.println("\n----[ 선택한 상품 ]---- \n - "
                + product.getName() + "  /  "
                + product.getPrice().getAmountString() + "원  /  "
                + product.getContents() + "  /  재고 : "
                + product.getStock() + "개 \n");

        System.out.println("위 상품을 장바구니에 추가하시겠습니까?");
        System.out.println("1.확인        2.취소" );

        int scan = sc.nextInt();

        if (scan == 1) {
            customer.getShoppingCart().addProducts(product, 1);
            System.out.println("장바구니에 [" + product.getName() + "]이(가) 추가되었습니다.");
            System.out.println("1. 뒤로 가기");
            scan = sc.nextInt();
        }
        ui.setScreen(UiController.Screen.CATEGORY);
    }

    public void shoppingCartScreen(Scanner sc, UiController ui, Customer customer) {

        OrderManager orderManager = OrderManager.getInstance();
        Map<Product, Integer> shoppingCartMap = customer.getShoppingCart().getProducts();
        String totalPrice = customer.getShoppingCart().getTotalPrice().getAmountString();

        if (shoppingCartMap.isEmpty()) {
            System.out.println("\n장바구니가 비었습니다.");
            ui.setScreen(UiController.Screen.MAIN);
            System.out.println("1. 뒤로 가기");
            int scan = sc.nextInt();
            return;
        }

        System.out.println("[ 장바구니 내역 ]");
        System.out.println("--------------------------------------------------");
        for (Map.Entry<Product, Integer> entry : shoppingCartMap.entrySet()) {
            System.out.println(entry.getKey().getName() + "  /  "
                    + entry.getKey().getPrice().getAmountString() + "원  /  "
                    + entry.getKey().getContents() + "  /  수량: "
                    + entry.getValue() + "개  /  재고: "
                    + entry.getKey().getStock() + "개");
        }
        System.out.println("--------------------------------------------------");
        System.out.println("\n[ 총 주문 금액 ]");
        System.out.println(totalPrice + "원");
        System.out.println("\n1. 주문 확정    2.상품 제거    3. 장바구니 비우기    4. 메인으로 돌아가기");

        int functionScan = sc.nextInt();
        switch (functionScan) {
            case 1 -> {
                System.out.println("\n고객 등급을 입력해주세요.");
                for (int i = 0; i < Customer.Grade.values().length; i++) {
                    Customer.Grade grade = Customer.Grade.values()[i];
                    System.out.println(i+1 + ". " + grade.getString() + " :  " + grade.getDiscount() + "% 할인");
                }
                functionScan = sc.nextInt();

                customer.setGrade(Customer.Grade.values()[functionScan-1]);

                Order order = orderManager.order(customer);

                System.out.println("\n주문이 완료되었습니다.");
                System.out.println("할인 전 금액: " + order.getOriginPrice().getAmountString() +"원");
                for (Discount discount : order.getDiscountList()) {
                    System.out.println(discount.name() + "(" + discount.rate() + "%): " + "-"
                            + discount.discountMoney().getAmountString() + "원");
                }
                System.out.println("최종 결제 금액: " + order.getTotalPrice().getAmountString() +"원");

                for (Map.Entry<Product, Integer> entry : shoppingCartMap.entrySet()) {
                    System.out.println(entry.getKey().getName() + "의 재고가 "
                            + (entry.getKey().getStock()+entry.getValue()) + "개 -> "
                            + entry.getKey().getStock()
                            + "개로 업데이트 됐습니다.");
                }
                System.out.println("1. 뒤로 가기");
                int scan = sc.nextInt();
            }
            case 2 -> {
                System.out.println("\n제거할 상품의 이름을 입력해주세요.");
                String temp = sc.nextLine();
                String scanStr = sc.nextLine();

                customer.getShoppingCart().removeProducts(scanStr);

                System.out.println("\n장바구니에서 [" + scanStr + "] 상품이 제거되었습니다.");
                System.out.println("1. 뒤로 가기");
                int scan = sc.nextInt();
            }
            case 3 -> {
                customer.getShoppingCart().clearProducts();
                System.out.println("장바구니를 비웠습니다.");
                System.out.println("1. 뒤로 가기");
                int scan = sc.nextInt();
            }
        }

        ui.setScreen(UiController.Screen.MAIN);
    }

    public void orderScreen(Scanner sc, UiController ui, Customer customer) {

        OrderManager orderManager = OrderManager.getInstance();
        List<Order> orderList = orderManager.getOrderListByUserId(customer.getId());
        System.out.println("[ 주문 내역 ]");
        for (int i = 0; i < orderList.size(); i++){
            Order order = orderList.get(i);
            System.out.println(i+1 + "번 주문 : " + order.getDateString() + " ");
            System.out.println("--------------------------------------------------");
            for (Map.Entry<Product, Integer> entry : order.getProducts().entrySet()) {
                System.out.println(" - " + entry.getKey().getName() + "  /  "
                        + entry.getKey().getPrice().getAmountString() + "원  /  "
                        + entry.getKey().getContents() + "  /  수량: "
                        + entry.getValue() + "개  /  재고: "
                        + entry.getKey().getStock() + "개");
            }
            System.out.println("--------------------------------------------------");
            System.out.println("총 금액 : " + order.getTotalPrice().getAmountString() + "원");
            System.out.println("주문 상태 : " + order.getState().getString() + "\n");
        }
        System.out.println("1. 주문 취소    2. 메인으로 돌아가기");
        int scan = sc.nextInt();

        switch (scan) {
            case 1 -> {
                if (orderList.isEmpty()) {
                    System.out.println("취소할 주문이 없습니다.");
                }
                else {
                    System.out.println("몇 번 주문을 취소할까요?");
                    scan = sc.nextInt();
                    orderManager.cancelOrder(orderList.get(scan-1));
                    System.out.println("주문이 취소되었습니다.");
                }
            }
        }
        ui.setScreen(UiController.Screen.MAIN);
    }

    final int ADMIN_PASSWORD_CHANCE = 3;
    final String ADMIN_PASSWORD = "admin123";

    public void adminScreen(Scanner sc, UiController ui) {

        if (!ui.getAdmin()) {
            System.out.println("\n관리자 비밀번호를 입력해주세요.");

            //// 비밀번호 체크 ////
            for (int i = 0; i < ADMIN_PASSWORD_CHANCE; i++) {

                int chance = ADMIN_PASSWORD_CHANCE - i - 1;
                String scanString = sc.next();

                if (scanString.equals(ADMIN_PASSWORD)) {
                    ui.setAdmin(true);
                    break;
                }
                else if (chance > 0){
                    System.out.println("틀렸습니다! 관리자 비밀번호를 입력해주세요. ( " + chance + "회 남음 )");
                }
            }

            if (!ui.getAdmin()) {
                System.out.println("\n관리자 인증에 실패했습니다.");
                System.out.println("1. 뒤로 가기");
                int scan = sc.nextInt();

                ui.setScreen(UiController.Screen.MAIN);
                return;
            }
        }

        //// 관리자 모드 ////
        System.out.println("\n----[관리자 모드]----");
        System.out.println("1. 상품 추가");
        System.out.println("2. 상품 수정");
        System.out.println("3. 상품 삭제");
        System.out.println("4. 전체 상품 현황");
        System.out.println("0. 메인으로 돌아가기");

        int scan =  sc.nextInt();

        switch (scan) {
            case 0 -> {
                ui.setScreen(UiController.Screen.MAIN);
                return;
            }
            case 1 -> {
                ui.setScreen(UiController.Screen.ADMIN_PRODUCT_ADD);
                return;
            }
            case 2 -> {
                ui.setScreen(UiController.Screen.ADMIN_PRODUCT_UPDATE);
                return;
            }
            case 3 -> {
                ui.setScreen(UiController.Screen.ADMIN_PRODUCT_DELETE);
                return;
            }
            case 4 -> {
                ui.setScreen(UiController.Screen.ADMIN_ALL_PRODUCT_VIEW);
                return;
            }
        }

        ui.setScreen(UiController.Screen.MAIN);
    }

    public void adminProductAddScreen(Scanner sc, UiController ui, DecimalFormat df) {

        System.out.println("\n어느 카테고리에 상품을 추가하겠습니까?");
        System.out.println("1. 전자제품");
        System.out.println("2. 의류");
        System.out.println("3. 식품");

        int scan =  sc.nextInt();
        String dummy = sc.nextLine();

        Category.Categories categoryEnum = Category.Categories.getCategoryByNumber(scan);

        System.out.println("[ " + categoryEnum.getString() +" 카테고리에 상품 추가");
        System.out.println("상품명을 입력해주세요.");
        dummy = sc.nextLine();
        String productName =  sc.nextLine();
        System.out.println("가격을 입력해주세요.");
        int productPrice =  sc.nextInt();
        System.out.println("상품 설명을 입력해주세요.");
        dummy = sc.nextLine();
        String productContents =  sc.nextLine();
        System.out.println("재고수량을 입력해주세요.");
        int productStock =  sc.nextInt();


        System.out.println(" - " + productName + "  /  "
                + df.format(productPrice) + "원  /  "
                + productContents + "  /  재고: "
                + productStock + "개");

        System.out.println("위 정보로 상품을 추가하시겠습니까?");
        System.out.println("1. 확인    2.취소");

        scan =  sc.nextInt();

        switch (scan) {
            case 1 -> {
                Category category = Category.getInstance();

                List<Product> nameDupleProductList = category.getProductsByCategory(categoryEnum).stream()
                        .filter(x -> x.getName().equals(productName))
                        .toList();

                if (!nameDupleProductList.isEmpty()) {
                    System.out.println("카테고리 내에 같은 이름의 상품이 존재합니다.");
                    ui.setScreen(UiController.Screen.ADMIN);
                    return;
                }

                category.addProduct(categoryEnum, new Product(productName, productPrice, productContents, productStock));

                System.out.println("\n상품이 성공적으로 추가되었습니다!");
            }
        }

        ui.setScreen(UiController.Screen.ADMIN);
    }

    public void adminProductUpdateScreen(Scanner sc, UiController ui, DecimalFormat df) {

        System.out.println("\n수정할 상품명을 입력해주세요.");
        String productName = sc.nextLine();
        productName = sc.nextLine();

        Category category = Category.getInstance();
        List<Product> productList = category.getProductsByName(productName);
        if (productList.isEmpty()) {
            System.out.println("\n상품을 찾을 수 없습니다.");
            ui.setScreen(UiController.Screen.ADMIN);
            return;
        }

        System.out.println("\n수정 할 상품을 선택해주세요.");
        for (int i = 0; i < productList.size(); i++) {
            System.out.println(i+1 + ". [" +category.getCategory(productList.get(i)) + "] "
                    + productList.get(i).getName() + "  /  "
                    + productList.get(i).getPrice().getAmountString() + "원  /  "
                    + productList.get(i).getContents() + "  /  재고: "
                    + productList.get(i).getStock() + "개");
        }
        int scan = sc.nextInt();

        Product product = productList.get(scan-1);

        System.out.println("\n현재 상품 : ");
        printProduct(product);

        System.out.println("\n수정할 항목을 선택해주세요.");
        System.out.println("1. 가격");
        System.out.println("2. 섫명");
        System.out.println("3. 재고수량");
        scan = sc.nextInt();

        switch (scan) {
            case 1 -> {
                System.out.println("\n현재 가격 : " + product.getPrice().getAmountString() + "원");
                System.out.print("새로운 가격을 입력해주세요 : ");
                int newPrice = sc.nextInt();

                Money oldPrice = product.getPrice();
                product.setPrice(newPrice);
                System.out.println("\n" + product.getName() + "의 가격이 "
                        + oldPrice.getAmountString() + "원 -> " + product.getPrice().getAmountString()
                        + "원으로 수정되었습니다.");
            }
            case 2 -> {
                System.out.println("\n현재 설명 : " + product.getContents());
                System.out.print("새로운 설명을 입력해주세요 : ");
                String newContents = sc.next();

                String oldContents = product.getContents();
                product.setContents(newContents);
                System.out.println("\n" + product.getName() + "의 설명이 {"
                        + oldContents + "} -> {" + product.getContents()
                        + "} 으로 수정되었습니다.");
            }
            case 3 -> {
                System.out.println("\n현재 재고 : " + product.getStock() + "개");
                System.out.print("새로운 재고를 입력해주세요 : ");
                int newStock = sc.nextInt();

                int oldStock = product.getStock();
                product.setStock(newStock);
                System.out.println("\n" + product.getName() + "의 재고가 "
                        + oldStock + "개 -> " + product.getStock()
                        + "개로 수정되었습니다.");
            }
        }

        ui.setScreen(UiController.Screen.ADMIN);
    }

    public void adminProductDeleteScreen(Scanner sc, UiController ui) {

        System.out.println("\n삭제할 상품명을 입력해주세요.");
        String productName = sc.nextLine();
        productName = sc.nextLine();

        Category category = Category.getInstance();
        List<Product> productList = category.getProductsByName(productName);
        if (productList.isEmpty()) {
            System.out.println("\n상품을 찾을 수 없습니다.\n");
            ui.setScreen(UiController.Screen.ADMIN);
            return;
        }

        System.out.println("\n삭제 할 상품을 선택해주세요.");
        for (int i = 0; i < productList.size(); i++) {
            System.out.println(i+1 + ". [" +category.getCategory(productList.get(i)).getString() + "] "
                    + productList.get(i).getName() + "  /  "
                    + productList.get(i).getPrice().getAmountString() + "원  /  "
                    + productList.get(i).getContents() + "  /  재고: "
                    + productList.get(i).getStock() + "개");
        }
        int scan = sc.nextInt();

        Product product = productList.get(scan-1);

        System.out.println("\n현재 상품:");
        printProduct(product);

        System.out.println("\n정말 삭제하시겠습니까?");
        System.out.println("1.확인    2.취소");
        scan = sc.nextInt();

        switch (scan) {
            case 1 -> {
                category.deleteProduct(product);
                System.out.println("\n" + product.getName() + "이 삭제되었습니다.");
            }
        }

        ui.setScreen(UiController.Screen.ADMIN);
    }

    public void adminAllProductViewScreen(Scanner sc, UiController ui) {

        System.out.println("\n[모든 상품 리스트]");

        Category category = Category.getInstance();
        Map<Category.Categories, List<Product>> productList = category.getCategoryProducts();
        if (productList.isEmpty()) {
            System.out.println("\n상품을 찾을 수 없습니다.\n");
            ui.setScreen(UiController.Screen.ADMIN);
            return;
        }

        for (Map.Entry<Category.Categories, List<Product>> entry : productList.entrySet()) {

            for (Product product : entry.getValue()) {
                System.out.println("ID: " + product.getId() + "  /  ["
                        + entry.getKey().getString() + "]  /  "
                        + product.getName() + "  /  "
                        + product.getPrice().getAmountString() + "원  /  "
                        + product.getContents() + "  /  재고: "
                        + product.getStock() + "개");
            }
        }
        System.out.println("\n1.뒤로 가기");
        int scan = sc.nextInt();

        switch (scan) {
            case 1 -> {
                ui.setScreen(UiController.Screen.ADMIN);
            }
        }

        ui.setScreen(UiController.Screen.ADMIN);
    }

    public void printProduct(Product product) {

        if (product == null) {
            throw new IllegalArgumentException("상품을 찾을 수 없습니다.");
        }

        System.out.println(" - " + product.getName() + "  /  "
                + product.getPrice().getAmountString() + "원  /  "
                + product.getContents() + "  /  재고: "
                + product.getStock() + "개");
    }

    public void start(Customer customer) {
        Scanner sc = new Scanner(System.in);
        UiController ui = new UiController();
        DecimalFormat df = new DecimalFormat("###,###");

        //// 콘솔 OUT 시작 ////
        while (!ui.getExit()) {
            try {
                switch (ui.getScreen()) {
                    case MAIN -> mainScreen(sc, ui);
                    case CATEGORY -> categoryScreen(sc,ui);
                    case CATEGORY_FILTER -> categoryFilterScreen(sc,ui);
                    case PRODUCT -> productScreen(sc, ui, customer);
                    case SHOPPING_CART -> shoppingCartScreen(sc, ui, customer);
                    case ORDER -> orderScreen(sc, ui, customer);
                    case ADMIN -> adminScreen(sc, ui);
                    case ADMIN_PRODUCT_ADD -> adminProductAddScreen(sc, ui, df);
                    case ADMIN_PRODUCT_UPDATE -> adminProductUpdateScreen(sc, ui, df);
                    case ADMIN_PRODUCT_DELETE -> adminProductDeleteScreen(sc, ui);
                    case ADMIN_ALL_PRODUCT_VIEW -> adminAllProductViewScreen(sc, ui);
                }
            } catch (Exception e) {
                System.out.println("\n ****" + e.getMessage() + "**** \n");
            }
        }
    }
}
