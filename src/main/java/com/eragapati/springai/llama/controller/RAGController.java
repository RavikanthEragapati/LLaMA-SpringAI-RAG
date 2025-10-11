package com.eragapati.springai.llama.controller;

import com.eragapati.springai.llama.service.RAGFeederService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class RAGController {

    private final RAGFeederService ragFeederService;

    @PostMapping("/rag/load")
    public String load(@RequestParam("fromAbsolutePath") String loadFromPath) {
        ragFeederService.loadFromFiles(loadFromPath);
        return "SUCCESS";
    }


    @PostMapping("/rag/offloadToFile")
    public String offLoadToFile() {
        ragFeederService.offloadToFile();
        return "SUCCESS";
    }


    @PostMapping("rag/load/file")
    public String loadFile(@RequestParam(value = "file") final MultipartFile multipartFile) {
        ragFeederService.loadFromFiles(multipartFile);
        return "SUCCESS";
    }


}
