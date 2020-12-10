import Server.Server;
import database.interfaces.Database;
import processor.Processor;
import services.FileUploader;
import services.SimpleDatabaseBuilder;
import services.builder.DatabaseBuilder;

import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Launcher {


    private static Database prepareDatabase(){
        DatabaseBuilder databaseBuilder = new SimpleDatabaseBuilder();
        return databaseBuilder.build();
    }

    public static void main(String[] args) throws IOException {
        Processor processor = new Processor(prepareDatabase());
        FileUploader fileUploader = new FileUploader(Collections.singletonList("data.txt"), processor);
        fileUploader.startUpload();
        Server server = new Server(9999, processor);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(server::startServer);

    }
}
