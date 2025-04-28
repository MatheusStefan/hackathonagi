package com.agibank.hackathon.repository;

import com.agibank.hackathon.entities.Equipamento;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EquipamentosRepository extends MongoRepository<Equipamento, String> {

}
