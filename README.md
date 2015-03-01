# Real-time Malicious JavaScript Detection using HTMLUnit


####Overview
This is a platform for real time malicious JavaScript detection using HTMLUnit. HTMLUnit can be thought of as a headless browser written in Java.  

Author: Omar Farooq, Professor Doug Szajda    
Version: 15 Apr 2014


The info below doesn't make sense

####Compiling 
```
javac -cp src/*.java -d bin/ 
```
####Running
```
java -cp . bin/TravelingSalesmanGui 
```
 
####Configuration Parameters
- Num Generations: The number of generation the algorith should run for (e.g. 1000).

- Populations Size : The number of individuals (possible solutions) in each population (e.g. 10,000).

- Number of Cities : The number of cities. Can be clicked on the grid or randomly generated (e.g. 25).

- Crossover Probability : The probability that two individuals are crossed.
 (e.g. 0.5)
- Muatation Probability : The probability that an individual mutates (usually low e.g. 0.01).

- Percent Retained : The percentage of the best population retained for the next generation (e.g. 0.2). Allows us to not lose our best solutions.