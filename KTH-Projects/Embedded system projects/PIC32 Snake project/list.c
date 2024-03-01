#include <pic32mx.h>
#include <stdlib.h>
#include "main.h"
#include "list.h"
#include "display.h"

// Author: Olof
int listLength = 0;
struct Position elements[420];

// Author: Olof
void listClear()
{
    struct Position reset = {-1, -1};
    int i;
    for (i = 0; i < listLength; i++)
    {
        elements[i] = reset;
    }

    listLength = 0;
}