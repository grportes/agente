package br.com.arcom.powerbatch.services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.PersistService;

@Singleton
public class JPAInitializerService {

    @Inject
    public JPAInitializerService( final PersistService persistService ) {

        persistService.start();
    }


}
