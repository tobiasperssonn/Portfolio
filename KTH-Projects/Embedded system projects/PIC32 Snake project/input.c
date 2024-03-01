#include <pic32mx.h>
#include <math.h>
#include "display.h"
#include "input.h"
#include "main.h"

int lastInput = 0;

// From: lab 3
int getSw(void)
{
    return (PORTD & 0xF00) >> 8;
}

// From: lab 3
int getBtns(void)
{
    return (PORTD & 0xE0) >> 5;
}

// Author: Olof
void inputUpdate()
{
    if (getBtns() & 0x04)
    {
        lastInput = 4;
    }
    if (getBtns() & 0x02)
    {
        lastInput = 3;
    }
    if (getBtns() & 0x01)
    {
        lastInput = 2;
    }
}
