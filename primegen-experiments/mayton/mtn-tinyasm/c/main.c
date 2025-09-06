#include <stdio.h>
#include "gcd-lcm.h"

int main() {
  int x = 1024;
  int y = 12;
  printf("GCD(%d,%d)=%d", x,y,gcd(x,y));
}