package it.is.io.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class FileCreator {

    private static String FILE_DESTINATION = "/Users/danilaterenin/";
    private static String FILE_NAME = "test.txt";

    public void createFile() throws IOException {
        File file = new File(FILE_DESTINATION, FILE_NAME);
        if (file.exists()) {
            return;
        }
        boolean isCreated = file.createNewFile();
        if (!isCreated) {
            throw new IllegalStateException();
        }
    }

    public File createFileByPath() {
        String fullPath = "%s%s".formatted(FILE_DESTINATION, FILE_NAME);
        Path newFilePath = Path.of(fullPath);
        try {
            newFilePath.toFile().createNewFile();
            return newFilePath.toFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
