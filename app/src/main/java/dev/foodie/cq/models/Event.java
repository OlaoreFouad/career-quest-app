package dev.foodie.cq.models;

public class Event {

    private String content;
    private int index;

    public Event(String content, int index) {
        this.content = content;
        this.index = index;
    }

    public Event() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
