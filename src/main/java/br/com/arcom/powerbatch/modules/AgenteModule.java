package br.com.arcom.powerbatch.modules;

import br.com.arcom.powerbatch.models.repository.PlbEscalonamentoRepository;
import br.com.arcom.powerbatch.models.repository.PlbHistoricoRepository;
import br.com.arcom.powerbatch.models.repository.impl.JPAPlbEscalonamentoRepository;
import br.com.arcom.powerbatch.models.repository.impl.JPAPlbHistoricoRepository;
import br.com.arcom.powerbatch.services.AgenteTimerService;
import br.com.arcom.powerbatch.services.impl.JPAInitializerServiceImpl;
import br.com.arcom.powerbatch.services.impl.AgenteTimerServiceImpl;
import com.google.inject.AbstractModule;
import com.google.inject.persist.jpa.JpaPersistModule;

public class AgenteModule extends AbstractModule {

    @Override
    protected void configure() {

        // Incializar JPA:
        install( new JpaPersistModule("agenteJpaUnit") );
        bind( JPAInitializerServiceImpl.class ).asEagerSingleton();

        // Services:
        bind( AgenteTimerService.class ).to( AgenteTimerServiceImpl.class );

        // Repository:
        bind( PlbEscalonamentoRepository.class).to( JPAPlbEscalonamentoRepository.class );
        bind( PlbHistoricoRepository.class).to( JPAPlbHistoricoRepository.class );
    }

}
