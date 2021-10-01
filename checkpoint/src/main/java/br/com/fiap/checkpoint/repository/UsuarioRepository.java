package br.com.fiap.checkpoint.repository;

import br.com.fiap.checkpoint.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Page<Usuario> findByNomeLike(String nome, Pageable pageable);

    Usuario getUsuarioById(long id);

}
