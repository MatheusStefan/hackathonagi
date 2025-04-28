package com.agibank.hackathon.controller;

import com.agibank.hackathon.entities.Equipamento;
import com.agibank.hackathon.service.EquipamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipamentos")
public class EquipamentoController {

    @Autowired
    private EquipamentoService equipamentoService;

    @PostMapping
    public void Cadastrar(@RequestBody Equipamento equipamento){
        equipamentoService.cadastrarEquipamento(equipamento);
    }

    @GetMapping
    public List<Equipamento> listarEquipamentos(){
        return equipamentoService.listarTodosEquipamentos();
    }

    @GetMapping("/{id}")
    public Equipamento listarEquipamentoById(@PathVariable String id){
        return equipamentoService.listarEquipamentoById(id);
    }

}
