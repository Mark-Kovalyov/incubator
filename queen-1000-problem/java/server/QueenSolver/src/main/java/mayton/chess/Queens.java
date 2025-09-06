package mayton.chess;

import static java.lang.System.out;

/******************************************************************************
 *  Compilation:  javac Queens.java
 *  Execution:    java Queens n
 *
 *  Solve the 8 queens problem using recursion and backtracing.
 *  Prints out all solutions.
 *
 *  Limitations: works for n <= 25, but slows down considerably
 *  for larger n.
 *
 *  Remark: this program implicitly enumerates all n^n possible
 *  placements (instead of n!), but the backtracing prunes off
 *  most of them, so it's not necessarily worth the extra
 *  complication of enumerating only permutations.
 *
 *
 *  % java Queens 3
 *
 *  % java Queens 4
 *  * Q * * 
 *  * * * Q 
 *  Q * * * 
 *  * * Q * 
 *
 *  * * Q * 
 *  Q * * * 
 *  * * * Q 
 *  * Q * * 
 *
 *  % java Queens 8
 *  Q * * * * * * * 
 *  * * * * Q * * * 
 *  * * * * * * * Q 
 *  * * * * * Q * * 
 *  * * Q * * * * * 
 *  * * * * * * Q * 
 *  * Q * * * * * * 
 *  * * * Q * * * * 
 *
 *  ...
 *
 ******************************************************************************/

public class Queens {

    /***************************************************************************
     * Return true if queen placement q[n] does not conflict with
     * other queens q[0] through q[n-1]
     ***************************************************************************/
    public static boolean isConsistent(int[] q, int n) {
        for (int i = 0; i < n; i++) {
            int qi = q[i];
            int qn = q[n];
            if (qi == qn) {
                return false;   // same column
            }
            int deltai = n - i;
            int deltaRow = qi - qn;
            if (deltaRow == deltai) {
                return false;   // same major diagonal
            }
            if (-deltaRow == deltai) {
                return false;   // same minor diagonal
            }
        }
        return true;
    }

    /***************************************************************************
     * Prints n-by-n placement of queens from permutation q in ASCII.
     ***************************************************************************/
    public static void printQueens(int[] q) {
        int n = q.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                out.print(q[i] == j ? "Q" : "*");
                out.print(" ");
            }
            out.println();
        }
        out.println();
    }


    /***************************************************************************
     *  Try all permutations using backtracking
     ***************************************************************************/
    public static void enumerate(int n) {
        int[] a = new int[n];
        enumerate(a, 0);
    }

    public static void enumerate(int[] q, int k) {
        int n = q.length;
        if (k == n) {
            printQueens(q);
        } else {
            for (int i = 0; i < n; i++) {
                q[k] = i;
                if (isConsistent(q, k)) {
                    enumerate(q, k + 1);
                }
            }
        }
    }


    public static void main(String[] args) {
        enumerate(18);
    }

}


