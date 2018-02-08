## Synopsis
In this project, I am asked to write a program to find a path for a robot to go from a point to another in a maze without colliding with obstacles. This robot consists of a number of fixed-length arms connected together by joints. 

## Input File
This file contains the structure of the maze. The file consists of k+4 lines, where k is the number of obstacles.
The first line is the number of joints, denoted as n.

The next two lines are the start and ending configurations written in x-y coordinate. Each couple of number represents x and y position of a joint.

The fourth line is the number of obstacles.

Each line in the last k lines represents the vertices of each rectangular obstacle. Each rectangular obstacle is written as a quadruple of the x-y coordinates of its vertices in counter-clockwise order, starting with the lower-left vertex.

## Output File
After running the program on the input file, an output text file will be produced.

The file consists of m+2 lines. The first line is the number of primitive steps, denoted as m, and the total length of the path, separated by a single white space. The next line is the initial configuration, and then each line in the next m lines contains the position of each joint at the end of every primitive step.

## Visualiser
The visualiser will read the input + output files and demonstrate the result.

To see the result of a problem. Open the visualiser and load the input + output files.

## Installation
1. Change the working directory into the repository.
2. Run "ant" to build everything or "ant clean" to clean everything
3. The executable jar file will be inside the "dist" folder after building everything.

## To solve a problem
To solve a problem, change the working directory into "dist" and run the program by typing:
"java -jar coordinating-simulation.jar [input file] [output file]"

## To visualise a solved problem
Run the compiled dist/Visualiser.jar and import the input + output files.
