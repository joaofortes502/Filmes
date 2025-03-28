package com.example.Filmes.service;

import com.example.Filmes.model.Usuario;
import com.example.Filmes.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;



    // Cadastra um novo usuário
    @Transactional
    public Usuario cadastrarUsuario(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("Email já cadastrado");
        }

        usuario.setSenha(usuario.getSenha());
        return usuarioRepository.save(usuario);
    }

    // Busca usuário por ID
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    // Busca usuário por email (para login)
    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    // Atualiza perfil do usuário
    @Transactional
    public Usuario atualizarPerfil(Long usuarioId, Usuario dadosAtualizados) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuario.setNome(dadosAtualizados.getNome());
        return usuarioRepository.save(usuario);
    }
}