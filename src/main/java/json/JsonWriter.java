package json;

import java.io.FileWriter;
import java.io.IOException;
import org.json.JSONObject;

/**
 * Writes a new JsonString in a new file.
 * Can generate 2 new files:
 *      - json: .txt file of only 1 line with the json object write as string.
 *      - jsonPrettier: Readable .txt file with the json object passed.
 **/
public class JsonWriter {
  /**
   * Save JSONObject as string in json.txt file.
   **/
  public static void saveJson(JSONObject json) {
    try {
      writeJson(json.toString(), "json");
    } catch (IOException e) {
      System.out.println(e);
    }
  }

  /**
   * Save JSONObject as string with line breaks and a
   * indentFactor of 2 in json.txt file.
   **/
  public static void saveJsonPrettier(JSONObject json) {
    try {
      writeJson(json.toString(2), "JsonPrettier");
    } catch (IOException e) {
      System.out.println(e);
    }
  }

  /**
   * Writes a string in "fileName".txt file
   **/
  private static void writeJson(String text, String fileName) throws IOException {
    FileWriter writer = new FileWriter("src/json/" + fileName + ".txt");
    writer.write(text);
    writer.close();
  }
}
