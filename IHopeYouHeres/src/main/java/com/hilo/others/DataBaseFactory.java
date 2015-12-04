package com.hilo.others;

import org.xutils.DbManager;
import org.xutils.x;

import java.io.File;

/**
 * Created by hilo on 15/12/3.
 * <p>
 * Drscription:
 */
public class DataBaseFactory {

    private static DbManager dbManager;
    private static DbManager.DaoConfig daoConfig;

    public DataBaseFactory() {

    }

    public static final DbManager getInstance() {
        if(dbManager == null) {
            synchronized (DataBaseFactory.class) {
                if(dbManager == null) {
                    dbManager = x.getDb(getDaoConfig());
                }
            }
        }
        return dbManager;
    }

    private static DbManager.DaoConfig getDaoConfig() {
        if(daoConfig == null) {
            synchronized (DataBaseFactory.class) {
                if(daoConfig == null) {
                    // init database
                    daoConfig = new DbManager.DaoConfig()
                            .setDbName("test")
                            .setDbDir(new File("/sdcard"))
                            .setDbVersion(1)
                            .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                                @Override
                                public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                                    // db.addColumn(...);
                                    // db.dropTable(...);

                                }
                            });
                }
            }
        }
        return daoConfig;
    }

    public static final void setDbManagerNull() {
        if(dbManager != null)
            dbManager = null;
        if(daoConfig != null)
            daoConfig = null;
    }

}
