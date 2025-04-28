package com.agibank.hackathon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/emprestimos")
public class EmprestimoEquipamentoController {

    @Autowired
    public MovimentacaoService movimentacaoService;

    @GetMapping
    public List<Movimentacao> listarEmprestimos() {
        return movimentacaoService.getAllMovimentacoes();
    }

    @GetMapping("/{id}")
    public Movimentacao listarEmprestimoById(@PathVariable Long id) {
        return movimentacaoService.getMovimentacaoById(id);
    }

    @PostMapping
    public void cadastrarEmprestimo(@RequestBody Movimentacao movimentacao) {
        movimentacaoService.cadastrarMovimentacao(movimentacao);
    }

    @PutMapping("/{id}")
    public void atualizarEmprestimo(@PathVariable Long id, @RequestBody Movimentacao movimentacaoAtualizada) {
        movimentacaoService.atualizarMovimentacao(id, movimentacaoAtualizada);
    }

    @PatchMapping("/{id}")
    public void atualizarEmprestimoStatus(@PathVariable Long id, @RequestBody Movimentacao movimentacaoAtualizada) {
        movimentacaoService.atualizarStatusMovimentacao(id, movimentacaoAtualizada);
    }

    @DeleteMapping("/{id}")
    public void deletarEmprestimo(@PathVariable Long id) {
        movimentacaoService.deletarMovimentacao(id);
    }
}
