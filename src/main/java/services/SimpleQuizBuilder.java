package services;

import entities.questions.interfaces.Question;
import entities.quiz.interfaces.Quiz;
import services.builder.QuizBuilder;

import java.util.*;

public class SimpleQuizBuilder implements QuizBuilder {
    private List<Question> questions;
    private String quizName;
    private String quizAuthor;
    private int numberOfQuestions;

    @Override
    public QuizBuilder setData(String quizName, String quizAuthor,
                               List<Question> questions, int numberOfQuestions) {
        this.questions = questions;
        this.quizAuthor = quizAuthor;
        this.quizName = quizName;
        this.numberOfQuestions = numberOfQuestions;
        return this;
    }

    private List<Question> getRandomQuestions(){
        if(numberOfQuestions > questions.size()) numberOfQuestions = questions.size();
        Random random = new Random();
        Set<Integer> used = new HashSet<>();
        List<Question> list = new ArrayList<>();
        while (list.size() != numberOfQuestions){
            int index = random.nextInt(questions.size());
            if(!used.contains(index)){
                used.add(index);
                list.add(questions.get(index));
            }
        }
        return list;

    }

    @Override
    public Quiz build() {
        List<Question> randomQuestions = getRandomQuestions();
        return new SimpleQuiz(randomQuestions, quizName, quizAuthor);
    }
}
