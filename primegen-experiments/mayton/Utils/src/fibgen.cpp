#include <iostream>
#include <algorithm>

using namespace std;

int main(int argc, char* argv[], char* env[]) {

	uint64_t begin  = 0L;
	uint64_t end    = 0x7FFFFFFFFFFFFFFFL;

        if (argc == 2){
            end   = atol(argv[1]);
        } else if (argc == 3 ) {
            begin = atol(argv[1]);
            end   = atol(argv[2]);
        }

        // 1 1 2 3 5 8

        uint64_t f1 = 1;
        uint64_t f2 = 1;
        uint64_t ftemp = 1;

	if (1 >= begin) {
              cout << "1\n";
        }

        while(f2 < end) {
             if (f2 >= begin) {
                  cout << f2 << "\n";
             }
             ftemp = f2 + f1;
	     f1 = f2;
             f2 = ftemp;
        }

	return 0;

}