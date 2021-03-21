package slogo.model.parser;

import java.util.List;
import java.util.Stack;
import slogo.model.InfoBundle;

public record ParserRecord(Stack<ParsingScope> scopeStack, List<String> tokensLeft,
                           String language, InfoBundle environmentInfo,
                           String currCommand) {
}
