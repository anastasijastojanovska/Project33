import java.util.Queue;
import java.util.Random;

public class Minefield {
    private int rows;
    private int columns;
    private int flags;

    private int[][] cellValues;


    Cell[][] minefield;
    /**
    Global Section
    */
    //made a comment
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE_BRIGHT = "\u001b[34;1m";
    public static final String ANSI_BLUE = "\u001b[34m";
    public static final String ANSI_RED_BRIGHT = "\u001b[31;1m";
    public static final String ANSI_RED = "\u001b[31m";
    public static final String ANSI_GREEN = "\u001b[32m";
    public static final String ANSI_GREY_BG = "\u001b[0m";
    /**
     * Constructor
     * @param rows       Number of rows.
     * @param columns    Number of columns.
     * @param flags      Number of flags, should be equal to mines
     */
    public Minefield(int rows, int columns, int flags) {
        this.rows = rows;
        this.columns = columns;
        this.flags = flags;

        this.cellValues = new int[rows][columns];
        this.minefield = new Cell[rows][columns];

        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++) {
                minefield[i][j] = new Cell(false, "");
                cellValues[i][j] = 0;
            }
        }

    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getFlags() {
        return flags;
    }

    public Cell[][] getMinefield() {
        return minefield;
    }

    /**
     * evaluateField
     *
     * @function When a mine is found in the field, calculate the surrounding 9x9 tiles values. If a mine is found, increase the count for the square.
     */
    public void evaluateField() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (minefield[i][j].getStatus().equals("M")) {
                    incrementSurroundingCells(i, j);
                }
            }
        }
    }

    //Helper function for the evaluateField method
    private void incrementSurroundingCells(int x, int y) {

        for (int i = x-1; i <= x+1; i++) {
            for (int j = y-1; j <= y+1; j++) {
                if (i >= 0 && i < rows && j >= 0 && j < columns) {
                    cellValues[i][j]++;
                }
            }
        }
    }

    private boolean isValid(int x, int y){
      if(x >= 0 && x < rows && y >= 0 && y < columns){
        return true;
      }

      return false;
    }



    /**
     * createMines
     *
     * @param x       Start x, avoid placing on this square.
     * @param y        Start y, avoid placing on this square.
     * @param mines      Number of mines to place.
     */
    public void createMines(int x, int y, int mines) {
        // Avoiding to place a mine on the starting square
        minefield[x][y].setRevealed(true);
        minefield[x][y].setStatus("N");

        Stack1Gen<Integer> stack = new Stack1Gen<>();

        while (mines > 0) {
            int randomX = (int) (Math.random() * rows);
            int randomY = (int) (Math.random() * columns);
            if (randomX == x && randomY == y) {
                continue;
            }
            if (minefield[randomX][randomY].getStatus().equals("M") || minefield[randomX][randomY].getRevealed()) {
                continue;
            }
            minefield[randomX][randomY].setStatus("M");
            stack.push(randomX * columns + randomY);
            mines--;
        }

        //Popping the mine positions from the stack and setting the corresponding cells in the minefield
        while (!stack.isEmpty()) {
            int index = stack.pop();
            int row = index / columns;
            int col = index % columns;
            Cell cell = minefield[row][col];
            if (!cell.getStatus().equals("M")) {
                cell.setStatus("N");
            }
        }
    }


    /**
     * guess
     *
     * @param x       The x value the user entered.
     * @param y       The y value the user entered.
     * @param flag    A boolean value that allows the user to place a flag on the corresponding square.
     * @return boolean Return false if guess did not hit mine or if flag was placed, true if mine found.
     */
    public boolean guess(int x, int y, boolean flag) {
        //Check if guess is in bounds
        if (x < 0 || x >= rows || y < 0 || y >= columns) {
            System.out.println("Guess is out of bounds.");
            return false;
        }
        //If flag is placed, check if enough flags ar left
        if (flag) {
            if (flags < 1) {
                System.out.println("No more flags left.");
                return false;
            }
            minefield[x][y].setStatus("F");
            flags--;
            return false;
        }

        //Check if cell has already been revealed
        if (minefield[x][y].getRevealed()) {
            System.out.println("Cell already revealed.");
            return false;
        }
        //Check if user hit a mine
        if (minefield[x][y].getStatus().equals("M")) {
            minefield[x][y].setRevealed(true);
            return true;
        }
        //Check if user hit a cell with zero status
        if (cellValues[x][y] == 0) {
            revealZeroes(x, y);
        }
        minefield[x][y].setRevealed(true);
        return false;
    }

    /**
     * gameOver
     *
     * @return boolean Return false if game is not over and squares have yet to be revealed, otheriwse return true.
     */
    public boolean gameOver() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (minefield[i][j].getStatus().equals("M") && minefield[i][j].getRevealed() == true) {
                    System.out.println("You hit a mine");
                    return true;
                }
            }
        }


        return false;
    }


    /**
     * revealField
     *
     * This method should follow the psuedocode given.
     * Why might a stack be useful here rather than a queue?
     *
     * @param x      The x value the user entered.
     * @param y      The y value the user entered.
     */
    public void revealZeroes(int x, int y) {
      System.out.println("reveal zeroes");
      Stack1Gen<int[]> cellStack = new Stack1Gen<int[]>();

      int[] startingCords = {x,y};
      cellStack.push(startingCords);

      while(!cellStack.isEmpty()){
        int[] rootCords = cellStack.pop();
        minefield[rootCords[0]][rootCords[1]].setRevealed(true);


        for (int i = rootCords[0]-1; i <= x+2; i++) {
            for (int j = rootCords[1]-1; j <= y+2; j++) {
                if (i >= 0 && i < rows && j >= 0 && j < columns) {
                    if(minefield[i][j].getRevealed() != true && cellValues[i][j] == 0){
                      int[] adjCell = {i,j};
                      cellStack.push(adjCell);
                    }
                }
          }
       }

      }
    }

    /**
     * revealMines
     *
     * This method should follow the psuedocode given.
     * Why might a queue be useful for this function?
     *
     * @param x     The x value the user entered.
     * @param y     The y value the user entered.
     */
    public void revealMines(int x, int y) {
      Q1Gen<int[]> CellQueue = new Q1Gen<int[]>();

      int[] rootCell = {x,y};
      CellQueue.add(rootCell);

      while(CellQueue.length() > 0){
        int[] frontCell = CellQueue.remove();
        minefield[frontCell[0]][frontCell[1]].setRevealed(true);

        if(minefield[frontCell[0]][frontCell[1]].getStatus().equals("M")){
          return;
        }

        for (int i = frontCell[0]-1; i <= x+1; i++) {
            for (int j = frontCell[1]-1; j <= y+1; j++) {
                if (i >= 0 && i < rows && j >= 0 && j < columns) {
                    if(minefield[i][j].getRevealed() != true){
                      int[] reachableCell = {i,j};
                      CellQueue.add(reachableCell);
                    }
                }
          }
       } 
      }
    }

    /**
     * revealStart
     *
     * @param x       The x value the user entered.
     * @param y       The y value the user entered.
     */

    public void revealStart(int x, int y) {

    }
    /**
     * printMinefield
     *
     * @fuctnion This method should print the entire minefield, regardless if the user has guessed a square.
     * *This method should print out when debug mode has been selected. 
     */

    /*
    public void printMinefield() {

        for (int i = 0; i < rows; i++) {
            System.out.printf("%2d |", i);
            for (int j = 0; j < columns; j++) {
                if (minefield[i][j].getStatus().equals("M")){
                    System.out.print(ANSI_RED + "M" + ANSI_GREY_BG + "\t");
                } else if (minefield[i][j].getRevealed()) {
                    int count = 0;
                    try {
                        count = cellValues[i][j];
                    } catch (NumberFormatException e) {
                        // handle the exception by treating the status as 0
                    }
                    if (count < 1) {
                        System.out.print("0" + "\t");
                    } else {
                        System.out.printf(" %d " + "\t", count);
                    }
                } else {
                    System.out.print(" - " + "\t");
                }
            }
            System.out.println("|");
        }

    }*/

    public void printMinefield() {

        for (int i = 0; i < rows; i++) {
            System.out.printf("%2d |", i);
            for (int j = 0; j < columns; j++) {
                if (minefield[i][j].getStatus().equals("M")){
                    System.out.print(ANSI_RED + "M" + ANSI_GREY_BG + "\t");
                } else if (minefield[i][j].getRevealed()) {
                    int count = 0;
                    try {
                        count = cellValues[i][j];
                    } catch (NumberFormatException e) {
                        // handle the exception by treating the status as 0
                    }
                    if (count < 1) {
                        System.out.print(ANSI_GREEN + "0" + ANSI_GREY_BG + "\t");
                    } else {
                        System.out.printf(ANSI_GREEN + " %d " + ANSI_GREY_BG + "\t", count);
                    }
                } else {
                    int count1 = cellValues[i][j];
                    System.out.printf(" %d " + "\t", count1);
                }
            }
            System.out.println("|");
        }

    }

    /**
     * toString
     *
     * @return String The string that is returned only has the squares that has been revealed to the user or that the user has guessed.
     */
    public String toString() {
    return "";
    }
}
