public class Main {


    public static void main(String[] args) {

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
        minefield.printMinefield();
    }

}
