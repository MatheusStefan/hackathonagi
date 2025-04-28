package com.agibank.hackathon.service;

public class EmprestimoEquipamentoService {

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

    @Service
    public class EmprestimoEquipamentoService {

        @Autowired
        public MovimentacaoRepository movimentacaoRepository;

        public List<Movimentacao> getAllMovimentacoes() {
            return movimentacaoRepository.findAll();
        }

        public Movimentacao getMovimentacaoById(Long id) {
            return movimentacaoRepository.findById(id).orElseThrow(
                    () -> new RuntimeException("Movimentação não encontrada para o ID: " + id)
            );
        }

        public void cadastrarMovimentacao(Movimentacao movimentacao) {
            movimentacaoRepository.save(movimentacao);
        }

        public void atualizarMovimentacao(Long id, Movimentacao movimentacaoAtualizada) {
            Movimentacao movimentacao = movimentacaoRepository.findById(id).orElseThrow(
                    () -> new RuntimeException("Movimentação não encontrada para o ID: " + id)
            );

            movimentacao.setFuncionario(movimentacaoAtualizada.getFuncionario());
            movimentacao.setEquipamento(movimentacaoAtualizada.getEquipamento());
            movimentacao.setTipoMovimentacao(movimentacaoAtualizada.getTipoMovimentacao());
            movimentacao.setDataMovimentacao(movimentacaoAtualizada.getDataMovimentacao());

            movimentacaoRepository.save(movimentacao);
        }

        public void atualizarStatusMovimentacao(Long id, Movimentacao movimentacaoAtualizada) {
            Movimentacao movimentacao = movimentacaoRepository.findById(id).orElseThrow(
                    () -> new RuntimeException("Movimentação não encontrada para o ID: " + id)
            );

            movimentacao.setTipoMovimentacao(movimentacaoAtualizada.getTipoMovimentacao());

            movimentacaoRepository.save(movimentacao);
        }

        public void deletarMovimentacao(Long id) {
            movimentacaoRepository.deleteById(id);
        }
    }


}
