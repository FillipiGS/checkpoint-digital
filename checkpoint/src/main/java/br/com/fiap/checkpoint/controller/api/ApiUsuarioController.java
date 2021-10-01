package br.com.fiap.checkpoint.controller.api;

import br.com.fiap.checkpoint.model.Usuario;
import br.com.fiap.checkpoint.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuario")
public class ApiUsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping()
    @Cacheable("usuarios")
    public Page<Usuario> index(@RequestParam(required = false) String nome, @PageableDefault(size = 20) Pageable pageable) {
        if (nome == null) {
            return usuarioRepository.findAll(pageable);
        }
        return usuarioRepository.findByNomeLike("%" + nome + "%", pageable);
    }

    @PostMapping()
    @CacheEvict(value = "usuarios", allEntries = true)
    public ResponseEntity<Usuario> create(@RequestBody @Valid Usuario usuario, UriComponentsBuilder uriComponentsBuilder) {
        usuarioRepository.save(usuario);
        URI uri = uriComponentsBuilder.path("/api/usuario/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> get(@PathVariable Long id) {
        return ResponseEntity.of(usuarioRepository.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Usuario> delete(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);

        if (usuario.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        usuarioRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    @CacheEvict(value = "usuarios", allEntries = true)
    public ResponseEntity<Usuario> update(@RequestBody @Valid Usuario novousuario, @PathVariable Long id) {
        Optional<Usuario> optional = usuarioRepository.findById(id);

        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Usuario usuario = optional.get();

        usuario.setNome(novousuario.getNome());
        usuario.setEmail(novousuario.getEmail());
        usuario.setSenha(novousuario.getSenha());

        usuarioRepository.save(usuario);

        return ResponseEntity.ok(usuario);

    }




}
