import java.util.Scanner;


public class Main {


    public static void main(String[] args) {


        Scanner GameScanner = new Scanner(System.in);

        System.out.println("Enter difficulty level for the game(Easy(0), Medium(1), Hard(2): ");
        String diffLevel = GameScanner.nextLine();

        Minefield minefield; 
        if(diffLevel.equals("0")){
          minefield = new Minefield(5,5,5);
        }
        if(diffLevel.equals("1")){
          minefield = new Minefield(9,9,12);
        }
        else{
          minefield = new Minefield(20,20,40);
        }

        
        minefield.createMines(2,2,5);
        minefield.evaluateField();

        minefield.revealMines(3,5);

        minefield.printMinefield();

        /*
        Minefield minefield = new Minefield(5, 5, 5);
        minefield.createMines(2, 2, 5);
        // Print initial state of minefield
        System.out.println("Initial minefield:");
        minefield.printMinefield();

        // Guess a safe cell at (1,1)
        System.out.println("Guessing safe cell at (1,1)");
        minefield.guess(1, 1, false);
        minefield.printMinefield();

        // Guess a mine at (2,2)
        System.out.println("Guessing mine at (2,2)");
        minefield.guess(2, 2, true);
        minefield.printMinefield();

        // Evaluate field after guessing mine at (2,2)
        System.out.println("Evaluating field after guessing mine at (2,2)");
        minefield.evaluateField();
        minefield.printMinefield();*/
    }

}
