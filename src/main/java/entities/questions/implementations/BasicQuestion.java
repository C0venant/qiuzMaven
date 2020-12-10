package entities.questions.implementations;

import entities.questions.interfaces.Question;

import java.util.List;

public class BasicQuestion implements Question {
    private final String questionBody;
    private final Double maxScore;

    public BasicQuestion(String questionBody, Double maxScore) {
        this.questionBody = questionBody;
        this.maxScore = maxScore;
    }

    @Override
    public String getQuestionBody() {
        return questionBody;
    }

    @Override
    public Double getAnswerScore(String answer) {
        return 0.0;
    }

    @Override
    public Double getMaxScore() {
        return maxScore;
    }

    @Override
    public List<String> getCorrectAnswers() {
        return null;
    }

    @Override
    public String toString(){
        return questionBody;
    }
}
