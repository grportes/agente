package br.com.arcom.powerbatch.models.repository;

import br.com.arcom.powerbatch.models.commons.dtos.PlbEscalonamentoDto;

import java.util.List;

public interface PlbEscalonamentoRepository {

    List<PlbEscalonamentoDto> buscarTarefas( int qtde );
}
