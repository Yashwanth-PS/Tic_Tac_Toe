**Enums:**

1) Create the Game State Enum
2) Create the Player Type Enum
3) Create the Cell State Enum
4) Create the Bot Difficulty Level Enum

**Models:**

1) Created the Board Class
2) Created the Bot Class
3) Created the Cell Class
4) Created the Game Class
5) Created the Move Class
6) Created the Player Class
7) Created the Symbol Class

**Strategies**:

1) Bot Playing Strategy
2) Winning Strategy

Condition for Check:-

1] Check for top Left Diagonal: Whether the player won by having all
same symbols through top-left diagonal
--> Condition to Check ==> if(i == j) OR if(cell.row == cell.col)

2] Check for bottom Left Diagonal: Whether the player won by having
all same symbols through bottom-left diagonal
--> Condition to Check ==> if(i + j == n - 1)
OR
if(cell.row + cell.col == dimension - 1)

3] Condition for winning by same symbols on all 4 corners:
- 0,0
- 0, dimension - 1
- dimension - 1, 0
- dimension - 1, dimension - 1