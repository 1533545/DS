package json;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import org.json.JSONObject;

/**
 * Given a file name, generates a json object of its content and returns it.
 **/
public class JsonReader {
  /**
   * comment.
   */
  public static JSONObject readJson(String fileName) {
    JSONObject jsonObject = null;
    try {
      String json = readJsonFile(fileName);
      jsonObject = new JSONObject(json);
    } catch (FileNotFoundException e) {
      System.out.println(e);
    }
    return jsonObject;
  }

  private static String readJsonFile(String fileName) throws FileNotFoundException {
    File file = new File("src/json/" + fileName);
    return readFile(file);
  }

  private static String readFile(File file) throws FileNotFoundException {
    String fileContent = "";
    Scanner scanner = new Scanner(file);

    while (scanner.hasNext()) {
      fileContent = fileContent.concat(scanner.nextLine());
    }
    scanner.close();

    return fileContent;
  }
}
