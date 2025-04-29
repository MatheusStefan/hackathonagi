package com.agibank.hackathon.controller.response;

import com.agibank.hackathon.entities.enums.StatusEquipamento;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EquipamentoResponse {
    private String tipo;
    private String modelo;
    private StatusEquipamento status;
    private String colaboradorId;
}
