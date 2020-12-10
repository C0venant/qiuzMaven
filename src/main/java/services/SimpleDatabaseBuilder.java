package services;

import database.interfaces.Database;
import services.builder.DatabaseBuilder;

public class SimpleDatabaseBuilder implements DatabaseBuilder {
    @Override
    public Database build() {
        return new SimpleDatabase();
    }
}
