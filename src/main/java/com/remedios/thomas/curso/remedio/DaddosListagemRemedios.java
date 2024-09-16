package com.remedios.thomas.curso.remedio;

import java.time.LocalDate;

public record DaddosListagemRemedios(
		Long id, String nome, Via via, String lote, Laboratorio laboratorio, LocalDate validade) {
		
		public DaddosListagemRemedios(Remedio remedio) {
			this(remedio.getId(), remedio.getNome(), remedio.getVia(), remedio.getLote(), remedio.getLaboratorio(), remedio.getValidade());
		}
}
