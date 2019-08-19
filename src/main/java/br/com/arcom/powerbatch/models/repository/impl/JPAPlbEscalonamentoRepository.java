package br.com.arcom.powerbatch.models.repository.impl;

import br.com.arcom.powerbatch.models.commons.constantes.StatusProcessamento;
import br.com.arcom.powerbatch.models.commons.dtos.PlbEscalonamentoDto;
import br.com.arcom.powerbatch.models.domains.PlbHistorico;
import br.com.arcom.powerbatch.models.repository.PlbEscalonamentoRepository;
import br.com.arcom.powerbatch.models.repository.PlbHistoricoRepository;
import com.google.inject.Inject;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static br.com.arcom.powerbatch.models.commons.constantes.StatusProcessamento.getValor;

public class JPAPlbEscalonamentoRepository implements PlbEscalonamentoRepository {

    private final EntityManager em;

    // Repository:
    private final PlbHistoricoRepository plbHistoricoRepository;

    @Inject
    public JPAPlbEscalonamentoRepository(
        final EntityManager em,
        final PlbHistoricoRepository plbHistoricoRepository
    ) {

        this.em = em;
        this.plbHistoricoRepository = plbHistoricoRepository;
    }

    @Override
    public List<PlbEscalonamentoDto> buscarTarefas( int qtde ) {

        return em
            .createNamedQuery("PlbEscalonamento.buscarTarefas", PlbEscalonamentoDto.class )
            .setMaxResults( qtde )
            .getResultList();
    }

    @Override
    public boolean atualizarStatus(
        final Long id,
        final StatusProcessamento statusAnterior,
        final StatusProcessamento statusNovo
    ) {

        em.getTransaction().begin();

        boolean atualizou = em
            .createNamedQuery("PlbEscalonamento.atualizarStatus" )
            .setParameter("id", id )
            .setParameter("statusAnterior", getValor(statusAnterior) )
            .setParameter("statusNovo", getValor(statusNovo) )
            .executeUpdate() == 1;

        if ( atualizou ) {
            final PlbHistorico plbHistorico = new PlbHistorico();
            plbHistorico.setIdEscalonamento(id);
            plbHistorico.setStatus(statusNovo);
            plbHistorico.setDataExecucao(LocalDateTime.now());
            plbHistorico.setDescricao("Tarefa: " + id);
            plbHistoricoRepository.save( plbHistorico );
        }

        em.getTransaction().commit();

        return atualizou;
    }

}
