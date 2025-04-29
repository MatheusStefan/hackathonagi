package com.agibank.hackathon.controller;

import com.agibank.hackathon.controller.request.ColaboradorRequest;
import com.agibank.hackathon.controller.request.ColaboradorStatusRequest;
import com.agibank.hackathon.controller.response.ColaboradorResponse;
import com.agibank.hackathon.controller.response.ColaboradorStatusResponse;
import com.agibank.hackathon.entities.Colaborador;
import com.agibank.hackathon.entities.Equipamento;
import com.agibank.hackathon.service.ColaboradorService;
import com.agibank.hackathon.service.EquipamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/colaboradores")
public class ColaboradorController {

    @Autowired
    public ColaboradorService colaboradorService;

    @Autowired
    public EquipamentoService equipamentoService;

    @GetMapping
    public List<Colaborador> listarColaboradores() {
        return colaboradorService.listarTodosColaboradores();
    }

    @GetMapping("/{id}")
    public Colaborador listarColaboradorById(@PathVariable String id) {
        return colaboradorService.listarColaboradorById(id);
    }

    @PostMapping
    public ResponseEntity<ColaboradorResponse> cadastrarColaborador(@RequestBody ColaboradorRequest colaboradorRequest) {
        Colaborador colaborador = Colaborador.builder()
                .nome(colaboradorRequest.getNome())
                .equipamentos(colaboradorRequest.getEquipamentos())
                .status(colaboradorRequest.getStatus())
                .build();
        Colaborador colaboradorSalvo = colaboradorService.cadastrarColaborador(colaborador);

        ColaboradorResponse colaboradorResponse = ColaboradorResponse.builder()
                .id(colaboradorSalvo.getId())
                .nome(colaboradorSalvo.getNome())
                .status(colaboradorSalvo.getStatus())
                .build();

        return new ResponseEntity<>(colaboradorResponse, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/equipamentos")
    public ResponseEntity<List<Equipamento>> adicionarEquipamento(@PathVariable String id, @RequestBody List<Equipamento> equipamento) {
        try {
            List<Equipamento> equipamentosAtualizados = colaboradorService.adicionarEquipamento(id, equipamento);
            return new ResponseEntity<>(equipamentosAtualizados, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ColaboradorResponse> atualizarColaborador(@PathVariable String id, @RequestBody ColaboradorRequest colaboradorRequest) {
        try {
            Colaborador colaborador = Colaborador.builder()
                    .nome(colaboradorRequest.getNome())
                    .equipamentos(colaboradorRequest.getEquipamentos())
                    .status(colaboradorRequest.getStatus())
                    .build();
            Colaborador colaboradorSalvo = colaboradorService.atualizar(id, colaborador);

            ColaboradorResponse colaboradorResponse = ColaboradorResponse.builder()
                    .id(colaboradorSalvo.getId())
                    .nome(colaboradorSalvo.getNome())
                    .status(colaboradorSalvo.getStatus())
                    .build();

            return new ResponseEntity<>(colaboradorResponse, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}")
    public void atualizarColaboradorStatus(@PathVariable String id, @RequestBody ColaboradorStatusRequest colaboradorAtualizado) {
        colaboradorService.atualizarStatus(id, colaboradorAtualizado);
    }

    @DeleteMapping("/{id}")
    public void deleteColaborador(@PathVariable String id) {
        colaboradorService.deleteColaborador(id);
    }


}
