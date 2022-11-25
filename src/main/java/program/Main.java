package program;

import kernel.*;
import visitor.Explorer;

/**
 * Program.Main entrypoint of execution for all test.
 **/
public class Main {
  /**
   * Executes Appendix tests.
   **/
  public static void main(String[] args) throws Exception {
    Kernel.main(null);
    Explorer.main(null);
  }
}
