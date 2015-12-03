package com.hilo.others;

import org.xutils.DbManager;

import java.io.File;

/**
 * Created by hilo on 15/12/3.
 * <p>
 * Drscription:
 */
public class DataBaseFactory {

    public static DbManager.DaoConfig daoConfig;

    public DataBaseFactory() {

    }

    public static DbManager.DaoConfig getDaoConfig() {
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

}
