package challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.model;

public class PlayerState {
    private Integer id;
    private Integer points;
    private String name;

    public PlayerState(Integer id, Integer points, String name) {
        this.id = id;
        this.points = points;
        this.name = name;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
