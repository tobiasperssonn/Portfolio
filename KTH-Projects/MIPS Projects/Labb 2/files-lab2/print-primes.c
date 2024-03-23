/*
 print-prime.c
 By David Broman.
 Last modified: 2023-02-07 by Tobias Persson
 This file is in the public domain.
*/


#include <stdio.h>
#include <stdlib.h>

#define COLUMNS 6

int counter = 0; //Deklarerar en global variabel som kan användas i alla funktoner

void print_number(int n){
  counter++; //En variabel som håller koll när ett radbryt skall skrivas ut
  printf("%10d ", n); //Skriver ut det inmatade talet
  if (counter == COLUMNS) //Gör ett radbryt ifall 6 tal har skrivits ut.
  {
    printf("\n");
    counter = 0;
  }
}

int is_prime(int n){
  for (int i = 2; i < n; i++) // For-loopen kommer att kontrollera ifall värdet är delbart men något annat värde än 1 och sig själv
  {
    if (n % i == 0) // n % i ger 0 ifall det är delbart utan någon rest
    {
      return 0; // Om det är jämt delbart så returna med värdet 0
    }
  }
  return 1; // Om det är ett primtal så returna med värdet 0
}

void print_primes(int n){
  for (int i = 2; i <= n; i++) //Printar alla prim tal från 2 till ända fram och til och med det angivna numret
  {
    if(is_prime(i) == 1) //Kontrollerar ifall numret är ett primtal
    {
      print_number(i); //kallar på funktionen som kommer skriva ut primtalet.
    }
  }
}

// 'argc' contains the number of program arguments, and
// 'argv' is an array of char pointers, where each
// char pointer points to a null-terminated string.
int main(int argc, char *argv[]){
  if(argc == 2)
    print_primes(atoi(argv[1]));
  else
    printf("Please state an interger number.\n");
  return 0;
}

 
