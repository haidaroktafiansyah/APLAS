package org.aplas.soccermatch;

public class LogItem {
    private String name;
    private String time;
    private String player;
    private String image;

    public LogItem(String n, String t, String p, String i) {
        name = n;
        time = t;
        player = p;
        image = i;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String val) {
        this.name = val;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String val) {
        this.time = val;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String val) {
        this.player = val;
    }
}
