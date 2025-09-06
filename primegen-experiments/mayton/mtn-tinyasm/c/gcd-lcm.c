#include "gcd-lcm.h"

int gcd(int a, int b) {
  if (b != 0)
    return gcd(b, a % b);
  else
    return a;
}

int lcm(int a,int b) {
  return (a * b) % gcd(a,b);
}
