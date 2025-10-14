package com.ifsp.Sir.Vine.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ifsp.Sir.Vine.repository.QueijoRepositorio;

import com.ifsp.Sir.Vine.repository.VinhoRepositorio;
import com.ifsp.Sir.Vine.repository.EspumanteRepositorio;

@Controller
public class ControllerPrincipal {

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

    @GetMapping("/especificacao/{tipo}/{id}")
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

}
