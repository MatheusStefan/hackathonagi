package com.agibank.hackathon.controller;

import com.agibank.hackathon.controller.request.EquipamentoRequest;
import com.agibank.hackathon.controller.response.EquipamentoResponse;
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
    public ResponseEntity<EquipamentoResponse> cadastrar(@RequestBody EquipamentoRequest equipamentoRequest) {
        Equipamento equipamento = Equipamento.builder()
                .tipo(equipamentoRequest.getTipo())
                .modelo(equipamentoRequest.getModelo())
                .status(equipamentoRequest.getStatus())
                .colaboradorId(equipamentoRequest.getColaboradorId())
                .build();

        equipamentoService.cadastrarEquipamento(equipamento);

        EquipamentoResponse response = EquipamentoResponse.builder()
                .tipo(equipamento.getTipo())
                .modelo(equipamento.getModelo())
                .status(equipamento.getStatus())
                .colaboradorId(equipamento.getColaboradorId())
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Equipamento>> listarEquipamentos() {
        return ResponseEntity.ok(equipamentoService.listarTodosEquipamentos());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Equipamento> listarEquipamentoById(@PathVariable String id){
        try {
            Equipamento equipamento = equipamentoService.listarEquipamentoById(id);
            return ResponseEntity.ok(equipamento);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @GetMapping("/colaborador/{colaboradorId}")
    public ResponseEntity<List<Equipamento>> listarEquipamentosPorColaborador(@PathVariable String colaboradorId) {
        List<Equipamento> equipamentos = equipamentoService.listarEquipamentosPorColaborador(colaboradorId);
        return ResponseEntity.ok(equipamentos);
    }
    @PutMapping("/{id}")
    public ResponseEntity<EquipamentoResponse> atualizarEquipamento(@PathVariable String id, @RequestBody EquipamentoRequest equipamentoRequest) {
        try {
            Equipamento equipamento = Equipamento.builder()
                    .tipo(equipamentoRequest.getTipo())
                    .modelo(equipamentoRequest.getModelo())
                    .status(equipamentoRequest.getStatus())
                    .colaboradorId(equipamentoRequest.getColaboradorId())
                    .build();

            Equipamento equipamentoSalvo = equipamentoService.atualizar(id, equipamento, equipamentoRequest.getColaboradorId());

            EquipamentoResponse equipamentoResponse = EquipamentoResponse.builder()
                    .tipo(equipamentoSalvo.getTipo())
                    .modelo(equipamentoSalvo.getModelo())
                    .status(equipamentoSalvo.getStatus())
                    .colaboradorId(equipamentoSalvo.getColaboradorId())
                    .build();

            return new ResponseEntity<>(equipamentoResponse, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
