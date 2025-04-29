package com.agibank.hackathon.controller.response;

import com.agibank.hackathon.entities.enums.statusEquipamento;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EquipamentoResponse {
    private String tipo;
    private String modelo;
    private statusEquipamento status;
    private String colaboradorId;
}
