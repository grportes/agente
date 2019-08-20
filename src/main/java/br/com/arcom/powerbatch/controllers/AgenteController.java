package br.com.arcom.powerbatch.controllers;

import br.com.arcom.powerbatch.models.commons.constantes.StatusProcessamento;
import br.com.arcom.powerbatch.models.domains.PlbEscalonamento;
import br.com.arcom.powerbatch.models.repository.PlbEscalonamentoRepository;
import com.google.inject.Inject;
import javafx.event.ActionEvent;

import java.util.List;

public class AgenteController {

    private final PlbEscalonamentoRepository escalonamentoRepository;

    @Inject
    public AgenteController(PlbEscalonamentoRepository escalonamentoRepository) {
        this.escalonamentoRepository = escalonamentoRepository;
    }


    public void handleBtnPrincipal( final ActionEvent actionEvent ) {

        List<PlbEscalonamento> plbEscalonamentos = escalonamentoRepository.buscarTarefas(StatusProcessamento.AGUARDANDO, 2);
        for (PlbEscalonamento plbEscalonamento : plbEscalonamentos) {
            System.out.println(plbEscalonamento.getTempoProcessamento());
        }
    }
}
