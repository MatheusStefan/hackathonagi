package com.agibank.hackathon.entities;

import com.agibank.hackathon.entities.enums.MotivoDevolucao;
import com.agibank.hackathon.entities.enums.StatusEmprestimoEquipamento;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Builder
@Document(collection = "emprestimos_equipamentos")
public class EmprestimosEquipamentos {
    @Id
    private String id;
    private String equipamentoId;
    private String colaboradorId;
    private LocalDate data_entrega;
    private LocalDate data_devolucao;
    private StatusEmprestimoEquipamento status;
    private MotivoDevolucao devolucao;
}
