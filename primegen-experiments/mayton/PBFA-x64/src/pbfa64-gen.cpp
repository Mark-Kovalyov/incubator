#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <time.h>
#include <math.h>
#include <string>
#include <iostream>
#include <algorithm>
#include <vector>

#define MAX_SIGNED_LONG64 "9223372036854775807"

//#define FLOATING_SQRT

//  Infinite Prime Generator 1.2 (c) sql.ru
// 
//  2009.05.02   -    Mayton
//
//  2009.05.02   -    Mayton   - увеличил разрядность основных типов данных до 64-bit
//                               необходимо проверить, как работает функция (uint64_t)sqrt((double)c1)
//                               Не будет-ли потери точности?
//
//                             - добавил console-like интерфейс
// 
//  2015.04.01   -    Mayton   - ported into MinGW (GCC 4.8.1) Win64 compiller
//  2015.04.02   -    Mayton   - added isqrt64(..) for integer square root. 
//                               added twin primes
//  2015.04.02   -    Mayton   - improovement speed of main loop. Added non linear step. 
//                               (Thnx to DimaT).
//  2015.04.04   -    Mayton   - added triplet count
//  2017.08.26   -    Mayton   - remove garbage, improove comments.
//  2017.08.27   -    Mayton   - added output formatting (hex, decimal, octal)
//  2019.03.16   -    Mayton   - fixed 'ns' usage. Removed sqrt() function for double. Remove unised functions.
// TODO: Fix more bugs!

using namespace std;

string toBin(uint64_t x) {
        string res = "";
        int i = 0;
        while(i<32) {
            if (x && 0x8000000000000000L ) break; 
            x >>= 1;
            i++;
        }
        while(i<32) {
            if (x && 0x8000000000000000L ) res+="1"; else res+="0"; 
            x >>= 1;
            i++;
        }
        return res;
}

bool allDigits(const string& s) {
    return all_of(s.begin(), s.end(), ::isdigit);
}

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

// TODO: Should be captured signal Ctlr+C to save state machine for contonuation ability.
int main(int argc, char* argv[], char* env[]) {


	int time1 = time(NULL);

        int twin_primes = 0;

        int triplet_primes = 0;

	uint64_t max_prime = 2;

	string format = "%llu";
        string splitter = "\n";
        bool noStat = false;

	if (argc >= 2) {
                string arg1(argv[1]);
                string max_signed_long64 = string(MAX_SIGNED_LONG64);
                if (arg1.length() > max_signed_long64.length() || !allDigits(arg1) || arg1 > max_signed_long64 ) {
                     fprintf(stderr, "The argument '%s' doesn't represent a number or is greather than maximum 64bit integer value. Please try less!", arg1.c_str());
                     return 0;
                }
                max_prime = atol(argv[1]);
                int argn = 2;
                while(argn < argc) {
                     string carg = argv[argn];
                     if (carg.substr(0,2) == "-o") {
                         if (carg == "-oh" ) { format = "%llX"; }
                         else if (carg == "-od" ) { format = "%llu"; }
                         else { 
                             fprintf(stderr, "Unknown argument '%s'. Please correct and try again", carg.c_str());
                             return 0;
                         }
                     } else if (carg.substr(0,2) == "-s") {
                         if (carg == "-sTAB") { 
                            splitter="\t"; 
                         } else if (carg == "-sEOL") { 
                            splitter="\n"; 
                         } else if (carg == "-sCOMMA") {
                            splitter=",";
                         } else if (carg == "-sSP") { 
                            splitter=" "; 
                         } else { 
                            // TODO: Complete for comma, e.t.c symbols 
                         }
                     } else if (carg == "-ns") {
                         noStat = true;
                     }
                     argn++;
                }
	} else {
		printf("\nInfinite Prime Generator 1.2 (x64) (c) sql.ru, Aug 2017\n");
		printf("Usage:\n\n");
		printf(" pbfa64 <maxPrime> [options] [ > outputFile.lst ]\n\n");
		printf("Where options are:\n");
		printf(" -o{b|d|h}             : format outpus as binary, or hex. Decimal is default.\n");
		printf(" -s{COMMA|EOL|TAB|SP} : splitter. Default is unix EOL ('\\n'). Aliases for\n");
                printf("                         space, tab e.t.c are accordingly.\n");
                printf(" -ns                   : suppress statistics report below\n");
                printf("\n");
		return 0;
	}

	primesCache.push_back(2);
	primesCache.push_back(3);

	string formatString = (format + splitter);

	printf(formatString.c_str(),2);
	printf(formatString.c_str(),3);

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
		if (++echo_count > 1000000) {
			fprintf(stderr, "Completed: %d %%\n",
					(int) (100.0 * (c1 - min_prime) / (double) interval));
			echo_count = 0;
		}

		bool isprime = true;
		// TODO: What is the reason to calculate SQRT every step? Should be replaced by linear approximation.
		uint64_t ub = 1 + isqrt64(c1); 
		
		uint64_t i = 1; // Starting from 3 because of even are absent

                int size = primesCache.size();

		while (i < size) {
			if (c1 % primesCache[i] == 0) {
				isprime = false;
				break;
			}
			if (primesCache[i] >= ub)
				break;
			i++;
		}
		if (isprime) {
			printf(formatString.c_str(),c1);
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

	if (!noStat) {
	    fprintf(stderr, "\n");
	    fprintf(stderr, "Primes detected      : %lu\n",           primes);
	    fprintf(stderr, "Twin primes          : %u\n",            twin_primes);
	    fprintf(stderr, "Triplet primes       : %i\n",            triplet_primes);
	    fprintf(stderr, "Range                : [2..%lu]\n",      max_prime);
	    fprintf(stderr, "Memory cache used    : %li K\n",         (sizeof(uint64_t ) * primesCache.capacity()) / 1024);
	    fprintf(stderr, "AVG speed generation : %lu units/sec\n", (timeElapsed == 0) ? 0 : primes / timeElapsed);
	    fprintf(stderr, "Elapsed time         : %i sec\n",       timeElapsed);
	}

	primesCache.clear();

	return 0;
}
