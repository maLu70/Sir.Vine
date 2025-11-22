package com.ifsp.Sir.Vine.controller;

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

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

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

    @GetMapping("/Carrinho")
    public String carrinho() {
        return "carrinho";
    }

    @GetMapping("/NovoUsuario")
    public String novoUsuario(Model model) {
        model.addAttribute("error", "");
        model.addAttribute("nome", "");
        model.addAttribute("CPF", "");
        model.addAttribute("email", "");
        model.addAttribute("senha", "");
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

    @GetMapping("login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/Perfil")
    public String perfilRedirect() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            return "login";
        }

        String email = auth.getName();
        return "redirect:/Perfil/" + email;
    }

    @GetMapping("/Perfil/{email}")
    public String perfil(@PathVariable String email, Model model, Authentication auth) {
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
    public String logarUsuario(@RequestParam String email, @RequestParam String senha, HttpServletRequest request) {

        try {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, senha);

            Authentication authentication = authenticationManager.authenticate(authToken);

            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authentication);

            HttpSession session = request.getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
            System.out.println("Usuário logado com sucesso: " + email);
            return "redirect:/";

        } catch (Exception e) {
            System.out.println("Falha ao autenticar o usuário: " + e);
            return "login";
        }
    }

    @PostMapping("/sairUsuario")
    public String sairUsuario(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        SecurityContextHolder.clearContext();
        return "redirect:/";
    }
}
