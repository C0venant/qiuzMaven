import Server.Server;
import database.interfaces.Database;
import processor.Processor;
import services.FileUploader;
import services.SimpleDatabaseBuilder;
import services.builder.DatabaseBuilder;
import utils.MarkupUtil;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;


public class Launcher {

    private static Database prepareDatabase(){
        DatabaseBuilder databaseBuilder = new SimpleDatabaseBuilder();
        return databaseBuilder.build();
    }

    public static void main(String[] args) throws IOException {
        File folder = new File(MarkupUtil.RESOURCE_FOLDER);
        File[] listOfFiles = folder.listFiles();
        Processor processor = new Processor(prepareDatabase());
        assert listOfFiles != null;
        FileUploader fileUploader = new FileUploader(Arrays.stream(listOfFiles).map(File::getAbsolutePath).collect(Collectors.toList()), processor);
        fileUploader.startUpload();
        Server server = new Server(9999, processor);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(server::startServer);

    }
}
