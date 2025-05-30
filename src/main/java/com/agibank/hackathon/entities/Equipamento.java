package com.agibank.hackathon.entities;

import com.agibank.hackathon.entities.enums.StatusEquipamento;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "equipamentos")
public class Equipamento {
    @Id
    private String id;
    private String tipo;
    private String modelo;
    private StatusEquipamento status;

    private String colaboradorId;
}
