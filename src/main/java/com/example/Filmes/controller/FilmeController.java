package com.example.Filmes.controller;

import com.example.Filmes.model.Filme;
import com.example.Filmes.service.FilmeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/filmes")
public class FilmeController {

    @Autowired
    private FilmeService filmeService;

    @GetMapping
    public List<Filme> listarTodos(){
        return filmeService.listarTodosFilmes();
    }
}
