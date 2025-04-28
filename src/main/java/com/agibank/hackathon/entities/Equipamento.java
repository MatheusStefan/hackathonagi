package com.agibank.hackathon.entities;

import com.agibank.hackathon.entities.enums.statusEquipamento;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "equipamentos")
public class Equipamento {
    private String id;
    private String tipo;
    private String modelo;
    private statusEquipamento status;
}
