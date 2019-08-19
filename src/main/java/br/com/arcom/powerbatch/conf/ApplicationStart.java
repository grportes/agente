package br.com.arcom.powerbatch.conf;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.PersistService;

@Singleton
public class ApplicationStart {

    @Inject
    public ApplicationStart( final PersistService persistService ) {

        persistService.start();
    }


}
