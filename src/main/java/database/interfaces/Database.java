package database.interfaces;

import entities.questions.interfaces.Question;
import entities.quiz.interfaces.Quiz;

import java.util.List;
import java.util.Optional;

public interface Database {
    void addQuestion(Question question);
    void addQuiz(Quiz quiz);
    List<Question> getAllQuestions();
    List<Quiz> getAllQuizzes();
    Optional<Quiz> getQuiz(String quizName);
}
