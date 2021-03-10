package slogo.model;

import java.util.ArrayList;
import java.util.HashMap;
import slogo.events.DisplayVariable;
import slogo.events.VariablesRecord;
import slogo.model.ASTNodes.ASTNode;


public class VariableTable extends HashMap<String, ASTNode> {

  InfoBundle infoBundle;

  public VariableTable(InfoBundle infoBundle) {
    super();
    this.infoBundle = infoBundle;
  }

  @Override
  public ASTNode put(String key, ASTNode value) {
    ASTNode ret = super.put(key, value);

    ArrayList<DisplayVariable> vars = new ArrayList<>();
    for (var entry : this.entrySet()) {
      double val = entry.getValue().evaluate(infoBundle);
      vars.add(new DisplayVariable(entry.getKey(), Double.toString(val)));
    }
    infoBundle.notifyVariableUpdate(new VariablesRecord(vars));
    return ret;
  }
}
