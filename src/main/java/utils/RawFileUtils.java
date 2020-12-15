package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RawFileUtils {
    public static final String RAW_DATA_FOLDER = "src/main/resources/rawFiles";

    public static Map<Integer, String> getCorrectAnswers(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(RAW_DATA_FOLDER+ "/" +fileName));
        HashMap<Integer, String> answers = new HashMap<>();
        while (true){
            String rawAnswer = br.readLine();
            if (rawAnswer == null) break;
            String answer = rawAnswer.split(" ")[0];
            int len = answer.length();
            if (len == 0 || !Character.isDigit(answer.charAt(0)) || answer.charAt(len - 1) != '.') continue;
            answers.put(Integer.parseInt(answer.substring(0, len - 2)), answer.substring(len - 2, len - 1));
        }
        return answers;
    }
}
