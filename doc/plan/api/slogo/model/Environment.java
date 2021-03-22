package slogo.model;

import java.util.Map;
import java.util.ResourceBundle;
import slogo.records.CommandsRecord;
import slogo.records.TurtleRecord;
import slogo.records.UpdateCommands;
import slogo.records.UpdateTurtle;
import slogo.records.UpdateVariables;
import slogo.records.VariablesRecord;
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