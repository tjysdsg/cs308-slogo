package slogo.model;

import java.io.File;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class SerializingTest {

  private static final String DIR = "data/workspaces/";
  private static final String TEST_FILE_NAME = "test.ser";
  private TrackableEnvironment env;

  private File testFile;

  @BeforeEach
  void setUp() {
    testFile = new File(DIR + TEST_FILE_NAME);
    env = EnvironmentFactory.createEnvironment();
  }

  @Test
  void testSerial() {
    env.saveEnv(testFile);
    assertTrue(testFile.exists());
  }

  @Test
  void testDeserial() {

  }
}
