package com.agibank.hackathon.controller;

import com.agibank.hackathon.entities.Colaborador;
import com.agibank.hackathon.service.ColaboradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/colaboradores")
public class ColaboradorController {

    @Autowired
    public ColaboradorService colaboradorService;

    @GetMapping
    public List<Colaborador> getColaboradores() {
        return colaboradorService.getAllColaboradores();
    }

    @GetMapping("/{id}")
    public Colaborador getColaboradorById(@PathVariable String id) {
        return colaboradorService.getColaboradorById(id);
    }

    @PostMapping
    public void createColaborador(@RequestBody Colaborador colaborador) {
        colaboradorService.createColaborador(colaborador);
    }

    @DeleteMapping("/{id}")
    public void deleteColaborador(@PathVariable String id) {
        colaboradorService.deleteColaborador(id);
    }

}
