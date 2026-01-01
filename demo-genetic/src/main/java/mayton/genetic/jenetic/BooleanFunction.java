package mayton.genetic.jenetic;

import java.util.HashMap;

public class BooleanFunction {

    HashMap<Character[], Character> truthTable;

    BooleanFunction(char[][] inputs, int xstart, int xend, int functionIndex) {
        truthTable = new HashMap<>();
        for (char[] row : inputs) {
            String key = "";
            truthTable.put(key.toCharArray(), row[row.length - 1]);
        }
    }

}
