package com.agibank.hackathon.entities;

import com.agibank.hackathon.entities.enums.statusColaborador;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@Document(collection = "colaboradores")
public class Colaborador {
    private String id;
    private String nome;
    private statusColaborador status;
}

