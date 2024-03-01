/* main.c

   This file written 2015 by F Lundevall and David Broman

   Latest update 2015-09-15 by David Broman

   For copyright and licensing, see file COPYING */

#include <stddef.h>   /* Declarations of integer sizes and the like, part 1 */
#include <stdint.h>   /* Declarations of integer sizes and the like, part 2 */
#include <pic32mx.h>  /* Declarations of system-specific addresses etc */

void saveinfo(char s[], void * a, size_t ns );
void showinfo(void);
void u32init(void);

int gv; /* Global variable. */
int in = 3; /* Global variable, initialized to 4711. */

void fun(int param)
{
  param++;
  saveword( "AF1: param", &param );
  gv = param; /* Change the value of a global variable. */
}

/* This is the main function */
int main()
{
  /* Local variables. */
  int m;
  int * p; /* Declare p as pointer, so that p can hold an address. */
  char cs[ 9 ] = "Bonjour!";
  char * cp = cs; /* Declare cp as pointer, initialise cp to point to cs */

  /* Do some calculation. */
  gv = 4;
  m = gv + in; //--m = 7

  /* Check the addresses and values of some variables and stuff */
  saveword( "AM1: gv", &gv ); //--displayar texten addressen och värdet som finns i addressen
  saveword( "AM2: in", &in );
  saveword( "AM3: fun", &fun );
  saveword( "AM4: main", &main );

  p = &m;

  /* Check p and m */
  saveword( "AM5: p", &p );
  saveword( "AM6: m", &m );

  /* Change *p */

  *p = *p + 1;  //--m ökar med 1,   m = m + 1 --------  p = &m, p* = m.    m = 8

  /* Check p and m */
  saveword( "AM7: p", &p );  //--- borde displaya 8
  saveword( "AM8: m", &m );  //--- borde displaya addressen till m

  p = (int*)cp;   /* Casting a char pointer to an integer pointer */  //-- p = pointer till text strängen

  saveword( "AM9: p", &p );  //--- displayer addressen till charen för strängen i decimal format

  savebyte( "AM10: cs[0]", &cs[0] );  //--B
  savebyte( "AM11: cs[1]", &cs[1] );  //--o
  savebyte( "AM12: cs[2]", &cs[2] );  //--n
  savebyte( "AM13: cs[3]", &cs[3] );  //--j

  *p = 0x1234abcd; /* It starts to get interesting... */ //--text strängen ska anta värdet 0x1234abcd vilket motsvarar

  savebyte( "AM14: cs[0]", &cs[0] );  //--0xcd
  savebyte( "AM15: cs[1]", &cs[1] );  //--0xab
  savebyte( "AM16: cs[2]", &cs[2] );  //--0x34
  savebyte( "AM17: cs[3]", &cs[3] );  //--0x12

  fun(m); //--fun(8)
  //-- gv = 9
  // -- output af1 address till m och värdet 9

  /* Re-check the addresses and values of m and gv */
  saveword( "AM18: m", &m );  //-----------------------------  m = 8
  saveword( "AM19: gv", &gv ); //----------------------------  gv = 9

  showinfo();
}
