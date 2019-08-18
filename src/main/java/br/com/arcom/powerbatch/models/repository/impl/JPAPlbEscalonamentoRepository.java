package br.com.arcom.powerbatch.models.repository.impl;

import br.com.arcom.powerbatch.models.commons.dtos.PlbEscalonamentoDto;
import br.com.arcom.powerbatch.models.repository.PlbEscalonamentoRepository;
import com.google.inject.Inject;

import javax.persistence.EntityManager;
import java.util.List;

public class JPAPlbEscalonamentoRepository implements PlbEscalonamentoRepository {

    private final EntityManager em;

    @Inject
    public JPAPlbEscalonamentoRepository( final EntityManager em ) {

        this.em = em;
    }

    @Override
    public List<PlbEscalonamentoDto> buscarTarefas( int qtde ) {

        return em
            .createNamedQuery("PlbEscalonamento.buscarTarefas", PlbEscalonamentoDto.class )
            .setMaxResults( qtde )
            .getResultList();
    }

}
