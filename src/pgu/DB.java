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

        ref2product.put(pA.reference, pA);
    }

}
