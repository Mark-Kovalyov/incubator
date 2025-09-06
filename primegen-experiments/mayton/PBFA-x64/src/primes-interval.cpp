#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <time.h>
#include <math.h>
#include <string>
#include <iostream>
#include <algorithm>
#include <vector>

using namespace std;

long double primesIntervalLongDouble(long double a, long double b) {
    return b / log(b) - a / log(a);
}

int main(int argc, char* argv[], char* env[]) {

	if (argc < 3) {
            long double dummy = 1.0;
	    printf("Primes-interval 1.0 (with %li bit of floating accuaracy)\n\n", 8 * sizeof(dummy));
	    printf(" Usage: primes-interval [a] [b]\n\n");
	    exit(0);
	} else {	
	    long double a = strtold(argv[1],NULL);
    	    long double b = strtold(argv[2],NULL);
	    long double primes = primesIntervalLongDouble(a,b);
	    printf("There are %.0Lf primes in range [%.0Lf..%.0Lf]\n", primes , a, b);
	}
	return 0;
}
