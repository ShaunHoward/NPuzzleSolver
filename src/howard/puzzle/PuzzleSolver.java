package howard.puzzle;

/**
 * Abstract class for solving puzzle. 
 * Breadth-first solver and depth-first solver extend this class.
 * Contains method for obtaining the sequence of puzzle states from
 * initial to solution.
 * 
 * @author Shaun Howard
 */
public abstract class PuzzleSolver {
  
  /** The goal state, when non-null, puzzle is in goal state. */
  protected PuzzleState goal;
  
  /** The length of time taken to solve the puzzle in milliseconds. */
  protected long time;
  
  /**
   * Outputs a string of the moves of the zero (blank space) to reach the solved puzzle
   * from the initial, randomized starting state.
   * 
   * @param puzzle - the puzzle to solve
   * @return the moves to reach the goal state from randomized state
   */
  public abstract String solve(Puzzle puzzle);
  
  /**
   * Returns the sequence of puzzle states and moves to get from the randomized state to the goal state.
   * 
   * @return the sequence of moves to reach the goal state from the initial, randomized state
   */
  public String getSequence() {
    
    /* Current state is goal state; parent state for printing history. */
    PuzzleState current = goal, parent;
    
    /* Builder to make a string of move sequence. */
    StringBuilder builder = new StringBuilder();
    
    /* Tell user which state is printed. */
    System.out.println("This is the state sequence from solved state to randomized state(reverse order): ");
    System.out.println("Solved, Goal State: ");
    
    /* Iterates through puzzle states and prints their level as well as states. */
    if (current != null) {
      while (true) {
        
        /* Prints level of this puzzle state in tree. */
        System.out.println("Level in tree: " + current.getLevel());
        
        /* When state is at top of tree, tell user it is starting state. */
        if (current.getLevel() == 0) {
          System.out.println("This is the initial, randomized start state of the puzzle: ");
        } else { //Otherwise, print the direction zero is moved in.
          System.out.println("Move: " + current.getMove());
        }
        
        /* Print the current state to console. */
        System.out.println(current.toString());
        
        /* Set parent state to current state's previous state. */
        parent = current.getPrev();
        
        /* When there is not parent, at top of tree. */
        if (parent == null)
          break;
        
        /* Add current state's move to the sequence of moves. */
        builder.append(current.getMove());
        
        /* Set current state to parent state. */
        current = parent;
      }
      
      /* Print time taken to solve puzzle. */
      System.out.println("This solving technique took: " + time + " ms");
      
      /* Set goal back to null. */
      goal = null;
      
      /* Reverses and outputs the string builder to order sequence from initial state to goal state. */
      return "This is the sequence of moves from randomized to goal states: " + builder.reverse().toString();
    } else {//Otherwise, system is out of memory.
      
      return "System ran out of memory!";
    }
  }
}
