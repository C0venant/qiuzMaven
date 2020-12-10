package services;

import entities.questions.interfaces.Question;
import processor.Processor;
import utils.QuestionParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileUploader {

    List<String> files;
    Processor processor;

    public FileUploader(List<String> files, Processor processor){
        this.files = files;
        this.processor = processor;
    }

    public void startUpload(){
        ExecutorService executorService = Executors.newCachedThreadPool();
        for(String filename : files){
            Worker worker = new Worker(filename);
            executorService.execute(worker::startJob);
        }
    }

    private class Worker {
        String fileName;

        public Worker(String fileName){
            this.fileName = fileName;
        }


        public void startJob() {
            try {
                QuestionParser qp = new QuestionParser();
                BufferedReader br = new BufferedReader(new FileReader(fileName));
                while (true){
                    String type = br.readLine();
                    if (type == null) break;
                    if(type.equals("~~~")) continue;
                    Question currQuestion = qp.getQuestion(type, br);
                    processor.uploadQuestion(currQuestion);
                }
            }catch (IOException ignored){}
        }
    }
}
