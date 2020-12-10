package utils;

import entities.questions.implementations.TestQuestion;
import entities.questions.interfaces.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


public class QuestionParser {
    public static final String TEST = "test";
    private final Map<String, Function<BufferedReader, Question>> functionMap ;

    public QuestionParser() {
        functionMap = new HashMap<>();
        functionMap.put(TEST, testFunction);
    }

    private final Function<BufferedReader, Question> testFunction = reader -> {
        try {
            StringBuilder body = new StringBuilder();
            while(true){
                String reading = reader.readLine();
                body.append(reading).append(" ");
                if(reading.endsWith("?") || reading.endsWith(".")) break;
            }
            Map<String, Double> answers = new HashMap<>();
            while(true){
                String[] probAnswer = reader.readLine().split("@");
                if(probAnswer.length == 1) break;
                String answerBody = probAnswer[0];
                Double answerPoint = Double.parseDouble(probAnswer[1]);
                answers.put(answerBody, answerPoint);
            }
            return new TestQuestion(body.toString(), answers);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    };

    public Question getQuestion(String type, BufferedReader reader) throws IOException {
        try {
            return functionMap.get(type).apply(reader);
        } catch (NullPointerException e){
            System.out.println("Such type of question doesn't exist");
            while(!reader.readLine().equals("~~~"));
            return null;
        }
    }
}
