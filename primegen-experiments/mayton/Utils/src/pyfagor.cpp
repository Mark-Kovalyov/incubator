#include <stdio.h>
#include <stdlib.h>
#include <set>
#include <string>


// Greatest Common Divisor
inline int gcd(int a,int b){
  int c;
  while ( a != 0 ) {
     c = a; a = b%a;  b = c;
  }
  return b;
}

inline int relationPrime(int m,int n){
	return gcd(m,n)==1?1:0;
}


void insert(int a,int b,int c){
	printf("(%d,%d,%d)\n",a,b,c);	
}

// Stuped brute-force pyfagorean triangle generator
int main(int argc,char **argv){


	if (argc>1){
		int maxside = atoi(argv[1]);
		for(int i=1;i<maxside;i++){
		    int ii = i * i;
	            for(int j=1;j<i;j++){
			int jj = j * j;
                        int a = ii - jj;
                        int b = 2 * i * j;
			int c = ii + jj;
	   		if (relationPrime(i,j)) {
				int GCD = gcd(a,b);
				if (GCD != 1){
					insert(a/GCD,b/GCD,c/GCD);
					
				} else {
					insert(a,b,c);
				}
			}        
	            }
	            
	        }
		return 0;
	} else {
		fprintf(stderr,"Pyfagorean Triangle Generator 1.0 of 1 jan 2014, by Mayton\n\nUsage: pyfagor [N]\n\n");
		return 1;	
	}

	
	return 0;
}
