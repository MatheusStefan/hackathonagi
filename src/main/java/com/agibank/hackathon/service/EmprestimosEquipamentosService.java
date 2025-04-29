package com.agibank.hackathon.service;

import com.agibank.hackathon.controller.request.EmprestimosEquipamentosRequest;
import com.agibank.hackathon.entities.Colaborador;
import com.agibank.hackathon.entities.EmprestimosEquipamentos;
import com.agibank.hackathon.entities.Equipamento;
import com.agibank.hackathon.entities.enums.StatusEmprestimoEquipamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmprestimosEquipamentosService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<EmprestimosEquipamentos> listarTodosEmprestimosEquipamentos() {
        return mongoTemplate.findAll(EmprestimosEquipamentos.class);
    }

    public EmprestimosEquipamentos listarEmprestimosEquipamentosById(String id) {
        EmprestimosEquipamentos emprestimosEquipamentos = mongoTemplate.findById(id, EmprestimosEquipamentos.class);
        if (emprestimosEquipamentos == null) {
            throw new RuntimeException("EmprestimosEquipamentos não encontrado com o ID: " + id);
        }
        return emprestimosEquipamentos;
    }

    public List<EmprestimosEquipamentos> listarEmprestimosPorColaborador(String colaboradorId) {
        Query query = new Query(Criteria.where("colaborador.id").is(colaboradorId));
        List<EmprestimosEquipamentos> emprestimos = mongoTemplate.find(query, EmprestimosEquipamentos.class);

        if (emprestimos.isEmpty()) {
            Colaborador colaborador = mongoTemplate.findById(colaboradorId, Colaborador.class);
            if (colaborador == null) {
                throw new RuntimeException("Colaborador não encontrado com o ID: " + colaboradorId);
            }
        }

        return emprestimos;
    }

    public List<EmprestimosEquipamentos> listarEmprestimosPorEquipamento(String equipamentoId) {
        Query query = new Query(Criteria.where("equipamento.id").is(equipamentoId));
        List<EmprestimosEquipamentos> emprestimos = mongoTemplate.find(query, EmprestimosEquipamentos.class);

        if (emprestimos.isEmpty()) {
            Equipamento equipamento = mongoTemplate.findById(equipamentoId, Equipamento.class);
            if (equipamento == null) {
                throw new RuntimeException("Equipamento não encontrado com o ID: " + equipamentoId);
            }
        }

        return emprestimos;
    }

    public EmprestimosEquipamentos cadastrarEmprestimosEquipamentos(EmprestimosEquipamentos emprestimosEquipamentos) {
        if (emprestimosEquipamentos.getData_entrega() != null && emprestimosEquipamentos.getDevolucao() == null) {
            throw new RuntimeException("Devolução obrigatória quando há data de entrega.");
        }

        String equipamentoId = emprestimosEquipamentos.getEquipamento().getId();
        Equipamento equipamento = mongoTemplate.findById(equipamentoId, Equipamento.class);
        if (equipamento == null) {
            throw new RuntimeException("Equipamento não encontrado com o ID: " + equipamentoId);
        }
        String colaboradorId = emprestimosEquipamentos.getColaborador().getId();
        Colaborador colaborador = mongoTemplate.findById(colaboradorId, Colaborador.class);
        if (colaborador == null) {
            throw new RuntimeException("Colaborador não encontrado com o ID: " + colaboradorId);
        }
        emprestimosEquipamentos.setEquipamento(equipamento);
        emprestimosEquipamentos.setColaborador(colaborador);

        return mongoTemplate.save(emprestimosEquipamentos);
    }

    public EmprestimosEquipamentos atualizar(String id, EmprestimosEquipamentos emprestimosEquipamentosAtualizado) {
        EmprestimosEquipamentos emprestimosEquipamentosExistente = mongoTemplate.findById(id, EmprestimosEquipamentos.class);

        if (emprestimosEquipamentosExistente == null) {
            throw new RuntimeException("EmprestimosEquipamentos não encontrado com o ID: " + id);
        }

        if (emprestimosEquipamentosAtualizado.getData_entrega() != null && emprestimosEquipamentosAtualizado.getDevolucao() == null) {
            throw new RuntimeException("Devolução obrigatória quando há data de entrega.");
        }

        if (emprestimosEquipamentosAtualizado.getEquipamento() != null &&
                emprestimosEquipamentosAtualizado.getEquipamento().getId() != null) {

            String equipamentoId = emprestimosEquipamentosAtualizado.getEquipamento().getId();
            Equipamento equipamento = mongoTemplate.findById(equipamentoId, Equipamento.class);
            if (equipamento == null) {
                throw new RuntimeException("Equipamento não encontrado com o ID: " + equipamentoId);
            }
            emprestimosEquipamentosAtualizado.setEquipamento(equipamento);
        }

        if (emprestimosEquipamentosAtualizado.getColaborador() != null &&
                emprestimosEquipamentosAtualizado.getColaborador().getId() != null) {

            String colaboradorId = emprestimosEquipamentosAtualizado.getColaborador().getId();
            Colaborador colaborador = mongoTemplate.findById(colaboradorId, Colaborador.class);
            if (colaborador == null) {
                throw new RuntimeException("Colaborador não encontrado com o ID: " + colaboradorId);
            }
            emprestimosEquipamentosAtualizado.setColaborador(colaborador);
        }
        emprestimosEquipamentosExistente.setEquipamento(emprestimosEquipamentosAtualizado.getEquipamento());
        emprestimosEquipamentosExistente.setColaborador(emprestimosEquipamentosAtualizado.getColaborador());
        emprestimosEquipamentosExistente.setData_entrega(emprestimosEquipamentosAtualizado.getData_entrega());
        emprestimosEquipamentosExistente.setData_devolucao(emprestimosEquipamentosAtualizado.getData_devolucao());
        emprestimosEquipamentosExistente.setStatus(emprestimosEquipamentosAtualizado.getStatus());
        emprestimosEquipamentosExistente.setDevolucao(emprestimosEquipamentosAtualizado.getDevolucao());

        return mongoTemplate.save(emprestimosEquipamentosExistente);
    }

    public EmprestimosEquipamentos atualizarStatus(String id, EmprestimosEquipamentosRequest emprestimosEquipamentosAtualizado) {
        EmprestimosEquipamentos emprestimosEquipamentosExistente = mongoTemplate.findById(id, EmprestimosEquipamentos.class);

        if (emprestimosEquipamentosExistente == null) {
            throw new RuntimeException("EmprestimosEquipamentos não encontrado com o ID: " + id);
        }
        emprestimosEquipamentosExistente.setStatus(emprestimosEquipamentosAtualizado.getStatus());
        return mongoTemplate.save(emprestimosEquipamentosExistente);
    }

    public void deleteEmprestimosEquipamentos(String id) {
        EmprestimosEquipamentos emprestimosEquipamentosExistente = mongoTemplate.findById(id, EmprestimosEquipamentos.class);
        if (emprestimosEquipamentosExistente != null) {
            mongoTemplate.remove(emprestimosEquipamentosExistente);
        } else {
            throw new RuntimeException("EmprestimosEquipamentos não encontrado com o ID: " + id);
        }
    }

    public List<EmprestimosEquipamentos> listarEmprestimosPorColaboradorEEquipamento(String colaboradorId, String equipamentoId) {
        Query query = new Query(new Criteria().andOperator(
                Criteria.where("colaborador.id").is(colaboradorId),
                Criteria.where("equipamento.id").is(equipamentoId)
        ));

        List<EmprestimosEquipamentos> emprestimos = mongoTemplate.find(query, EmprestimosEquipamentos.class);

        if (emprestimos.isEmpty()) {
            throw new RuntimeException("Nenhum empréstimo encontrado para colaboradorId: " + colaboradorId + " e equipamentoId: " + equipamentoId);
        }

        return emprestimos;
    }

    public void realizarEmprestimo(Equipamento equipamento, Colaborador colaborador) {
        EmprestimosEquipamentos emprestimo = EmprestimosEquipamentos.builder()
                .equipamento(equipamento)
                .colaborador(colaborador)
                .data_entrega(LocalDate.now())
                .status(StatusEmprestimoEquipamento.ATIVO)
                .build();
        mongoTemplate.save(emprestimo);

        // Update the equipment's collaboratorId
        equipamento.setColaboradorId(colaborador.getId());
        mongoTemplate.save(equipamento);
    }
}