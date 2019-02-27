package challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.model;

public class Nota {
    private Integer id;
    private Double nota;
    private Long timestamp;
    private Integer playerStateId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getPlayerStateId() {
        return playerStateId;
    }

    public void setPlayerStateId(Integer playerStateId) {
        this.playerStateId = playerStateId;
    }
}
