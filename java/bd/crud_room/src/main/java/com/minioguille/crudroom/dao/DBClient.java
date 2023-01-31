/**
 * =================================================
 * CLASS: DBClient
 * DESCRIPTION: A singleton to connect with the db
 *              and manipulate it.
 * CREATE BY: GM
 * =================================================
 */
package com.minioguille.crudroom.dao;

import android.content.Context;

import androidx.room.Room;

public class DBClient {

    private static DBClient mInstance;
    private final DBProducts mDB;

    public static synchronized DBClient getmInstance(Context aContext){
        if ( mInstance == null)
            mInstance = new DBClient(aContext);
        return mInstance;
    }

    public DBProducts getDB(){
        return mDB;
    }

    private DBClient(Context aContext){
        mDB = Room.databaseBuilder(aContext,DBProducts.class,"dbproducts").createFromAsset("database/dbproducts.db").build();
    }


}
