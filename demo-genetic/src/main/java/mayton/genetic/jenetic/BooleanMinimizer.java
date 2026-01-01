package mayton.genetic.jenetic;

import java.util.HashMap;

public class BooleanMinimizer {


    static char[][] input = {
            //i    x3   x2   x1   x0   y6   y5   y4   y3   y2   y1   y0
            {'0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'},
            {'1', '0', '0', '0', '1', '0'},
            {'2', '0', '0', '1', '0', '0'},
            {'3', '0', '0', '1', '1', '1'},
            {'4', '0', '1', '0', '0', '1'},
            {'5', '0', '1', '0', '1', '0'},
            {'6', '0', '1', '1', '0', '1'},
            {'7', '0', '1', '1', '1', '1'},
            {'8', '1', '0', '0', '0', '0'},
            {'9', '1', '0', '0', '1', '1'},
            {'a', '1', '0', '1', '0', '*'},
            {'b', '1', '0', '1', '1', '*'},
            {'c', '1', '1', '0', '0', '*'},
            {'d', '1', '1', '0', '1', '*'},
            {'e', '1', '1', '1', '0', '*'},
            {'f', '1', '1', '1', '1', '*'}
    };


    static final int NUM_FUNCTIONS = 7;

    static BooleanFunction[] generateFunctions(char[][] input) {
        BooleanFunction[] functions = new BooleanFunction[NUM_FUNCTIONS];

        for(int i = 0; i < NUM_FUNCTIONS; i++) {
            functions[i] = new BooleanFunction(input, 1, 4, i);
        }
        
        return functions;
    }


    static void main() {
        new BooleanMinimizer();

    }

}
