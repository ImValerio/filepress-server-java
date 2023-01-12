package com.example.serverjava.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class DecompressUtil {

    private final Path source;
    private final Path target;

    public DecompressUtil(Path source, Path target){

        this.source = source;

        this.target = target;
    }

    public void decompressGzip() throws IOException {
        try (FileOutputStream gos = new FileOutputStream(target.toFile());
             GZIPInputStream fis = new GZIPInputStream( new FileInputStream(source.toFile()))) {

            // copy file
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) > 0) {
                gos.write(buffer, 0, len);
            }

        }

    }
}
