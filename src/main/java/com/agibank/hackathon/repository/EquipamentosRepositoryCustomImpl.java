package com.agibank.hackathon.repository;

import com.agibank.hackathon.entities.Equipamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EquipamentosRepositoryCustomImpl implements EquipamentosRepositoryCustom{

    @Autowired
    private MongoTemplate mongoTemplate;

    public void cadastrarEquipamento(Equipamento equipamento){
        Equipamento.setId(null);
        mongoTemplate.save(equipamento);
    }
}
