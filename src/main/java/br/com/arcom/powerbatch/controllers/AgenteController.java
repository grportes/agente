package br.com.arcom.powerbatch.controllers;

import br.com.arcom.powerbatch.models.domains.PlbEscalonamento;
import br.com.arcom.powerbatch.models.repository.PlbEscalonamentoRepository;
import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.util.Pair;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static br.com.arcom.powerbatch.models.commons.constantes.StatusProcessamento.AGUARDANDO;
import static br.com.arcom.powerbatch.models.commons.constantes.StatusProcessamento.ERRO;
import static br.com.arcom.powerbatch.models.commons.constantes.StatusProcessamento.EXECUCAO;
import static br.com.arcom.powerbatch.models.commons.constantes.StatusProcessamento.FINALIZADO;

public class AgenteController extends TimerTask {

    // Pool de threads.
    private static int MAX_QUEUES = 3;
    private static AtomicInteger qtQueueExecucao = new AtomicInteger(0);
    private static ExecutorService pool = Executors.newFixedThreadPool(MAX_QUEUES);

    // EntityManager:
    private final EntityManager em;

    // Repository:
    private final PlbEscalonamentoRepository escalonamentoRepository;

    // Timer:
    private Timer timer = new Timer();

    // UI - Controles
    @FXML
    private Button btnPrincipal;


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // CONSTRUCTOR
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Inject
    public AgenteController(
        final EntityManager em,
        final PlbEscalonamentoRepository escalonamentoRepository
    ) {

        this.em = em;
        this.escalonamentoRepository = escalonamentoRepository;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // EVENTOS.
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void handleBtnPrincipal( final ActionEvent actionEvent ) {

        btnPrincipal.setText("Cancelar");
        btnPrincipal.setDisable(true);

        timer.schedule(this, 0 , 5000 );

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

        List<PlbEscalonamento> tasks = escalonamentoRepository.buscarTarefas( AGUARDANDO, qtQueuesDisponiveis );
        if ( tasks.isEmpty() ) {
            System.out.println("Sem tarefas!!!!");
            System.out.println("---------------------------------------------------------------------------");
            return;
        }

        System.out.printf("Tasks para processar [%s]%n", tasks.size());
        System.out.println("---------------------------------------------------------------------------");

        for ( final PlbEscalonamento task : tasks ) {

            boolean reservouProcesso = false;

            try {
                em.getTransaction().begin();
                reservouProcesso = escalonamentoRepository.atualizarStatus(task.getId(), AGUARDANDO, EXECUCAO);
                em.getTransaction().commit();
            } catch ( Exception e ) {
                em.getTransaction().rollback();
            }

            if ( reservouProcesso ) {

                qtQueueExecucao.incrementAndGet();

                CompletableFuture.supplyAsync( () -> {
                    try {
                        System.out.printf(" [%s - %s] - INICIO PROCESSAMENTO %n", task.getId(), task.getUrl() );
                        Thread.sleep(task.getTempoProcessamento() * 10000);
                        System.out.printf(" [%s - %s] - FIM PROCESSAMENTO %n", task.getId(), task.getUrl() );
                        return new Pair<>(task.getId(), "");
                    } catch (InterruptedException e) {
                        return new Pair<>(task.getId(), e.getMessage());
                    }
                }, pool )
                    .thenAccept( result -> {
                        try {
                            em.getTransaction().begin();
                            escalonamentoRepository.atualizarStatus(
                                result.getKey(),
                                EXECUCAO,
                                result.getValue().isEmpty() ? FINALIZADO : ERRO
                            );
                            em.getTransaction().commit();
                        } catch ( Exception e ) {
                            em.getTransaction().rollback();
                        }
                    })
                    .thenRun(() -> qtQueueExecucao.decrementAndGet() );
            }
        }
    }

}
