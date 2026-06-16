
import Object.Product;
import System.CommerceSystem;

public class Main {
    public static void main(String[] args) {

        CommerceSystem commerceSystem = new CommerceSystem();

        Product galaxyS25 = new Product("Galaxy S25", 1200000, "최신 안드로이드 스마트폰", 50);
        commerceSystem.addProduct(galaxyS25);

        Product iPhone16 = new Product("iPhone 16", 1350000, "Apple의 최신 스마트폰", 50);
        commerceSystem.addProduct(iPhone16);

        Product macBookPro = new Product("MacBook Pro", 2400000, "M3 칩셋이 탑재된 노트북", 50);
        commerceSystem.addProduct(macBookPro);

        Product airPodPro = new Product("AirPod Pro", 350000, "노이즈 캔슬링 무선 이어폰", 50);
        commerceSystem.addProduct(airPodPro);

        commerceSystem.start();
    }
}