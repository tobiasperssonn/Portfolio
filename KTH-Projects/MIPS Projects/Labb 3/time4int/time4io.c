#include <stdint.h>
#include <pic32mx.h>
#include "mipslab.h"

int getsw ( void )
{
    int switchState = (PORTD >> 8) & 0xf; //Skiftar alla bits 8 steg åt höger, och sparar sedan de 4 minst signifikanta bitsen. 1111 = 0xf
    return switchState;
}

int getbtns ( void )
{
    int buttonState = (PORTD >> 5) & 0x7; //Samma som ovan fast sparar nu bara de 3 minst signifikanta bitsen. 0111 = 0x7
    return buttonState;
}