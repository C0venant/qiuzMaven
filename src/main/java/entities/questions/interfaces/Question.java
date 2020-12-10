package entities.questions.interfaces;

import java.util.List;

public interface Question {
    String getQuestionBody();
    Double getAnswerScore(String answer);
    Double getMaxScore();
    List<String> getCorrectAnswers();
}
