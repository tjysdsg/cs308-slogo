package slogo.model.parser;

import java.util.List;
import java.util.Stack;
import slogo.model.InfoBundle;
import slogo.model.parser.Scope;

public record ParserRecord(Stack<Scope> scopeStack, List<String> tokensLeft,
                           String language, InfoBundle environmentInfo,
                           String currCommand) {
}
