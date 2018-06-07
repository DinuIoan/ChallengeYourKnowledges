package challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.model;

public class Question {
    private Integer id;
    private String text;
    private String type;
    private String answear1;
    private String answear2;
    private String answear3;
    private String correct_answear;
    private Integer points;
    private Integer player_state_id;


    public Question(Integer id, String text, String type, String answear1,
                    String answear2, String answear3, String correct_answear, Integer points, Integer player_state_id) {
        this.id = id;
        this.text = text;
        this.type = type;
        this.answear1 = answear1;
        this.answear2 = answear2;
        this.answear3 = answear3;
        this.correct_answear = correct_answear;
        this.player_state_id = player_state_id;
        this.points = points;
    }

    public String getCorrect_answear() {
        return correct_answear;
    }

    public void setCorrect_answear(String correct_answear) {
        this.correct_answear = correct_answear;
    }

    public Integer getPlayer_state_id() {
        return player_state_id;
    }

    public void setPlayer_state_id(Integer player_state_id) {
        this.player_state_id = player_state_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAnswear1() {
        return answear1;
    }

    public void setAnswear1(String answear1) {
        this.answear1 = answear1;
    }

    public String getAnswear2() {
        return answear2;
    }

    public void setAnswear2(String answear2) {
        this.answear2 = answear2;
    }

    public String getAnswear3() {
        return answear3;
    }

    public void setAnswear3(String answear3) {
        this.answear3 = answear3;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

}
