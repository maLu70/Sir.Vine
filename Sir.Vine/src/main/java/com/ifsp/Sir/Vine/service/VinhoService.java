package com.ifsp.Sir.Vine.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class VinhoService {
    public static final String endImg = "Sir.Vine/src/main/resources/static/img/vinhos";

    public String guardarImg(MultipartFile imagem) throws IOException {

        if (imagem == null) {
            System.out.println("Vazio");
        }

        var enderecoArquivo = new File(endImg + File.separator + imagem.getOriginalFilename());

        Files.copy(imagem.getInputStream(), enderecoArquivo.toPath(), StandardCopyOption.REPLACE_EXISTING);

        String endereco = "/img/vinhos/" + imagem.getOriginalFilename();
        return endereco;

    }
    
}
