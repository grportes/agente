package br.com.arcom.powerbatch.services.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.PersistService;

@Singleton
public class JPAInitializerServiceImpl {

    @Inject
    public JPAInitializerServiceImpl(final PersistService persistService ) {

        persistService.start();
    }


}
