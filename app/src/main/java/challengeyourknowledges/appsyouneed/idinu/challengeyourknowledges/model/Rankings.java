package challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.model;

public class Rankings {
    private Integer id;
    private Integer points;
    private Integer player_state_id;

    public Rankings() {

    }

    public Rankings(Integer id, Integer points, Integer player_state_id) {
        this.id = id;
        this.points = points;
        this.player_state_id = player_state_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getPlayer_state_id() {
        return player_state_id;
    }

    public void setPlayer_state_id(Integer player_state_id) {
        this.player_state_id = player_state_id;
    }
}
