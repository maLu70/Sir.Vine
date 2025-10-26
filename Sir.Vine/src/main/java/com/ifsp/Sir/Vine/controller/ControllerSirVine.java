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
import com.ifsp.Sir.Vine.model.Produto;
import com.ifsp.Sir.Vine.model.Queijo;
import com.ifsp.Sir.Vine.model.Usuario;
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
        return "index";
    }

    @GetMapping("/Carrinho")
    public String carrinho() {
        return "carrinho";
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
        return "adicionarEditarProduto";
    }

    @PostMapping("/CriarProduto")
    public String criarProduto(
            @RequestParam(required = false) Integer radioVinho,
            @RequestParam(required = false) Integer radioQueijo,
            @RequestParam(required = false) Integer radioEspuma,

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

        if (radioVinho == 1) {
            Vinho vinho = new Vinho(desc, nome, pc, vinhoService.guardarImg(image), cidade, ano, pais, tipoV, teorV,
                    volV, uvaV, est, "vinho");
            vinhoRepositorio.save(vinho);
        } else if (radioQueijo == 1) {
            Queijo queijo = new Queijo(desc, nome, pc, queijoService.guardarImg(image), cidade, ano, pais, anmQueijo,
                    tipoQueijo, pesoQueijo, gordQueijo, est, "queijo");
            queijoRepositorio.save(queijo);
        } else if (radioEspuma == 1) {
            Espumante espumante = new Espumante(desc, nome, pc, espumanteService.guardarImg(image), cidade, ano, pais,
                    teorEspuma, volEspuma, tipoEspuma, atmEspuma, est, "espumante");
            espumanteRepositorio.save(espumante);
        } else {

            return "redirect:/cadastrarProduto";

        }

        return "redirect:/";
    }

    @GetMapping("/NovoUsuario")
    public String novoUsuario(Model model) {
        model.addAttribute("error", "");
        return "novoUsuario";
    }

    @PostMapping("/CriarUsuario")
    public String criarUsuario(
            @RequestParam String nome,
            @RequestParam String CPF,
            @RequestParam String email,
            @RequestParam String senha,
            @RequestParam String confSenha,
            Model model) {
        if (nome == null || nome.isEmpty() ||
                CPF == null || CPF.isEmpty() ||
                email == null || email.isEmpty() ||
                senha == null || senha.isEmpty() ||
                confSenha == null || confSenha.isEmpty()) {
            model.addAttribute("error", "Por favor, preencha todos os campos.");
            return "novoUsuario";
        } else if (!senha.equals(confSenha)) {
            model.addAttribute("error", "As senhas não coincidem.");
            return "novoUsuario";
        } else {
            Usuario usuario = new Usuario(nome, CPF, email, senha);
            usuarioRepositorio.save(usuario);
            return "redirect:/index";
        }
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
}