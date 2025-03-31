package com.example.Filmes.service;

import com.example.Filmes.model.Filme;
import com.example.Filmes.repository.FilmeRepository;
import com.example.Filmes.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class FilmeService {

    @Autowired
    private FilmeRepository filmeRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    // Cadastra um novo filme
    @Transactional
    public Filme cadastrarFilme(Filme filme) {
        return filmeRepository.save(filme);
    }

    // Lista todos os filmes
    public List<Filme> listarTodosFilmes() {
        return filmeRepository.findAll();
    }

    // Lista filmes paginado
    public Page<Filme> listarFilmesPaginados(Pageable pageable) {
        return filmeRepository.findAll(pageable);
    }

    // Busca filme por ID
    public Filme buscarFilmePorId(Long id) {
        Optional<Filme> filmeOptional = filmeRepository.findById(id);
        return filmeOptional.orElse(null);
    }

    // Atualiza um filme existente
    @Transactional
    public Filme atualizarFilme(Long id, Filme filmeAtualizado) {
        Optional<Filme> filmeOptional = filmeRepository.findById(id);

        if (filmeOptional.isPresent()) {
            Filme filmeExistente = filmeOptional.get();

            // Atualiza apenas os campos permitidos
            filmeExistente.setTitulo(filmeAtualizado.getTitulo());
            filmeExistente.setGenero(filmeAtualizado.getGenero());
            filmeExistente.setAno(filmeAtualizado.getAno());

            return filmeRepository.save(filmeExistente);
        }
        return null;
    }

    // Deleta um filme
    @Transactional
    public boolean deletarFilme(Long id) {
        Optional<Filme> filmeOptional = filmeRepository.findById(id);

        if (filmeOptional.isPresent()) {
            // Antes de deletar o filme, deleta as reviews associadas
            reviewRepository.deleteById(id);
            filmeRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Busca filmes por gênero
    public List<Filme> buscarFilmesPorGenero(String genero) {
        return filmeRepository.findByGenero(genero);
    }

    // Busca filmes por ano de lançamento
    public List<Filme> buscarFilmesPorAno(int ano) {
        return filmeRepository.findByAno(ano);
    }


    // Busca filmes por título
    public List<Filme> buscarFilmesPorTitulo(String titulo) {
        return filmeRepository.findByTituloContainingIgnoreCase(titulo);
    }
}