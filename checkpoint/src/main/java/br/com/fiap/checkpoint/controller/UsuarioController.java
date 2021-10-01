package br.com.fiap.checkpoint.controller;

import br.com.fiap.checkpoint.model.Usuario;
import br.com.fiap.checkpoint.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/usuario")
    public ModelAndView index() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        ModelAndView modelAndView = new ModelAndView("usuarios");
        modelAndView.addObject("usuarios", usuarios);
        return modelAndView;
    }

    @PostMapping("/usuario")
    public String cadastrar(@Valid Usuario usuario, BindingResult result){
        if (result.hasErrors()) return "usuario-form";
        usuarioRepository.save(usuario);
        return "usuarios";
    }

    @RequestMapping("/usuario/novo")
    public String criar(Usuario usuario) {
        return "usuario-form";
    }

    @GetMapping("/usuario/deletar/{id}")
    public String deletar(@PathVariable long id) {
        usuarioRepository.deleteById(id);
        return "usuarios";
    }

    @GetMapping("/usuario/detalhar/{id}")
    public String detalhar(@PathVariable long id, Model model) {
        Usuario usuario = usuarioRepository.getUsuarioById(id);
        model.addAttribute("usuario", usuario);
        return "usuario-detalhe";
    }

    @PostMapping("/usuario/atualizar/{id}")
    public String atualizar(@PathVariable("id") long id, Model model) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID de usuário inválido: " + id));

        model.addAttribute("usuario", usuario);
        return "usuarios";
    }


}
