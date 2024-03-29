package br.com.arcom.powerbatch.models.repository;

import br.com.arcom.powerbatch.models.commons.constantes.StatusProcessamento;
import br.com.arcom.powerbatch.models.domains.PlbEscalonamento;

import java.util.List;

public interface PlbEscalonamentoRepository {

    List<PlbEscalonamento> buscarTarefas(
        final StatusProcessamento statusProcessamento,
        final int qtde
    );

    boolean atualizarStatus(
        final Long id,
        final StatusProcessamento statusAnterior,
        final StatusProcessamento statusNovo
    );


}
