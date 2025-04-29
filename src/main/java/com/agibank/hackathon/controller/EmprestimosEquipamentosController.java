package com.agibank.hackathon.controller;

import com.agibank.hackathon.controller.request.EmprestimosEquipamentosRequest;

import com.agibank.hackathon.controller.response.EmprestimosEquipamentosResponse;

import com.agibank.hackathon.entities.EmprestimosEquipamentos;
import com.agibank.hackathon.service.ColaboradorService;
import com.agibank.hackathon.service.EmprestimosEquipamentosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/emprestimo-equipamentos")
public class EmprestimosEquipamentosController {

    @Autowired
    public EmprestimosEquipamentosService emprestimosEquipamentosService;

    @GetMapping
    public List<EmprestimosEquipamentos> listarEmprestimosEquipamentos() {
        return emprestimosEquipamentosService.listarTodosEmprestimosEquipamentos();
    }

    @GetMapping("/{id}")
    public EmprestimosEquipamentos listarEmprestimosEquipamentosById(@PathVariable String id) {
        return emprestimosEquipamentosService.listarEmprestimosEquipamentosById(id);
    }

    @GetMapping("/colaborador/{colaboradorId}")
    public ResponseEntity<List<EmprestimosEquipamentosResponse>> listarEmprestimosPorColaborador(@PathVariable String colaboradorId) {
        try {
            List<EmprestimosEquipamentos> emprestimos = emprestimosEquipamentosService.listarEmprestimosPorColaborador(colaboradorId);

            List<EmprestimosEquipamentosResponse> emprestimosResponse = emprestimos.stream()
                    .map(emprestimo -> EmprestimosEquipamentosResponse.builder()
                            .id(emprestimo.getId())
                            .equipamento(emprestimo.getEquipamento())
                            .colaborador(emprestimo.getColaborador())
                            .data_entrega(emprestimo.getData_entrega())
                            .data_devolucao(emprestimo.getData_devolucao())
                            .status(emprestimo.getStatus())
                            .devolucao(emprestimo.getDevolucao())
                            .build())
                    .collect(Collectors.toList());

            return new ResponseEntity<>(emprestimosResponse, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/equipamento/{equipamentoId}")
    public ResponseEntity<List<EmprestimosEquipamentosResponse>> listarEmprestimosPorEquipamento(@PathVariable String equipamentoId) {
        try {
            List<EmprestimosEquipamentos> emprestimos = emprestimosEquipamentosService.listarEmprestimosPorEquipamento(equipamentoId);

            List<EmprestimosEquipamentosResponse> emprestimosResponse = emprestimos.stream()
                    .map(emprestimo -> EmprestimosEquipamentosResponse.builder()
                            .id(emprestimo.getId())
                            .equipamento(emprestimo.getEquipamento())
                            .colaborador(emprestimo.getColaborador())
                            .data_entrega(emprestimo.getData_entrega())
                            .data_devolucao(emprestimo.getData_devolucao())
                            .status(emprestimo.getStatus())
                            .devolucao(emprestimo.getDevolucao())
                            .build())
                    .collect(Collectors.toList());

            return new ResponseEntity<>(emprestimosResponse, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<EmprestimosEquipamentosResponse> cadastrarEmprestimosEquipamentos(@RequestBody EmprestimosEquipamentosRequest emprestimosEquipamentosRequest) {
        EmprestimosEquipamentos emprestimosEquipamentos = EmprestimosEquipamentos.builder()
                .equipamento(emprestimosEquipamentosRequest.getEquipamento())
                .colaborador(emprestimosEquipamentosRequest.getColaborador())
                .data_entrega(emprestimosEquipamentosRequest.getData_entrega())
                .data_devolucao(emprestimosEquipamentosRequest.getData_devolucao())
                .status(emprestimosEquipamentosRequest.getStatus())
                .data_entrega(emprestimosEquipamentosRequest.getData_entrega())
                .devolucao(emprestimosEquipamentosRequest.getDevolucao())
                .build();
        EmprestimosEquipamentos emprestimosEquipamentosSalvo = emprestimosEquipamentosService.cadastrarEmprestimosEquipamentos(emprestimosEquipamentos);

        EmprestimosEquipamentosResponse emprestimosEquipamentosResponse = EmprestimosEquipamentosResponse.builder()
                .equipamento(emprestimosEquipamentosSalvo.getEquipamento())
                .colaborador(emprestimosEquipamentosSalvo.getColaborador())
                .data_entrega(emprestimosEquipamentosSalvo.getData_entrega())
                .data_devolucao(emprestimosEquipamentosSalvo.getData_devolucao())
                .status(emprestimosEquipamentosSalvo.getStatus())
                .data_entrega(emprestimosEquipamentosSalvo.getData_entrega())
                .devolucao(emprestimosEquipamentosSalvo.getDevolucao())
                .build();

        return new ResponseEntity<>(emprestimosEquipamentosResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmprestimosEquipamentosResponse> atualizarColaborador(@PathVariable String id, @RequestBody EmprestimosEquipamentosRequest emprestimosEquipamentosRequest) {
        try {
            EmprestimosEquipamentos emprestimosEquipamentos = EmprestimosEquipamentos.builder()
                    .equipamento(emprestimosEquipamentosRequest.getEquipamento())
                    .colaborador(emprestimosEquipamentosRequest.getColaborador())
                    .data_entrega(emprestimosEquipamentosRequest.getData_entrega())
                    .data_devolucao(emprestimosEquipamentosRequest.getData_devolucao())
                    .status(emprestimosEquipamentosRequest.getStatus())
                    .data_entrega(emprestimosEquipamentosRequest.getData_entrega())
                    .devolucao(emprestimosEquipamentosRequest.getDevolucao())
                    .build();
            EmprestimosEquipamentos emprestimosEquipamentosSalvo = emprestimosEquipamentosService.cadastrarEmprestimosEquipamentos(emprestimosEquipamentos);

            EmprestimosEquipamentosResponse emprestimosEquipamentosResponse = EmprestimosEquipamentosResponse.builder()
                    .id(emprestimosEquipamentosSalvo.getId())
                    .equipamento(emprestimosEquipamentosSalvo.getEquipamento())
                    .colaborador(emprestimosEquipamentosSalvo.getColaborador())
                    .data_entrega(emprestimosEquipamentosSalvo.getData_entrega())
                    .data_devolucao(emprestimosEquipamentosSalvo.getData_devolucao())
                    .status(emprestimosEquipamentosSalvo.getStatus())
                    .data_entrega(emprestimosEquipamentosSalvo.getData_entrega())
                    .devolucao(emprestimosEquipamentosSalvo.getDevolucao())
                    .build();

            return new ResponseEntity<>(emprestimosEquipamentosResponse, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}")
    public void atualizarEmprestimosEquipamentosStatus(@PathVariable String id, @RequestBody EmprestimosEquipamentosRequest emprestimosEquipamentoAtualizado) {
        emprestimosEquipamentosService.atualizarStatus(id, emprestimosEquipamentoAtualizado);
    }

    @DeleteMapping("/{id}")
    public void deleteColaborador(@PathVariable String id) {
        emprestimosEquipamentosService.deleteEmprestimosEquipamentos(id);
    }
    @GetMapping("/historico")
    public ResponseEntity<List<EmprestimosEquipamentosResponse>> listarHistorico(
            @RequestParam(required = false) String colaboradorId,
            @RequestParam(required = false) String equipamentoId) {
        try {
            List<EmprestimosEquipamentos> emprestimos;

            if (colaboradorId != null && equipamentoId != null) {
                emprestimos = emprestimosEquipamentosService.listarEmprestimosPorColaboradorEEquipamento(colaboradorId, equipamentoId);
            } else if (colaboradorId != null) {
                emprestimos = emprestimosEquipamentosService.listarEmprestimosPorColaborador(colaboradorId);
            } else if (equipamentoId != null) {
                emprestimos = emprestimosEquipamentosService.listarEmprestimosPorEquipamento(equipamentoId);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            List<EmprestimosEquipamentosResponse> emprestimosResponse = emprestimos.stream()
                    .map(emprestimo -> EmprestimosEquipamentosResponse.builder()
                            .id(emprestimo.getId())
                            .equipamento(emprestimo.getEquipamento())
                            .colaborador(emprestimo.getColaborador())
                            .data_entrega(emprestimo.getData_entrega())
                            .data_devolucao(emprestimo.getData_devolucao())
                            .status(emprestimo.getStatus())
                            .devolucao(emprestimo.getDevolucao())
                            .build())
                    .collect(Collectors.toList());

            return new ResponseEntity<>(emprestimosResponse, HttpStatus.OK);

        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}