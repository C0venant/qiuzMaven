package services;

import entities.questions.interfaces.Question;
import entities.quiz.interfaces.Quiz;

import java.util.ArrayList;
import java.util.List;

class SimpleQuiz implements Quiz {
    private final List<Question> questions;
    private final String quizName;
    private final String quizAuthor;

    public SimpleQuiz(List<Question> questions, String quizName, String quizAuthor) {
        this.questions = questions;
        this.quizName = quizName;
        this.quizAuthor = quizAuthor;
    }

    public SimpleQuiz(String quizName, String quizAuthor) {
        this.questions = new ArrayList<>();
        this.quizName = quizName;
        this.quizAuthor = quizAuthor;
    }

    @Override
    public String getQuizName() {
        return quizName;
    }

    @Override
    public String getQuizAuthor() {
        return quizAuthor;
    }

    @Override
    public List<Question> getQuestions() {
        return questions;
    }

    @Override
    public void addQuestion(Question question) {
        questions.add(question);
    }

    @Override
    public Double getOverallScore() {
        return questions.stream().
                map(Question::getMaxScore).
                reduce(0.0, Double::sum);
    }
}
