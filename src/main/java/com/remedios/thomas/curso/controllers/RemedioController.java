package com.remedios.thomas.curso.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.remedios.thomas.curso.remedio.DaddosListagemRemedios;
import com.remedios.thomas.curso.remedio.DadosAtualizarRemedios;
import com.remedios.thomas.curso.remedio.DadosCadastroRemedios;
import com.remedios.thomas.curso.remedio.DadosDetalhamentoRemedios;
import com.remedios.thomas.curso.remedio.Remedio;
import com.remedios.thomas.curso.remedio.RemedioRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/remedios")
public class RemedioController {
	
	@Autowired
	private RemedioRepository repository;
	
	@PostMapping
	@Transactional
	public void cadastrar(@RequestBody @Valid DadosCadastroRemedios dados) {
		
		repository.save(new Remedio(dados));
		
	}
	
	@GetMapping
	public ResponseEntity<List<DaddosListagemRemedios>> listar(){
		var lista = repository.findAllByAtivoTrue().stream().map(DaddosListagemRemedios::new).toList();
		
		return ResponseEntity.ok(lista);
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<DadosDetalhamentoRemedios> detalhar(@PathVariable Long id) {
		var remedio = repository.getReferenceById(id);
		
		return ResponseEntity.ok(new DadosDetalhamentoRemedios(remedio));
	}
	
	
	@PutMapping
	@Transactional
	public ResponseEntity<DadosDetalhamentoRemedios> atualizar(@RequestBody @Valid DadosAtualizarRemedios dados) {
		var remedio = repository.getReferenceById(dados.id());
		
		remedio.atualizarInformacoes(dados);
		
		return ResponseEntity.ok(new DadosDetalhamentoRemedios(remedio));
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<Void> excluir(@PathVariable Long id) {
		repository.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/inativar/{id}")
	@Transactional
	public ResponseEntity<Void> inativar(@PathVariable Long id) {
		var remedio = repository.getReferenceById(id);
		
		remedio.inativarRemedio();
		
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/ativar/{id}")
	@Transactional
	public ResponseEntity<Void> ativar(@PathVariable Long id) {
		var remedio = repository.getReferenceById(id);
		remedio.ativarRemedio();
		
		return ResponseEntity.noContent().build();
	}
}
