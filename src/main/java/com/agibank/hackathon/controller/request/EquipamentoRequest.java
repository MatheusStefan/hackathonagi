package com.agibank.hackathon.controller.request;

import com.agibank.hackathon.entities.enums.StatusEquipamento;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EquipamentoRequest {
    private String tipo;
    private String modelo;
    private StatusEquipamento status;
    private String colaboradorId;
}
