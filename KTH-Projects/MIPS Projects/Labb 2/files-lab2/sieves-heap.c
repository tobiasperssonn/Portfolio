#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>

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

void print_sieves(int max){
    bool* numbers = (bool*) malloc(max); //Allokerar max antal bytes i minnet genom heap
    int i = 0;

    for (i = 0; i < max; i++) //sätter alla värden i arrayen till 1
    {
        numbers[i] = true;
    }

    for (i = 2; i < max; i++)
    {   
        if (numbers[i-1] == true) //Vi hoppar över att kolla mutiplerna på de tal som vi redan markerat som ej prim
        {
            for (int j = i+i; j <= max; j += i) //Vi sätter j som i+i eftersom vi inte vill markera primnumret under första körningen
            {
                numbers[j-1] = false; //-1 eftersom arrayen går från 0 till max-1, dvs nummer 6 motsvarar element 5 i arrayen
            }
        }       
    }

    for (i = 2; i <= max; i++)
    {
        if (numbers[i-1] == true) //Skriv bara ut de tal som har sitt motsvarade element i arrayen markerad som 1
        {
            print_number(i); //+1 eftersom vi inte räknar med 0
        }
        
    }

    free(numbers);
}

// 'argc' contains the number of program arguments, and
// 'argv' is an array of char pointers, where each
// char pointer points to a null-terminated string.
int main(int argc, char *argv[]){
  if(argc == 2)
    print_sieves(atoi(argv[1]));
  else
    printf("Please state an interger number.\n");
  return 0;
}
