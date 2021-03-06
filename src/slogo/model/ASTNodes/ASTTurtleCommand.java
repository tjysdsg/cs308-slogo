package slogo.model.ASTNodes;

import java.util.ArrayList;
import java.util.List;
import slogo.model.InfoBundle;
import slogo.model.Turtle;

/**
 * Base class for commands that applies effects on the turtle(s)
 * <p>
 * The evaluation is done repeatedly on all active turtles
 *
 * @author Jiyang Tang
 */
public abstract class ASTTurtleCommand extends ASTGroupableCommand {

  public ASTTurtleCommand(String name, int numParams) {
    super(name, numParams);
  }

  @Override
  protected final double doEvaluate(InfoBundle info, List<ASTNode> params) {
    ArrayList<Double> _params = new ArrayList<>();
    for (int i = 0; i < getNumParams(); ++i) {
      _params.add(params.get(i).evaluate(info));
    }

    double ret = 0;
    List<Turtle> turtles = info.getActiveTurtles();
    for (Turtle t : turtles) {
      info.setMainTurtle(t.getId());
      ret = evaluateForTurtle(t, _params, info);
    }
    return ret;
  }

  protected abstract double evaluateForTurtle(Turtle turtle, List<Double> parameters,
      InfoBundle info);
}
