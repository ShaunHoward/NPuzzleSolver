package howard.puzzle;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * Class for solving the N = n x n - 1 puzzle using breadth-first search (bfs). 
 * This class extends puzzle solver since it solves the n x n puzzle. 
 * A queue is used to approach the breadth-first search tree.
 * 
 * @author Shaun Howard
 */
public class BFSSolver extends PuzzleSolver{
  
  /** An instance of the breadth-first solver for solving the puzzle. */
  private static BFSSolver instance = new BFSSolver();
  
  /** A new hash set for storing puzzle states. Helps eliminate duplicate states. */
  private Set<PuzzleState> stateSet = new HashSet<PuzzleState>();
  
  /** A first - in - first - out queue (FIFO) to store the puzzle states for bfs. */
  private Queue<PuzzleState> stateQueue = new LinkedList<PuzzleState>();
  
  /** Nullary constructor. */
  private BFSSolver() {
    
  }
  
  /**
   * Returns the instance of this solver.
   * 
   * @return the instance of the breadth-first solver
   */
  public static BFSSolver getInstance() {
    return instance;
  }
  
  /**
   * Begins the breadth-first search within a given puzzle state.
   * 
   * @param state - the puzzle state to perform bfs on
   */
  private void bfs(PuzzleState state) {
    
    /* Clear the hash set to free memory. */
    stateSet.clear();
    
    /* Clear the queue to free memory. */
    stateQueue.clear();
    
    /* Add the input state to the hash set. */
    stateSet.add(state);
    
    /* Add the input state to the queue. */
    stateQueue.add(state);
    
    /* Make a new puzzle state for the moves (up, down, left, right). */
    PuzzleState newState;
    
    /* Perform breadth-first search while puzzle states exist in the queue. */
    while (!stateQueue.isEmpty()){
      
      /* Poll the queue to get the next state at end of queue. */
      state = stateQueue.poll();
      
      /* When the state is the goal state, leave search loop. */
      if (state.isGoalState()) {
        
        /* Set goal to current state, if it is the goal state. */
        goal = state;
        break;
      }
      
      /* Make sure program doesn't run over memory limit. */
      if (Runtime.getRuntime().freeMemory() < (.0001) * Runtime.getRuntime().totalMemory()){
        break;
      }
      
      /* Try to move zero (blank space) up in the puzzle and set the new state if moves. */
      newState = PuzzleState.moveUp(state);
      
      /* Checks if new state exists and is not in state set. */
      if (newState != null && !stateSet.contains(newState)) {
        
        /* Adds new state to state set. */
        stateSet.add(newState);
        
        /* Adds new state to state queue. */
        stateQueue.add(newState);
      }
      
      /* Try to move zero (blank space) down in the puzzle and set the new state if moves. */
      newState = PuzzleState.moveDown(state);
      
      /* Checks if new state exists and is not in state set. */
      if (newState != null && !stateSet.contains(newState)) {
        
        /* Adds new state to state set. */
        stateSet.add(newState);
        
        /* Adds new state to state queue. */
        stateQueue.add(newState);
      }
      
      /* Try to move zero (blank space) left in the puzzle and set the new state if moves. */
      newState = PuzzleState.moveLeft(state);
      
      /* Checks if new state exists and is not in state set. */
      if (newState != null && !stateSet.contains(newState)) {
        
        /* Adds new state to state set. */
        stateSet.add(newState);
        
        /* Adds new state to state queue. */
        stateQueue.add(newState);
      }
      
      /* Try to move zero (blank space) right in the puzzle and set the new state if moves. */
      newState = PuzzleState.moveRight(state);
      
      /* Checks if new state exists and is not in state set. */
      if (newState != null && !stateSet.contains(newState)) {
        
        /* Adds new state to state set. */
        stateSet.add(newState);
        
        /* Adds new state to state queue. */
        stateQueue.add(newState);
      }
    }
  }
  
  /**
   * Solves the input puzzle using breadth-first search.
   * Also tracks the amount of time taken to solve puzzle. 
   * 
   * @param puzzle - the puzzle to solve with breadth-first search
   * @return the string sequence of moves from initial puzzle state to goal puzzle state
   */
  public String solve(Puzzle puzzle) {
    
    /* Gets the start time of the search. */
    long startTime = System.currentTimeMillis();
    
    /* Initializes goal state to not found. */
    goal = null;
    
    /* Creates a new state from the input puzzle for solving. */
    PuzzleState state = new PuzzleState(puzzle);
    
    /* Performs breadth-first search on the initial, randomized puzzle state. */
    bfs(state);   
    
    /* Gets the difference between end time and start time to find total time taken to solve puzzle. */
    time = System.currentTimeMillis() - startTime;
    
    /* Returns the sequence of moves from initial, randomized puzzle state to goal state. */
    return getSequence();
  }
}