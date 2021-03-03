package slogo.model;

import java.util.Map;
import java.util.ResourceBundle;
import slogo.events.CommandsRecord;
import slogo.events.TurtleRecord;
import slogo.events.UpdateCommands;
import slogo.events.UpdateTurtle;
import slogo.events.UpdateVariables;
import slogo.events.VariablesRecord;
import slogo.model.ASTNodes.ASTNode;

public class Environment implements TrackableEnvironment {


  public void setOnTurtleUpdate(UpdateTurtle callback) {
  }

  public void setOnVariableUpdate(UpdateVariables callback) {
  }

  public void setOnCommandUpdate(UpdateCommands callback) {
  }

  public void runCommand(String command) {
  }

  @Override
  public void setBundle(ResourceBundle bundle) {

  }

  private class ExecutionEnvironment implements InfoBundle {

    @Override
    public Turtle getTurtle() {
      return null;
    }

    @Override
    public Map<String, ASTNode> getLookupTable() {
      return null;
    }

    public void notifyTurtleUpdate(TurtleRecord info) {
    }

    public void notifyCommandUpdate(CommandsRecord info) {
    }

    public void notifyVariableUpdate(VariablesRecord info) {
    }
  }
}