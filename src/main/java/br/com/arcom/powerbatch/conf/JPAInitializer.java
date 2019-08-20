package br.com.arcom.powerbatch.conf;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.PersistService;

@Singleton
public class JPAInitializer {

    @Inject
    public JPAInitializer(final PersistService persistService ) {

        persistService.start();
    }


}
