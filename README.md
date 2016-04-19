
Linear Regression
===============


Data Structures/Concepts Used:
==============================
Binary Search Trees,
Structs, Object Oriented Programming (OOP), File I/O, Header and cpp files


Description:
============
Linear Learning Model. 
This program generates 1000 random data points within the region X = [-1,1]x[-1,1]. The program picks two random points, and uses them to 
create a line f(x). THe rest of the points are mapped with +1 on one side, and -1 on another.

Linear Regression is then used to calculate a Hypothesis function, g(x). The function is then used on our Data Set to determine
The insample error, Ein.

The experiment is repeated 1000 times, and the Average Ein is returned.

1000 more data points are generated, and mapped using the orignal weights of the line. The points are tested against g(x), the experiemnt is 
repeated 1000x times, and the average Eout is reported.
 
 
Output:
=======
Average Ein = 0.04631000000000011
Average Eout = 0.04712299999999999

        	