package br.com.arcom.powerbatch.models.repository.impl;

import br.com.arcom.powerbatch.models.domains.PlbHistorico;
import br.com.arcom.powerbatch.models.repository.PlbHistoricoRepository;
import com.google.inject.Inject;

import javax.persistence.EntityManager;

public class JPAPlbHistoricoRepository implements PlbHistoricoRepository {

    private final EntityManager em;

    @Inject
    public JPAPlbHistoricoRepository( final EntityManager em ) {
        this.em = em;
    }

    @Override
    public void save(final PlbHistorico model) {

        em.persist( model );
    }
}
