package com.gregb.library.rxormlite.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by gregoire barret on 6/4/15.
 * For Tops project.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    public static final String TAG = "TestDbOpenHelper";

    private HashMap<String, Class> mDaoClass;
    private HashMap<String, Dao> mDao;
    private static DatabaseHelper mInstance;
    private final static ReentrantLock lock = new ReentrantLock();

    public DatabaseHelper(Context context, String databaseName, SQLiteDatabase.CursorFactory
            factory, int databaseVersion, HashMap<String, Class> daoClazz) {
        super(context, databaseName, factory, databaseVersion);
        mDaoClass = daoClazz;
        mDao = new HashMap<>();
        mInstance = this;
    }


    /**
     * This is called when the database is first created. Usually you should call createTable statements here to create
     * the tables that will store your data.
     */
    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onCreate");
            for(Map.Entry<String,Class>entry : mDaoClass.entrySet()){
                TableUtils.createTable(connectionSource, entry.getValue());
            }
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * This is called when your application is upgraded and it has a higher version number. This allows you to adjust
     * the various data to match the new version number.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onUpgrade");
            for(Map.Entry<String,Class>entry : mDaoClass.entrySet()){
                TableUtils.dropTable(connectionSource, entry.getValue(), true);
            }
            // after we drop the old databases, we create the new ones
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }


    /**
     * Returns the Database Access Object (DAO) for our SimpleData class. It will create it or just give the cached
     * value.
     */
    @SuppressWarnings("unchecked")
    public <T> Dao<T, Integer> getDao(String tableName) throws SQLException {
        if(!mDaoClass.containsKey(tableName)){
            throw new SQLException("This table doesn't exist");
        }

        if (mDao.get(tableName) == null) {
            mDao.put(tableName, getDao(mDaoClass.get(tableName)));
        }

        return mDao.get(tableName);
    }

    @Override
    public void close() {
        super.close();
        mDao = null;
    }

    public static DatabaseHelper getInstance(){
        if(mInstance==null){
            throw new NullPointerException("DatabaseHelper is not initialize");
        }
        lock.lock(); // block until condition holds
        try {
            return mInstance;
        } finally {
            lock.unlock();
        }
    }
}
