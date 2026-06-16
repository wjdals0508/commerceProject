package System;

import Object.Product;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommerceSystem {

    private final List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        products.add(product);
    }

    public void start() {
        Scanner sc = new Scanner(System.in);
        DecimalFormat df = new DecimalFormat("###,###");

        //// 콘솔 OUT 시작 ////
        String functionScan = "";
        while (!functionScan.equals("0")) {
            System.out.println("[ 실시간 커머스 플랫폼 - 전자제품 ]");

            for (int i = 0; i < products.size(); i++) {
                Product product = products.get(i);
                System.out.println((i+1)+ ". "
                        + product.getName() + "  /  "
                        + df.format(product.getPrice()) + "원  /  "
                        + product.getContents());
            }

            System.out.println("0. 종료  /  프로그램 종료");

            functionScan =  sc.next();
        }


    }
}
