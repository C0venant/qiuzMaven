package entities;

import entities.questions.interfaces.Question;
import entities.quiz.interfaces.Quiz;

import java.util.List;

public class ScoringEngine {

    private final Quiz quiz;
    private final List<String> answers;

    public ScoringEngine(Quiz quiz, List<String> answers){
        this.quiz = quiz;
        this.answers = answers;
    }

    public double evaluateScore(){
        double score = 0;
        List<Question> questionsList = quiz.getQuestions();
        for(int i = 0; i < answers.size(); i++){
            score+= questionsList.get(i).getAnswerScore(answers.get(i));
        }
        return score;
    }
}
