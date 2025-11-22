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
import com.ifsp.Sir.Vine.repository.UsuarioRepositorio;
import com.ifsp.Sir.Vine.repository.VinhoRepositorio;
import com.ifsp.Sir.Vine.service.EspumanteService;
import com.ifsp.Sir.Vine.service.VinhoService;
import com.ifsp.Sir.Vine.service.QueijoService;
import com.ifsp.Sir.Vine.model.Espumante;
import com.ifsp.Sir.Vine.model.Queijo;
import com.ifsp.Sir.Vine.model.Vinho;
import com.ifsp.Sir.Vine.repository.EspumanteRepositorio;
import com.ifsp.Sir.Vine.repository.ProdutoRepositorio;

@Controller
public class ControllerSirVine {

    @Autowired
    private ProdutoRepositorio produtoRepositorio;
    @Autowired
    private EspumanteService espumanteService;

    @Autowired
    private QueijoService queijoService;

    @Autowired
    private VinhoService vinhoService;

    @Autowired
    QueijoRepositorio queijoRepositorio;

    @Autowired
    EspumanteRepositorio espumanteRepositorio;

    @Autowired
    VinhoRepositorio vinhoRepositorio;

    @Autowired
    UsuarioRepositorio usuarioRepositorio;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("randomv", vinhoRepositorio.randomVinho());
        model.addAttribute("randome", espumanteRepositorio.randomEspumante());
        model.addAttribute("randomq", queijoRepositorio.randomQueijo());
        model.addAttribute("nvinhos", vinhoRepositorio.tamanho());
        model.addAttribute("nespumantes", espumanteRepositorio.tamanho());
        model.addAttribute("nqueijos", queijoRepositorio.tamanho());
        return "index";
    }

   
    @GetMapping("/Especificacao/{tipo}/{id}")
    public String especificacao(@PathVariable String id, @PathVariable String tipo, Model model) {

        if (tipo.toLowerCase().equals("vinho")) {

            model.addAttribute("produto", vinhoRepositorio.findById(Long.valueOf(id)));
            model.addAttribute("vinho", vinhoRepositorio.findById(Long.valueOf(id)));
            model.addAttribute("queijo", queijoRepositorio.randomQueijo());
            model.addAttribute("espumante", espumanteRepositorio.randomEspumante());
            model.addAttribute("tipo", "vinho");

        } else if (tipo.toLowerCase().equals("espumante")) {

            model.addAttribute("produto", espumanteRepositorio.findById(Long.valueOf(id)));
            model.addAttribute("espumante", espumanteRepositorio.findById(Long.valueOf(id)));
            model.addAttribute("vinho", vinhoRepositorio.randomVinho());
            model.addAttribute("queijo", queijoRepositorio.randomQueijo());
            model.addAttribute("tipo", "espumante");

        } else if (tipo.toLowerCase().equals("queijo")) {

            model.addAttribute("produto", queijoRepositorio.findById(Long.valueOf(id)));
            model.addAttribute("queijo", queijoRepositorio.findById(Long.valueOf(id)));

            model.addAttribute("vinho", vinhoRepositorio.randomVinho());
            model.addAttribute("espumante", espumanteRepositorio.randomEspumante());

            model.addAttribute("tipo", "queijo");
        }

        return "especificacoes";
    }

    @GetMapping("/CadastrarProduto")
    public String cadastrarProduto() {
        return "adicionarProduto";
    }

    @PostMapping("/CriarProduto")
    public String criarProduto(
            @RequestParam Integer radio,

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
            @RequestParam Integer est,

            @RequestParam String nome,
            @RequestParam String desc,
            @RequestParam("image") MultipartFile image) throws IOException {

        if (radio == 1) {
            Vinho vinho = new Vinho(desc, nome, pc, vinhoService.guardarImg(image), cidade, ano, pais, tipoV, teorV,
                    volV, uvaV, est, "vinho");

            vinhoRepositorio.save(vinho);
        } else if (radio == 2) {
            Queijo queijo = new Queijo(desc, nome, pc, queijoService.guardarImg(image), cidade, ano, pais, anmQueijo,
                    tipoQueijo, pesoQueijo, gordQueijo, est, "queijo");

            queijoRepositorio.save(queijo);
        } else if (radio == 3) {
            Espumante espumante = new Espumante(desc, nome, pc, espumanteService.guardarImg(image), cidade, ano, pais,
                    teorEspuma, volEspuma, tipoEspuma, atmEspuma, est, "espumante");

            espumanteRepositorio.save(espumante);
        } else {
            return "redirect:/cadastrarProduto";
        }

        return "redirect:/";
    }

    
    @GetMapping("/Catalogo")
    public String Catalgo(Model model) {
        model.addAttribute("produtos", produtoRepositorio.findAll());
        model.addAttribute("paises", produtoRepositorio.findAllCountries());
        return "catalogo";
    }

    @PostMapping("/FiltrarProdutos")
    public String filtrarProdutos(
            @RequestParam(required = false) String nomeProduto,
            @RequestParam(required = false) String ordenar,
            @RequestParam(required = false) Float precoMin,
            @RequestParam(required = false) Float precoMax,
            @RequestParam(required = false) String radio,
            @RequestParam(required = false) String radiopais,
            Model model) {
        model.addAttribute("produtos",
                produtoRepositorio.filter(precoMin, precoMax, radio, radiopais, ordenar, nomeProduto));
        model.addAttribute("paises", produtoRepositorio.findAllCountries());

        return "catalogo";
    }

    @GetMapping("/CatalogoEspuma")
    public String CatalogoEspuma(Model model) {
        model.addAttribute("produtos", produtoRepositorio.filter(null, null, "espumante", null, "az", null));
        model.addAttribute("paises", produtoRepositorio.findAllCountries());
        return "catalogo";
    }

    @GetMapping("/CatalogoQueijo")
    public String CatalogoQueijo(Model model) {
        model.addAttribute("produtos", produtoRepositorio.filter(null, null, "queijo", null, "az", null));
        model.addAttribute("paises", produtoRepositorio.findAllCountries());
        return "catalogo";
    }

    @GetMapping("/CatalogoVinho")
    public String CatalogoVinho(Model model) {
        model.addAttribute("produtos", produtoRepositorio.filter(null, null, "vinho", null, "az", null));
        model.addAttribute("paises", produtoRepositorio.findAllCountries());
        return "catalogo";
    }

    @GetMapping("/editarProduto/{tipo}/{id}")
    public String editarProduto(@PathVariable String id, @PathVariable String tipo, Model model) {

        if (tipo.toLowerCase().equals("vinho")) {

            model.addAttribute("produto", produtoRepositorio.findById(Long.valueOf(id)));
            model.addAttribute("vinho", vinhoRepositorio.findById(Long.valueOf(id)));
            model.addAttribute("queijo", new Queijo());
            model.addAttribute("espumante", new Espumante());

        } else if (tipo.toLowerCase().equals("espumante")) {

            model.addAttribute("produto", espumanteRepositorio.findById(Long.valueOf(id)));
            model.addAttribute("espumante", espumanteRepositorio.findById(Long.valueOf(id)));
            model.addAttribute("vinho", new Vinho());
            model.addAttribute("queijo", new Queijo());

        } else if (tipo.toLowerCase().equals("queijo")) {

            model.addAttribute("produto", queijoRepositorio.findById(Long.valueOf(id)));
            model.addAttribute("queijo", queijoRepositorio.findById(Long.valueOf(id)));
            model.addAttribute("vinho", new Vinho());
            model.addAttribute("espumante", new Espumante());
        }

        return "editarProduto";
    }

    @PostMapping("/DeletarProduto/{tipo}/{id}")
    public String deletarProduto(@PathVariable String id, @PathVariable String tipo, Model model) {

        if (tipo.toLowerCase().equals("vinho")) {
            vinhoRepositorio.delete(Long.valueOf(id));

        } else if (tipo.toLowerCase().equals("espumante")) {
            espumanteRepositorio.delete(Long.valueOf(id));

        } else if (tipo.toLowerCase().equals("queijo")) {
            queijoRepositorio.delete(Long.valueOf(id));

        }

        return "redirect:/Catalogo";
    }

    @PostMapping("/editarProduto/{tipo}/{id}")
    public String editarProdutoPost(@PathVariable String id,
            @PathVariable String tipo,
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
            @RequestParam Integer est,

            @RequestParam String nome,
            @RequestParam String desc,
            @RequestParam("image") MultipartFile image) throws IOException {

        System.out.println("\n\n\n" + tipo + "\n\n\n");

        if (tipo.equals("vinho") || tipo == "vinho") {
            String imagemvinho = vinhoService.guardarImg(image);

            Vinho vinho = new Vinho(desc, nome, pc,
                    imagemvinho, cidade, ano, pais, tipoV, teorV,
                    volV, uvaV, est, "vinho");
            vinho.setId(Long.valueOf(id));
            vinhoRepositorio.update(vinho);

        } else if (tipo.equals("queijo") || tipo == "queijo") {
            String imagemQueijo = queijoService.guardarImg(image);

            Queijo queijo = new Queijo(desc, nome, pc,
                    imagemQueijo, cidade, ano, pais, anmQueijo,
                    tipoQueijo, pesoQueijo, gordQueijo, est, "queijo");
            queijo.setId(Long.valueOf(id));
            queijoRepositorio.update(queijo);

        } else if (tipo.equals("espumante") || tipo == "espumante") {
            String imagemEspumante = espumanteService.guardarImg(image);

            Espumante espumante = new Espumante(desc, nome, pc,
                    imagemEspumante, cidade, ano, pais,
                    teorEspuma, volEspuma, tipoEspuma, atmEspuma, est, "espumante");
            espumante.setId(Long.valueOf(id));
            espumanteRepositorio.update(espumante);

        } else {
            return "redirect:/editarProduto/" + tipo + "/" + id;
        }

        return "redirect:/Especificacao/" + tipo + "/" + id;
    }
}