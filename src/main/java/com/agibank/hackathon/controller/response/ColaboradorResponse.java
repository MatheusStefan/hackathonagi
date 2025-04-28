package com.agibank.hackathon.controller.response;

import com.agibank.hackathon.entities.Equipamento;
import com.agibank.hackathon.entities.enums.statusColaborador;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class ColaboradorResponse {
    private String id;
    private String nome;
    private List<Equipamento> equipamentos;
    private statusColaborador status;
}
