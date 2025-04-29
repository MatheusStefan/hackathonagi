package com.agibank.hackathon.entities;

import com.agibank.hackathon.entities.enums.StatusSolicitacaoCompra;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@Document(collection = "solicitacoes_compra")
public class SolicitacaoCompra {
    @Id
    private String id;
    private Equipamento equipamentoSolicitado;
    private Equipamento equipamentoComprado; // Novo campo
    private Colaborador solicitante;
    private LocalDateTime dataSolicitacao;
    private LocalDateTime dataConclusao;
    private StatusSolicitacaoCompra status;
}
