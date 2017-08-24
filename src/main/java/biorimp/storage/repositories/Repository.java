package biorimp.storage.repositories;

import biorimp.optmodel.mappings.metaphor.MetaphorCode;
import biorimp.storage.connection.ConnectionJDBC;
import biorimp.storage.connection.ConnectionSQLite;
import biorimp.storage.entities.Entity;

import java.sql.Connection;
import java.sql.ResultSet;

/**
 * Created by developer on 12/29/15.
 */
public abstract class Repository<T extends Entity> {
    protected Connection connection;

    protected void getConnection() {
        try {
            if( MetaphorCode.isIsSqlIte() ){
                connection =  ConnectionSQLite.getInstance().connect();
            }else{
                connection = ConnectionJDBC.getInstance().obtenerConexion();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public abstract T resultEntity(ResultSet resultSet);

}
