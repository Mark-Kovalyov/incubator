#include <iostream>     // std::cout
#include <algorithm>    // std::sort
#include <vector>       // std::vector

#include <string.h>

using namespace std;

int main(int argc, char* argv[], char* env[]) {

  if (argc < 2) {
	cerr << "\nPermutation Generator 1.0 (x64) (c) sql.ru, Aug 2017\n";
	cerr << "Usage:\n";
	cerr << " permutate <string> [ > outputFile.lst ]\n\n";
	exit(0);
  }

  int size = strlen(argv[1]);

  char* chars = new char[size];

  strncpy(chars, argv[1], size);

  do {

     for(int i = 0;i<size;i++) cout << chars[i];

     cout << "\n";

  } while ( next_permutation(chars,chars+size) );

  delete[] chars;

  return 0;

}