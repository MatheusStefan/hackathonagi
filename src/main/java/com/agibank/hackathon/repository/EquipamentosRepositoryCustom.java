package com.agibank.hackathon.repository;

import com.agibank.hackathon.entities.Equipamento;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipamentosRepositoryCustom {
    void cadastrarEquipamento(Equipamento equipamento);
}
