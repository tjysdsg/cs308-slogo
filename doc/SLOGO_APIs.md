# API Lab Discussion
# SLogo API Design

## Names and NetIDs


### Planning Questions

 * What behaviors (methods) should the turtle have?

 * When does parsing need to take place and what does it need to start
 properly?

 * What is the result of parsing and who receives it?

 * When are errors detected and how are they reported?

 * What do commands know, when do they know it, and how do they get it?

 * How is the GUI updated after a command has completed execution?

 * What behaviors does the result of a command need to have to be used
 by the front end?

### APIs
 
#### Backend External API


#### Frontend External API



#### Backend Internal API


#### Frontend Internal API



### Design

#### Backend Design CRCs


#### Frontend Design CRCs



#### Use Cases

 * The user types 'fd 50' in the command window, sees the turtle move in the display window leaving a trail, and has the command added to the environment's history.

 * The user types '50 fd' in the command window and sees an error message that the command was not formatted correctly.

 * The user types 'pu fd 50 pd fd 50' in the command window and sees the turtle move twice (once without a trail and once with a trail).

 * The user changes the color of the environment's background.

