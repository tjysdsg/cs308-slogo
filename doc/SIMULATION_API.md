# API Lab Discussion
# Cell Society API Discussion

## Names and NetIDs

Martha Aboagye(mfa23)
Joshua Petitma (jmp157)
Oliver Rodas (oar5)
Jiyang Tang (jt304)

### Simulation API Motivation/Analogies

### External

- setConfig(String name, T value): controller configurates simulation
- getStatsMap(): controller gets the statistics of a simulation
- setState(int r, int c, State s, boolean immediate): cotroller sets the state of a cell
- update(): controller calls this to update the simulation
- getGrid(): controller uses this to get a representation of grid

### Internal

- updateNextStates(): Simulation calls this in `update()` to calculate and update the states

### Controller API Classes/Methods

#### External

- setSpeed(double speed): MainView calls this in a callback way to change game speed
- setPause(): MainView calls this in a callback way to pause
- setResume(): MainView calls this in a callback way to resume
- setStart(): MainView calls this in a callback way to start 
- step(): JavaFX calls to update the game
- setConfig(String filename): MainView calls this in a callback way to set the current config file 

#### Internal

- setXMLParser(String type): called by itself to change current XML parser
- makePopulationGraph(): callbed by itself to make population graph
