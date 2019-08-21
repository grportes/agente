package br.com.arcom.powerbatch.controllers;

import br.com.arcom.powerbatch.infra.util.UtilDate;
import br.com.arcom.powerbatch.models.domains.PlbEscalonamento;
import br.com.arcom.powerbatch.models.repository.PlbEscalonamentoRepository;
import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.util.Pair;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
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
import static java.lang.String.format;
import static java.lang.String.valueOf;

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
    @FXML private Button btnPrincipal;
    @FXML private TextField txtProxExecucao;
    @FXML private TextField txtMaxThreads;
    @FXML private TextField txtEmExecucao;
    @FXML private TextField txtDisponivel;
    @FXML private TextField txtMsg;


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

        if ( Objects.equals(btnPrincipal.getText(),"STOP" ) ) {
            btnPrincipal.setText("START");
            timer.cancel();
        } else {
            btnPrincipal.setText("STOP");
            timer.schedule(this, 0 , 5000 );
            txtProxExecucao.setText(UtilDate.convString( LocalDateTime.now().plusSeconds(5)) );
        }
    }

    @Override
    public void run() {

        txtMaxThreads.setText( valueOf(MAX_QUEUES) );
        txtEmExecucao.setText( valueOf(qtQueueExecucao.get()) );

        int qtQueuesDisponiveis = MAX_QUEUES - qtQueueExecucao.get();
        txtDisponivel.setText( valueOf(qtQueuesDisponiveis) );

        if ( qtQueuesDisponiveis == 0 ) {
            txtMsg.setText( "EM PROCESSAMENTO....");
            return;
        }

        List<PlbEscalonamento> tasks = escalonamentoRepository.buscarTarefas( AGUARDANDO, qtQueuesDisponiveis );
        if ( tasks.isEmpty() ) {
            txtMsg.setText( "SEM TAREFAS PARA DISPARAR!!");
            return;
        }

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

                txtEmExecucao.setText( valueOf( qtQueueExecucao.incrementAndGet() ) );

                CompletableFuture.supplyAsync( () -> {
                    try {
                        txtMsg.setText( format( " [%s - %s] - INICIO PROCESSAMENTO %n", task.getId(), task.getUrl() ) );
                        Thread.sleep(task.getTempoProcessamento() * 10000);
                        txtMsg.setText( format( " [%s - %s] - FIM PROCESSAMENTO %n", task.getId(), task.getUrl() ) );
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
                    .thenRun(() -> txtEmExecucao.setText( valueOf( qtQueueExecucao.decrementAndGet() ) ) );
            }
        }
    }

}
