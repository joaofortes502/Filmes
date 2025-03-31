package com.example.Filmes.service;

import com.example.Filmes.model.Filme;
import com.example.Filmes.repository.FilmeRepository;
import com.example.Filmes.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmeService {

    private final FilmeRepository filmeRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public Filme cadastrarFilme(Filme filme) {
        return filmeRepository.save(filme);
    }

    @Transactional(readOnly = true)
    public List<Filme> listarTodosFilmes() {
        return filmeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Page<Filme> listarFilmesPaginados(Pageable pageable) {
        return filmeRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Filme buscarFilmePorId(Long id) {
        return filmeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Filme não encontrado com ID: " + id));
    }

    @Transactional
    public Filme atualizarFilme(Long id, Filme filmeAtualizado) {
        Filme filmeExistente = filmeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Filme não encontrado com ID: " + id));

        filmeExistente.setTitulo(filmeAtualizado.getTitulo());
        filmeExistente.setDescricao(filmeAtualizado.getDescricao());
        filmeExistente.setDataLancamento(filmeAtualizado.getDataLancamento());
        filmeExistente.setPosterPath(filmeAtualizado.getPosterPath());
        filmeExistente.setGenerosIds(filmeAtualizado.getGenerosIds());

        // Atualize outros campos conforme necessário
        return filmeRepository.save(filmeExistente);
    }

    @Transactional
    public boolean deletarFilme(Long id) {
        return filmeRepository.findById(id).map(filme -> {
            reviewRepository.deleteByFilmeId(id);
            filmeRepository.delete(filme);
            return true;
        }).orElse(false);
    }


    @Transactional(readOnly = true)
    public List<Filme> buscarFilmesPorTitulo(String titulo) {
        return filmeRepository.findByTituloContainingIgnoreCase(titulo);
    }
}