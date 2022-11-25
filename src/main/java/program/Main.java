package program;

import kernel.Kernel;
import visitor.Explorer;

/**
 * Entrypoint for the execution of all tests.
 **/
public class Main {
  /**
   * Executes all test.
   **/
  public static void main(String[] args) throws Exception {
    Kernel.main(null);
    Explorer.main(null);
  }
}
