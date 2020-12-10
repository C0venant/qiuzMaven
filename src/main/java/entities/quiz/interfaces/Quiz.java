package entities.quiz.interfaces;

import entities.questions.interfaces.Question;

import java.util.List;

public interface Quiz {
    String getQuizName();
    String getQuizAuthor();
    List<Question> getQuestions();
    void addQuestion(Question question);
    Double getOverallScore();
}
