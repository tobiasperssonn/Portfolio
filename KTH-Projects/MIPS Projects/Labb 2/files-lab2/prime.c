/*
 prime.c
 By David Broman.
 Last modified: 2015-09-15
 This file is in the public domain.
*/


#include <stdio.h>

int is_prime(int n){
  if(n <= 1){
    return 0;
  }
  for (int i = 2; i < n; i++) // For-loopen kommer att kontrollera ifall värdet är delbart men något annat värde än 1 och sig själv
  {
    if (n % i == 0) // n % i ger 0 ifall det är delbart utan någon rest
    {
      return 0; // Om det är jämt delbart så returna med värdet 0
    }
  }
  return 1; // Om det är ett primtal så returna med värdet 1
}

int main(void){
  printf("%d\n", is_prime(1));  // 11 is a prime.      Should print 1.
  printf("%d\n", is_prime(383)); // 383 is a prime.     Should print 1.
  printf("%d\n", is_prime(987)); // 987 is not a prime. Should print 0.
}
