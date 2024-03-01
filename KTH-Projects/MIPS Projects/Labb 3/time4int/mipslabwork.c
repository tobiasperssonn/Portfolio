/* mipslabwork.c

   This file written 2015 by F Lundevall
   Updated 2017-04-21 by F Lundevall

   This file should be changed by YOU! So you must
   add comment(s) here with your name(s) and date(s):

   This file modified 2017-04-31 by Ture Teknolog 

   For copyright and licensing, see file COPYING */

#include <stdint.h>   /* Declarations of uint_32 and the like */
#include <pic32mx.h>  /* Declarations of system-specific addresses etc */
#include "mipslab.h"  /* Declatations for these labs */

int mytime = 0x5957;
int timeoutcount = 0;
int prime = 1234567;

char textstring[] = "text, more text, and even more text!";

volatile int* ledState = (volatile int*) 0xbf886100;  //Skapar en pekare till input/output väljaren för led lamporna, TRISE
volatile int* leds = (volatile int*) 0xbf886110; //PORTE


/* Interrupt Service Routine */
void user_isr( void ) { //Programmet hoppar hit när än interrupt som är påslagen aktiveras

  if(IFS(0) & 0x100){ //Denna while loop körs om bit 8 är 1 i IFS registret
    timeoutcount++;
    IFSCLR(0) = 0x100; //Sätter interrupt flaggan på bit 8 till 0, och startar därmed om timern
  }

  if (timeoutcount == 10)
  {
    time2string( textstring, mytime );
    display_string( 3, textstring );
    display_update();
    tick( &mytime );

    timeoutcount = 0;
  }
}

/* Lab-specific initialization goes here */
void labinit( void )
{
  *ledState &= ~0xff; //Sätter de 8 minst signifikanta bitsen till 0 vilket indikerar output
  *leds &= ~0xff; //Släcker alla led lampor när programmet initializerar

  TRISD |= 0xfe0; //Ändrar bits 11-5 till 1 vilket motsvarar input.   111111100000 = 0xfe0

  //PIC32 processorn har en frekvens på 80 MHz enligt dess produkt sida
  T2CON = 0x70; //Skalar ner frekvensen med 256 för att få ett mycket lägre värde på bit 6-4
  PR2 = (80000000 / 256) / 10; //använder att count wrappar på 10. 80 000 000/256/10 = 31250. Delar med 10 för att få 10 timeouts per sekund
  TMR2 = 0x0; //Initierar timern med värdet 0
  T2CONSET = 0x8000; //Sätter på timern. 0x8000 motsvarar en 1 på bit 15

  IECSET(0) = 0x100; //Enablar interrupten för timer 2
  IPCSET(2) = 0x1F; //Sätter högsta prioritet och subprioritet för timer 2 interrupten. T2IP och T2IS ligger bredvid varandra i IPC2

  enable_interrupt(); //Sätter igång interrupts globalt genom att köra ei instruktionen

  return;
}

/* This function is called repetitively from the main program */
void labwork( void ) {
  prime = nextprime( prime );
  display_string( 0, itoaconv( prime ) );
  display_update();
}

