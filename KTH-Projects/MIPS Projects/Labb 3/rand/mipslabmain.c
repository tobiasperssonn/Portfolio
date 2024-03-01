/* mipslabmain.c

   This file written 2015 by Axel Isaksson,
   modified 2015, 2017 by F Lundevall

   Latest update 2017-04-21 by F Lundevall

   For copyright and licensing, see file COPYING */

#include <stdint.h>   /* Declarations of uint_32 and the like */
#include <pic32mx.h>  /* Declarations of system-specific addresses etc */
#include "mipslab.h"  /* Declatations for these labs */
#include <stdlib.h>

int check = 0;

void *stdout = (void *) 0;

char int_to_char(int num) {
    return (char) (num + '0');
}

void randomInit()
{
	// Sets the A9 pin to input
	//TRISBSET = 0x200;

	// Sets pin A1 to analog mode and the rest to digital mode
	//AD1PCFG = 0xFFF7;
	//AD1PCFG = 0xFDFF;
	AD1PCFG = 0xFFFB;

	//   Enables auto conversion at the
	//   end of the RC-clock count
	AD1CON1 = 0x00E0;

	// Selects unused analog pin A0 as the positive MUX A input
	//AD1CHS = 0x20000;
	AD1CHS = 0x40000;

	// Makes sure that the CON2 register is clear
	AD1CON2 = 0;

	AD1CSSL = 0;

	// Sets the clock source to the RC-clock
	AD1CON3 = 0x8000;

	// Sets the output form as a 32 bit int
	AD1CON1SET = 0x400;

	// Enable the ADC
	AD1CON1SET = 0x8000;

	// Starts sampling
	AD1CON1SET = 0x2;
	

	// Wait for the sampling to start
	while (!(AD1CON1 & 0x2))
		;

	// Wait for the conversion to finish
	// by checking if the DONE bit is set
	while (!(AD1CON1 & 0x1))
		;
	

	// Fetch the converted ADC value
	int adcValue = ADC1BUF0;

	//Calculate a random number using the random lsb and a LCG algorithm
	int adcSeed = (unsigned)(1103515245 * adcValue + 12345);

	display_debug(&adcValue);

	//int i;
	//for (i = 0; i < 10; i++)
	//{
	//	adcSeed = (unsigned)(1103515245 * adcSeed + 12345);
	//}

		// int timeSeed = PR2 * totalTimeout + TMR2;

	// Calculates a final seed by adding the two seeds
	//unsigned int finalSeed = (unsigned)adcSeed; // + timeSeed;

	// Sets the randomizer seed to our generated value
	//srand(finalSeed);

	// Disable the ADC
	//AD1CON1CLR = 0x8000;
}

int main(void) {
	//srand((unsigned) 30);
	rand();
        /*
	  This will set the peripheral bus clock to the same frequency
	  as the sysclock. That means 80 MHz, when the microcontroller
	  is running at 80 MHz. Changed 2017, as recommended by Axel.
	*/
	SYSKEY = 0xAA996655;  /* Unlock OSCCON, step 1 */
	SYSKEY = 0x556699AA;  /* Unlock OSCCON, step 2 */
	while(OSCCON & (1 << 21)); /* Wait until PBDIV ready */
	OSCCONCLR = 0x180000; /* clear PBDIV bit <0,1> */
	while(OSCCON & (1 << 21));  /* Wait until PBDIV ready */
	SYSKEY = 0x0;  /* Lock OSCCON */
	
	/* Set up output pins */
	AD1PCFG = 0xFFFF;
	ODCE = 0x0;
	TRISECLR = 0xFF;
	PORTE = 0x0;
	
	/* Output pins for display signals */
	PORTF = 0xFFFF;
	PORTG = (1 << 9);
	ODCF = 0x0;
	ODCG = 0x0;
	TRISFCLR = 0x70;
	TRISGCLR = 0x200;
	
	/* Set up input pins */
	TRISDSET = (1 << 8);
	TRISFSET = (1 << 1);
	
	/* Set up SPI as master */
	SPI2CON = 0;
	SPI2BRG = 4;
	/* SPI2STAT bit SPIROV = 0; */
	SPI2STATCLR = 0x40;
	/* SPI2CON bit CKP = 1; */
        SPI2CONSET = 0x40;
	/* SPI2CON bit MSTEN = 1; */
	SPI2CONSET = 0x20;
	/* SPI2CON bit ON = 1; */
	SPI2CONSET = 0x8000;

	randomInit();
	
	display_init();
	//display_debug(&ADC1BUF0);
	//display_string(0, "KTH/ICT lab");
	//display_string(1, "in Computer");
	//display_string(2, "Engineering");
	//display_string(3, "Welcome!");
	display_update();
	//srand(10);
	
	
	labinit(); /* Do any lab-specific initialization */

	while( 1 )
	{
	  labwork(); /* Do lab-specific things again and again */
	}
	return 0;
}
