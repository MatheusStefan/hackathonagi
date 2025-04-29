package com.agibank.hackathon.service;

import com.agibank.hackathon.entities.Equipamento;
import com.agibank.hackathon.entities.SolicitacaoCompra;
import com.agibank.hackathon.entities.enums.StatusEquipamento;
import com.agibank.hackathon.entities.enums.StatusSolicitacaoCompra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SolicitacaoCompraService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private EquipamentoService equipamentoService;

    @Autowired
    private EmprestimosEquipamentosService emprestimoService;

    public SolicitacaoCompra criarSolicitacao(SolicitacaoCompra solicitacao) {
        if (solicitacao.getEquipamentoSolicitado() == null || solicitacao.getSolicitante() == null) {
            throw new IllegalArgumentException("Equipamento solicitado e solicitante são obrigatórios.");
        }
        solicitacao.setDataSolicitacao(LocalDateTime.now());
        solicitacao.setStatus(StatusSolicitacaoCompra.PENDENTE);
        return mongoTemplate.save(solicitacao);
    }

    public List<SolicitacaoCompra> listarTodasSolicitacoes() {
        return mongoTemplate.findAll(SolicitacaoCompra.class);
    }

    public List<SolicitacaoCompra> listarSolicitacoesPendentes() {
        Query query = new Query(Criteria.where("status").is(StatusSolicitacaoCompra.PENDENTE));
        return mongoTemplate.find(query, SolicitacaoCompra.class);
    }

    public SolicitacaoCompra concluirSolicitacao(String id, Equipamento novoEquipamento) {
        SolicitacaoCompra solicitacao = mongoTemplate.findById(id, SolicitacaoCompra.class);
        if (solicitacao == null) {
            throw new RuntimeException("Solicitação não encontrada com o ID: " + id);
        }

        if (solicitacao.getStatus() != StatusSolicitacaoCompra.PENDENTE) {
            throw new IllegalStateException("A solicitação não está pendente e não pode ser concluída.");
        }

        if (novoEquipamento == null) {
            throw new IllegalArgumentException("Novo equipamento é obrigatório para concluir a solicitação.");
        }

        // Verifica se o equipamento corresponde ao solicitado
        if (!novoEquipamento.getTipo().equals(solicitacao.getEquipamentoSolicitado().getTipo()) ||
                !novoEquipamento.getModelo().equals(solicitacao.getEquipamentoSolicitado().getModelo())) {
            throw new IllegalArgumentException("O equipamento fornecido não corresponde ao solicitado.");
        }

        // Garante que o novo equipamento esteja ativo
        novoEquipamento.setStatus(StatusEquipamento.ATIVO);
        Equipamento equipamentoSalvo = equipamentoService.cadastrarEquipamento(novoEquipamento);

        // Atualiza a solicitação
        solicitacao.setStatus(StatusSolicitacaoCompra.CONCLUIDA);
        solicitacao.setDataConclusao(LocalDateTime.now());

        // Realiza o empréstimo automaticamente para o solicitante
        emprestimoService.realizarEmprestimo(equipamentoSalvo, solicitacao.getSolicitante());

        return mongoTemplate.save(solicitacao);
    }
}


