package com.agibank.hackathon.service;

import com.agibank.hackathon.entities.Colaborador;
import com.agibank.hackathon.entities.Equipamento;
import com.agibank.hackathon.entities.enums.StatusEquipamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipamentoService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ColaboradorService colaboradorService;

    @Autowired
    private EmprestimosEquipamentosService emprestimoService;

    public Equipamento cadastrarEquipamento(Equipamento equipamento) {
        return mongoTemplate.save(equipamento);
    }

    public List<Equipamento> listarTodosEquipamentos() {
        return mongoTemplate.findAll(Equipamento.class);
    }

    public Equipamento listarEquipamentoById(String id) {
        Equipamento equipamento = mongoTemplate.findById(id, Equipamento.class);
        if (equipamento == null) {
            throw new RuntimeException("Equipamento não encontrado com o ID: " + id);
        }
        return equipamento;
    }

    public List<Equipamento> buscarEquipamentosDisponiveis(String tipo, String modelo) {
        return mongoTemplate.find(
                Query.query(Criteria.where("tipo").is(tipo)
                        .and("modelo").is(modelo)
                        .and("status").is(StatusEquipamento.ATIVO)),
                Equipamento.class);
    }

    public List<Equipamento> listarEquipamentosPorColaborador(String colaboradorId) {
        Query query = new Query(Criteria.where("colaboradorId").is(colaboradorId));
        List<Equipamento> equipamentos = mongoTemplate.find(query, Equipamento.class);

        if (equipamentos.isEmpty()) {
            Colaborador colaborador = colaboradorService.buscarColaboradorPorId(colaboradorId);
            if (colaborador == null) {
                throw new RuntimeException("Colaborador não encontrado com o ID: " + colaboradorId);
            }
        }

        return equipamentos;
    }

    public Equipamento atualizarEquipamento(String id, Equipamento equipamentoAtualizado) {
        Equipamento equipamentoExistente = mongoTemplate.findById(id, Equipamento.class);
        if (equipamentoExistente == null) {
            throw new RuntimeException("Equipamento não encontrado com o ID: " + id);
        }

        equipamentoExistente.setTipo(equipamentoAtualizado.getTipo());
        equipamentoExistente.setModelo(equipamentoAtualizado.getModelo());
        equipamentoExistente.setStatus(equipamentoAtualizado.getStatus());
        equipamentoExistente.setColaboradorId(equipamentoAtualizado.getColaboradorId());

        return mongoTemplate.save(equipamentoExistente);
    }
}