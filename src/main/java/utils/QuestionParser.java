package utils;

import entities.questions.implementations.TestQuestion;
import entities.questions.interfaces.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import static utils.MarkupUtil.*;


public class QuestionParser {
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
                if(reading.endsWith(QUESTION_BODY_END)) break;
            }
            Map<String, Double> answers = new HashMap<>();
            while(true){
                String read = reader.readLine();
                if(read.startsWith(EXP_SPLITTER)){
                    while(!read.equals(QUESTION_SPLITTER)){
                        read = reader.readLine();
                    }
                    break;
                }
                String[] probAnswer = read.split(PROB_ANSWERS_REGEX);
                String answerBody = probAnswer[0];
                Double answerPoint = Double.parseDouble(probAnswer[1]);
                answers.put(answerBody, answerPoint);
            }
            String bodyStr = body.substring(0, body.length() - QUESTION_BODY_END.length() - 1);
            return new TestQuestion(bodyStr, answers);
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
            while(reader.readLine() != null && !reader.readLine().equals(QUESTION_SPLITTER)){}
            return null;
        }
    }
}
