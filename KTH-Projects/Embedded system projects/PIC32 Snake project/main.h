int gameState;
int selectedMenuItem;
int dir;

void *stdout;

struct Position
{
    char x;
    char y;
};

void death();
void reset();

void menuItem(int selected, int pageIndex, char *text);

void gameUpdate();
void menuUpdate();
