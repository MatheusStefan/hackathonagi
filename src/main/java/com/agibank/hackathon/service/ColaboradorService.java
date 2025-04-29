package com.agibank.hackathon.service;

import com.agibank.hackathon.controller.request.ColaboradorStatusRequest;
import com.agibank.hackathon.entities.Colaborador;
import com.agibank.hackathon.entities.Equipamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColaboradorService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Colaborador> listarTodosColaboradores() {
        return mongoTemplate.findAll(Colaborador.class);
    }

    public Colaborador buscarColaboradorPorId(String id) {
        Colaborador colaborador = mongoTemplate.findById(id, Colaborador.class);
        if (colaborador == null) {
            throw new RuntimeException("Colaborador não encontrado com o ID: " + id);
        }
        return colaborador;
    }

    public Colaborador cadastrarColaborador(Colaborador colaborador) {
        return mongoTemplate.save(colaborador);
    }

    public Equipamento adicionarEquipamento(String id, String equipamentoId) {
        Colaborador colaborador = buscarColaboradorPorId(id);
        Equipamento equipamento = mongoTemplate.findById(equipamentoId, Equipamento.class);

        if (colaborador == null || equipamento == null) {
            throw new RuntimeException("Colaborador ou equipamento não encontrado.");
        }

        equipamento.setColaboradorId(id);
        List<Equipamento> equipamentos = colaborador.getEquipamentos();
        equipamentos.add(equipamento);
        colaborador.setEquipamentos(equipamentos);
        mongoTemplate.save(colaborador);

        return mongoTemplate.save(equipamento);
    }

    public Colaborador atualizarStatus(String id, ColaboradorStatusRequest colaboradorAtualizado) {
        Colaborador colaboradorExistente = mongoTemplate.findById(id, Colaborador.class);

        if (colaboradorExistente == null) {
            throw new RuntimeException("Colaborador não encontrado com o ID: " + id);
        }
        colaboradorExistente.setStatus(colaboradorAtualizado.getStatus());
        return mongoTemplate.save(colaboradorExistente);
    }

    public Colaborador atualizar(String id, Colaborador colaboradorAtualizado) {
        Colaborador colaboradorExistente = mongoTemplate.findById(id, Colaborador.class);
        if (colaboradorExistente == null) {
            throw new RuntimeException("Colaborador não encontrado com o ID: " + id);
        }

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