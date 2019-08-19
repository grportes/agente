package br.com.arcom.powerbatch.models.domains;

import br.com.arcom.powerbatch.models.commons.constantes.StatusProcessamento;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "plb_historicos")
public class PlbHistorico {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column( name = "id_escalonamento" )
    private Long idEscalonamento;

    @Column( name = "data_execucao" )
    private LocalDateTime dataExecucao;

    @Column( name = "status" )
    private StatusProcessamento status;

    @Column( name = "descricao" )
    private String descricao;


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // GETTERS && SETTERS
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdEscalonamento() {
        return idEscalonamento;
    }

    public void setIdEscalonamento(Long idEscalonamento) {
        this.idEscalonamento = idEscalonamento;
    }

    public LocalDateTime getDataExecucao() {
        return dataExecucao;
    }

    public void setDataExecucao(LocalDateTime dataExecucao) {
        this.dataExecucao = dataExecucao;
    }

    public StatusProcessamento getStatus() {
        return status;
    }

    public void setStatus(StatusProcessamento status) {
        this.status = status;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // EQUALS & HASHCODE
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof PlbHistorico)) return false;
        PlbHistorico that = (PlbHistorico) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId());
    }
}
