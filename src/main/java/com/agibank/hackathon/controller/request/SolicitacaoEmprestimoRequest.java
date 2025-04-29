package com.agibank.hackathon.controller.request;

import lombok.Data;

@Data
public class SolicitacaoEmprestimoRequest {
    private String equipamentoId; // Can be null if equipment doesn't exist
    private String tipo; // Added to specify equipment type if not in stock
    private String modelo; // Added to specify equipment model if not in stock
    private String colaboradorId;
}