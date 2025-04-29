package com.agibank.hackathon.service;

import com.agibank.hackathon.entities.Colaborador;
import com.agibank.hackathon.entities.Equipamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class EquipamentoService {

    @Autowired
    public MongoTemplate mongoTemplate;

    public Equipamento cadastrarEquipamento(Equipamento equipamento) {
        return mongoTemplate.save(equipamento);
    }

    public List<Equipamento> listarTodosEquipamentos() {
        return mongoTemplate.findAll(Equipamento.class);
    }

    public Equipamento listarEquipamentoById(String id){
        Equipamento equipamento = mongoTemplate.findById(id, Equipamento.class);
        if (equipamento == null){
            throw  new RuntimeException("Equipamento não encontrado com o ID: " + id);
        }
        return equipamento;
    }

    public List<Equipamento> listarEquipamentosPorColaborador(String colaboradorId) {
        return mongoTemplate.find(query(Criteria.where("colaboradorId").is(colaboradorId)), Equipamento.class);
    }

    public Equipamento atualizar(String id, Equipamento equipamentoAtualizado, String colaboradorId) {
        Equipamento equipamentoExistente = mongoTemplate.findById(id, Equipamento.class);
        Colaborador colaboradorExistente = mongoTemplate.findById(colaboradorId,Colaborador.class);

        if (equipamentoExistente == null || colaboradorExistente == null) {
            throw new RuntimeException("Colaborador não encontrado com o ID: " + id);
        }
        equipamentoExistente.setTipo(equipamentoAtualizado.getTipo());
        equipamentoExistente.setModelo(equipamentoAtualizado.getModelo());
        equipamentoExistente.setStatus(equipamentoAtualizado.getStatus());
        equipamentoExistente.getColaboradorId();
        return mongoTemplate.save(equipamentoExistente);
    }
}
