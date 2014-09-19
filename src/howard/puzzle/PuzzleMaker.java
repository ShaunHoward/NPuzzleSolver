package howard.puzzle;

import java.util.Scanner;

/**
 * Class for making an n x n puzzle of integers, randomizing the puzzle, and solving the puzzle.
 * Puzzle is randomized using values adjacent to blank space in puzzle.
 * Solving types are breadth-first search (bfs) and depth-first search (dfs).
 * 
 * @author Shaun Howard
 */
public class PuzzleMaker {
  
  /**
   * Runs the n x n puzzle solver.
   * Takes four parameters:
   * Puzzle size as number (3, 8, 15, etc.), 
   * Search type as string (bfs or dfs),
   * State type as string (goal or custom), and
   * if custom, numbers of the puzzle from top row to bottom row in left to right order
   * separated by spaces.
   * 
   * i.e. "8 bfs goal" to run 8-puzzle with goal state as initial puzzle
   * i.e. "8 dfs custom 0 1 2 3 4 5 6 7 8" to manually input goal state
   * 
   * Note: the goal/custom input state will still be randomized to solve.
   * 
   * @param args - puzzle size, search type, state type, 
   */
  public static void main(String[] args){
    
    try {
      
      /* Puzzle to use. */
      Puzzle puzzle = Puzzle.getInstance();
      
      /* Scans input from command prompt. */
      Scanner scanner = new Scanner(System.in);
      
      /* Converts String to Integer for size variable of puzzle. */
      int size = (int)Math.sqrt(scanner.nextInt() + 1);
      
      /* Stores the search type to make sure it is a correct value. */
      String search = scanner.next();
      
      /* Stores the state type of the puzzle. */
      String state = scanner.next();
      
      /* Checks to see if the arguments were entered correctly. */
      if (size < 2 || !(search.equals("bfs") || search.equals("dfs") ||
                        state.equals("goal") || state.equals("custom"))) {
        
        scanner.close();
        throw new UnsupportedOperationException();
      }
      
      /* Checks if a custom state is entered. */
      if (state.equals("custom")) {
        
        /* Make a new puzzle array based on puzzle size entered. */
        int[][] puzzleArray = new int[size][size];
        
        /* Iterate through rows of puzzle array. */
        for(int i = 0; i < size; i++){
          
          /* Iterate through columns of puzzle array. */
          for(int j = 0; j < size; j++){
            
            /* Store next scanned integer in the puzzle array at this index. */
            puzzleArray[i][j] = scanner.nextInt();
          }
        }
        
        /* Initialize the puzzle instance based on entered values for puzzle array. */
        puzzle.initialize(puzzleArray, size);
      } else { //Otherwise, make a new puzzle based on input size.
        
        /* Makes a puzzle of the given size. */
        puzzle.initialize(size);
      }
      
      /* Close scanner to avoid memory leak. */
      scanner.close();
      
      /* Show user first, non-randomized state of puzzle. */
      System.out.println("This is the initial, unrandomized state of the puzzle.");
      
      /* Print initial puzzle board before randomization. */
      System.out.println(puzzle.toString());
      
      /* Randomizes the puzzle using a specific number of random moves based on puzzle size. */
      puzzle.randomize();
      
      /* Shows the randomized state of puzzle. */
      System.out.println("This is the initial, randomized start state of the puzzle: "); 
      
      /* Print the randomized state to console. */
      System.out.println(puzzle.toString());
      
      /* Checks if user wants breadth-first solving. */
      if (search.equals("bfs")) {
        
        /* Solve the puzzle with bfs. */
        PuzzleSolver sol = BFSSolver.getInstance();
        
        /* Print solution sequence to console. */
        System.out.println(sol.solve(puzzle));
        
      } else { //Otherwise, user gets depth-first solving. 
        
        /* Solve the puzzle with dfs. */
        PuzzleSolver sol = DFSSolver.getInstance();
        
        /* Print solution sequence to console. */
        System.out.println(sol.solve(puzzle));
      }
      
    } catch (Exception e){ //Catch exception when making puzzle
      
      e.printStackTrace();
      
      System.out.println("Please type a puzzle size greater than 1, hit enter,"
                           + " and then type a solving type of bfs or dfs, then hit enter again!");
    }
  }
}
