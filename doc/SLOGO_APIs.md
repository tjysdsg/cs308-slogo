# SLogo API Design

## Names and NetIDs

Oliver Rodas (oar5)
Martha Aboagye (mfa23)
Joshua Petitma
Jiyang Tang (jt304)

Questions

1.  Oliver: exact seperation of view from backend/controller.
    1.  How does the file structure for the view look like
    2.  How does the file structure for the controller look like

### Planning Questions

**General Structure**

*   Model: (uses loops, conditionals etc to basically carry out the user instructions (eg; forward 50).

    *   Backend internal- How it executes that instruction.
    *   Backend external-after implementing of the command

*   View: Has buttons and text inputs which it passes to the controller

*   Controller

    *   Uses backend external API to pass information(about new turle locations etc) to view

*   What behaviors (methods) should the turtle have?

    *   Commands: Forward back  left, right, setheading, towards, setxy, pendown, penup, showturtle, hideturtle, home and clearscreen.
    *   Queries: Xcor, Ycor, Pendown? etc

*   Math Operations like sum/difference/product

    *   Handled within the model

*   Boolean Operation like less? greater? etc

*   When does parsing need to take place and what does it need to start properly?

    *   Parsing needs to take place when a user inputs a command and presses the button to execute it.
    *   It needs the text of the command to parse as well as the available commands the turtle can execute

*   What is the result of parsing and who receives it?

    *   The result of parsing is a command recognized by the backend to update the state of th e turtle. The model would receive the command and implement it. So the view sends  the text command to the controller which sends it to the xml parser classes.

*   When are errors detected and how are they reported?

    *   Errors are detected when parsing commands. They would be reported in the controller and displayed by the view

*   What do commands know, when do they know it, and how do they get it?

    *   Commands should know the state of the turtle and what action they do

*   How is the GUI updated after a command has completed execution?

    *   The model tells the controller it's completed execution using a callback
    *   The controller then calls an API exposed by the view to update the UI

*   What behaviors does the result of a command need to have to be used
    by the front end?

### APIs

#### Backend External API

Backend exposes an API that tells about the internal position of the turtle whenever it changes.
The view interacts

*   the model can have a public method that tells controller/view new coordinates of the turtle after it has executed move forward 50.

Backend exposes turtle

```java
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

*   Text field to input commands
*   Controller APIs that interact with model. (eg; sendCommand)
*   Methods to change things such as backgroundcolor, language or other aestetic things

#### Backend Internal API

*   An internal "turtle" class that holds onto its position and direction
*   Command objects that the internal turtle class can execute
*   Concrete classes that implement the needed methods within the backend external API

#### Frontend Internal API

*   Action listener for when GUI events happen (button clicking, text input) that call
    corresponding external controller APIs.
*   Settings page that can modify the view.

### Design

#### Backend Design CRCs

#### Frontend Design CRCs

#### Use Cases

*   The user types 'fd 50' in the command window, sees the turtle move in the display window leaving a trail, and has the command added to the environment's history.

    *   User types in text field, hits enter
    *   Event listener on text field calls controller and passes text
    *   controller passes string command to the Executer which will parse the command.
    *   The command will be passed to an internal "turtle" which will change its y position by +50.
    *   Every time the internal makes a movement it notifies all listeners of its updated location
    *   The view's 'Turtle display' class listens to the events that occur within the turtle and changes it accordingly and leaves a trail based on the previous and new position.
    *   The view (after seeing something has been excuted bc it was notified) saves 'fd 50' to the environment history and clears the textbox.
    *   Profit.

*   The user types '50 fd' in the command window and sees an error message that the command was not formatted correctly.

    *   User types in text field, hits enter
    *   Event listener on text field calls controller and passes text
    *   Controller passes string command to the Executer which will parse the command
    *   Executer determines that the string is not a proper command and throws an exception
    *   This same exception is rethrown by the controller and triggers the GUI altert dialog.
    *   No profit :(.

*   The user types 'pu fd 50 pd fd 50' in the command window and sees the turtle move twice (once without a trail and once with a trail).

    *   User types in text field, hits enter
    *   Event listener on text field calls controller and passes text
    *   controller passes string command to the Executer which will parse the commands into a list.
    *   The command will be passed to an internal "turtle" which will make a change based on each command
        it is passed notifying listeners of the change each time it finishes executing one command
    *   Every time the internal makes a movement it notifies all listeners of its updated location
    *   The view's 'Turtle display' class listens to the events that occur within the turtle and changes
        it accordingly and leaves a trail based on the previous and new position.
    *   The view (after seeing something has been excuted bc it was notified) saves 'fd 50'
        to the environment history and clears the textbox.
    *   Profit.

*   The user changes the color of the environment's background.

    *   The user clicks on the "edit" > "Preferences"
    *   The user navigates to the "background color section
    *   The user changes the color to something dark because dark mode
        is the way to go
    *   The new color is passed to the external API of the view and the
        corresponding method for changing background color.
    *   The setting is saved in an autosave.properties so when the user
        reloads their preferences are saved :)
    *   PROFIT!
