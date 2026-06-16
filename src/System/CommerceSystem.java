package System;

import Object.Product;
import Object.Category;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Scanner;

public class CommerceSystem {

    public CommerceSystem() {
    }

    public void start(Category category) {
        Scanner sc = new Scanner(System.in);
        DecimalFormat df = new DecimalFormat("###,###");

        //// 콘솔 OUT 시작 ////
        while (true) {
            try {
                System.out.println("[ 실시간 커머스 플랫폼 - 전자제품 ]");
                System.out.println("1.전자제품");
                System.out.println("2.의류");
                System.out.println("3.식품");
                System.out.println("0.종료");
                int functionScan = sc.nextInt();
                if (functionScan == 0) break;

                Category.Categories categoryEnum = Category.Categories.getCategoryByNumber(functionScan);
                List<Product> products = category.getProducts(categoryEnum);

                System.out.println("[ " + categoryEnum.getString() + " 카테고리 ]");
                for (int i = 0; i < products.size(); i++) {
                    Product product = products.get(i);
                    System.out.println((i + 1) + ". "
                            + product.getName() + "  /  "
                            + df.format(product.getPrice()) + "원  /  "
                            + product.getContents());
                }
                System.out.println("0. 뒤로가기");

                functionScan = sc.nextInt();

                if (functionScan == 0) continue;

                int productNumber = functionScan;
                Product product = products.get(productNumber - 1);

                System.out.println("<선택한 상품> : "
                        + product.getName() + "  /  "
                        + df.format(product.getPrice()) + "원  /  "
                        + product.getContents() + "  /  재고 : "
                        + product.getStock() + "개 \n");

            } catch (Exception e) {
                System.out.println("유효하지 않은 요청입니다.\n");
            }
        }
    }
}
