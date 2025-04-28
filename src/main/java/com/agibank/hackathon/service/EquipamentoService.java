package com.agibank.hackathon.service;

import com.agibank.hackathon.entities.Equipamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class EquipamentoService {

    @Autowired
    public MongoTemplate mongoTemplate;

    public void cadastrarEquipamento(Equipamento equipamento){
        mongoTemplate.save(equipamento);
    }
}
