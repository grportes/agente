package br.com.arcom.powerbatch.conf;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.PersistService;

import javax.annotation.PreDestroy;

@Singleton
public class ApplicationStart {

    @Inject
    public ApplicationStart(final PersistService persistService ) {

        persistService.start();
    }

    @PreDestroy
    public void shutdown() {

        System.out.println("Entrou aqui!!");
    }
}
