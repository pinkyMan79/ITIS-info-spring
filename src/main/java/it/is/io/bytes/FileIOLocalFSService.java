package it.is.io.bytes;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileIOLocalFSService {

    private final InputStream simpleInputStream;
    private final OutputStream simpleOutputStream;

    public FileIOLocalFSService(File file, ImplSource implSource) throws FileNotFoundException {
        switch (implSource) {
            case BUFFER -> {
                this.simpleOutputStream = new BufferedOutputStream(new FileOutputStream(file));
                this.simpleInputStream = new BufferedInputStream(new FileInputStream(file));
            }
            case NON_BUFFER -> {
                this.simpleOutputStream = new FileOutputStream(file);
                this.simpleInputStream = new FileInputStream(file);
            }
            default -> throw new IllegalStateException("Have no implementation for " + implSource);
        }
    }

    public void write(byte[] data) {
        try {
            simpleOutputStream.write(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void flush() {
        try {
            this.simpleOutputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeAndFlush(byte[] data) {
        write(data);
        flush();
    }

    public byte[] read() {
        try {
            return simpleInputStream.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        try {
            this.simpleOutputStream.close();
            this.simpleInputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public enum ImplSource {
        NON_BUFFER,
        BUFFER
    }
}
