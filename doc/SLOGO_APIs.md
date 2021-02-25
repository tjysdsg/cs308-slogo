# SLogo API Design

## Names and NetIDs

Oliver Rodas (oar5)
Martha Aboagye (mfa23)
Joshua Petitma
Jiyang Tang (jt304)

1. Oliver: exact seperation of view from backend/controller.

### Planning Questions
**General Structure**
- Model: (uses loops, conditionals etc to basically carry out the user instructions (eg; forward 50). 
     + Backend internal- How it executes that instruction. 
     + Backend external-after implementing of the command

    -  
- View: Has buttons and text inputs which it passes to the controller
- Controller
   - Uses backend external API to pass information(about new turle locations etc) to view

 * What behaviors (methods) should the turtle have?
     *Commands: Forward back  left, right, setheading, towards, setxy, pendown, penup, showturtle, hideturtle, home and clearscreen.
     * Queries: Xcor, Ycor, Pendown? etc
* Math Operations like sum/difference/product
    * Handled within the model
* Boolean Operation like less? greater? etc

 * When does parsing need to take place and what does it need to start properly?
     * Parsing needs to take place when a user inputs a command and presses the button to execute it.
     * It needs the text of the command to parse as well as the available commands the turtle can execute

 * What is the result of parsing and who receives it?
     * The result of parsing is a command recognized by the backend to update the state of th e turtle. The model would receive the command and implement it. So the view sends  the text command to the controller which sends it to the xml parser classes. 

 * When are errors detected and how are they reported?
     * Errors are detected when parsing commands. They would be reported in the controller and displayed by the view

 * What do commands know, when do they know it, and how do they get it?
     * Commands should know the state of the turtle and what action they do

 * How is the GUI updated after a command has completed execution?
     * The model tells the controller it's completed execution using a callback
     * The controller then calls an API exposed by the view to update the UI

 * What behaviors does the result of a command need to have to be used
   by the front end?

### APIs
 
#### Backend External API
Backend exposes an API that tells about the internal position of the turtle whenever it changes.
The view interacts

* the model can have a public method that tells controller/view new coordinates of the turtle after it has executed move forward 50.

Backend exposes turtle
```java=
public abstract class TurtleEvent {
   public int getX();
   public int getY();
}

public interface TurtleFunction {
public void execute(TurtleEvent event); 
}

public interface Turtle {
    public void setOnTurtleUpdate(TurtleFunction function)
}
```
The front end exposes a turtle which has a method which allows classes to set a listener
for when the turtle updates. This listener passes an event object to the the method which
allows the view to updated what it needs.
#### Frontend External API
- Text field to input commands
- ???


#### Backend Internal API


#### Frontend Internal API
- Action listener to 


### Design

#### Backend Design CRCs


#### Frontend Design CRCs



#### Use Cases

 * The user types 'fd 50' in the command window, sees the turtle move in the display window leaving a trail, and has the command added to the environment's history.

    - User types in text field, hits enter
    - Event listener on text field calls controller and passes text
    - Controller calls parseCommand on a CommandParser class and receives a command
    - controller passes command to the Executer which will determine what it needs to do with the command
        and modify the turtle accordingly
    - Turtle creates an atomic change to its position based on command.
    - When the turtle is (every) modified the event listener for the turtle will be called that passes event object with information. 

 * The user types '50 fd' in the command window and sees an error message that the command was not formatted correctly.

 * The user types 'pu fd 50 pd fd 50' in the command window and sees the turtle move twice (once without a trail and once with a trail).

 * The user changes the color of the environment's background.
