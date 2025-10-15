package com.ifsp.Sir.Vine.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ifsp.Sir.Vine.repository.QueijoRepositorio;

import com.ifsp.Sir.Vine.repository.VinhoRepositorio;
import com.ifsp.Sir.Vine.service.EspumanteService;
import com.ifsp.Sir.Vine.service.VinhoService;
import com.ifsp.Sir.Vine.service.QueijoService;
import com.ifsp.Sir.Vine.model.Espumante;
import com.ifsp.Sir.Vine.model.Queijo;
import com.ifsp.Sir.Vine.model.Vinho;
import com.ifsp.Sir.Vine.repository.EspumanteRepositorio;

@Controller
public class ControllerSirVine {

    @Autowired
    private EspumanteService EspumanteService;

    @Autowired
    private QueijoService QueijoService;

    @Autowired
    private VinhoService VinhoService;

    Model model;
    QueijoRepositorio queijoRepositorio;
    EspumanteRepositorio espumanteRepositorio;
    VinhoRepositorio vinhoRepositorio;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/Carrinho")
    public String carrinho() {
        return "carrinho";
    }

    @GetMapping("/Especificacao/{tipo}/{id}")
    public String especificacao(@PathVariable int id, @PathVariable String tipo) {
        if (tipo.toLowerCase().equals("vinho")) {

            model.addAttribute("produto", vinhoRepositorio.findById(id));
            model.addAttribute("tipo", "vinho");

        } else if (tipo.toLowerCase().equals("espumante")) {

            model.addAttribute("produto", espumanteRepositorio.findById(id));
            model.addAttribute("tipo", "espumante");

        } else if (tipo.toLowerCase().equals("queijo")) {

            model.addAttribute("produto", queijoRepositorio.findById(id));
            model.addAttribute("tipo", "queijo");
        }

        return "especificacao";
    }

    @GetMapping("/CadastrarProduto")
    public String cadastrarProduto() {
        return "adicionarEditarProduto";
    }

    @PostMapping("/CriarProduto")
    public String criarProduto(
            @RequestParam(required = false) Boolean radioVinho,
            @RequestParam(required = false) Boolean radioQueijo,
            @RequestParam(required = false) Boolean radioEspuma,

            @RequestParam(required = false) String volV,
            @RequestParam(required = false) String tipoV,
            @RequestParam(required = false) String teorV,
            @RequestParam(required = false) String uvaV,

            @RequestParam(required = false) String volEspuma,
            @RequestParam(required = false) String tipoEspuma,
            @RequestParam(required = false) String teorEspuma,
            @RequestParam(required = false) String atmEspuma,

            @RequestParam(required = false) String pesoQueijo,
            @RequestParam(required = false) String tipoQueijo,
            @RequestParam(required = false) String gordQueijo,
            @RequestParam(required = false) String anmQueijo,

            @RequestParam String pais,
            @RequestParam String cidade,
            @RequestParam String ano,
            @RequestParam Double pc,
            @RequestParam String est,
            
            @RequestParam String nome,
            @RequestParam String desc,
            @RequestParam("image") MultipartFile image) throws IOException {

        if (radioVinho) {
            Vinho vinho = new Vinho(desc, nome, pc, VinhoService.guardarImg(image), cidade, ano, pais, tipoV, teorV,
                    volV, uvaV);
        } else if (radioQueijo) {
            Queijo queijo = new Queijo(desc, nome, pc, QueijoService.guardarImg(image), cidade, ano, pais, anmQueijo,
                    tipoQueijo, pesoQueijo, gordQueijo);
        } else if (radioQueijo) {
            Espumante espumante = new Espumante(desc, nome, pc, EspumanteService.guardarImg(image), cidade, ano, pais,
                    teorEspuma, volEspuma, tipoEspuma, atmEspuma);
        } else {

            System.out.println("\n  AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA ");
        }

        return "redirect:/";
    }

}
