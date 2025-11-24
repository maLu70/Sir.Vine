package com.ifsp.Sir.Vine.controller;

import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ifsp.Sir.Vine.repository.QueijoRepositorio;
import com.ifsp.Sir.Vine.repository.UsuarioRepositorio;
import com.ifsp.Sir.Vine.repository.VinhoRepositorio;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ifsp.Sir.Vine.model.ItemCarrinho;
import com.ifsp.Sir.Vine.model.Usuario;

@Controller
public class ControllerUsuario {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    QueijoRepositorio queijoRepositorio;

    @Autowired
    VinhoRepositorio vinhoRepositorio;

    @Autowired
    UsuarioRepositorio usuarioRepositorio;

    private ObjectMapper mapper = new ObjectMapper();

    @GetMapping("/NovoUsuario")
    public String novoUsuario(Model model, Principal principal, HttpServletRequest request) {
        
        List<ItemCarrinho> carrinho = lerCarrinho(request);
        model.addAttribute("itensCarrinho", carrinho.size());
        model.addAttribute("error", "");
        model.addAttribute("nome", "");
        model.addAttribute("CPF", "");
        model.addAttribute("email", "");
        model.addAttribute("senha", "");
        Usuario usuario = new Usuario();
        if (principal != null) {
            model.addAttribute("usuario", usuario = usuarioRepositorio.findByEmail(principal.getName()));
        } else {
            model.addAttribute("usuario", usuario);
        }
        return "novoUsuario";
    }

    @PostMapping("/CriarUsuario")
    public String criarUsuario(
            @RequestParam String nome,
            @RequestParam String CPF,
            @RequestParam String email,
            @RequestParam String senha,
            @RequestParam String confSenha,
            Model model, Principal principal) {
        Usuario user = new Usuario();
        if (principal != null) {
            model.addAttribute("usuario", user = usuarioRepositorio.findByEmail(principal.getName()));
        } else {
            model.addAttribute("usuario", user);
        }
        if (nome == null || nome.isEmpty() ||
                CPF == null || CPF.isEmpty() ||
                email == null || email.isEmpty() ||
                senha == null || senha.isEmpty() ||
                confSenha == null || confSenha.isEmpty()) {
            model.addAttribute("error", "Por favor, preencha todos os campos.");

            return "novoUsuario";
        } else if (!senha.equals(confSenha)) {

            model.addAttribute("error", "As senhas não coincidem.");
            model.addAttribute("nome", nome);
            model.addAttribute("CPF", CPF);
            model.addAttribute("email", email);
            model.addAttribute("senha", senha);
            return "novoUsuario";

        } else if (usuarioRepositorio.existsByEmail(email)) {
            model.addAttribute("error", "Já existe um usuário cadastrado com esse email.");
            model.addAttribute("nome", nome);
            model.addAttribute("CPF", CPF);
            model.addAttribute("email", email);
            model.addAttribute("senha", senha);

            return "novoUsuario";
        } else {
            Usuario usuario = new Usuario(nome, CPF, email, confSenha);
            usuario.setAdmin(false);
            usuarioRepositorio.save(usuario);

            return "redirect:/login";
        }
    }

    @GetMapping("/login")
    public String login(Model model, Principal principal, HttpServletRequest request) {
        List<ItemCarrinho> carrinho = lerCarrinho(request);
        model.addAttribute("itensCarrinho", carrinho.size());
        model.addAttribute("email", "");
        model.addAttribute("senha", "");
        Usuario usuario = new Usuario();
        if (principal != null) {
            model.addAttribute("usuario", usuario = usuarioRepositorio.findByEmail(principal.getName()));
        } else {
            model.addAttribute("usuario", usuario);
        }
        return "login";
    }

    @GetMapping("/Perfil")
    public String perfilRedirect(Model model, Principal principal, HttpServletRequest request) {
        
        List<ItemCarrinho> carrinho = lerCarrinho(request);
        model.addAttribute("itensCarrinho", carrinho.size());
        Usuario usuario = new Usuario();
        if (principal != null) {
            model.addAttribute("usuario", usuario = usuarioRepositorio.findByEmail(principal.getName()));
        } else {
            model.addAttribute("usuario", usuario);
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            model.addAttribute("email", "");
            model.addAttribute("senha", "");
            return "login";
        }

        String email = auth.getName();
        return "redirect:/Perfil/" + email;
    }

    @GetMapping("/Perfil/{email}")
    public String perfil(@PathVariable String email, Model model, Authentication auth, Principal principal) {
        Usuario usuario = usuarioRepositorio.findByEmail(email);
        model.addAttribute("usuario", usuario);
        return "perfil";
    }

    @PostMapping("/ExcluirUsuario/{email}")
    public String excluirUsuario(Model model, @PathVariable String email, HttpServletRequest request) {
        usuarioRepositorio.delete(email);
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        SecurityContextHolder.clearContext();
        return "redirect:/";
    }

    @PostMapping("/logarUsuario")
    public String logarUsuario(@RequestParam String email, @RequestParam String senha, HttpServletRequest request,
            Model model, Principal principal) {

        Usuario usuario = new Usuario();
        if (principal != null) {
            model.addAttribute("usuario", usuario = usuarioRepositorio.findByEmail(principal.getName()));
        } else {
            model.addAttribute("usuario", usuario);
        }
        try {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, senha);

            Authentication authentication = authenticationManager.authenticate(authToken);

            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authentication);

            HttpSession session = request.getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
            return "redirect:/";

        } catch (Exception e) {
            model.addAttribute("email", email);
            model.addAttribute("senha", senha);
            return "login";
        }
    }

    @PostMapping("/sairUsuario")
    public String sairUsuario(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        SecurityContextHolder.clearContext();
        Cookie cookie = new Cookie("carrinho", "");
        cookie.setMaxAge(60 * 60 * 24);
        cookie.setPath("/");
        response.addCookie(cookie);
        return "redirect:/";
    }

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
}
