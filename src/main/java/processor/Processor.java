package processor;

import database.interfaces.Database;
import entities.questions.interfaces.Question;
import entities.quiz.interfaces.Quiz;

import java.util.List;
import java.util.Optional;

public class Processor {
    private final Database database;

    public Processor(Database database) {
        this.database = database;
    }

    public void uploadQuestion(Question question){
        if(question == null) return;
        database.addQuestion(question);
    }

    public void uploadQuiz(Quiz quiz){
        database.addQuiz(quiz);
    }

    public Quiz getQuiz(String quizName) throws Exception {
        Optional<Quiz> quiz = database.getQuiz(quizName);
        if(quiz.isPresent()){
            return quiz.get();
        } else {
            throw new Exception("Quiz with this name doesn't exist");
        }
    }

    public List<Quiz> downloadAllQuizzes(){
        return database.getAllQuizzes();
    }

    public List<Question> downloadAllQuestions(){
        return database.getAllQuestions();
    }
}
