import java.util.Scanner;


public class Main {


    public static void main(String[] args) {


        Scanner GameScanner = new Scanner(System.in);

        System.out.println("Enter difficulty level for the game(Easy(0), Medium(1), Hard(2): ");
        String diffLevel = GameScanner.nextLine();

        Minefield minefield; 
        int mines;
        int flags;
        boolean debug;

        if(diffLevel.equals("1")){
          minefield = new Minefield(9,9,12,12);
          mines = 12;
          flags = 12;
        }
        if(diffLevel.equals("2")){
          mines = 40;
          flags = 40;
          minefield = new Minefield(20,20,40,40);
        
        }
        else{
          minefield = new Minefield(5,5,5,5);
          mines = 5;
          flags = 5;
        }

        System.out.println("Debug mode(no(-1), yes(else))");

        int debugInput = GameScanner.nextInt();
        debug = debugInput != -1 ? true: false;

        System.out.println("Enter starting coordinates: [x] [y]");

        int x = GameScanner.nextInt();
        int y = GameScanner.nextInt();

        System.out.println(x);
        System.out.println(y);

        minefield.createMines(x,y);
        minefield.evaluateField();
        minefield.revealMines(x,y);

        System.out.println(minefield);

        if(debug){
            minefield.printMinefield();
        }

        while(minefield.gameOver() != true){

          System.out.printf("Enter cell coordinates and if you wish to place a flag(%d remaining): [x] [y] [F (-1,else)]", minefield.getFlags());
          int cordX = GameScanner.nextInt();
          int cordY = GameScanner.nextInt();
          int flag = GameScanner.nextInt();

          if(flag != -1){
            minefield.guess(cordX, cordY, true);
          }else{
            minefield.guess(cordX, cordY, false);
          }

          System.out.println(minefield);

          if(debug){
            minefield.printMinefield();
          }
        }

      
    }

}
