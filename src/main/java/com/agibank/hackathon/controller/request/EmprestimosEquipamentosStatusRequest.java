package com.agibank.hackathon.controller.request;
import com.agibank.hackathon.entities.enums.StatusEmprestimoEquipamento;
import lombok.Data;

@Data
public class EmprestimosEquipamentosStatusRequest {
    private StatusEmprestimoEquipamento status;
}
