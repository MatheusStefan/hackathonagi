package com.agibank.hackathon.controller;

import com.agibank.hackathon.controller.request.ColaboradorStatusRequest;
import com.agibank.hackathon.controller.response.ColaboradorStatusResponse;
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
    public List<Colaborador> listarColaboradores() {
        return colaboradorService.listarTodosColaboradores();
    }

    @GetMapping("/{id}")
    public Colaborador listarColaboradorById(@PathVariable String id) {
        return colaboradorService.listarColaboradorById(id);
    }

    @PostMapping
    public void cadastrarColaborador(@RequestBody Colaborador colaborador) {
        colaboradorService.cadastrarColaborador(colaborador);
    }

    @PutMapping("/{id}")
    public void atualizarColaborador(@PathVariable String id, @RequestBody ColaboradorStatusResponse colaboradorAtualizado) {
        colaboradorService.atualizar(id, colaboradorAtualizado);
    }

    @PatchMapping("/{id}")
    public void atualizarColaboradorStatus(@PathVariable String id, @RequestBody ColaboradorStatusResponse colaboradorAtualizado) {
        colaboradorService.atualizar(id, colaboradorAtualizado);
    }

    @DeleteMapping("/{id}")
    public void deleteColaborador(@PathVariable String id) {
        colaboradorService.deleteColaborador(id);
    }

}
