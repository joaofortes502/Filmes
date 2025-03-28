package com.example.Filmes.service;

import com.example.Filmes.model.Filme;
import com.example.Filmes.repository.FilmeRepository;
import com.example.Filmes.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    // Busca todos os filmes
    public List<Filme> listarTodosFilmes() {
        return filmeRepository.findAll();
    }

    // Busca filmes por gênero
    public List<Filme> buscarPorGenero(String genero) {
        return filmeRepository.findByGenero(genero);
    }

}