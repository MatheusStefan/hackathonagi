package com.agibank.hackathon.controller.request;

import com.agibank.hackathon.entities.Equipamento;
import com.agibank.hackathon.entities.enums.statusColaborador;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

@Builder
@Data
public class ColaboradorRequest {
    private String nome;

    @DBRef
    private List<Equipamento> equipamentos;
    private statusColaborador status;
}
