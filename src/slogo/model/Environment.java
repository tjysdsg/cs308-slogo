package slogo.model;

import java.lang.ProcessHandle.Info;
import java.util.Map;
import slogo.events.CommandsInfo;
import slogo.events.TurtleInfo;
import slogo.events.UpdateCommands;
import slogo.events.UpdateTurtle;
import slogo.events.UpdateVariables;
import slogo.events.VariablesInfo;
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

  private class ExecutionEnvironment implements InfoBundle {

    @Override
    public Turtle getTurtle() {
      return null;
    }

    @Override
    public Map<String, ASTNode> getLookupTable() {
      return null;
    }

    public void notifyTurtleUpdate(TurtleInfo info) {
    }

    public void notifyCommandUpdate(CommandsInfo info) {
    }

    public void notifyVariableUpdate(VariablesInfo info) {
    }
  }
}