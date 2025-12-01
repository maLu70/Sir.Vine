package com.ifsp.Sir.Vine.controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ifsp.Sir.Vine.repository.EspumanteRepositorio;
import com.ifsp.Sir.Vine.repository.ProdutoRepositorio;
import com.ifsp.Sir.Vine.repository.QueijoRepositorio;
import com.ifsp.Sir.Vine.repository.UsuarioRepositorio;
import com.ifsp.Sir.Vine.repository.VinhoRepositorio;
import com.ifsp.Sir.Vine.model.ItemCarrinho;
import com.ifsp.Sir.Vine.model.Produto;
import com.ifsp.Sir.Vine.model.Usuario;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class ControllerCarrinho {

    @Autowired
    private ProdutoRepositorio produtoRepositorio;
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    private ObjectMapper mapper = new ObjectMapper();

    private List<ItemCarrinho> lerCarrinho(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null)
            return new ArrayList<>();
        for (Cookie c : cookies) {
            if (c.getName().equals("carrinho")) {
                try {
                    String value = java.net.URLDecoder.decode(c.getValue(), StandardCharsets.UTF_8);
                    return mapper.readValue(value, new TypeReference<List<ItemCarrinho>>() {
                    });
                } catch (Exception e) {
                    return new ArrayList<>();
                }
            }
        }
        return new ArrayList<>();
    }

    private void salvarCarrinho(HttpServletResponse response, List<ItemCarrinho> carrinho) {
        try {
            String json = mapper.writeValueAsString(carrinho);
            String encoded = URLEncoder.encode(json, StandardCharsets.UTF_8);
            Cookie cookie = new Cookie("carrinho", encoded);
            cookie.setMaxAge(60 * 60 * 24);
            cookie.setPath("/");
            cookie.setHttpOnly(false);
            response.addCookie(cookie);
        } catch (Exception e) {
        }
    }

    @PostMapping("/Carrinho/Adicionar/{id}/{quantidade}")
    public String adicionarItem(HttpServletRequest request, HttpServletResponse response,
            @PathVariable String id, @PathVariable String quantidade) {

        String tipo = produtoRepositorio.findById(Long.parseLong(id)).getTipo_do_produto();
        List<ItemCarrinho> carrinho = lerCarrinho(request);

        for (ItemCarrinho item : carrinho) {
            if (item.getId().equals(id) && item.getTipo().equals(tipo)) {
                item.setQtd(item.getQtd() + Integer.parseInt(quantidade));
                salvarCarrinho(response, carrinho);
                return "redirect:/Carrinho";
            }
        }
        carrinho.add(new ItemCarrinho(Long.parseLong(id), tipo, Integer.parseInt(quantidade)));
        salvarCarrinho(response, carrinho);
        return "redirect:/Carrinho";
    }

    @PostMapping("/Carrinho/Adicionar/{id}")
    public String adicionarItemsemPath(HttpServletRequest request, HttpServletResponse response,
            @PathVariable String id, @RequestParam String quantidade) {

        String tipo = produtoRepositorio.findById(Long.parseLong(id)).getTipo_do_produto();
        List<ItemCarrinho> carrinho = lerCarrinho(request);

        for (ItemCarrinho item : carrinho) {
            if (item.getId().equals(id) && item.getTipo().equals(tipo)) {
                item.setQtd(item.getQtd() + Integer.parseInt(quantidade));
                salvarCarrinho(response, carrinho);
                return "redirect:/Carrinho";
            }
        }

        carrinho.add(new ItemCarrinho(Long.parseLong(id), tipo, Integer.parseInt(quantidade)));
        salvarCarrinho(response, carrinho);
        return "redirect:/Carrinho";
    }

    @PostMapping("/Carrinho/Aumentar/{id}/{tipo}")
    public String aumentarItem(HttpServletRequest request, HttpServletResponse response,
            @PathVariable Long id, @PathVariable String tipo) {

        List<ItemCarrinho> carrinho = lerCarrinho(request);

        for (ItemCarrinho item : carrinho) {
            if (item.getId().equals(id) && item.getTipo().equals(tipo)) {
                item.setQtd(item.getQtd() + 1);
                salvarCarrinho(response, carrinho);
                return "redirect:/Carrinho";
            }
        }

        return "redirect:/Carrinho";
    }

    @PostMapping("/Carrinho/Diminuir/{id}/{tipo}")
    public String diminuirItem(HttpServletRequest request, HttpServletResponse response,
            @PathVariable Long id, @PathVariable String tipo) {

        List<ItemCarrinho> carrinho = lerCarrinho(request);

        for (ItemCarrinho item : carrinho) {
            if (item.getId().equals(id) && item.getTipo().equals(tipo)) {
                if (item.getQtd() > 1) {
                    item.setQtd(item.getQtd() - 1);
                } else {
                    carrinho.remove(item);
                }
                salvarCarrinho(response, carrinho);
                return "redirect:/Carrinho";
            }
        }

        return "redirect:/Carrinho";
    }

    @GetMapping("/Carrinho/Remover/{id}/{tipo}")
    public String removerItem(HttpServletRequest request, HttpServletResponse response,
            @PathVariable Long id, @PathVariable String tipo) {

        List<ItemCarrinho> carrinho = lerCarrinho(request);

        for (int index = 0; index < carrinho.size(); index++) {
            ItemCarrinho item = carrinho.get(index);
            if (item.getId().equals(id) && item.getTipo().equals(tipo)) {
                carrinho.remove(index);
                break;
            }
        }

        salvarCarrinho(response, carrinho);
        return "redirect:/Carrinho";
    }

    @GetMapping("/Carrinho")
    public String paginaCarrinho(HttpServletRequest request, Model model, Principal principal) {

        Usuario usuario = new Usuario();
        if (principal != null) {
            model.addAttribute("usuario", usuario = usuarioRepositorio.findByEmail(principal.getName()));
        } else {
            model.addAttribute("usuario", usuario);
        }
        List<ItemCarrinho> carrinho = lerCarrinho(request);
        List<Produto> produtos = new ArrayList<>();
        float valorTotal = 0;

        for (ItemCarrinho item : carrinho) {
            Produto produto = produtoRepositorio.findById(item.getId());
            produto.setEstoque(item.getQtd());
            produtos.add(produto);
            valorTotal += produto.getPreco() * item.getQtd();
        }

        model.addAttribute("valorProdutos", valorTotal);
        model.addAttribute("frete", 20);
        model.addAttribute("valorTotal", valorTotal + 20);
        model.addAttribute("carrinho", produtos);
        model.addAttribute("itensCarrinho", carrinho.size());

        return "carrinho";
    }

    @PostMapping("/Carrinho/Esvaziar")
    public String esvaziarCarrinho(HttpServletResponse response, HttpServletRequest request) {
        List<ItemCarrinho> carrinho = lerCarrinho(request);
        List<Produto> produtos = new ArrayList<>();
        for (ItemCarrinho item : carrinho) {
            Produto produto = produtoRepositorio.findById(item.getId());
            produtos.add(produto);
        }
        produtoRepositorio.DiminurQuantidadeByProdutos(produtos);

        Cookie cookie = new Cookie("carrinho", "");
        cookie.setMaxAge(60 * 60 * 24);
        cookie.setPath("/");
        response.addCookie(cookie);
        return "redirect:/";
    }
}
