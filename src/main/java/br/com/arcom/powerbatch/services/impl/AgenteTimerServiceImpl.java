package br.com.arcom.powerbatch.services.impl;

import br.com.arcom.powerbatch.models.commons.dtos.PlbEscalonamentoDto;
import br.com.arcom.powerbatch.models.repository.PlbEscalonamentoRepository;
import br.com.arcom.powerbatch.services.AgenteTimerService;
import com.google.inject.Inject;

import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.String.format;
import static java.util.Objects.isNull;

public class AgenteTimerServiceImpl
    extends TimerTask
    implements AgenteTimerService
{

    // Pool de threads.
    private static int MAX_QUEUES = 3;
    private static AtomicInteger qtQueueExecucao = new AtomicInteger(0);
    public static ExecutorService pool = Executors.newFixedThreadPool(MAX_QUEUES);

    // Repository
    private final PlbEscalonamentoRepository plbEscalonamentoRepository;

    @Inject
    public AgenteTimerServiceImpl( final PlbEscalonamentoRepository plbEscalonamentoRepository ) {

        this.plbEscalonamentoRepository = plbEscalonamentoRepository;
    }

    @Override
    public void run() {

        System.out.println("---------------------------------------------------------------------------");
        System.out.printf("Queues em execução: %s%n", qtQueueExecucao.get());

        int qtQueuesDisponiveis = MAX_QUEUES - qtQueueExecucao.get();
        System.out.printf("Queues disponiveis: %s%n", qtQueuesDisponiveis);
        if (qtQueuesDisponiveis == 0) {
            System.out.println("Não vamos agendar!!");
            System.out.println("---------------------------------------------------------------------------");
            return;
        }

        List<PlbEscalonamentoDto> tasks = plbEscalonamentoRepository.buscarTarefas( qtQueuesDisponiveis );
        if ( tasks.isEmpty() ) {
            System.out.println("Sem tarefas!!!!");
            System.out.println("---------------------------------------------------------------------------");
            return;
        }

        System.out.printf("Tasks para processar [%s]%n", tasks.size());
        System.out.println("---------------------------------------------------------------------------");

        for ( final PlbEscalonamentoDto task : tasks ) {

            qtQueueExecucao.incrementAndGet();

            final CompletableFuture<PlbEscalonamentoDto> future = new CompletableFuture<>();

            CompletableFuture.runAsync(() -> {
                try {
                    future.complete( execTask(task) );
                } catch (Throwable e) {
                    future.completeExceptionally(e);
                }
            }, pool);

            future.handle((content,ex) -> {
                qtQueueExecucao.decrementAndGet();
                if ( isNull(ex) ) {
                    System.out.printf(" [%s - FINALIZADO] %n", task );
                } else {
                    ex.printStackTrace();
                }
                return null;
            });
        }
    }

    private PlbEscalonamentoDto execTask( final PlbEscalonamentoDto task ) throws InterruptedException {

        System.out.printf(" [%s - INICIANDO] %n", task );
        Thread.sleep(task.getTempoProcessamento() * 10000);
        return new PlbEscalonamentoDto(task, "Processado!");
    }
}
