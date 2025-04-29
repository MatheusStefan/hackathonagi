package com.agibank.hackathon.controller.request;
import com.agibank.hackathon.entities.Colaborador;
import com.agibank.hackathon.entities.Equipamento;
import com.agibank.hackathon.entities.enums.MotivoDevolucao;
import com.agibank.hackathon.entities.enums.StatusEmprestimoEquipamento;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Builder
public class EmprestimosEquipamentosRequest {
    private Equipamento equipamento;
    private Colaborador colaborador;
    private LocalDate data_entrega;
    private LocalDate data_devolucao;
    private StatusEmprestimoEquipamento status;
    private MotivoDevolucao devolucao;
}
