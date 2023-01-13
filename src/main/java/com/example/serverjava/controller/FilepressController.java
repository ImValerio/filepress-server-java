package com.example.serverjava.controller;

import com.example.serverjava.dto.CompressDTO;
import com.example.serverjava.util.CompressUtil;
import com.example.serverjava.util.DecompressUtil;
import com.example.serverjava.util.FileUploadUtil;
import com.example.serverjava.util.SharedUtil;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class FilepressController {

    private CompressUtil compressUtil;
    private DecompressUtil decompressUtil;


    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/compress/{mode}")

    public ResponseEntity<CompressDTO> compressFile(@RequestParam("file") MultipartFile multipartFile, @PathVariable String mode) throws IOException {
        Path filePath = FileUploadUtil.saveFile(multipartFile.getOriginalFilename(),multipartFile);
        String fileName = SharedUtil.addExtension(filePath.getFileName().toString(),mode);
        Path targetPath = Paths.get("downloads").resolve(fileName);

        CompressUtil compressUtil = new CompressUtil(filePath,targetPath);

        if(mode.equals("gzip")){
            compressUtil.compressGzip();
        }
        compressUtil.compressBrotli();

        CompressDTO compressDTO = new CompressDTO("/"+targetPath.toString(),"10s, 500ms");
        return ResponseEntity.ok(compressDTO);
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/decompress/{mode}")
    public ResponseEntity<CompressDTO> decompressFile(@RequestParam("file") MultipartFile multipartFile, @PathVariable String mode) throws IOException {

        Path filePath = FileUploadUtil.saveFile(multipartFile.getOriginalFilename(),multipartFile);
        String fileName = SharedUtil.removeExtension(filePath.getFileName().toString());
        Path targetPath = Paths.get("downloads").resolve(fileName);

        DecompressUtil decompressUtil = new DecompressUtil(filePath,targetPath);

        if(mode.equals("gzip")){
            decompressUtil.decompressGzip();
        }

        CompressDTO compressDTO = new CompressDTO("/"+targetPath.toString(),"10s, 500ms");
        return ResponseEntity.ok(compressDTO);
    }

    @RequestMapping(path = "/downloads/{fileName}", method = RequestMethod.GET)
    public ResponseEntity<Resource> download( @PathVariable String fileName) throws IOException {
        //TODO: rename file with the original name
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+fileName);

        Path path = Paths.get("downloads").resolve(fileName);
        File file = path.toFile();
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
