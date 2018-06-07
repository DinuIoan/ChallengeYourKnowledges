package challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.model;

public class Game {
    private Integer id;
    private Integer games_number;
    private Integer player_state_id;

    public Game(Integer id, Integer games_number, Integer player_state_id) {
        this.id = id;
        this.games_number = games_number;
        this.player_state_id = player_state_id;
    }

    public Integer getGames_number() {
        return games_number;
    }

    public void setGames_number(Integer games_number) {
        this.games_number = games_number;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getPlayer_state_id() {
        return player_state_id;
    }

    public void setPlayer_state_id(Integer player_state_id) {
        this.player_state_id = player_state_id;
    }

}
