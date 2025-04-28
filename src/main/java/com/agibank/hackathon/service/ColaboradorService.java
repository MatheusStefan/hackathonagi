package com.agibank.hackathon.service;

import com.agibank.hackathon.entities.Colaborador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColaboradorService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Colaborador> getAllColaboradores() {
        return mongoTemplate.findAll(Colaborador.class);
    }

    public Colaborador getColaboradorById(String id) {
        return mongoTemplate.findById(id, Colaborador.class);
    }

    public void createColaborador(Colaborador colaborador) {
        mongoTemplate.save(colaborador);
    }

    public void deleteColaborador(String id) {
        Colaborador colaborador = mongoTemplate.findById(id, Colaborador.class);
        if (colaborador != null) {
            mongoTemplate.remove(colaborador);
        }
    }
}
