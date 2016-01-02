The program then simulates the motion of the system using Newtonian mechanics w/o collisions. (sorry Einstein!)


***INSTRUCTIONS***
Run NBody.java in the src folder.
In the window, go into the 'data' folder and select one of the .txt files. They contain starting 
conditions for various celestial systems.
***



Name: Alex Boldt
NetID: apb34
Hours Spent: 4.5
Consulted With: NONE
Resources Used: Stack Overflow page on how to print arrays: Stack Overflow page about how to print arrays: http://stackoverflow.com/questions/409784/whats-the-simplest-way-to-print-a-java-array

Impressions: Great assignment! You can make really interesting universes and the simulation is actually pretty realistic. it's nice knowing we can code such complicated simulations at our level.

Question 1: What is the final position of the planets after 1,000,000
seconds with a timestep of 25,000?

Earth: (1.46570725796695E11, 2.960357182041392E10)
Mars: (2.265919409933931E11, 2.4055025981476788E10)
Mercury: (3.863596765601349E10, 4.256928645683625E10)
Sun: (26805.98329994436, 3092.3319615824826)
Venus: (1.0243682300236674E11, 3.439142006307294E10)

Question 2: For what values of timeStep, does the simulation no longer behave correctly? 

The simulation starts breaking at dt>=1500000