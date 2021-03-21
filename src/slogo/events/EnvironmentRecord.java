package slogo.events;

import java.util.Map;

public record EnvironmentRecord(Map<Integer, String> colorIndexes, int backgroundIndex) {
}
