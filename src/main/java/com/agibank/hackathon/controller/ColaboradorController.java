package com.agibank.hackathon.controller;

import com.agibank.hackathon.entities.Colaborador;
import com.agibank.hackathon.service.ColaboradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public class ColaboradorController {

    @Autowired
    public ColaboradorService colaboradorService;

    @GetMapping("/colaboradores")
    public List<Colaborador> getColaboradores() {
         return colaboradorService.getAllColaboradores();
    }

}
