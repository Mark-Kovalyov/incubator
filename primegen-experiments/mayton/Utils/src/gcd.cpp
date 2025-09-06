#include <iostream>     // std::cout
#include <algorithm>    // std::sort
#include <vector>       // std::vector

#include <string.h>

#define MAX_INT64 "9223372036854775807"

using namespace std;

// gcd(a,0) = 0
// gcd(a,b) = gcd(b,a mod b)
// gcd(a,a) = a
// gcd(a,b) = gcd(a - b,b), if (a>b)


uint64_t gcd(uint64_t a,uint64_t b){
  uint64_t c;
  while ( a != 0 ) {
     c = a; 
     a = b % a;  
     b = c;
  }
  return b;
}

/*
uint64_t gcd(uint64_t a,uint64_t b) {
    if (a == 0) return b;

    while (b != 0) {
        if (a > b)
            a = a - b;
        else
            b = b - a;
    }

    return a;
}*/

int main(int argc, char* argv[], char* env[]) {

  if (argc < 2) {
	cerr << "\nGeneral Common Divisor 1.0 (x64) (c) mayton 18-Feb 2018\n";
	cerr << "Usage:\n";
	cerr << " gcd a b\n\n";
	exit(0);
  }

  if (strlen(argv[1])>19 || strlen(argv[2])>19 || strcmp(argv[1],MAX_INT64)==1 || strcmp(argv[2],MAX_INT64)==1) {
        cerr << "Arguments " << argv[1] << ", " << argv[2] << " to large.";
        exit(1);
  }

  uint64_t a = atol(argv[1]);
  uint64_t b = atol(argv[2]);

  printf("%lu\n", gcd(a,b));  
  
  return 0;

}