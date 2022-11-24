//Class JsonWriter, class created to save the tree build in projectComponent in a .txt file

package json;

import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;

/*
 * Writes a new JsonString in a new file.
 * Can generate 2 new files:
 *      - json: .txt file of only 1 line with the json object write as string.
 *      - jsonPrettier: Readable .txt file with the json object passed.
 */
public class JsonWriter {
  public static void saveJson(JSONObject json) {
    try {
      writeJson(json.toString(), "json");
    } catch (IOException e) {
      System.out.println(e);
    }
  }

  public static void saveJsonPrettier(JSONObject json) {
    try {
      writeJson(json.toString(2),"JsonPrettier");
    } catch (IOException e) {
      System.out.println(e);
    }
  }

  private static void writeJson(String text, String fileName) throws IOException {
    FileWriter writer = new FileWriter("src/json/" + fileName + ".txt");
    writer.write(text);
    writer.close();
  }
}
