package pgu;

import java.util.HashMap;

public class DB {

    public static HashMap<String, Product> ref2product = new HashMap<String, Product>();

    static {
        resetData();
    }

    public static void resetData() {

        ref2product.clear();

        final Product nexus4 = new Product();
        nexus4.depth = "9.1 mm";
        nexus4.designation = "Google nexus 4";
        nexus4.height = "133.9 mm";
        nexus4.reference = "google_nexus_4";
        nexus4.weight = "139 g";
        nexus4.width = "68.7 mm";
        nexus4.pictures.add("google-nexus.png");

        final Product piggyBank = new Product();
        piggyBank.depth = "11.1 inches";
        piggyBank.designation = "Fisher-Price Laugh & Learn: Learning Piggy Bank";
        piggyBank.height = "6.7 inches";
        piggyBank.reference = "piggy_bank";
        piggyBank.weight = "2.2 pounds";
        piggyBank.width = "6.7 inches";
        piggyBank.pictures.add("piggy-bank.png");

        final Product set = new Product();
        set.depth = "12 inches";
        set.designation = "Stanley 94-248 65-Piece General Homeowner's Tool Set";
        set.height = "3.9 inches";
        set.reference = "B000UHMITE";
        set.weight = "7.6 pounds";
        set.width = "12 inches";
        set.pictures.add("tool_set.png");

        ref2product.put(nexus4.reference, nexus4);
        ref2product.put(piggyBank.reference, piggyBank);
        ref2product.put(set.reference, set);
    }

}
