package br.com.arcom.powerbatch.conf;

import br.com.arcom.powerbatch.models.repository.PlbEscalonamentoRepository;
import br.com.arcom.powerbatch.models.repository.impl.JPAPlbEscalonamentoRepository;
import br.com.arcom.powerbatch.services.AgenteTimerService;
import br.com.arcom.powerbatch.services.impl.AgenteTimerServiceImpl;
import com.google.inject.AbstractModule;

public class Module extends AbstractModule {

    @Override
    protected void configure() {

        bind(ApplicationStart.class).asEagerSingleton();
        bind(AgenteTimerService.class).to(AgenteTimerServiceImpl.class);
        bind(PlbEscalonamentoRepository.class).to(JPAPlbEscalonamentoRepository.class);
    }


}
