package slogo.model;

import java.util.ArrayList;
import java.util.HashMap;
import slogo.events.CommandsRecord;
import slogo.events.DisplayCommand;
import slogo.events.DisplayVariable;
import slogo.events.VariablesRecord;
import slogo.model.ASTNodes.ASTFunctionDefinition;
import slogo.model.ASTNodes.ASTNode;


public class CommandTable extends HashMap<String, ASTNode> {

  InfoBundle infoBundle;

  public CommandTable(InfoBundle infoBundle) {
    super();
    this.infoBundle = infoBundle;
  }

  @Override
  public ASTNode put(String key, ASTNode value) {
    ASTNode ret = super.put(key, value);

    ArrayList<DisplayCommand> funcs = new ArrayList<>();
    for (var entry : this.entrySet()) {
      ASTFunctionDefinition func = (ASTFunctionDefinition) entry.getValue();
      funcs.add(new DisplayCommand(entry.getKey(), func.toString()));
    }
    infoBundle.notifyCommandUpdate(new CommandsRecord(funcs));
    return ret;
  }
}
