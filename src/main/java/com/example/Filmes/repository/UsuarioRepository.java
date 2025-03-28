package com.example.Filmes.repository;

import com.example.Filmes.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Busca usuário por email (para login)
    Optional<Usuario> findByEmail(String email);

    // Verifica se email já está cadastrado
    boolean existsByEmail(String email);

    // Busca usuários por nome (ignorando maiúsculas/minúsculas)
    List<Usuario> findByNomeContainingIgnoreCase(String nome);
}