package pgu;

import java.util.HashMap;

public class DB {

    public static HashMap<String, Product> ref2product = new HashMap<String, Product>();

    static {

        final Product pA = new Product();
        pA.depth = "10''";
        pA.designation = "Product for you";
        pA.height = "20''";
        pA.reference = "AAA";
        pA.weight = "30 Kg";
        pA.width = "40''";

        final Product pB = new Product();
        pB.depth = "15''";
        pB.designation = "Product for us";
        pB.height = "25''";
        pB.reference = "BBB";
        pB.weight = "35 Kg";
        pB.width = "45''";

        ref2product.put(pA.reference, pA);
        ref2product.put(pB.reference, pB);
    }

}
