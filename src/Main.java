
import Manager.Category;
import Manager.OrderManager;
import Model.*;
import System.CommerceSystem;

public class Main {
    public static void main(String[] args) {
        Category category = Category.getInstance();
        CommerceSystem commerceSystem = CommerceSystem.getInstance();

        Product galaxyS25 = new Product("Galaxy S25", 1200000, "최신 안드로이드 스마트폰", 0);
        category.addProduct(Category.Categories.ELECTRONIC_DEVICES, galaxyS25);

        Product iPhone16 = new Product("iPhone 16", 1350000, "Apple의 최신 스마트폰", 50);
        category.addProduct(Category.Categories.ELECTRONIC_DEVICES, iPhone16);

        Product macBookPro = new Product("MacBook Pro", 2400000, "M3 칩셋이 탑재된 노트북", 50);
        category.addProduct(Category.Categories.ELECTRONIC_DEVICES, macBookPro);

        Product airPodPro = new Product("AirPod Pro", 350000, "노이즈 캔슬링 무선 이어폰", 50);
        category.addProduct(Category.Categories.ELECTRONIC_DEVICES, airPodPro);

        Customer user = new Customer("jeongmin0508","윤정민", "wjdals2229@naver.com", "SILVER");

        commerceSystem.start(user);
    }
}