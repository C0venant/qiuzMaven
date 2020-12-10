package entities.questions.implementations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class TestQuestion extends BasicQuestion {
    // String - probable answer, Double - score
    private final Map<String, Double> probableAnswers;

    public TestQuestion(String questionBody, Map<String, Double> probableAnswers) {
        super(questionBody, Collections.max(probableAnswers.values()));
        this.probableAnswers = probableAnswers;
    }

    @Override
    public String toString(){
        StringBuilder ret = new StringBuilder(super.getQuestionBody()+"\n");
        int counter = 1;
        List<String> probAnswersList = new ArrayList<>(probableAnswers.keySet());
        Collections.shuffle(probAnswersList);
        for(String s: probAnswersList){
            ret.append(counter).append(") ").append(s).append("\n");
            counter++;
        }
        return ret.toString();
    }

    @Override
    public Double getAnswerScore(String answer){
        return probableAnswers.getOrDefault(answer, 0.0);
    }

    @Override
    public List<String> getCorrectAnswers(){
        List<String> resList = new ArrayList<>();
        probableAnswers.keySet().forEach(ans -> {
            if(getAnswerScore(ans) > 0.0) resList.add(ans);
        });
        return resList;
    }
}
