package howard.puzzle;

/**
 * Class for puzzle states of the puzzle.
 * This class represents any moves of the zero (blank space) from the initial state.
 * 
 * @author Shaun Howard
 */
public class PuzzleState {
 
  /** The puzzle length. */
   private int size;
   
   /** Array of puzzle numbers. */
   private int[][] puzzleArray;
   
   /** Array of the goal state of this puzzle state. */
   private static int[][] goalState;
   
   /** The column of the puzzle state the zero space is in. */
   private int zeroColumn;
   
   /** The row of the puzzle state the zero space is in. */
   private int zeroRow;
   
   /** The level of this puzzle state in the tree. */
   private int level;
   
   /** Tracks goal state, true if state is goal state. */
   private boolean isGoalState;
   
   /** Tracks the move of this puzzle state. */
   private char move;
   
   /** Previous state of this puzzle state. */
   private PuzzleState prev;
   
   /**
    * Constructor to make a new Puzzle for solving from puzzle side size.
    * 
    * @param sizeInput - the length and width of the puzzle
    */
   public PuzzleState(int sizeInput){
     
     /* Set the length of the puzzle. */
     this.size = sizeInput;
     
     /* Make puzzle with sizeInput - 1 amount of columns and rows. */
     puzzleArray = new int[size][size];
     
     /* Make puzzle goal state array for future reference when solving. */
     goalState = new int[size][size];
     
     /* Iterate through the rows of the array. */
     for (int i = 0; i < size; i++) {
       
       /* Iterate through the columns of the array. */
       for (int j = 0; j < size; j++){
         
         /* Set the value at this index as a function of i and j. */
         puzzleArray[i][j] = (i * size) + j;
         
         /* Set the value at this index as a function of i and j. */
         goalState[i][j] = (i * size) + j;
       }
     }
     
     /* Set the initial positions of the zero. */
     setZeroRow(0);
     setZeroColumn(0);
     
     /* Set goal state, initially true. */
     isGoalState = true;
   }
   
   /**
    * Makes new puzzle state from puzzle instance.
    */
   PuzzleState() {
       this(Puzzle.getInstance());
   }

   /**
    * Constructor to make a new puzzle state from a puzzle.
    * 
    * @param puzzle - the puzzle to make the new puzzle state with
    */
   public PuzzleState(Puzzle puzzle){
     
     /* Set the size of the puzzle array to the puzzle's width. */
     this.size = puzzle.getSize();
     
     /* Makes a new puzzle state from input puzzle's dimensions. */
     puzzleArray = new int[size][size];
     
     /* Sets the level of this puzzle state in the search tree. */
     this.level = puzzle.getLevel();
     
     /* Copies the contents of the input puzzle array to this puzzle array. */
     for(int i = 0; i < size; i++){ //Iterate through rows of puzzle.
       for(int j = 0; j < size; j++){ //Iterate through columns of puzzle.
         
         /* Copy value at this index in the puzzle to the puzzle state array. */
         puzzleArray[i][j] = puzzle.getNumber(i, j);
       }
     }
     
        /* Set zero row position. */
        this.zeroRow = puzzle.getZeroRow();
        
        /* Set zero column position. */
        this.zeroColumn = puzzle.getZeroColumn();
        
        /* Check and set goal state of this state. */
        this.isGoalState = puzzle.isGoalState();
     }
   
   /**
    * Copies contents from input puzzle state to this puzzle state. 
    * 
    * @param state - the puzzle state to copy to this state
    */
   private void copy(PuzzleState state){
    
    /* Set size to size of input state. */
    this.size = state.getSize();
    
    /* Set zero row to that of input state. */
    this.zeroRow = state.getZeroRow();
    
    /* Set zero column to that of input state. */
    this.zeroColumn = state.getZeroColumn();
    
    /* Check and set goal state to that of input state. */
    this.isGoalState = state.getGoalState();
    
    /* Initialize puzzle array based on input state side size. */
    puzzleArray = new int[size][size];
    
    /* Copies the contents of the input puzzle array to this puzzle array. */
      for(int i = 0; i < size; i++){ //Iterate through rows of puzzle state.
        for(int j = 0; j < size; j++){ //Iterate through columns of puzzle state.
          
          /* Copy value at this index of input puzzle state to this puzzle array. */
          puzzleArray[i][j] = state.getNumber(i, j); 
        }
      }
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
    return this.puzzleArray[row][column];
   }
   
   /** 
    * Sets the specified number to the specified index of the puzzle array.
    * 
    * @param row - the row of the number in the array
    * @param column - the column of the number in the array
    * @param number - the number to set at the specified index
    */
   public void setNumber(int row, int column, int number){
    /* Sets the value at the specified index of the puzzle to the input number. */
    this.puzzleArray[row][column] = number;
   }
   
   /**
    * Returns whether or not this puzzle state is the goal state.
    * 
    * @return whether or not this puzzle state is the goal state
    */
   public boolean getGoalState(){
    return this.isGoalState;
   }
   
   /**
    * Get the direction of the move to get to this state. 
    * 
    * @return the direction as a character (l, r, u, d) of the move to get to this state
    */
   public char getMove(){
    return this.move;
   }
   
   /**
    * Set the direction of the move to get to this state.
    * 
    * @param c - the direction of the move to get to this state (l = left, r = right, u = up, d = down)
    */
   private void setMove(char c){
    this.move = c;
   }
   
   public PuzzleState getPrev(){
    return prev;
   }
   
   private void setPrev(PuzzleState s){
    this.prev = s;
   }
   
   /**
    * Sets the column position of the zero.
    * 
    * @param column - the column position of the zero in the table
    */
   public void setZeroColumn(int column){
     this.zeroColumn = column;
   }
   
   /**
    * Sets the row position of the zero.
    * 
    * @param row - the row position of the zero in the table
    */
   public void setZeroRow(int row){
     this.zeroRow = row;
   }
   
   /**
    * Returns the column position of the zero. 
    */
   public int getZeroColumn(){
    return this.zeroColumn; 
   }
   
   /** 
    * Returns the row position of the zero.
    */
   public int getZeroRow(){
    return this.zeroRow; 
   }

   /**
    * Moves the zero left in the puzzle.
    */
   public static PuzzleState moveLeft(PuzzleState s){
     
      /* Check if zero can be moved left. */
      if (s.getZeroColumn() <= 0)
        return null;
      
      /* Set the next state to null so it's clear. */
      PuzzleState nextState = null;
      
      /* Attempt to make a new instance of the input puzzle state. */
            try {
             /* Set the next state to the new instance of the input state. */
                    nextState = s.getClass().newInstance();
            } catch (InstantiationException e) {
                System.err.println("Error creating a new down-shifted puzzle state.");
            } catch (IllegalAccessException e) {
                System.err.println("Error accessing new puzzle instance.");
            }
            
            /* Copy contents of input state to new state. */
            nextState.copy(s);

     /* Stores a temporary copy of the value at left of zero. */
     int temp = s.getNumber(s.getZeroRow(), s.getZeroColumn() - 1);
     
     /* Moves zero left one position in puzzle. */
     nextState.setNumber(s.getZeroRow(), s.getZeroColumn() - 1, 0);
     
     /* Moves what was left value to right side of zero. */
     nextState.setNumber(s.getZeroRow(), s.getZeroColumn(), temp);
     
     /* Decrement the zero column counter. */
     nextState.setZeroColumn(s.getZeroColumn() - 1);
     
     /* Set move to left. */
     nextState.setMove('l');
     
     /* Set previous state to input state s. */
     nextState.setPrev(s);
     
     /* Set previous state to input state s. */
     nextState.setLevel(s.getLevel() + 1);
     
     return nextState;

   }
   
   /**
    * Moves the zero right in the puzzle. 
    */
   public static PuzzleState moveRight(PuzzleState s){
    
      /* Check if zero can be moved right. */
      if (s.getZeroColumn() >= s.getSize() - 1)
        return null;
      
      /* Set the next state to null so it's clear. */
      PuzzleState nextState = null;
      
      /* Attempt to make a new instance of the input puzzle state. */
            try {
             /* Set the next state to the new instance of the input state. */
                    nextState = s.getClass().newInstance();
            } catch (InstantiationException e) {
                System.err.println("Error creating a new down-shifted puzzle state.");
            } catch (IllegalAccessException e) {
                System.err.println("Error accessing new puzzle instance.");
            }
            
            /* Copy contents of input state to new state. */
            nextState.copy(s);

     /* Stores a temporary copy of the value at right of zero. */
     int temp = s.getNumber(s.getZeroRow(), s.getZeroColumn() + 1);
     
     /* Moves zero right one position in puzzle. */
     nextState.setNumber(s.getZeroRow(), s.getZeroColumn() + 1, 0);
     
     /* Moves what was right value to left side of zero. */
     nextState.setNumber(s.getZeroRow(), s.getZeroColumn(), temp);
     
     /* Increment the zero column counter. */
     nextState.setZeroColumn(s.getZeroColumn() + 1);
     
     /* Set move to right. */
     nextState.setMove('r');
     
     /* Set previous state to input state s. */
     nextState.setPrev(s);
     
     /* Set previous state to input state s. */
     nextState.setLevel(s.getLevel() + 1);
     
     return nextState;
   }
   
   /**
    * Moves the zero up in the puzzle.
    */
   public static PuzzleState moveUp(PuzzleState s){
    
      /* Check if zero can be moved up. */
      if (s.getZeroRow() <= 0)
        return null;
      
      /* Set the next state to null so it's clear. */
      PuzzleState nextState = null;
      
      /* Attempt to make a new instance of the input puzzle state. */
            try {
             /* Set the next state to the new instance of the input state. */
                    nextState = s.getClass().newInstance();
            } catch (InstantiationException e) {
                System.err.println("Error creating a new down-shifted puzzle state.");
            } catch (IllegalAccessException e) {
                System.err.println("Error accessing new puzzle instance.");
            }
            
            /* Copy contents of input state to new state. */
            nextState.copy(s);

     /* Stores a temporary copy of the value above zero. */
     int temp = s.getNumber(s.getZeroRow() - 1, s.getZeroColumn());
     
     /* Moves zero up one position in puzzle. */
     nextState.setNumber(s.getZeroRow() - 1, s.getZeroColumn(), 0);
     
     /* Moves what was above value to below zero. */
     nextState.setNumber(s.getZeroRow(), s.getZeroColumn(), temp);
     
     /* Decrement the zero row counter. */
     nextState.setZeroRow(s.getZeroRow() - 1);
     
     /* Set move to up. */
     nextState.setMove('u');
     
     /* Set previous state to input state s. */
     nextState.setPrev(s);
     
     /* Set previous state to input state s. */
     nextState.setLevel(s.getLevel() + 1);
     
     return nextState;
   }
   
   /**
    * Moves the zero down in the puzzle.
    */
   public static PuzzleState moveDown(PuzzleState s){
    
      /* Check if zero can be moved down. */
      if(s.getZeroRow() >= s.getSize() - 1)
        return null;
      
      /* Set the next state to null so it's clear. */
      PuzzleState nextState = null;
      
      /* Attempt to make a new instance of the input puzzle state. */
            try {
             /* Set the next state to the new instance of the input state. */
                    nextState = s.getClass().newInstance();
            } catch (InstantiationException e) {
                System.err.println("Error creating a new down-shifted puzzle state.");
            } catch (IllegalAccessException e) {
                System.err.println("Error accessing new puzzle instance.");
            }
            
            /* Copy contents of input state to new state. */
            nextState.copy(s);

     /* Stores a temporary copy of the value below zero. */
     int temp = s.getNumber(s.getZeroRow() + 1, s.getZeroColumn());
     
     /* Moves zero down one position in puzzle. */
     nextState.setNumber(s.getZeroRow() + 1, s.getZeroColumn(), 0);
     
     /* Moves what was below value to above zero. */
     nextState.setNumber(s.getZeroRow(), s.getZeroColumn(), temp);
     
     /* Increment the zero row counter. */
     nextState.setZeroRow(s.getZeroRow() + 1);
     
     /* Set move to down. */
     nextState.setMove('d');
     
     /* Set previous state to input state s. */
     nextState.setPrev(s);
     
     /* Set previous state to input state s. */
     nextState.setLevel(s.getLevel() + 1);
     
     return nextState;
   }
   
   /**
    * Return the array width for the puzzle state.
    * 
    * @return the width of the puzzle state array
    */
   public int getSize(){
     
     return this.size;
   }
   
   /**
    * Returns the array of this puzzle state.
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
      
      /* When value at index is not goal value, not in goal state. */
      if (puzzleArray[i][j] != (i * getSize() + j)){
       
       /* Set goal state to false. */
       isGoalState = false;
       
       return isGoalState;
      }
     }
    }
    
    /* Otherwise, to must be in goal state to make it this far. */
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
     
    /* Checks if o is a puzzle state. */
     if (o instanceof PuzzleState) {
       PuzzleState state = (PuzzleState) o;
       
       /* Compares the size of both puzzle states. */
       if (state.getSize() != this.getSize())
        return false;
       /* Iterate through rows of puzzles. */
       for (int i = 0; i < getSize(); i++){
        
        /* Iterate through columns of puzzles. */
         for (int j = 0; j < getSize(); j++){
          
          /* When two values at same index are not the same, puzzles are not equal. */
           if (state.getNumber(i, j) != puzzleArray[i][j])
             return false;
         }
       }
       return true;
     }
     
     return false;
   }
 }
