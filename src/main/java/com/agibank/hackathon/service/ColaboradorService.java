package com.agibank.hackathon.service;

import com.agibank.hackathon.controller.response.ColaboradorResponse;
import com.agibank.hackathon.controller.response.ColaboradorStatusResponse;
import com.agibank.hackathon.entities.Colaborador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ColaboradorService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Colaborador> listarTodosColaboradores() {
        return mongoTemplate.findAll(Colaborador.class);
    }

    public Colaborador listarColaboradorById(String id) {
     Colaborador colaborador = mongoTemplate.findById(id, Colaborador.class);
     if (colaborador == null) {
            throw new RuntimeException("Colaborador não encontrado com o ID: " + id);
     }
        return colaborador;
    }

    public Colaborador cadastrarColaborador(Colaborador colaborador) {
        return mongoTemplate.save(colaborador);
    }

    public Colaborador atualizarStatus(String id, ColaboradorStatusResponse colaboradorAtualizado) {
        Colaborador colaboradorExistente = mongoTemplate.findById(id, Colaborador.class);

        if (colaboradorExistente == null) {
            throw new RuntimeException("Colaborador não encontrado com o ID: " + id);
        }
        colaboradorExistente.setStatus(colaboradorAtualizado.getStatus());
            return mongoTemplate.save(colaboradorExistente);
    }

    public Colaborador atualizar(String id, Colaborador colaboradorAtualizado) {
        Colaborador colaboradorExistente = mongoTemplate.findById(id, Colaborador.class);

        System.out.println("Passou aqui");
        if (colaboradorExistente == null) {
            throw new RuntimeException("Colaborador não encontrado com o ID: " + id);
        }
        System.out.println("Passou aqui");
        colaboradorExistente.setNome(colaboradorAtualizado.getNome());
        colaboradorExistente.setEquipamentos(colaboradorAtualizado.getEquipamentos());
        colaboradorExistente.setStatus(colaboradorAtualizado.getStatus());
        return mongoTemplate.save(colaboradorExistente);
    }

    public void deleteColaborador(String id) {
        Colaborador colaboradorExistente = mongoTemplate.findById(id, Colaborador.class);
        if (colaboradorExistente != null) {
            mongoTemplate.remove(colaboradorExistente);
        } else {
            throw new RuntimeException("Colaborador não encontrado com o ID: " + id);
        }
    }
}
