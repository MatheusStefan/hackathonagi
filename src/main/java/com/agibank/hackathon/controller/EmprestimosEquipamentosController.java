package com.agibank.hackathon.controller;

import com.agibank.hackathon.controller.request.EmprestimosEquipamentosRequest;
import com.agibank.hackathon.controller.request.SolicitacaoEmprestimoRequest;
import com.agibank.hackathon.controller.response.EmprestimosEquipamentosResponse;
import com.agibank.hackathon.entities.Colaborador;
import com.agibank.hackathon.entities.EmprestimosEquipamentos;
import com.agibank.hackathon.entities.Equipamento;
import com.agibank.hackathon.entities.SolicitacaoCompra;
import com.agibank.hackathon.service.ColaboradorService;
import com.agibank.hackathon.service.EmprestimosEquipamentosService;
import com.agibank.hackathon.service.EquipamentoService;
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

    @Autowired
    public EquipamentoService equipamentoService;

    @Autowired
    public ColaboradorService colaboradorService;

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
                            .equipamento(equipamentoService.listarEquipamentoById(emprestimo.getEquipamentoId()))
                            .colaborador(colaboradorService.buscarColaboradorPorId(emprestimo.getColaboradorId()))
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
                            .equipamento(equipamentoService.listarEquipamentoById(emprestimo.getEquipamentoId()))
                            .colaborador(colaboradorService.buscarColaboradorPorId(emprestimo.getColaboradorId()))
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
    public ResponseEntity<EmprestimosEquipamentosResponse> cadastrarEmprestimosEquipamentos(@RequestBody EmprestimosEquipamentosRequest request) {
        EmprestimosEquipamentos salvo = emprestimosEquipamentosService.cadastrarEmprestimosEquipamentos(request);

        EmprestimosEquipamentosResponse response = EmprestimosEquipamentosResponse.builder()
                .id(salvo.getId())
                .equipamento(equipamentoService.listarEquipamentoById(salvo.getEquipamentoId()))
                .colaborador(colaboradorService.buscarColaboradorPorId(salvo.getColaboradorId()))
                .data_entrega(salvo.getData_entrega())
                .data_devolucao(salvo.getData_devolucao())
                .status(salvo.getStatus())
                .devolucao(salvo.getDevolucao())
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/status")
    public void atualizarEmprestimosEquipamentosStatus(@PathVariable String id, @RequestBody EmprestimosEquipamentosRequest request) {
        emprestimosEquipamentosService.atualizarStatus(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteEmprestimo(@PathVariable String id) {
        emprestimosEquipamentosService.deleteEmprestimosEquipamentos(id);
    }


}




//    @PutMapping("/{id}")
//    public ResponseEntity<EmprestimosEquipamentosResponse> atualizarEmprestimosEquipamentos(

//            @PathVariable String id,
//            @RequestBody EmprestimosEquipamentosRequest request) {
//        try {
//            EmprestimosEquipamentos atualizado = EmprestimosEquipamentos.builder()
//                    .id(id)
//                    .equipamentoId(request.getEquipamentoId())
//                    .colaboradorId(request.getColaboradorId())
//                    .data_entrega(request.getData_entrega())
//                    .data_devolucao(request.getData_devolucao())
//                    .status(request.getStatus())
//                    .devolucao(request.getDevolucao())
//                    .build();
//
//            EmprestimosEquipamentos salvo = emprestimosEquipamentosService.atualizarEmprestimosEquipamentos(id, atualizado);
//
//            EmprestimosEquipamentosResponse response = EmprestimosEquipamentosResponse.builder()
//                    .id(salvo.getId())
//                    .equipamento(equipamentoService.listarEquipamentoById(salvo.getEquipamentoId()))
//                    .colaborador(colaboradorService.listarColaboradorById(salvo.getColaboradorId()))
//                    .data_entrega(salvo.getData_entrega())
//                    .data_devolucao(salvo.getData_devolucao())
//                    .status(salvo.getStatus())
//                    .devolucao(salvo.getDevolucao())
//                    .build();
//
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        } catch (RuntimeException e) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
