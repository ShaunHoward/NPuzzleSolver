package howard.puzzle;

import java.util.Random;

/**
 * Class for an n x n - 1 puzzle, in which you input one number, n, to make. 
 * 
 * @author Shaun Howard
 */
public class Puzzle {
  
  /** A static instance of Puzzle for use throughout package. */
  private static Puzzle instance = new Puzzle();
  
  /** The puzzle length. */
  private int size;
  
  /** Array of puzzle numbers. */
  private int[][] puzzleArray;
  
  /** Array of the goal state of this puzzle. */
  private static int[][] goalState;
  
  /** The column of the puzzle the zero space is in. */
  private int zeroColumn;
  
  /** The row of the puzzle the zero space is in. */
  private int zeroRow;
  
  /** The level of this puzzle state in the tree. */
  private int level;
  
  /** Tracks goal state, true if state is goal state. */
  private boolean isGoalState;
  
  /**
   * Makes a new 8-puzzle instance.
   */
  public Puzzle(){
    this(3);
  }
  
  /**
   * Constructor to make a new Puzzle for solving from puzzle side size.
   * N = n x n - 1 puzzle can be made.
   * 
   * @param sizeInput - the length and width of the puzzle
   */
  public Puzzle(int sizeInput){
    
    /* Check if instance size. */
    if (sizeInput == 3) {
      
      /* Initialize instance with size of 3, for 8-puzzle. */
      initialize(sizeInput);
    } else { // Otherwise, initialize the instance to different size.
      
      /* Initialize instance with size of sizeInput, for N = n x n - 1 puzzle. */
      instance.initialize(sizeInput);
    }
  }
  
  /**
   * Returns the instance of this puzzle.
   * 
   * @return the instance of this puzzle
   */
  public static Puzzle getInstance(){
    return instance;
  }
  
  /**
   * Initializes the instance of this puzzle based on input size for n x n - 1 puzzle.
   * 
   * @param sizeInput - the size of the side of the puzzle (n in n x n - 1 puzzle)
   */
  public void initialize(int sizeInput){
    
    /* Set the side length of the puzzle. */
    this.size = sizeInput;
    
    /* Make puzzle with sizeInput - 1 amount of columns and rows. */
    puzzleArray = new int[size][size];
    
    /* Make puzzle goal state array for future reference when solving. */
    goalState = new int[size][size];
    
    /* Iterate through the rows of the array. */
    for (int i = 0; i < size; i++) {
      
      /* Iterate through the columns of the array. */
      for (int j = 0; j < size; j++){
        
        /* Set the value at this index in puzzle array as a function of i and j. */
        puzzleArray[i][j] = (i * size) + j;
        
        /* Set the value at this index in goal state array as a function of i and j. */
        goalState[i][j] = (i * size) + j;
      }
    }
    
    /* Set the initial row position of the zero (blank space). */
    setZeroRow(0);
    
    /* Set the initial column position of the zero (blank space). */
    setZeroColumn(0);
    
    /* Initialized to goal state. */
    isGoalState = true;
  }
  
  /**
   * Makes a new puzzle from a puzzle array and puzzle side size.
   * 
   * @param puzzle - the puzzle array to make the new puzzle state with
   * @param size - the side size of the puzzle
   */
  public void initialize(int[][] puzzle, int sizeInput){
    
    /* Set the size of the puzzle array to the puzzle's width. */
    this.size = sizeInput;
    
    /* Makes a new puzzle from this puzzle's dimensions. */
    puzzleArray = new int[size][size];
    
    /* Copies the contents of the input puzzle array to this puzzle array. */
    for(int i = 0; i < size; i++){
      for(int j = 0; j < size; j++){
        
        /* Copy value at this index of old puzzle to this puzzle array. */
        puzzleArray[i][j] = puzzle[i][j];
        
        /* Check if the value at this index is 0, if so, set the column and row positions. */
        if(puzzle[i][j] == 0){
          
          /* Set zero row position. */
          setZeroRow(i);
          
          /* Set zero column position. */
          setZeroColumn(j);
        }
      }
    }
    
    /* Sets the goal state boolean for this new puzzle. */
    this.isGoalState = isGoalState();
  }
  
  /**
   * Gets the number at a specific row and column.
   * Very useful when in a static method.
   * 
   * @param row - the row of the value in the puzzle array
   * @param column - the column of the value in the puzzle array
   * @return the number value at the specified index
   */
  public int getNumber(int row, int column){
    return puzzleArray[row][column];
  }
  
  /**
   * Sets the column position of the zero.
   * 
   * @param column - the column position of the zero in the puzzle
   */
  public void setZeroColumn(int column){
    this.zeroColumn = column;
  }
  
  /**
   * Returns the column position of the zero in the puzzle. 
   */
  public int getZeroColumn(){
    return this.zeroColumn; 
  }
  
  /**
   * Sets the row position of the zero.
   * 
   * @param row - the row position of the zero in the puzzle
   */
  public void setZeroRow(int row){
    this.zeroRow = row;
  } 
  
  /** 
   * Returns the row position of the zero in the puzzle.
   */
  public int getZeroRow(){
    return this.zeroRow; 
  }
  
  /**
   * Randomizes this puzzle.
   */
  public void randomize(){
    
    /* An instance of random to help randomize the n x n - 1 puzzle. */
    Random random = new Random();
    
    /* Randomize 100 times the side size of the puzzle. */
    for (int i = 0; i < size * 100; i++) {
      
      /* Pick a random integer from 0 to 4. */
      switch (random.nextInt(4)) {                                
        
        /* Move the zero to the left, if possible. */
        case (0):
          
          /* Check if zero can be moved left. */
          if (moveLeft()) {
          
        } else { //Otherwise, decrement shuffle counter. 
          
          /* This is not considered a shuffle move. */
          i--;
        }
        break;
        
        /* Move the zero to the right, if possible. */
        case (1):
          
          /* Check if zero can be moved right. */
          if (moveRight()) {
          
        } else { //Otherwise, decrement shuffle counter.
          
          i--;
        }
        break;
        
        /* Moves the zero up, if possible. */
        case (2):
          
          /* Check if zero can be moved up. */
          if (moveUp()) {
          
        } else { //Otherwise, decrement shuffle counter.
          
          i--; 
        }
        break;
        
        /* Moves the zero down, if possible. */
        case (3):
          
          /* Check if zero can be moved down. */
          if(moveDown()){
          
        } else { //Otherwise, decrement shuffle counter.
          
          i--;
        }
        break;
      }        
    }
    
    /* Check if puzzle is in goal state, if so, randomize again. */
    if (isGoalState())
      randomize();
  }
  
  /**
   * Checks whether zero can be moved left, if so, moves zero left in puzzle.
   * 
   * @return whether the zero can be moved left in the puzzle
   */
  public boolean moveLeft(){
    
    /* Check if zero can be moved left. */
    if (getZeroColumn() <= 0)
      return false;
    
    /* Stores a temporary copy of the value at left of zero. */
    int temp = puzzleArray[zeroRow][zeroColumn - 1];
    
    /* Moves zero left one position in puzzle. */
    puzzleArray[zeroRow][zeroColumn - 1] = 0;
    
    /* Moves what was left value to right side of zero. */
    puzzleArray[zeroRow][zeroColumn] = temp;
    
    /* Decrement the zero column counter. */
    setZeroColumn(getZeroColumn() - 1);
    
    return true;
  }
  
  /**
   * Checks whether zero can be moved right, if so, moves zero right in puzzle.
   * 
   * @return whether the zero can be moved right in the puzzle
   */
  public boolean moveRight(){
    
    /* Check if zero can be moved right. */
    if (getZeroColumn() >= getSize() - 1)
      return false;
    
    /* Stores a temporary copy of the value at right of zero. */
    int temp = puzzleArray[zeroRow][zeroColumn + 1];
    
    /* Moves zero right one position in puzzle. */
    puzzleArray[zeroRow][zeroColumn + 1] = 0;
    
    /* Moves what was right value to left side of zero. */
    puzzleArray[zeroRow][zeroColumn] = temp;
    
    /* Increment the zero column counter. */
    setZeroColumn(getZeroColumn() + 1);
    
    return true;
  }
  
  /**
   * Checks whether zero can be moved up, if so, moves zero up in puzzle.
   * 
   * @return whether the zero can be moved up in the puzzle
   */
  public boolean moveUp(){
    
    /* Check if zero can be moved up. */
    if (getZeroRow() <= 0)
      return false;
    
    /* Stores a temporary copy of the value above zero. */
    int temp = puzzleArray[zeroRow - 1][zeroColumn];
    
    /* Moves zero up one position in puzzle. */
    puzzleArray[zeroRow - 1][zeroColumn] = 0;
    
    /* Moves what was above value underneath zero. */
    puzzleArray[zeroRow][zeroColumn] = temp;
    
    /* Decrement the zero row counter. */
    setZeroRow(getZeroRow() - 1);
    
    return true;
  }
  
  /**
   * Checks whether zero can be moved down, if so, moves zero down in puzzle.
   * 
   * @return whether the zero can be moved down in the puzzle
   */
  public boolean moveDown(){
    
    /* Check if zero can be moved down. */
    if(getZeroRow() >= getSize() - 1)
      return false;
    
    /* Stores a temporary copy of the value below zero. */
    int temp = puzzleArray[zeroRow + 1][zeroColumn];
    
    /* Moves zero down one position in puzzle. */
    puzzleArray[zeroRow + 1][zeroColumn] = 0;
    
    /* Moves what was below value above zero. */
    puzzleArray[zeroRow][zeroColumn] = temp;
    
    /* Increment the zero row counter. */
    setZeroRow(getZeroRow() + 1);
    
    return true;
  }
  
  /**
   * Return the array width for the puzzle board.
   * 
   * @return the width of the puzzle board array
   */
  public int getSize(){
    
    return this.size;
  }
  
  /**
   * Returns the array of this puzzle.
   * 
   * @return the multidimensional array of this puzzle state
   */
  public int[][] getPuzzleArray(){
    
    return this.puzzleArray; 
  }
  
  /**
   * Sets the level of this puzzle in the tree of states.
   * 
   * @param n - the level of this puzzle in the tree of states
   */
  public void setLevel(int n){
    this.level = n;
  }
  
  /**
   * Returns the level of this puzzle in the tree of states.
   * 
   * @return the level of this puzzle in the tree of states
   */
  public int getLevel(){
    return this.level; 
  }
  
  /**
   * Checks if this puzzle is in the goal state.
   * 
   * @return whether this puzzle is in the goal state
   */
  public boolean isGoalState(){
    
    /* Iterate through rows of puzzle. */
    for (int i = 0; i < size; i++){
      
      /* Iterate through columns of puzzle. */
      for (int j = 0; j < size; j++){
        
        /* When two values are unequal at the same index, not in goal state. */
        if (puzzleArray[i][j] != goalState[i][j]){
          
          /* Set goal state to false. */
          isGoalState = false;
          
          return isGoalState;
        }
      }
    }
    
    /* When this point is reached, puzzle is in goal state. */
    isGoalState = true;
    
    return isGoalState;
  }
  
  /**
   * Returns a String of the puzzle state. 
   * 
   * @return a string of the current puzzle state
   * 
   * @Override - the toString() method of object
   */
  @Override
  public String toString(){
    
    /* A new string builder for the puzzle state. */
    StringBuilder builder = new StringBuilder();
    
    /* A new line separator for printing the contents of the puzzle. */
    String newLine = System.getProperty("line.separator");
    
    /* Iterate through rows of puzzle. */
    for(int n = 0; n < puzzleArray.length; n++){
      
      /* Iterate through columns of puzzle. */
      for (int j = 0; j < puzzleArray[n].length; j++){
        
        /* Add this position's number to the puzzle state builder. */
        builder.append(puzzleArray[n][j] + " ");
      }  
      
      /* Add a new line after row in puzzle. */
      builder.append(newLine);
    }
    
    return builder.toString();
  }
  
  /**
   * Generates hash code for this puzzle for sorting in Hash Set.
   * 
   * @return the hash code of this puzzle
   * @Override 
   */
  @Override
  public int hashCode() {
    
    /* Generate hash code based on string value of this puzzle. */
    return this.toString().hashCode();
  }
  
  /**
   * Allows ability to compare two puzzles.
   * 
   * @return whether o is equal to this puzzle
   * @Override 
   */
  @Override
  public boolean equals(Object o) {
    
    /* Checks if o is a puzzle. */
    if (o instanceof Puzzle) {
      Puzzle puzzle = (Puzzle) o;
      
      /* Compares the size of both puzzles. */
      if (puzzle.getSize() != this.getSize())
        return false;
      
      /* Stores the array of the input puzzle. */
      int[][] tempArray = puzzle.getPuzzleArray();
      
      /* Iterate through rows of puzzles. */
      for (int i = 0; i < getSize(); i++){
        
        /* Iterate through columns of puzzles. */
        for (int j = 0; j < getSize(); j++){
          
          /* When two values at same index are not the same, puzzles are not equal. */
          if (tempArray[i][j] != puzzleArray[i][j])
            return false;
        }
      }
      
      return true;
    }
    
    return false;
  }
}