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

	for(uint64_t i = begin ; i <= end ; i++){
             cout << i << "\n";
        }

	return 0;

}