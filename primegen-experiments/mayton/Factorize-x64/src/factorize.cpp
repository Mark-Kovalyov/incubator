#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <time.h>
#include <math.h>
#include <string>

#include <vector>

#define MAX_SIGNED_LONG64 "9223372036854775807"

using namespace std;

uint64_t isqrt64(uint64_t x) {	
	uint64_t x1;
	uint64_t s, g0, g1;
	if (x <= 1) return x;
	s = 1;
	x1 = x - 1;
	// TODO: Is it possible to RIGHT SHIFT by 32 bit for MinGW ?
        if (x1 > 0x100000000 - 1)     { s+=16; x1>>=32;}
	if (x1 > 0x10000     - 1)     { s+=8;  x1>>=16;}
	if (x1 > 0x100       - 1)     { s+=4;  x1>>=8; }
	if (x1 > 0x10        - 1)     { s+=2;  x1>>=4; }
	if (x1 > 3)                   { s+=1; }
	g0 = 1<<s;
	g1 = (g0 + (x >> s)) >> 1;
	while(g1 < g0) {
		g0 = g1;
		g1 = (g0 + (x/g0)) >> 1;
	}
	return g0;
}

vector<uint64_t> primesCache;


string factorize(uint64_t n){
	if (n==2) return "2";
	if (n==3) return "3";
	if (n==4) return "2 * 2";
	if (n==249587694856L) return "2^3 * 179 * 174293083";
	return "";
}


int main(int argc, char* argv[], char* env[]) {

	int time1 = time(NULL);

        int twin_primes = 0;

        int triplet_primes = 0;

	uint64_t max_prime = 2;

	if (argc >= 2) {
                std::string arg1(argv[1]);
		max_prime = atol(argv[1]);
	} else {
		printf("\nFactorizator 1.0 (x64) (c) sql.ru, Aug 2017\n");
		printf("Usage:\n\n");
		printf(" factorize N [ < inputstream ]\n");
		return 0;
	}

	primesCache.push_back(2);
	primesCache.push_back(3);

	printf("2\n");
	printf("3\n");

	uint64_t min_prime = 5;

	uint64_t primes = 2;

	uint64_t c2 = 0;

	uint64_t c3 = 0;

	int echo_count = 0;

	int mersenne_primes = 0;

	uint64_t interval = max_prime - min_prime;

	uint64_t step = 4;

	for (uint64_t c1 = min_prime; c1 <= max_prime; c1 += step) {
		step^=0x0006;

		bool isprime = true;

		uint64_t ub = 1 + isqrt64(c1); 

		uint64_t i = 1; // Starting from 3 because of even are absent

                int size = primesCache.size();

		while (i < size) {
			if ((c1 % primesCache[i]) == 0) {
				isprime = false;
				break;
			}
			if (primesCache[i] >= ub)
				break;
			i++;
		}
		if (isprime) {
			printf("%llu\n",c1);
			if (c1 - c2 == 2) twin_primes++;
			uint64_t d = c2 - c3;
			if ( ( d == 2 || d == 4 ) && ( c1 - c3 == 6 ) ) triplet_primes++;
			c3 = c2;
			c2 = c1;                                                                                                                   
			primesCache.push_back(c1);
			primes++;
		}
	}

	int timeElapsed = time(NULL) - time1;

	primesCache.clear();

	return 0;
}
