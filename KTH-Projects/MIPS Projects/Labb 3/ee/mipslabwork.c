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

char textstring[] = "text, more text, and even more text!";

volatile int* ledState = (volatile int*) 0xbf886100;  //Skapar en pekare till input/output väljaren för led lamporna
volatile int* leds = (volatile int*) 0xbf886110;


/* Interrupt Service Routine */
void user_isr( void )
{
  return;
}

int i2c_send(uint8_t data) {
	i2c_idle();
	I2C1TRN = data;
	i2c_idle();
	return !(I2C1STAT & (1 << 15)); //ACKSTAT
}

/* Receive one byte from I2C bus */
uint8_t i2c_recv() {
	i2c_idle();
	I2C1CONSET = 1 << 3; //RCEN = 1
	i2c_idle();
	I2C1STATCLR = 1 << 6; //I2COV = 0
	return I2C1RCV;
}

/* Send acknowledge conditon on the bus */
void i2c_ack() {
	i2c_idle();
	I2C1CONCLR = 1 << 5; //ACKDT = 0
	I2C1CONSET = 1 << 4; //ACKEN = 1
}

/* Send not-acknowledge conditon on the bus */
void i2c_nack() {
	i2c_idle();
	I2C1CONSET = 1 << 5; //ACKDT = 1
	I2C1CONSET = 1 << 4; //ACKEN = 1
}

/* Send start conditon on the bus */
void i2c_start() {
	i2c_idle();
	I2C1CONSET = 1 << 0; //SEN
	i2c_idle();
}

/* Send restart conditon on the bus */
void i2c_restart() {
	i2c_idle();
	I2C1CONSET = 1 << 1; //RSEN
	i2c_idle();
}

/* Send stop conditon on the bus */
void i2c_stop() {
	i2c_idle();
	I2C1CONSET = 1 << 2; //PEN
	i2c_idle();
}


/* Lab-specific initialization goes here */
void labinit( void )
{
  *ledState &= ~0xff; //Sätter de 8 minst signifikanta bitsen till 0 vilket indikerar output
  *leds &= ~0xff; //Släcker alla led lampor när programmet initializerar

  TRISD |= 0xfe0; //Ändrar bits 11-5 till 1 vilket motsvarar input.   111111100000 = 0xfe0. |= gör så att bara dessa bits ändras

  //PIC32 processorn har en frekvens på 80 MHz enligt dess produkt sida
  T2CON = 0x70; //Skalar ner frekvensen med 256 för att få ett mycket lägre värde på bit 6-4, och säter resen av alla bits till 0 vilket stannar timern
  PR2 = (80000000 / 256) / 10; //använder att count wrappar på 10. 80 000 000/256/10 = 31250. Delar med 10 för att få 10 timeouts per sekund
  TMR2 = 0x0; //Initierar timern med värdet 0
  T2CONSET = 0x8000; //Sätter på timern. 0x8000 motsvarar en 1 på bit 15

  i2cInit();

  uint8_t lol = mytime;

  i2c_start();
  i2c_send(EEPROM_WRITE);
  i2c_send(EEPROM_MEM_ADD >> 2);
  i2c_send(EEPROM_MEM_ADD + highscore_offset * HIGHSCORE_LENGTH);
  i2c_restart();
  i2c_send();
  entry[0] = i2c_recv();
  int i;
  for(i = 1; i < HIGHSCORE_LENGTH + 1; i++) {
    if(i == 3) {
      entry[i] = ' ';
      continue;
    }
    i2c_ack();
    entry[i] = i2c_recv();
  }
  i2c_stop();

  return;
}

/* This function is called repetitively from the main program */
void labwork( void )
{

  if (IFS(0) & 0x100) //Denna while loop körs om bit 8 är 1 i IFS
  {
    timeoutcount++; //Räkna antalet timeouts
    IFSCLR(0) = 0x100;  //Sätter interrupt flaggan på bit 8 till 0, och startar därmed om timern
  }
  

  if(timeoutcount == 10){ //Denna if-sats körs efter 10 timeouts
    //time2string( textstring, mytime );
    //display_string( 3, textstring );
    display_update();
    tick( &mytime );
    (*leds)++;
    //display_image(96, icon);


    timeoutcount = 0;
  }

  int buttonState = getbtns(); //Hämtar vilken knapp som trycks
  int switchState = getsw(); //Hämtar lägget på spakarna
  
  if(buttonState == 0x4 || buttonState == 0x5 || buttonState == 0x6 || buttonState == 0x7){ //Motsvarar första siffran av mytime
    int shiftedSwitch = switchState << 12; //Shiftar bitsen så att de är på motsvarande 4 bits som den första siffran är på.
    mytime &= ~0xf000; //Sätter de de bits vi vill ändra på till 0
    mytime |= shiftedSwitch; //kombinerar switch bitsen med de rensade mytimebitsen och sparar de i mytime variabeln 
  }

  if(buttonState == 0x2 || buttonState == 0x3 || buttonState == 0x6 || buttonState == 0x7){ //Motsvarar andra siffran av mytime
    int shiftedSwitch = switchState << 8;
    mytime &= ~0xf00;
    mytime |= shiftedSwitch;
  }

  if(buttonState == 0x1 || buttonState == 0x3 || buttonState == 0x5 || buttonState == 0x7){ //Motsvarar tredje siffran av mytime
    int shiftedSwitch = switchState << 4;
    mytime &= ~0xf0;
    mytime |= shiftedSwitch;
  }
}
