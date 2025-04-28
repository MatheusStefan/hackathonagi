package com.agibank.hackathon.controller;

import com.agibank.hackathon.entities.Equipamento;
import com.agibank.hackathon.service.EquipamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/equipamentos")
public class TesteController {

    @Autowired
    private EquipamentoService equipamentoService;

    @PostMapping
    public void Cadastrar(@RequestBody Equipamento equipamento){
        equipamentoService.cadastrarEquipamento(equipamento);
    }
}
