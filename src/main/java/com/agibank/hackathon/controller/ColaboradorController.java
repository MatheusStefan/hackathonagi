package com.agibank.hackathon.controller;

import com.agibank.hackathon.controller.request.ColaboradorRequest;
import com.agibank.hackathon.controller.request.ColaboradorStatusRequest;
import com.agibank.hackathon.controller.response.ColaboradorResponse;
import com.agibank.hackathon.controller.response.EquipamentoResponse;
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
@SuppressWarnings("unused") // Suppress unused class warning
public class ColaboradorController {

    @Autowired
    private ColaboradorService colaboradorService;

    @Autowired
    private EquipamentoService equipamentoService;

    @GetMapping
    @SuppressWarnings("unused") // Suppress unused method warning
    public List<Colaborador> listarColaboradores() {
        return colaboradorService.listarTodosColaboradores();
    }

    @GetMapping("/{id}")
    @SuppressWarnings("unused") // Suppress unused method warning
    public Colaborador listarColaboradorById(@PathVariable String id) {
        return colaboradorService.buscarColaboradorPorId(id); // Updated method name
    }

    @PostMapping
    @SuppressWarnings("unused") // Suppress unused method warning
    public ResponseEntity<ColaboradorResponse> cadastrarColaborador(@RequestBody ColaboradorRequest colaboradorRequest) {
        Colaborador colaborador = Colaborador.builder()
                .nome(colaboradorRequest.getNome())
                .equipamentos(colaboradorRequest.getEquipamentos()) // Added to handle equipment list
                .status(colaboradorRequest.getStatus())
                .build();
        Colaborador colaboradorSalvo = colaboradorService.cadastrarColaborador(colaborador);

        ColaboradorResponse colaboradorResponse = ColaboradorResponse.builder()
                .id(colaboradorSalvo.getId())
                .nome(colaboradorSalvo.getNome())
                .equipamentos(colaboradorSalvo.getEquipamentos()) // Added to response
                .status(colaboradorSalvo.getStatus())
                .build();

        return new ResponseEntity<>(colaboradorResponse, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/equipamentos/{equipamentoId}")
    @SuppressWarnings("unused") // Suppress unused method warning
    public ResponseEntity<EquipamentoResponse> adicionarEquipamento(@PathVariable String id, @PathVariable String equipamentoId) {
        try {
            Equipamento equipamentosAtualizados = colaboradorService.adicionarEquipamento(id, equipamentoId);
            EquipamentoResponse equipamentoResponse = EquipamentoResponse.builder()
                    .tipo(equipamentosAtualizados.getTipo())
                    .modelo(equipamentosAtualizados.getModelo())
                    .status(equipamentosAtualizados.getStatus())
                    .colaboradorId(equipamentosAtualizados.getColaboradorId())
                    .build();
            return new ResponseEntity<>(equipamentoResponse, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    @SuppressWarnings("unused") // Suppress unused method warning
    public ResponseEntity<ColaboradorResponse> atualizarColaborador(@PathVariable String id, @RequestBody ColaboradorRequest colaboradorRequest) {
        try {
            Colaborador colaborador = Colaborador.builder()
                    .nome(colaboradorRequest.getNome())
                    .equipamentos(colaboradorRequest.getEquipamentos()) // Added to handle equipment list
                    .status(colaboradorRequest.getStatus())
                    .build();
            Colaborador colaboradorSalvo = colaboradorService.atualizar(id, colaborador);

            ColaboradorResponse colaboradorResponse = ColaboradorResponse.builder()
                    .id(colaboradorSalvo.getId())
                    .nome(colaboradorSalvo.getNome())
                    .equipamentos(colaboradorSalvo.getEquipamentos()) // Added to response
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