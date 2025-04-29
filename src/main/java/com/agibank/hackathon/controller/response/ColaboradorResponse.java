package com.agibank.hackathon.controller.response;

import com.agibank.hackathon.entities.Equipamento;
import com.agibank.hackathon.entities.enums.StatusColaborador;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

@Builder
@Data
public class ColaboradorResponse {
    private String id;
    private String nome;
    private StatusColaborador status;
}
