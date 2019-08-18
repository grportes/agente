package br.com.arcom.powerbatch.models.commons.dtos;

import java.util.Objects;

import static java.lang.String.format;

public class PlbEscalonamentoDto {

    private final Long id;
    private final String titulo;
    private final String url;
    private final Short tempoProcessamento;
    private final String status;

    @Deprecated
    public PlbEscalonamentoDto(
        final Long id,
        final String titulo,
        final String url,
        final Short tempoProcessamento
    ) {

        this.id = id;
        this.titulo = titulo;
        this.url = url;
        this.tempoProcessamento = tempoProcessamento;
        this.status = "";
    }

    @Deprecated
    public PlbEscalonamentoDto(
        final PlbEscalonamentoDto dto,
        final String status
    ) {

        this.id = dto.getId();
        this.titulo = dto.getTitulo();
        this.url = dto.getUrl();
        this.tempoProcessamento = dto.getTempoProcessamento();
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getUrl() {
        return url;
    }

    public Short getTempoProcessamento() {
        return tempoProcessamento;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlbEscalonamentoDto)) return false;
        PlbEscalonamentoDto that = (PlbEscalonamentoDto) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {

        final String processo = format(
            "Processo [%s] com duração de [%s] segundos",
            getTitulo(), getTempoProcessamento()
        );

        return processo;
    }
}
