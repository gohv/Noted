package xyz.georgyhristov.calculator.grosseries.noted.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class Note extends RealmObject {

    private String id;
    private String title;
    private String note;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
