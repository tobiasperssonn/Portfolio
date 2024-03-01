#include <pic32mx.h>
#include <stdint.h>
#include <stdlib.h>
#include "input.h"
#include "display.h"
#include "main.h"
#include "list.h"

int gameState = 0;
int selectedMenuItem = 0;

int updateTimeout = 0;
int totalTimeout = 0;

int startTime = 0;

struct Position position;

int foodX = 0;
int foodY = 0;

int spawnFood = 1;

int score = 0;

int dir = 0;

const int WIDTH = 127;
const int HEIGHT = 31;

void *stdout = (void *)0;

void user_isr()
{
	//
	// Author: Olof
	//
	if (IFS(0) & 0x100)
	{
		IFSCLR(0) = 0x100;
		updateTimeout++;
		totalTimeout++;
		inputUpdate();
	}
	if (!(updateTimeout % 1))
	{
	}
	if (updateTimeout >= 30)
	{
		if (gameState == 0)
		{
			menuUpdate();
		}
		else if (gameState == 1)
		{
			gameUpdate();
		}
		else
		{
			death();
		}
		updateTimeout = 0;
	}
	// End Author
}

// Author: Tobias
//
// Uses ADC with a floating analog pin A1 together with a time value fetched
// when the user pushes the start game for the first time to generate a random number
void randomInit()
{
	// Sets the A1 pin to input
	TRISBSET = 0x10;

	// Sets pin A1 to analog mode and the rest to digital mode
	AD1PCFG = 0xFFEF;

	// Sets the clock source to the RC-clock
	AD1CON3 = 0x8000;

	// Enables auto conversion at the
	// end of the RC-clock count
	AD1CON1 = 0x00E0;

	// Selects unused analog pin A1 as the positive MUX A input
	AD1CHS = 0x40000;

	// Makes sure that the CON2 register is clear
	AD1CON2 = 0;

	AD1CSSL = 0;

	// Sets the output form as a 32 bit int
	AD1CON1SET = 0x400;

	// Enable the ADC
	AD1CON1SET = 0x8000;

	// Create a small delay between turning the ADC on and the sampling start
	// This prevents bugs and false random values that can occur.
	quicksleep(100);

	// Starts sampling
	AD1CON1SET = 0x2;

	// Wait for the sampling to start
	while (!(AD1CON1 & (0x1 << 1)))
		;

	// Wait for the conversion to finish
	// by checking if the DONE bit is set
	while (!(AD1CON1 & 0x1))
		;

	// Fetch the converted ADC value
	int adcValue = ADC1BUF0;

	//Calculates a random value via the ADC value and a LCG algorithm
	//Uses the parameter values that is commonly used in C
	int adcSeed = (unsigned)(1103515245 * adcValue + 12345) % 2147483648;

	// Calculates a final seed by using a XOR-gate between the time value and the ADC output
	unsigned int finalSeed = (unsigned)startTime^adcSeed;

	// Sets the randomizer seed to our generated value
	srand(finalSeed);

	//display_clear();
	//display_buffer(0, 128, displayBuff);

	//while (1)
	//{
	//	display_debug(&finalSeed);
	//}

	// Turn off the ADC
	AD1CON1CLR = 0x8000;
}
// End Author

// Author: Tobias
void food()
{
	// Spawn a new apple at a new location if the previous apple has been eaten.
	if (spawnFood == 1)
	{
		// Randomizes a new x-coordinate and a y-coordinate for the apples
		foodX = (rand() % (WIDTH - 2) + 2);
		foodY = (rand() % (HEIGHT - 2) + 2);
		display_rect(foodX, foodY, 2);
		spawnFood = 0;
	}
	else
	{
		display_rect(foodX, foodY, 2);
	}
}

// Author: Tobias
void collision()
{
	int i;
	int distX = abs(position.x - foodX);
	int distY = abs(position.y - foodY);

	// Check collision with the 3x3 pixel snake head and the 2x2 pixel apple
	if ((distX <= 1 && distY <= 1))
	{
		spawnFood = 1;
		score++;
		struct Position newBodyPos = {elements[listLength].x - 1, elements[listLength].y};
		elements[listLength++] = newBodyPos;
	}

	if (position.x < 1 || position.x > 124)
	{
		gameState = 3;
	}

	if (position.y < 2 || position.y > 28)
	{
		gameState = 3;
	}

	// Check collision with tail
	for (i = 2; i < listLength; i++)
	{
		distX = abs(position.x - elements[i].x);
		distY = abs(position.y - elements[i].y);
		if (distX <= 1 && distY <= 1)
		{
			gameState = 3;
			break;
		}
	}
}
// End Author

void init()
{

	// Set buttons and swithces to input
	TRISDSET = 0xFE0;

	/*
  This will set the peripheral bus clock to the same frequency
  as the sysclock. That means 80 MHz, when the microcontroller
  is running at 80 MHz. Changed 2017, as recommended by Axel.
*/
	SYSKEY = 0xAA996655; /* Unlock OSCCON, step 1 */
	SYSKEY = 0x556699AA; /* Unlock OSCCON, step 2 */
	while (OSCCON & (1 << 21))
		;				  /* Wait until PBDIV ready */
	OSCCONCLR = 0x180000; /* clear PBDIV bit <0,1> */
	while (OSCCON & (1 << 21))
		;		  /* Wait until PBDIV ready */
	SYSKEY = 0x0; /* Lock OSCCON */

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

	display_init();

	// Author: Olof
	// Set timer 2 to count 30 times a second (30 fps)
	T2CON = 0x00;
	TMR2 = 0x00;
	PR2 = 0x28b0; // 0x7a12;
	T2CONSET = 0x70;
	IFSCLR(0) = 0x100;

	IPCSET(2) = 0x1F;

	IECSET(0) = 0x100;

	T2CONSET = 0x8000;

	// Enable interrups globally
	enable_interrupt();

	// End Author
}

void highscore()
{
	menuItem(0, 0, "HIGHSCORE!");

	menuItem(0, 2, "Enter name");
}

// Author: Tobias
void death()
{
	char *convScore = itoaconv(score);
	char *text = "Score: ";

	int charCounter = 0;
	while (*convScore)
	{
		charCounter++;
		convScore++;
	}
	convScore -= charCounter;

	char finalScore[7 + charCounter];

	int i;
	for (i = 0; i < sizeof(finalScore); i++)
	{
		if (i < 7)
		{
			finalScore[i] = text[i];
		}
		else
		{
			finalScore[i] = convScore[i - 7];
		}
	}

	if (lastInput == 2)
	{
		lastInput = 0;
		reset();
	}

	display_clear();
	menuItem(0, 0, finalScore);
	menuItem(0, 2, "You died!");
	menuItem(1, 3, "Play again?");
	display_buffer(0, 128, displayBuff);
}

// Author: Tobias
void reset()
{
	dir = 0;
	position.x = 63;
	position.y = 15;
	spawnFood = 1;
	score = 0;
	listClear();
	elements[listLength++] = position;
	startTime = (unsigned)PR2 * totalTimeout + TMR2;
	randomInit();
	display_clear();
	gameState = 1;
}

// Author: Olof
void menuItem(int selected, int pageIndex, char *text)
{
	int i;
	int a = 0;
	while (*text)
	{
		a++;
		text++;
	}
	text -= a;
	char temp[16];
	//(16- a) / 2 + a
	for (i = 0; i < 16; i++)
	{
		if (i < (16 - a) / 2 || i > (16 - a) / 2 + a)
		{
			temp[i] = 0x80;
		}
		else
		{
			temp[i] = text[i - ((16 - a) / 2)];
		}
	}
	display_string(pageIndex, temp);

	if (selected)
	{
		display_rect(((16 - a) / 2 - 1) * 8 - 2, pageIndex * 8 + 4, 2);
		display_rect(((16 - a) / 2 + a + 1) * 8, pageIndex * 8 + 4, 2);
	}
}

// Author: Olof
void menuUpdate()
{
	if (lastInput == 4)
	{
		lastInput = 0;
		selectedMenuItem--;
		if (selectedMenuItem < 0)
			selectedMenuItem = 0;
	}
	else if (lastInput == 3)
	{
		lastInput = 0;
		selectedMenuItem++;
		if (selectedMenuItem > 1)
			selectedMenuItem = 1;
	}
	else if (lastInput == 2)
	{
		lastInput = 0;
		if (selectedMenuItem == 0)
		{
			reset();
		}
	}

	display_clear();
	menuItem(0, 0, "Snek");
	menuItem(selectedMenuItem == 0, 2, "Start Game");
	menuItem(selectedMenuItem == 1, 3, "Scores");
	display_buffer(0, 128, displayBuff);
}

void gameUpdate()
{
	// Author: Olof
	int i;
	if (lastInput == 4)
	{
		lastInput = 0;
		if (dir == 2 || dir == -2)
		{
			dir = -1;
		}
		else
		{
			dir++;
		}
	}
	else if (lastInput == 3)
	{
		lastInput = 0;
		if (dir == -2 || dir == 2)
		{
			dir = 1;
		}
		else
		{
			dir--;
		}
	}

	if (dir == 0)
	{
		position.x += 2;
	}
	else if (dir == 1)
	{
		position.y -= 2;
	}
	else if (dir == 2 || dir == -2)
	{
		position.x -= 2;
	}
	else if (dir == -1)
	{
		position.y += 2;
	}

	for (i = listLength - 1; i >= 0; i--)
	{
		if (i == 0)
		{
			elements[0] = position;
			continue;
		}
		// struct Position bodyPos = {elements[i - 1].x - (1) * ((abs(dir) / 2 == 0) * 2 - 1) * (dir == 0 || abs(dir) == 2), elements[i - 1].y - (i) * -dir * (abs(dir) == 1)};
		elements[i] = elements[i - 1];
	}

	display_clear();
	food();
	for (i = 0; i < listLength; i++)
	{
		display_rect(elements[i].x - 1, elements[i].y - 1, 3);
	}
	draw_border();
	collision();
	display_buffer(0, 128, displayBuff);
	// End Author
}

int main(void)
{
	init();
	//menuUpdate();
	//display_buffer(0, 128, displayBuff);

	while (1)
	{
		//display_debug(&totalTimeout);
	}
	return 0;
}
