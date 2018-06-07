package challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.model;

public class AppInfo {
    private Long id;
    private long lastTimePlayed;


    public AppInfo(Long id, long lastTimePlayed) {
        this.id = id;
        this.lastTimePlayed = lastTimePlayed;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public long getLastTimePlayed() {
        return lastTimePlayed;
    }

    public void setLastTimePlayed(long lastTimePlayed) {
        this.lastTimePlayed = lastTimePlayed;
    }
}
