package com.gregb.library.rxormlite.dao;

import com.j256.ormlite.field.DatabaseField;
import java.util.Date;

/**
 * Created by gregoire on 9/22/14.
 */
public class DatabaseModel<T> {

    public final static String ID = "id";
    public final static String ENABLE = "enable";
    public final static String UPDATED_AT = "updated_at";
    public final static String CREATED_AT = "created_at";

    @DatabaseField(id = true, columnName = ID)
    public int id;
    @DatabaseField(columnName = ENABLE)
    public Boolean enable;
    @DatabaseField(columnName = CREATED_AT)
    public Date createdAt;
    @DatabaseField(columnName = UPDATED_AT)
    public Date updatedAt;

    public DatabaseModel() {
    }

    public DatabaseModel(int id) {
        this.id = id;
    }




}
