package com.agibank.hackathon.controller.response;
import com.agibank.hackathon.entities.enums.StatusEmprestimoEquipamento;
import lombok.Data;

@Data
public class EmprestimosEquipamentosStatusResponse {
    private StatusEmprestimoEquipamento status;
}
