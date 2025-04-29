package com.agibank.hackathon.controller.request;

import com.agibank.hackathon.entities.Colaborador;
import com.agibank.hackathon.entities.enums.statusEquipamento;
import lombok.Data;

@Data
public class EquipamentoRequest {
    private String tipo;
    private String modelo;
    private statusEquipamento status;
    private String colaboradorId;
}
