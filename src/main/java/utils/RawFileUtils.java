package utils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class RawFileUtils {
    public static final String RAW_DATA_FOLDER = "src/main/resources/rawFiles";
    public static final String MARKUP_DATA_FOLDER = "src/main/resources/markUpFiles";
    private static String read = "";

    private static Map<Integer, String> getCorrectAnswers(String fileName) throws IOException {
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

    private static String parseBody(BufferedReader reader) throws IOException {
        StringBuilder body = new StringBuilder();
        int lineNumber = 0;
        while(read.length() > 3 && Character.isDigit(read.charAt(0))) {
            read = read.substring(read.indexOf(" ") + 1);
            if(lineNumber > 0){
                body.append("\n").append(read);
            } else {
                body.append(read);
            }
            read = reader.readLine();
            lineNumber++;
        }
        return body.toString();
    }

    private static String parseProbAnswers(BufferedReader reader, Map<Integer, String> correctAnswers, int questionNum) throws IOException {
        StringBuilder probAnswers = new StringBuilder();
        if(read.charAt(0) == 'A' && read.charAt(1) == ' ') {
            String correctAnswer = correctAnswers.get(questionNum);
            while(read != null && !Character.isDigit(read.charAt(0))) {
                String point = "0.0";
                if(correctAnswer.charAt(0) == read.charAt(0)) point = "1.0";
                probAnswers.append("\n").append(read).append(MarkupUtil.PROB_ANSWERS_REGEX).append(point);
                read = reader.readLine();
            }
        }
        return probAnswers.toString();
    }

    private static void writeInFile(BufferedWriter writer, String bodyStr, String probAnswersStr) throws IOException {
        String wholeQuestion =
                MarkupUtil.TEST + "\n" + bodyStr +
                        probAnswersStr + "\n" +
                        MarkupUtil.QUESTION_SPLITTER + "\n";
        writer.write(wholeQuestion);
        writer.flush();
    }

    public static String rawToMarkupFile(String questionsFile, String answersFile) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(RAW_DATA_FOLDER + "/" + questionsFile));
        String newMarkupFileStr = MARKUP_DATA_FOLDER + "/" + System.currentTimeMillis() + ".txt";
        File markupFile = new File(newMarkupFileStr);
        markupFile.createNewFile();
        BufferedWriter writer = new BufferedWriter(new FileWriter(markupFile.getAbsolutePath()));
        Map<Integer, String> correctAnswers = getCorrectAnswers(answersFile);
        int questionNum = 1;

        while(true) {
            read = reader.readLine();
            if(read == null) break;

            String bodyStr = parseBody(reader);
            if(read.isBlank()) continue;
            String probAnswersStr = parseProbAnswers(reader, correctAnswers, questionNum);

            if(!bodyStr.isEmpty()){
                writeInFile(writer, bodyStr, probAnswersStr);
                questionNum++;
            }
        }
        return newMarkupFileStr;
    }

    public static void main(String[] args) throws IOException {
        rawToMarkupFile("Chapter01_Java_Basics.txt", "Answers_Chapter1.txt");
    }
}
