package com.agibank.hackathon.entities;

import com.agibank.hackathon.entities.enums.StatusColaborador;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "colaboradores")
public class Colaborador {
    @Id
    private String id;
    private String nome;;
    private StatusColaborador status;
}

