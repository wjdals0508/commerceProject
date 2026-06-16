
import Object.Product;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        DecimalFormat df = new DecimalFormat("###,###");

        List<Product> list = new ArrayList<>();

        Product galaxyS25 = new Product("Galaxy S25", 1200000, "최신 안드로이드 스마트폰", 50);
        list.add(galaxyS25);

        Product iPhone16 = new Product("iPhone 16", 1350000, "Apple의 최신 스마트폰", 50);
        list.add(iPhone16);

        Product macBookPro = new Product("MacBook Pro", 2400000, "M3 칩셋이 탑재된 노트북", 50);
        list.add(macBookPro);

        Product airPodPro = new Product("AirPod Pro", 350000, "노이즈 캔슬링 무선 이어폰", 50);
        list.add(airPodPro);

        //// 콘솔 OUT 시작 ////
        String functionScan = "";
        while (!functionScan.equals("0")) {
            System.out.println("[ 실시간 커머스 플랫폼 - 전자제품 ]");

            for (int i = 0; i < list.size(); i++) {
                Product product = list.get(i);
                System.out.println((i+1)+ ". "
                        + product.getName() + "  /  "
                        + df.format(product.getPrice()) + "원  /  "
                        + product.getContents());
            }

            System.out.println("0. 종료  /  프로그램 종료");

            functionScan =  sc.next();
            switch (functionScan) {
               // 입력 기능
            }
        }
    }
}