package com.agibank.hackathon.repository.ColaboradorRepository;

import com.agibank.hackathon.entities.Colaborador;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

 @Repository
public interface IColaboradorRepository extends MongoRepository<Colaborador, String> {

}


