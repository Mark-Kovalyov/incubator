#include <stdio.h> 
#include <stdlib.h>
#include <signal.h>

long gcd(long a,long b){
    if (b!=0) return gcd(b, a % b); else return a;
}

long lcm(long a,long b){
    return labs((a * b)) / gcd(a,b);
}


