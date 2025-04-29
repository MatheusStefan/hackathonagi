package com.agibank.hackathon.entities;

import com.agibank.hackathon.entities.enums.StatusColaborador;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@Document(collection = "colaboradores")
public class Colaborador {
    @Id
    private String id;
    private String nome;
    @DBRef
    private List<Equipamento> equipamentos; // Added to store equipment assigned to the collaborator
    private StatusColaborador status;
}