(C) matan steinmetz 4/18/2018 from team 3211.

**Camputer:**
Camputer folder contain the code to change the HSV value in the Raspberry via PC, the values for HSV will be save automatic in the rasperry, you can find the HSV vales in "java" directory in file named "Values", make sure the camputer is connecting to the robot will you canging the HSV values.

there is code in java and python:	
 if you whant to use java code:
 * open the java project in eclipse and add the jar from the folder 'NTJar'.
 * run the code and screen will show up.
 
 if you whant to use python code:
 * open the python project and make sure you have Wpilib python network table, if you don't have them download them from her:
   - for all Wpilib python git: https://github.com/robotpy/robotpy-wpilib.
   - for Wpilib python network table git: https://github.com/robotpy/pynetworktables.
 * run the python code and screen will show up, change the last vaulue to 1 to start changing the HSV values. 

Pi:
Pi folder contain the vision code for the raspberry.

* copy the code to the raspberry.
* in the first time i recomend to do build for the code by going to "java" directory and type "./grblew build"
* to run the code go to the "java" directory and run the code buy typing "./grblew run --offline"


good luck, and i hope i help.
