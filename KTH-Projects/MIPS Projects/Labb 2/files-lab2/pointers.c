


#include <stdio.h>
#include <stdlib.h>

char* text1 = "This is a string.";
char* text2 = "Yet another thing.";
int* list1;
int* list2;
int count = 0;

void copycodes(char* text, int* list, int* count){
  while (*text != 0) //Loopa ifall värdet på addressen som text pekar på inte är 0
  {
    *list = *text; //Kopiera och konvertera char värdet som finns vid addressen text till motsvarande int värd sparat i addressen vid list

    text += 1;  //Öka addressen för ditt text pekar med 1, detta kommer stega igenom varje char i text1 strängen 1 byte i taget
    list += 1;  //+ 1 motsvarar en ökning med 4 bytes när man ökar en int, eftersom en int är oftast 4 bytes.

    *count += 1;  //Öka värdet med 1 på det värde som finns på den address som count pekar på.
  }
}

void work(){
  copycodes(text1, list1, &count);
  copycodes(text2, list2, &count);
}

void printlist(const int* lst){
  printf("ASCII codes and corresponding characters.\n");
  while(*lst != 0){
    printf("0x%03X '%c' ", *lst, (char)*lst);
    lst++;
  }
  printf("\n");
}

void endian_proof(const char* c){
  printf("\nEndian experiment: 0x%02x,0x%02x,0x%02x,0x%02x\n", 
         (int)*c,(int)*(c+1), (int)*(c+2), (int)*(c+3));
  
}

int main(void){
  list1 = (int*) malloc(80); //Allokerar 80 bytes i minnet för list1 och list2
  list2 = (int*) malloc(80);

  work();

  printf("\nlist1: ");
  printlist(list1);
  printf("\nlist2: ");
  printlist(list2);
  printf("\nCount = %d\n", count);

  endian_proof((char*) &count);

  free(list1); //Frigör de allokerade minnet
  free(list2);
}
