package br.com.arcom.powerbatch.conf;

import br.com.arcom.powerbatch.models.repository.PlbEscalonamentoRepository;
import br.com.arcom.powerbatch.models.repository.PlbHistoricoRepository;
import br.com.arcom.powerbatch.models.repository.impl.JPAPlbEscalonamentoRepository;
import br.com.arcom.powerbatch.models.repository.impl.JPAPlbHistoricoRepository;
import br.com.arcom.powerbatch.services.AgenteTimerService;
import br.com.arcom.powerbatch.services.impl.AgenteTimerServiceImpl;
import com.google.inject.AbstractModule;
import com.google.inject.persist.jpa.JpaPersistModule;

public class Module extends AbstractModule {

    @Override
    protected void configure() {

        install( new JpaPersistModule("agenteJpaUnit") );

        bind(JPAInitializer.class).asEagerSingleton();
        bind(AgenteTimerService.class).to(AgenteTimerServiceImpl.class);
        bind(PlbEscalonamentoRepository.class).to(JPAPlbEscalonamentoRepository.class);
        bind(PlbHistoricoRepository.class).to(JPAPlbHistoricoRepository.class);



    }


}
