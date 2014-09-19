package howard.puzzle;

import java.util.HashSet;
import java.util.Set;

/**
 * Class for solving the N = n x n - 1 puzzle using depth-first search (dfs). 
 * This class extends puzzle solver since it solves the n x n puzzle. 
 * A queue is used to approach the depth-first search tree.
 * 
 * @author Shaun Howard
 */
public class DFSSolver extends PuzzleSolver{
  
  /** A new hash set for storing puzzle states. Helps eliminate duplicate states. */
  protected Set<PuzzleState> stateSet = new HashSet<PuzzleState>();
  
  /** The maximum depth of the dfs. */
  public static final int MAX_DEPTH = 30;
  
  /** An instance of the depth-first solver for solving the puzzle. */
  private static DFSSolver instance = new DFSSolver();
  
  /** Nullary constructor. */
  private DFSSolver(){
    
  }
  
  /**
   * Returns the instance of the depth-first solver.
   * 
   * @return the instance of the depth-first solver.
   */
  public static DFSSolver getInstance(){
    return instance;
  }
  
  /**
   * Recursive method for exploring puzzle states in tree with dfs.
   * 
   * @param state - the puzzle state to explore
   * @param depthLimit - the limit on the depth of the tree
   */
  void iterativeDeepening(PuzzleState state, int depthLimit){
    
    /* Clear the state set to make sure it is fresh. */
    stateSet.clear();
    
    /* Add this state to the state set. */
    stateSet.add(state);
    
    /* Explore states until limit is reached using recursion. */
    for(int i = 1; i <= depthLimit; i++){
      
      /* Call the depth-first search on this state at the given level, i. */
      dfs(state, i);
      
      /* When goal is found, exit recursive state. */
      if(goal != null)
        return;
      
      /* Make sure program does not exceed memory limit. */
      if (Runtime.getRuntime().freeMemory() < (.0001) * Runtime.getRuntime().totalMemory()){
        return;
      }
    }
  }
  
  /**
   * Depth-first search algorithm for recursively solving the puzzle.
   * 
   * @param state - the state to explore
   * @param depth - the depth of the exploration in the dfs tree
   */
  void dfs(PuzzleState state,int depth){
    
    /* When depth is negative, end recursion. */
    if(depth < 0)
      return;
    
    /* When input state is the goal state, set it goal to input state. */
    if(state.isGoalState()){
      goal = state;
    }
    
    /* When the goal state is saved, end recursion, goal has been found. */
    if(goal != null)
      return;
    
    /* A new puzzle state for depth-first search. */
    PuzzleState newState;
    
    /* Try to move zero (blank space) up in the puzzle and set the new state if moves. */
    newState = PuzzleState.moveUp(state);
    
    /* Checks if new state exists and is not in state set. */
    if(newState != null && !stateSet.contains(newState)){
      
      /* Add the new state to the state set. */
      stateSet.add(newState);
      
      /* Call recursive dfs method to explore next level of states in tree. */
      dfs(newState, depth - 1);
      
      /* When goal exists, leave recursive search. */
      if(goal != null)
        return;
      
      /* Remove the new state from the set to save memory. */
      stateSet.remove(newState);
    }
    
    /* Try to move zero (blank space) down in the puzzle and set the new state if moves. */
    newState = PuzzleState.moveDown(state);
    
    /* Checks if new state exists and is not in state set. */
    if(newState!=null && !stateSet.contains(newState)){
      
      /* Add the new state to the state set. */
      stateSet.add(newState);
      
      /* Call recursive dfs method to explore next level of states in tree. */
      dfs(newState, depth - 1);
      
      /* When goal exists, leave recursive search. */
      if(goal != null)
        return;
      
      /* Remove the new state from the set to save memory. */
      stateSet.remove(newState);
    }
    
    /* Try to move zero (blank space) left in the puzzle and set the new state if moves. */
    newState = PuzzleState.moveLeft(state);
    
    /* Checks if new state exists and is not in state set. */
    if(newState!=null && !stateSet.contains(newState)){
      
      /* Add the new state to the state set. */
      stateSet.add(newState);
      
      /* Call recursive dfs method to explore next level of states in tree. */
      dfs(newState, depth - 1);
      
      /* When goal exists, leave recursive search. */
      if(goal != null)
        return;
      
      /* Remove the new state from the set to save memory. */
      stateSet.remove(newState);
    }
    
    /* Try to move zero (blank space) right in the puzzle and set the new state if moves. */
    newState = PuzzleState.moveRight(state);
    
    /* Checks if new state exists and is not in state set. */
    if(newState!=null && !stateSet.contains(newState)){
      
      /* Add the new state to the state set. */
      stateSet.add(newState);
      
      /* Call recursive dfs method to explore next level of states in tree. */
      dfs(newState, depth - 1);
      
      /* When goal exists, leave recursive search. */
      if(goal != null)
        return;
      
      /* Remove the new state from the set to save memory. */
      stateSet.remove(newState);
    }
  }
  
  /**
   * Solves the input puzzle using depth-first search.
   * Outputs the sequence of moves to get from initial, randomized state to goal state.
   * Also tracks the amount of time taken to solve puzzle with this algorithm.
   * 
   * @param puzzle - the puzzle to solve with dfs
   * @return the sequence of moves to reach the goal state from the initial, randomized state
   */
  public String solve(Puzzle puzzle) {
    
    /* Gets the start time of the search. */
    long startTime = System.currentTimeMillis();
    
    /* Initializes goal state to not found. */
    goal = null;
    
    /* Creates a new state from the input puzzle for solving. */
    PuzzleState state = new PuzzleState(puzzle);
    
    /* Performs recursive depth-first search to find the goal state. */
    iterativeDeepening(state, MAX_DEPTH);
    
    /* Gets the difference between end time and start time to find total time taken to solve puzzle. */
    time = System.currentTimeMillis() - startTime;
    
    /* Returns the sequence of moves from initial, randomized puzzle state to goal state. */
    return getSequence();
  }
}
