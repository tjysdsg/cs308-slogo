package slogo.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.model.ASTNodes.ASTNode;
import slogo.model.notifiers.ModelTracker;
import slogo.model.parser.ParserTest;
import slogo.records.TurtleRecord;

public class EnvironmentTest {

  private static final String DIR = "data/environments/";
  private static final String TEST_FILE_NAME = "test.ser";
  private TrackableEnvironment env;
  private File testFile;
  private ModelTracker tracker;
  private Map<String, String> variables;
  private Map<String, String> commands;
  private Map<Integer, TurtleRecord> turtles;

  @BeforeEach
  void setUp() {
    variables = new HashMap<>();
    commands = new HashMap<>();
    turtles = new HashMap<>();

    env = EnvironmentFactory.createEnvironment();
    tracker = env.getTracker();

    tracker.setOnVarUpdate((record) -> {
      for (var variable : record.variables()) {
        variables.put(variable.name(), variable.value());
      }
    });

    tracker.setOnCommandUpdate((record) -> {
      for (var command : record.commands()) {
        commands.put(command.name(), command.signature());
      }
    });

    tracker.setOnTurtleUpdate((record -> {
      turtles.put(record.id(), record);
    }));

    testFile = new File(DIR + TEST_FILE_NAME);
  }

  @Test
  void testSerialize() {
    testFile.delete();
    assertFalse(testFile.exists());

    env.save(testFile);
    assertTrue(testFile.exists());
  }

  @Test
  void testDeserialize() {
    testFile.delete();
    env.addTurtle();

    runFile("procedures_with_parameters/face.slogo");
    env.save(testFile);
    assertTrue(testFile.exists());

    String expected = commands.get("face");

    setUp();

    runFile("procedures_with_parameters/house.slogo");
    env.load(testFile);

    assertTrue(commands.containsKey("face"));
    assertTrue(commands.containsKey("house"));
    assertEquals(3, turtles.size());
    assertEquals(expected, commands.get("face"));
  }

  private static final String EXAMPLE_DIR = "data/examples/";
  void runFile(String toRun) {
    File program = new File(EXAMPLE_DIR + toRun);

    try {
      StringBuilder builder = new StringBuilder();
      Scanner myReader =  new Scanner(program);
      while (myReader.hasNext()) {
        builder.append(myReader.nextLine());
      }

      env.runCommand(builder.toString());

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }
}
