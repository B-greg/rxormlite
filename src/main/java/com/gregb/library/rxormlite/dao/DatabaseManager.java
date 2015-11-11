package com.gregb.library.rxormlite.dao;

import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;
import java.util.List;

/**
 * The type Database manager.
 * @param <T>  the type parameter
 * @author Gregoire BARRET
 * @date 12 oct. 2013
 */
public abstract class DatabaseManager<T extends DatabaseModel> implements IRepository<T>{


    /**
     * Instantiates a new Database manager.
     */
    protected DatabaseManager() {
    }

    /**
     *  Get the table name.
     * @return The table name.
     */
    public abstract String getTableName();

    /**
     * Gets helper.
     *
     * @return the helper
     */
    public DatabaseHelper getHelper() {
        return DatabaseHelper.getInstance();
    }

    @Override
    public List<T> GetAll() {
        List<T> items = null;
        try {
            items = getHelper().<T>getDao(getTableName()).queryForEq(DatabaseModel.ENABLE, true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    @Override
    public T GetById(int id) {
        T items = null;
        try {
            items = getHelper().<T>getDao(getTableName()).queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    @Override
    public void Update(T entite) {
        try {
            getHelper().getDao(getTableName()).update(entite);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void Delete(T entite) {
        try {
            getHelper().getDao(getTableName()).delete(entite);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void DeleteAll() {
        try {
            TableUtils.clearTable(getHelper().<T>getDao(getTableName()).getConnectionSource(),
                    getHelper().<T>getDao(getTableName()).getDataClass());
        } catch (SQLException e) {
            e.printStackTrace();
        }   
    }

    @Override
    public void Refresh(T entite) {
        try {
            getHelper().<T>getDao(getTableName()).refresh(entite);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void CreateOrUpdate(T entite) {
        try {
            getHelper().<T>getDao(getTableName()).createOrUpdate(entite);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void Add(T entite) {
        try {
            getHelper().<T>getDao(getTableName()).create(entite);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
