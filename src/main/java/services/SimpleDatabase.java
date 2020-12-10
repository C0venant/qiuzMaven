package services;

import database.interfaces.Database;
import entities.questions.interfaces.Question;
import entities.quiz.interfaces.Quiz;

import java.util.*;

public class SimpleDatabase implements Database {
    private List<Question> questionList;
    private List<Quiz> quizList;

    public SimpleDatabase() {
        quizList = new ArrayList<>();
        questionList = new ArrayList<>();
    }

    @Override
    public void addQuestion(Question question) {
        questionList.add(question);
    }

    @Override
    public void addQuiz(Quiz quiz) {
        quizList.add(quiz);
    }

    @Override
    public List<Question> getAllQuestions() {
        return questionList;
    }

    @Override
    public List<Quiz> getAllQuizzes() {
        return quizList;
    }

    @Override
    public Optional<Quiz> getQuiz(String quizName) {
        return quizList.stream().
                filter(q -> q.getQuizName().equals(quizName)).
                findAny();
    }
}
