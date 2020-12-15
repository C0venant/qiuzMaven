package services.builder.interfaces;

import entities.questions.interfaces.Question;
import entities.quiz.interfaces.Quiz;

import java.util.List;

public interface QuizBuilder {
    QuizBuilder setData(String quizName, String quizAuthor, List<Question> questions,int numberOfQuestions);
    Quiz build();
}
