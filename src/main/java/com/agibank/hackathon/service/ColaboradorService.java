package com.agibank.hackathon.service;

import com.agibank.hackathon.controller.request.ColaboradorRequest;
import com.agibank.hackathon.controller.request.ColaboradorStatusRequest;
import com.agibank.hackathon.controller.response.ColaboradorResponse;
import com.agibank.hackathon.controller.response.ColaboradorStatusResponse;
import com.agibank.hackathon.entities.Colaborador;
import com.agibank.hackathon.entities.Equipamento;
import com.agibank.hackathon.entities.enums.StatusColaborador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

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
            throw new RuntimeException("Colaborador não encontrado com o ID: " + id);
        }

        equipamento.setColaboradorId(id);

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
    public List<Colaborador> colaboradoresComPendenciaDeEquipamentoParaDesligamento() {
        // 1. Buscar todos os colaboradores com status de DESLIGADO
        List<Colaborador> colaboradores = mongoTemplate.find(
                Query.query(Criteria.where("status").is(StatusColaborador.DESLIGADO)),
                Colaborador.class
        );

        List<Colaborador> colaboradoresComPendencias = new ArrayList<>();

        for (Colaborador colaborador : colaboradores) {
            List<Equipamento> equipamentosPendentes = mongoTemplate.find(
                    Query.query(Criteria.where("colaboradorId").is(colaborador.getId())
                            .and("status").ne("DEVOLVIDO")),
                    Equipamento.class
            );

            if (!equipamentosPendentes.isEmpty()) {
                colaboradoresComPendencias.add(colaborador);
            }
        }

        return colaboradoresComPendencias;
    }

    public List<Equipamento> deleteColaboradorComEquipamentos(String id) {
        // Verifica se o colaborador existe
        Colaborador colaboradorExistente = mongoTemplate.findById(id, Colaborador.class);
        if (colaboradorExistente == null) {
            throw new RuntimeException("Colaborador não encontrado com o ID: " + id);
        }

        // Busca os equipamentos associados ao colaborador
        List<Equipamento> equipamentosAssociados = mongoTemplate.find(
                Query.query(Criteria.where("colaboradorId").is(id)),
                Equipamento.class
        );

        // Se houver equipamentos associados, impede a exclusão e retorna a lista
        if (!equipamentosAssociados.isEmpty()) {
            return equipamentosAssociados;
        }

        // Remove o colaborador se não houver equipamentos associados
        mongoTemplate.remove(colaboradorExistente);
        return List.of(); // Retorna uma lista vazia indicando que a exclusão foi realizada
    }

}
