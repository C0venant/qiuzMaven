package utils;

import entities.questions.implementations.CorrectAnswer;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class RawFileUtils {
    private static String readLine = "";

    private static Map<Integer, CorrectAnswer> getCorrectAnswers(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(MarkupUtil.RAW_DATA_FOLDER + "/" + fileName));
        HashMap<Integer, CorrectAnswer> answers = new HashMap<>();
        while (true) {
            String rawAnswer = br.readLine();
            if (rawAnswer == null) break;
            String answer = rawAnswer.split(" ")[0];
            int len = answer.length();
            if (len == 0 || !Character.isDigit(answer.charAt(0)) || answer.charAt(len - 1) != '.') continue;
            String exlpanation = rawAnswer.substring(rawAnswer.indexOf(" ") + 1);
            answers.put(Integer.parseInt(answer.substring(0, len - 2)),
                    new CorrectAnswer(answer.substring(len - 2, len - 1), exlpanation));
        }
        return answers;
    }

    private static String parseBody(BufferedReader reader) throws IOException {
        StringBuilder body = new StringBuilder();
        while (readLine.length() > 3 && Character.isDigit(readLine.charAt(0))) {
            String bodyLine = readLine.substring(readLine.indexOf(" ") + 1);
            if (body.length() > 0) {
                body.append("\n").append(bodyLine);
            } else {
                body.append(bodyLine);
            }
            readLine = reader.readLine();
        }
        return body.toString();
    }

    private static String parseProbAnswers(BufferedReader reader, Map<Integer, CorrectAnswer> correctAnswers, int questionNum) throws IOException {
        StringBuilder probAnswers = new StringBuilder();
        if (readLine.charAt(0) == 'A' && readLine.charAt(1) == ' ') {
            CorrectAnswer correctAnswer = correctAnswers.get(questionNum);
            String answer = correctAnswer.getAnswer();
            String explanation = correctAnswer.getExplanation();
            while (readLine != null && !Character.isDigit(readLine.charAt(0))) {
                String point = "0.0";
                if (answer.charAt(0) == readLine.charAt(0)) point = "1.0";
                probAnswers.append("\n").append(readLine).append(MarkupUtil.PROB_ANSWERS_REGEX).append(point);
                readLine = reader.readLine();
            }
            if (explanation.isEmpty()) explanation = MarkupUtil.EMPTY_EXP;
            probAnswers.append("\n").
                    append(MarkupUtil.EXP_SPLITTER).append("\n").
                    append(explanation);
        }
        return probAnswers.toString();
    }

    private static void writeInFile(BufferedWriter writer, String bodyStr, String probAnswersStr) throws IOException {
        String wholeQuestion =
                MarkupUtil.TEST + "\n" + bodyStr +
                        MarkupUtil.QUESTION_BODY_END +
                        probAnswersStr + "\n" +
                        MarkupUtil.QUESTION_SPLITTER + "\n";
        writer.write(wholeQuestion);
        writer.flush();
    }

    public static String rawToMarkupFile(String questionsFile, String answersFile) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(MarkupUtil.RAW_DATA_FOLDER + "/" + questionsFile));
        String newMarkupFileStr = MarkupUtil.MARKUP_DATA_FOLDER + "/" + System.currentTimeMillis() + ".txt";
        File markupFile = new File(newMarkupFileStr);
        markupFile.createNewFile();
        BufferedWriter writer = new BufferedWriter(new FileWriter(markupFile.getAbsolutePath()));
        Map<Integer, CorrectAnswer> correctAnswers = getCorrectAnswers(answersFile);
        int questionNum = 1;

        while (true) {
            readLine = reader.readLine();
            if (readLine == null) break;

            String bodyStr = parseBody(reader);
            if (readLine.isBlank()) continue;
            String probAnswersStr = parseProbAnswers(reader, correctAnswers, questionNum);

            if (!bodyStr.isEmpty()) {
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
