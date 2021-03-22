package slogo.records;

import java.util.List;
import java.util.Stack;
import slogo.model.InfoBundle;
import slogo.model.parser.ParsingScope;

public record ParserRecord(Stack<ParsingScope> scopeStack, List<String> tokensLeft,
                           String language, InfoBundle environmentInfo,
                           String currCommand) {
}
