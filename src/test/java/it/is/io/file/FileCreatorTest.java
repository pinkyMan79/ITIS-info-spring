package it.is.io.file;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileCreatorTest {

    private FileCreator fileCreator;

    @BeforeEach
    public void setUp() {
        fileCreator = new FileCreator();
    }

    @Test
    public void testCreateFile_thenFileCreated() {
        fileCreator.createFileByPath();
    }

    @Test
    public void testCreateFile_thenThrownException() {
        fileCreator.createFileByPath();
    }

}