# FGRemoteApp
This project is an android application of a Flight Gear remote control. The app contains a connection rubric and the controls.

View - display of the controllers- rudder, throttle, and a joystick for aileron and elevator. The view is an observer that updates the movement of the controllers

View Model – the attributes are bound to the controllers so while the user moves one the view model, so each movement is sending a command to the model.

Model – the update of the attributes is translated into sending a command to the Flight Gear over a TCP connection.

Project UML- 
![projectuml](https://github.com/y-jacobs/FGRemoteApp/blob/master/UML%20myremoteapp.png)

DEMO Presentation-
https://github.com/y-jacobs/FGRemoteApp/blob/master/DEMO.mp4
