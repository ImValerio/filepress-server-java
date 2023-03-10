package com.example.serverjava.util;

import com.nixxcode.jvmbrotli.enc.BrotliOutputStream;
import com.nixxcode.jvmbrotli.enc.Encoder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.GZIPOutputStream;

public class CompressUtil {

    private final Path source;
    private final Path target;

    public CompressUtil(Path source, Path target) {

        this.source = source;

        this.target = target;
    }


    public void compressGzip() throws IOException {
        try (GZIPOutputStream gos = new GZIPOutputStream(
                new FileOutputStream(target.toFile()));
             FileInputStream fis = new FileInputStream(source.toFile())) {

            // copy file
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) > 0) {
                gos.write(buffer, 0, len);
            }

        }

    }

    public void compressBrotli() throws IOException {
        // Init file input and output
        FileInputStream inFile = new FileInputStream(source.toFile());
        FileOutputStream outFile = new FileOutputStream(target.toFile());

        Encoder.Parameters params = new Encoder.Parameters().setQuality(6);

        BrotliOutputStream brotliOutputStream = new BrotliOutputStream(outFile, params);

        int read = inFile.read();
        while (read > -1) { // -1 means EOF
            brotliOutputStream.write(read);
            read = inFile.read();
        }

        brotliOutputStream.close();
        inFile.close();
    }
}
