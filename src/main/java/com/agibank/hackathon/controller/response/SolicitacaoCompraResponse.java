package com.agibank.hackathon.controller.response;

import com.agibank.hackathon.entities.Colaborador;
import com.agibank.hackathon.entities.enums.StatusSolicitacaoCompra;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class SolicitacaoCompraResponse {
    private String id;
    private EquipamentoResponse equipamentoSolicitado; // Changed to EquipamentoResponse
    private Colaborador solicitante;
    private LocalDateTime dataSolicitacao;
    private LocalDateTime dataConclusao;
    private StatusSolicitacaoCompra status;
}