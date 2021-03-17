package slogo.model;

import java.util.ArrayList;
import java.util.HashMap;
import slogo.events.CommandsRecord;
import slogo.events.DisplayCommand;
import slogo.model.ASTNodes.ASTMakeUserInstruction;
import slogo.model.ASTNodes.ASTFunctionCall;


public class CommandTable extends HashMap<String, ASTFunctionCall> {

  InfoBundle infoBundle;

  public CommandTable(InfoBundle infoBundle) {
    super();
    this.infoBundle = infoBundle;
  }

  @Override
  public ASTFunctionCall put(String key, ASTFunctionCall value) {
    ASTFunctionCall ret = super.put(key, value);

    ArrayList<DisplayCommand> funcs = new ArrayList<>();
    for (var entry : this.entrySet()) {
      ASTFunctionCall func = entry.getValue();
      funcs.add(new DisplayCommand(entry.getKey(), func.toString()));
    }
    infoBundle.notifyCommandUpdate(new CommandsRecord(funcs));
    return ret;
  }
}
