package com.agibank.hackathon.controller;

import com.agibank.hackathon.controller.request.EquipamentoRequest;
import com.agibank.hackathon.controller.request.SolicitacaoEmprestimoRequest;
import com.agibank.hackathon.controller.response.EquipamentoResponse;
import com.agibank.hackathon.controller.response.SolicitacaoCompraResponse;
import com.agibank.hackathon.entities.Colaborador;
import com.agibank.hackathon.entities.Equipamento;
import com.agibank.hackathon.entities.SolicitacaoCompra;
import com.agibank.hackathon.service.ColaboradorService;
import com.agibank.hackathon.service.EmprestimosEquipamentosService;
import com.agibank.hackathon.service.EquipamentoService;
import com.agibank.hackathon.service.SolicitacaoCompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/equipamentos")
@SuppressWarnings("unused") // Suppress unused class warning
public class EquipamentoController {

    private final EquipamentoService equipamentoService;
    private final SolicitacaoCompraService solicitacaoCompraService;
    @SuppressWarnings("NonAsciiCharacters") // Suppress non-ASCII warning for Portuguese accents
    private final EmprestimosEquipamentosService empréstimoService;
    private final ColaboradorService colaboradorService;

    @Autowired
    @SuppressWarnings({"unused", "NonAsciiCharacters"}) // Suppress unused constructor and non-ASCII warnings
    public EquipamentoController(EquipamentoService equipamentoService,
                                 SolicitacaoCompraService solicitacaoCompraService,
                                 EmprestimosEquipamentosService empréstimoService,
                                 ColaboradorService colaboradorService) {
        this.equipamentoService = equipamentoService;
        this.solicitacaoCompraService = solicitacaoCompraService;
        this.empréstimoService = empréstimoService;
        this.colaboradorService = colaboradorService;
    }

    @PostMapping
    @SuppressWarnings("unused") // Suppress unused method warning
    public ResponseEntity<EquipamentoResponse> cadastrar(@RequestBody EquipamentoRequest equipamentoRequest) {
        Equipamento equipamento = convertToEntity(equipamentoRequest);
        Equipamento equipamentoSalvo = equipamentoService.cadastrarEquipamento(equipamento);
        return new ResponseEntity<>(convertToResponse(equipamentoSalvo), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EquipamentoResponse>> listarEquipamentos() {
        List<Equipamento> equipamentos = equipamentoService.listarTodosEquipamentos();
        List<EquipamentoResponse> responses = equipamentos.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquipamentoResponse> listarEquipamentoById(@PathVariable String id) {
        try {
            Equipamento equipamento = equipamentoService.listarEquipamentoById(id);
            return ResponseEntity.ok(convertToResponse(equipamento));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/colaborador/{colaboradorId}")
    public ResponseEntity<List<EquipamentoResponse>> listarEquipamentosPorColaborador(@PathVariable String colaboradorId) {
        List<Equipamento> equipamentos = equipamentoService.listarEquipamentosPorColaborador(colaboradorId);
        List<EquipamentoResponse> responses = equipamentos.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EquipamentoResponse> atualizarEquipamento(@PathVariable String id,
                                                                    @RequestBody EquipamentoRequest equipamentoRequest) {
        try {
            Equipamento equipamento = convertToEntity(equipamentoRequest);
            Equipamento equipamentoSalvo = equipamentoService.atualizarEquipamento(id, equipamento);
            return ResponseEntity.ok(convertToResponse(equipamentoSalvo));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/solicitar-emprestimo")
    @SuppressWarnings("NonAsciiCharacters") // Suppress non-ASCII warning for "Empréstimo"
    public ResponseEntity<?> solicitarEmprestimo(@RequestBody SolicitacaoEmprestimoRequest request) {
        try {
            Colaborador colaborador = colaboradorService.buscarColaboradorPorId(request.getColaboradorId());
            Equipamento equipamento = null;

            if (request.getEquipamentoId() != null) {
                try {
                    equipamento = equipamentoService.listarEquipamentoById(request.getEquipamentoId());
                } catch (RuntimeException e) {
                    // Equipment not found, proceed with tipo and modelo
                }
            }

            if (equipamento == null) {
                equipamento = Equipamento.builder()
                        .tipo(request.getTipo())
                        .modelo(request.getModelo())
                        .build();
            }

            List<Equipamento> disponiveis = equipamentoService.buscarEquipamentosDisponiveis(
                    equipamento.getTipo(), equipamento.getModelo());

            if (!disponiveis.isEmpty()) {
                empréstimoService.realizarEmprestimo(disponiveis.getFirst(), colaborador);
                return ResponseEntity.ok("Empréstimo realizado com sucesso");
            } else {
                SolicitacaoCompra solicitacao = SolicitacaoCompra.builder()
                        .equipamentoSolicitado(equipamento)
                        .solicitante(colaborador)
                        .build();

                SolicitacaoCompra solicitacaoSalva = solicitacaoCompraService.criarSolicitacao(solicitacao);
                return ResponseEntity.accepted().body(convertToResponse(solicitacaoSalva));
            }
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/solicitacoes-compra/pendentes")
    public ResponseEntity<List<SolicitacaoCompraResponse>> listarSolicitacoesCompraPendentes() {
        List<SolicitacaoCompra> solicitacoes = solicitacaoCompraService.listarSolicitacoesPendentes();
        List<SolicitacaoCompraResponse> responses = solicitacoes.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @PostMapping("/concluir-compra/{solicitacaoId}")
    public ResponseEntity<?> concluirCompra(
            @PathVariable String solicitacaoId,
            @RequestBody EquipamentoRequest equipamentoRequest) {
        try {
            Equipamento novoEquipamento = convertToEntity(equipamentoRequest);
            SolicitacaoCompra solicitacao = solicitacaoCompraService.concluirSolicitacao(
                    solicitacaoId, novoEquipamento);
            return ResponseEntity.ok(convertToResponse(solicitacao));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private Equipamento convertToEntity(EquipamentoRequest request) {
        return Equipamento.builder()
                .tipo(request.getTipo())
                .modelo(request.getModelo())
                .status(request.getStatus())
                .colaboradorId(request.getColaboradorId())
                .build();
    }

    private EquipamentoResponse convertToResponse(Equipamento equipamento) {
        return EquipamentoResponse.builder()
                .id(equipamento.getId())
                .tipo(equipamento.getTipo())
                .modelo(equipamento.getModelo())
                .status(equipamento.getStatus())
                .colaboradorId(equipamento.getColaboradorId())
                .build();
    }

    private SolicitacaoCompraResponse convertToResponse(SolicitacaoCompra solicitacao) {
        return SolicitacaoCompraResponse.builder()
                .id(solicitacao.getId())
                .equipamentoSolicitado(convertToResponse(solicitacao.getEquipamentoSolicitado()))
                .solicitante(solicitacao.getSolicitante())
                .status(solicitacao.getStatus())
                .dataSolicitacao(solicitacao.getDataSolicitacao())
                .dataConclusao(solicitacao.getDataConclusao())
                .build();
    }
}