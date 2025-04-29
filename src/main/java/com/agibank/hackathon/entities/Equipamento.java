package com.agibank.hackathon.entities;

import com.agibank.hackathon.entities.enums.statusEquipamento;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@Document(collection = "equipamentos")
public class Equipamento {
    @Id
    private String id;
    private String tipo;
    private String modelo;
    private statusEquipamento status;

    private String colaboradorId;
}
