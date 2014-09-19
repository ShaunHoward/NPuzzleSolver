NPuzzleSolver
=============

Solves the N-Puzzle in solvable scenarios. Utilizes depth-first and breadth-firth solving based on arguments entered.

READ_ME for N = n x n - 1 Puzzle Solver project (4). 

I made an N = n x n - 1 implementation of the puzzle, so the user can input a specific puzzle size:

i.e. 3, 8, 15, to make a new puzzle. The user can also input a custom puzzle to start with as integers. 

To run the program, open a command prompt and make sure you navigate to the directory that the "Puzzle.jar" 
file is in. Then type "java -jar Puzzle.jar". Hit enter. ***See note about memory usage below.

After this, you will enter arguments for the puzzle solver.
The arguments will be in the form "size search type state type integers(if custom state)". An example of 
how to run this program with 8-puzzle of initial state as goal state and breadth-first search is :
"java -jar Puzzle.jar" then enter then "8 bfs goal" then hit enter. The initial puzzle states will then appear in console, and
the search algorithm will take some time to solve the puzzle. Once the search is complete, the states to
reach the goal state will be printed to the console along with the time taken to solve the puzzle with that
search algorithm. 

Another example of how to run the puzzle with a custom puzzle using depth-first search is:
"java -jar Puzzle.jar" hit enter, then type "8 dfs custom 0 1 2 3 4 5 6 7 8" then hit enter. This will start the puzzle at the goal 
state, although you can put in any reasonable state that is solvable. The size of the puzzle input must correspond
with the amount of puzzle values input. There should be size + 1 amount of values input to make a custom puzzle. 
 
Both of the above examples will start with an initial, non-randomized puzzle like so:

0 1 2
3 4 5
6 7 8

Or an 8-puzzle in the goal state. 

So the sizes can be integer values like 3, 8, 15, etc. 
The search types are "bfs" for breadth-first search or "dfs" for depth-first search.
The state types are "goal", where user does not enter any values or "custom" in which the puzzle values follow 
the state type. i.e. "java -jar Puzzle.jar" hit enter then "3 bfs goal" then enter or "java -jar Puzzle.jar" hit enter then
"3 bfs custom 0 1 2 3" then enter again.

The custom puzzle values are entered in order of left to right across row, from top to bottom of puzzle. 

i.e. "5 4 0 6 1 8 7 3 2" would make an 8-puzzle, but 8 must be entered as size also:

5 4 0
6 1 8
7 3 2

Moving on, some insight on my programming strategy:

Have a puzzle class that makes an initial puzzle from either a size input or array input (custom values)
and randomizes the puzzle. Class also has other features necessary to solve puzzle.

Have a puzzle state class that represents each state of the puzzle, based on an instance of the initial puzzle.
This class is used for storage in hash set, so it has equals() and hashCode() methods to make it comparable in the
hash set.

Have a puzzle solver class that is an abstract class for both search/solving techniques. Contains a getSequence() 
method that tracks the past states of the puzzle in order to get to the solution. Also records the level of the
puzzle in the tree and the direction of the move to get to that puzzle state like (l = left, r = right, u = up, d = down).

Have a breadth-first solver class to perform breadth-first search of puzzle. This class utilizes a LIFO queue and hash set
to solve puzzle.

Have a depth-first solver class to perform depth-first search of puzzle using recursion. This class utilizes recursion and
a hash set to solve the puzzle. 

Have a puzzle maker class for the main method of the program. Takes the parameters described above and makes a new puzzle
based on those parameters. Then prints puzzle to console, randomizes the puzzle, and prints that puzzle state to console.
Proceeding, the class will perform either a breadth-first search or a depth-first search based on user input. Finally,
class will print the sequence of moves, states of puzzle, levels of states in tree, and the time taken to solve the
puzzle in milliseconds to the console.

***Note: I make sure to check memory usage in both searching techniques because they can both utilize more memory than the 
Java virtual machine allows. Total allocated memory can be changed with a command like "-Xmx256m" to set the total runtime
memory allocation to 256 mb or other values allowed by the jvm. Such may be useful when completing depth-first recursive search 
to solve a large puzzle. 

I believe the output tests itself. I do not have time to produce test code, for it is very tedious for this project to test 
everything, especially with the randomized puzzle states. However, the console output shows each step necessary to solve the puzzle,
from initial state, to randomized state, to state sequences from initial to goal state, and their levels in tree, the move directions
for each, and the amount of time taken to solve puzzle. This output speaks for itself. I consider this my test. I have tried several
different cases of inputs to make puzzle and have a try-catch block to catch any errors on input. Also, I make sure that my code works 
by running several iterations of different puzzle states, including goal and custom states of varying puzzle sizes to make sure puzzle 
works correctly. When the puzzle is a 15 puzzle, the amount of time taken to solve puzzle is very long. I believe such will work if you
have patience and lots of time; however, the 8-puzzle solves in a short amount of time.

The differences between breadth-first search and depth-first search are that bfs takes much less memory and time. Dfs is recursive, so 
it takes a long time and a lot of memory to solve the puzzle, although in some cases it may be faster than bfs. So depending on the 
scenario, dfs may be quicker and more efficient than bfs, but a majority of the time, bfs will dominate dfs in solving the puzzle. 

To sum up everything, this project was very intense and made me realize how to approach game design using static instances and diverse
data structures. Although very time-consuming, I'm glad that we had this project and would like to thank you for taking the time to 
read this READ_ME. It's been an eye-opening semester for me, and I hope that you're doing well. All in all it's been fun.

Sincerely,

Shaun Howard,
Computer Science / Artificial Intelligence
Case Western Reserve University
Class of 2016
