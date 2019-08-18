package br.com.arcom.powerbatch.models;

import java.util.Objects;

public class Task {

    private Long id;
    private String url;
    private String statusProcessamento;

    public Task(Long id, String url) {
        this.id = id;
        this.url = url;
        this.statusProcessamento = "INICIADO";
    }

    public Task(
        final Task task,
        final String statusProcessamento
    ) {
        this.id = task.id;
        this.url = task.url;
        this.statusProcessamento = statusProcessamento;
    }

    public Long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getStatusProcessamento() {
        return statusProcessamento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return Objects.equals(getId(), task.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
