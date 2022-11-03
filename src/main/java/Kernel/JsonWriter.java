package Kernel;

import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;

public class JsonWriter {
        public static void saveJson(JSONObject json) {
            try {
                writeJson(json.toString(),"Json");
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
