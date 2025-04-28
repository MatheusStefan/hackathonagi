package com.agibank.hackathon.entities;

import com.agibank.hackathon.entities.enums.motivoDevolucao;
import com.agibank.hackathon.entities.enums.statusEmprestimoEquipamento;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Builder
@Document(collection = "emprestimos_equipamentos")
public class emprestimosEquipamentos {
    @Id
    private String id;
    private Equipamento equipamento;
    private Colaborador colaborador;
    private LocalDate data_entrega;
    private LocalDate data_devolucao;
    private statusEmprestimoEquipamento status;
    private motivoDevolucao devolucao;
}
