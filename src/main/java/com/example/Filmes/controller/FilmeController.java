package com.example.Filmes.controller;

import com.example.Filmes.model.Filme;
import com.example.Filmes.service.FilmeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/filmes")
public class FilmeController {

    @Autowired
    private FilmeService filmeService;

    // Endpoint paginado (GET /api/filmes?page=0&size=10)
    @GetMapping
    public Page<Filme> listarFilmesPaginados(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return filmeService.listarFilmesPaginados(PageRequest.of(page, size));
    }

    // não-paginado
    @GetMapping("/todos")
    public List<Filme> listarTodosFilmes() {
        return filmeService.listarTodosFilmes();
    }

    // Busca filme por ID
    @GetMapping("/{id}")
    public ResponseEntity<Filme> buscarFilmePorId(@PathVariable Long id) {
        Filme filme = filmeService.buscarFilmePorId(id);
        if (filme != null) {
            return new ResponseEntity<>(filme, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Cadastra novo filme
    @PostMapping
    public ResponseEntity<Filme> cadastrarFilme(@RequestBody Filme filme) {
        try {
            Filme novoFilme = filmeService.cadastrarFilme(filme);
            return new ResponseEntity<>(novoFilme, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Atualiza filme existente
    @PutMapping("/{id}")
    public ResponseEntity<Filme> atualizarFilme(@PathVariable Long id, @RequestBody Filme filmeAtualizado) {
        try {
            Filme filme = filmeService.atualizarFilme(id, filmeAtualizado);
            if (filme != null) {
                return new ResponseEntity<>(filme, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Deleta filme
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarFilme(@PathVariable Long id) {
        try {
            boolean deletado = filmeService.deletarFilme(id);
            if (deletado) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}