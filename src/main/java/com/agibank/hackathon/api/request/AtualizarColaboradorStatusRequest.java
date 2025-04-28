package com.agibank.hackathon.api.request;

import com.agibank.hackathon.entities.enums.statusColaborador;

public class AtualizarColaboradorStatusRequest {
    private String id;
    private String nome;
    private statusColaborador status;
}
