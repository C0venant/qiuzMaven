package Server;

import entities.ScoringEngine;
import entities.questions.interfaces.Question;
import entities.quiz.interfaces.Quiz;
import processor.Processor;
import services.SimpleQuizBuilder;
import services.builder.QuizBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    ServerSocket serverSocket;
    Processor processor;

    public Server(int port, Processor processor) throws IOException {
        serverSocket = new ServerSocket(port);
        this.processor = processor;
    }

    public void startServer(){
        ExecutorService executorService = Executors.newCachedThreadPool();
        System.out.println("Quiz server started");
        while (true){
            System.out.println("waiting for client");
            try {
                Socket clientSocket = serverSocket.accept();
                System.out.println("client connected");
                Worker worker = new Worker(clientSocket);
                executorService.execute(worker::startJob);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class Worker {
        Socket clientSocket;
        private final BufferedReader br;
        private final PrintWriter pw;

        public Worker(Socket clientSocket) throws IOException {
            this.clientSocket = clientSocket;
            pw = new PrintWriter(clientSocket.getOutputStream());
            br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        }

        private void sendText(String text){
            pw.println(text);
            pw.flush();
        }

        private int getQuestionNumber() throws IOException {
            while (true){
                String line = br.readLine();
                try {
                    return Integer.parseInt(line);
                } catch (NumberFormatException e) {
                    sendText("please enter valid number: ");
                }
            }
        }

        private void launchQuiz(Quiz quiz) throws IOException {
            sendText("this quiz is by: "+quiz.getQuizAuthor());
            sendText("--------------------------"+quiz.getQuizName()+"--------------------------");
            List<Question> questions = quiz.getQuestions();
            List<String> answers = new ArrayList<>();
            for(int i = 0; i < questions.size(); i++){
                sendText("Question #" + i);
                Question question = questions.get(i);
                sendText(question.toString());
                answers.add(br.readLine());
            }
            ScoringEngine scoringEngine = new ScoringEngine(quiz, answers);
            double score = scoringEngine.evaluateScore();
            sendText("your score is: " + score + "/" + quiz.getOverallScore());
            System.out.println("client finished quiz");
        }

        public void startJob(){
            sendText("please enter number of questions: ");
            try {
                int numberOfQuestions = getQuestionNumber();
                QuizBuilder quizBuilder = new SimpleQuizBuilder();
                quizBuilder.setData("random quiz", "socket server",
                        processor.downloadAllQuestions(), numberOfQuestions);
                launchQuiz(quizBuilder.build());
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
