package com.agibank.hackathon.service;

import com.agibank.hackathon.entities.Colaborador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ColaboradorService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Colaborador> getAllColaboradores() {
        return mongoTemplate.findAll();
    }

    public Colaborador getColaboradorById(String id) {
        Optional<Colaborador> colaborador = mongoTemplate.findById(id);
        return colaborador.orElseThrow(() -> new RuntimeException("Colaborador não encontrado com o ID: " + id));
    }

    public Colaborador createColaborador(Colaborador colaborador) {
        return mongoTemplate.save(colaborador);
    }

    public Colaborador atualizar(String id, Colaborador colaboradorAtualizado) {
        Optional<Colaborador> colaboradorExistente = mongoTemplate.findById(id);

        if (colaboradorExistente.isPresent()) {
            Colaborador colaborador = colaboradorExistente.get();
            colaborador.setNome (colaboradorAtualizado.getNome());
            colaborador.setEquipamentos(colaboradorAtualizado.getEquipamentos());
            colaborador.setStatus(colaboradorAtualizado.getStatus());

            return mongoTemplate.save(colaborador);
        } else {
            throw new RuntimeException("Colaborador não encontrado com o ID: " + id);
        }
    }

    public void deleteColaborador(String id) {
        if (mongoTemplate.findById(id)) {
            mongoTemplate.remove(id);
        } else {
            throw new RuntimeException("Colaborador não encontrado com o ID: " + id);
        }
    }
}
