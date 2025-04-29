package com.agibank.hackathon.controller;

import com.agibank.hackathon.controller.request.ColaboradorRequest;
import com.agibank.hackathon.controller.request.EquipamentoRequest;
import com.agibank.hackathon.controller.response.ColaboradorResponse;
import com.agibank.hackathon.controller.response.EquipamentoResponse;
import com.agibank.hackathon.entities.Colaborador;
import com.agibank.hackathon.entities.Equipamento;
import com.agibank.hackathon.service.EquipamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PutMapping("/{id}")
    public ResponseEntity<EquipamentoResponse> atualizarEquipamento(@PathVariable String id, @RequestBody EquipamentoRequest equipamentoRequest) {
        try {
            Equipamento equipamento = Equipamento.builder()
                    .tipo(equipamentoRequest.getTipo())
                    .modelo(equipamentoRequest.getModelo())
                    .status(equipamentoRequest.getStatus())
                    .build();
            Equipamento equipamentoSalvo = equipamentoService.atualizar(id, equipamento, equipamentoRequest.getColaboradorId());

            EquipamentoResponse equipamentoResponse = EquipamentoResponse.builder()
                    .tipo(equipamentoSalvo.getTipo())
                    .modelo(equipamentoSalvo.getModelo())
                    .status(equipamentoSalvo.getStatus())
                    .colaboradorId(equipamentoSalvo.getColaborador().getId())
                    .build();

            return new ResponseEntity<>(equipamentoResponse, HttpStatus.OK);
        } catch (RuntimeException e) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            throw e;
        }
    }
}
