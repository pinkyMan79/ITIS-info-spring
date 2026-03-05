package it.is.io.file;

import it.is.io.bytes.FileIOLocalFSService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;

public class FileIOLocalFSServiceTest {

    @Test
    @DisplayName("When file is created and data exists, then data possibly to read")
    public void whenFileCreatedAndDataWroteToFile_thenDataPossiblyToConsume() {
        System.out.println(124 & 1);
    }
}
