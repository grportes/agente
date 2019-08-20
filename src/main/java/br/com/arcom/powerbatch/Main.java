package br.com.arcom.powerbatch;

import br.com.arcom.powerbatch.conf.Module;
import br.com.arcom.powerbatch.services.AgenteTimerService;
import com.google.inject.Guice;
import com.google.inject.Injector;

import java.util.Timer;
import java.util.TimerTask;

public final class Main {

    public static void main(String[] args) {

        Injector injector = Guice.createInjector( new Module() );

        AgenteTimerService service = injector.getInstance(AgenteTimerService.class);

        Timer timer = new Timer();
        timer.schedule((TimerTask) service, 0 , 5000 );
    }

}
