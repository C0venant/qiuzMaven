package services.builder.simpleImplementations;

import database.interfaces.Database;
import services.builder.interfaces.DatabaseBuilder;
import services.builder.simpleImplementations.SimpleDatabase;

public class SimpleDatabaseBuilder implements DatabaseBuilder {
    @Override
    public Database build() {
        return new SimpleDatabase();
    }
}
