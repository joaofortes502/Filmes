package com.example.Filmes.service;

import com.example.Filmes.model.Usuario;
import com.example.Filmes.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    // Lista todos os usuários
    public List<Usuario> listarTodosUsuarios() {
        return usuarioRepository.findAll();
    }

    // Deleta um usuário por ID
    @Transactional
    public boolean deletarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuário não encontrado para exclusão"); // Ou exceção específica
        }
        // Adicionar lógica aqui se precisar verificar relacionamentos antes de deletar (ex: reviews associadas)
        try {
            usuarioRepository.deleteById(id);
            return true;
        } catch (DataIntegrityViolationException e) { // Exemplo se houver FK constraints
            throw new RuntimeException("Não é possível excluir usuário pois ele possui registros associados.", e);
        }
    }

    // Renomear para consistência (opcional, mas recomendado)
    public Usuario buscarUsuarioPorId(Long id) { // Renomeado de buscarPorId
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado")); // Mantenha ou mude para exceção específica
    }

    // Atualiza usuário (mais genérico que atualizarPerfil)
    @Transactional
    public Usuario atualizarUsuario(Long usuarioId, Usuario dadosAtualizados) { // Renomeado e ajustado
        Usuario usuario = buscarUsuarioPorId(usuarioId); // Reutiliza o método de busca

        // Atualize os campos desejados (exceto senha aqui)
        usuario.setNome(dadosAtualizados.getNome());

        // Exemplo: Atualizar email SE ele foi fornecido e é diferente, com validação
        if (dadosAtualizados.getEmail() != null && !dadosAtualizados.getEmail().equals(usuario.getEmail())) {
            if (usuarioRepository.existsByEmail(dadosAtualizados.getEmail())) {
                throw new RuntimeException("Email já cadastrado por outro usuário.");
            }
            usuario.setEmail(dadosAtualizados.getEmail());
        }
        // Adicione outros campos se necessário

        return usuarioRepository.save(usuario);
    }
}