package biorimp.storage.repositories;

import biorimp.optmodel.mappings.metaphor.MetaphorCode;
import biorimp.storage.entities.RefKey;
import biorimp.storage.entities.Register;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by developer on 12/29/15.
 */
public class RegisterRepository extends Repository<Register> {

    public static String TABLE_NAME = "brp_register";
    protected static RegisterRepository instancia;

    protected RegisterRepository() {
    }

    public static RegisterRepository getInstance() {
        if (instancia == null){
            instancia = new RegisterRepository();
        }
        return instancia;
    }

    public List<Register> fetchAll() {
        getConnection();
        List<Register> results = new ArrayList();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + Register.COLUMN_REFACTOR;
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                results.add(resultEntity(resultSet));
            }

            resultSet.close();
            statement.close();
            //connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }

    @Override
    public Register resultEntity(ResultSet resultSet) {
        try {
            String refactor = resultSet.getString(Register.COLUMN_REFACTOR);
            String metric = resultSet.getString(Register.COLUMN_METRIC);
            String sources = resultSet.getString(Register.COLUMN_SOURCES);
            String targets = resultSet.getString(Register.COLUMN_TARGETS);
            String method = resultSet.getString(Register.COLUMN_METHOD);
            String field = resultSet.getString(Register.COLUMN_FIELD);
            String classs = resultSet.getString(Register.COLUMN_CLASS);
            String system = resultSet.getString(Register.COLUMN_SYSTEM);
            double value = resultSet.getDouble(Register.COLUMN_VALUE);

            return new Register(refactor, metric, value, sources, targets, field, method, classs);
        } catch (Exception e) {
            return null;
        }
    }

    @Deprecated
    public Register getRegister(String refactorID, String src, String tgt, String mth, String fld, String system) {
        //Fixme wrong set string
        getConnection();
        Register register = new Register();
        if (connection != null) {
            try {
                String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + Register.COLUMN_REFACTOR + " = ? AND " + Register.COLUMN_SOURCES + "= ? AND "
                        + Register.COLUMN_TARGETS + "= ? AND " + Register.COLUMN_METHOD + "= ? AND " + Register.COLUMN_FIELD + "= ? AND " + Register.COLUMN_SYSTEM + "= ?";

                //1000 danaderp Query depends on the refactor type
                if (refactorID.equals("EM") || refactorID.equals("IM") || refactorID.equals("RMMO")) { //-> Only matters src + mtd
                    query = "SELECT * FROM " + TABLE_NAME + " WHERE " + Register.COLUMN_REFACTOR + " = ? AND " + Register.COLUMN_SOURCES
                            + "= ? AND " + Register.COLUMN_METHOD + "= ? AND " + Register.COLUMN_SYSTEM + "= ?";
                } else {
                    if (refactorID.equals("MF") || refactorID.equals("PDF") || refactorID.equals("PUF")) {//->Only matters src+tgt+fld
                        query = "SELECT * FROM " + TABLE_NAME + " WHERE " + Register.COLUMN_REFACTOR + " = ? AND " + Register.COLUMN_SOURCES + "= ? AND "
                                + Register.COLUMN_TARGETS + "= ? AND " + Register.COLUMN_FIELD + "= ? AND " + Register.COLUMN_SYSTEM + "= ?";
                    } else {
                        if (refactorID.equals("MM") || refactorID.equals("PDM") || refactorID.equals("PUM")) {//->Only matters src+mtd+tgt
                            query = "SELECT * FROM " + TABLE_NAME + " WHERE " + Register.COLUMN_REFACTOR + " = ? AND " + Register.COLUMN_SOURCES + "= ? AND "
                                    + Register.COLUMN_TARGETS + "= ? AND " + Register.COLUMN_METHOD + "= ? AND " + Register.COLUMN_SYSTEM + "= ?";
                        } else {
                            if (refactorID.equals("RDI") || refactorID.equals("RID")) {//->Only matters src+tgt
                                query = "SELECT * FROM " + TABLE_NAME + " WHERE " + Register.COLUMN_REFACTOR + " = ? AND " + Register.COLUMN_SOURCES + "= ? AND "
                                        + Register.COLUMN_TARGETS + "= ? AND " + Register.COLUMN_SYSTEM + "= ?";
                            } else {
                                if (refactorID.equals("EC")) { //->Only matters src+fld+mtd
                                    query = "SELECT * FROM " + TABLE_NAME + " WHERE " + Register.COLUMN_REFACTOR + " = ? AND " + Register.COLUMN_SOURCES + "= ? AND "
                                            + Register.COLUMN_METHOD + "= ? AND " + Register.COLUMN_FIELD + "= ? AND " + Register.COLUMN_SYSTEM + "= ?";
                                }
                            }
                        }
                    }
                }

                //1000
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, refactorID);
                statement.setString(2, src);
                statement.setString(3, tgt);
                statement.setString(4, mth);
                statement.setString(5, fld);
                statement.setString(6, system);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    register = resultEntity(resultSet);
                }

                resultSet.close();
                statement.close();
                //connection.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return register;
    }

    public void insertRegister(String refactorID, String src, String metric,
                               double value, String tgt, String mth, String fld,
                               String classs, String system) {
        getConnection();
        if (connection != null) {
            try {
                String query = "INSERT INTO " + TABLE_NAME + " (" + Register.COLUMN_REFACTOR + "," + Register.COLUMN_SOURCES + "," + Register.COLUMN_METRIC + "," + Register.COLUMN_VALUE +
                        "," + Register.COLUMN_TARGETS + "," + Register.COLUMN_METHOD + "," + Register.COLUMN_FIELD + "," +
                        Register.COLUMN_CLASS + "," + Register.COLUMN_SYSTEM + ") VALUES (?,?,?,?,?,?,?,?,?)";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, refactorID);
                statement.setString(2, src);
                statement.setString(3, metric);
                statement.setDouble(4, value);
                statement.setString(5, tgt);
                statement.setString(6, mth);
                statement.setString(7, fld);
                statement.setString(8, classs);
                statement.setString(9, system);
                statement.executeUpdate();
                statement.close();
                //connection.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void insertRegister(Register register) {
        insertRegister(register.getRefactor(), register.getSources(), register.getMetric(), register.getValue(), register.getTargets(),
                register.getMethod(), register.getField(), register.getClasss(), register.getSystem());

    }

    public List<Register> getRegistersByClass(String refactorID, String src, String tgt, String mth, String fld, String system) {
        getConnection();
        List<Register> results = new ArrayList();
        if (connection != null) {
            try {
                String sourceQ = "";
                if (!src.isEmpty()) {
                    for (String sr : src.split(",")) {
                        sourceQ += " AND ";
                        sourceQ += Register.COLUMN_SOURCES + " LIKE '%" + sr + "%' ";
                    }
                    sourceQ += " AND LENGTH(" + Register.COLUMN_SOURCES + ") =" + src.length();
                }
                String targetQ = "";
                if (!tgt.isEmpty()) {
                    for (String tg : tgt.split(",")) {
                        targetQ += " AND ";
                        targetQ += Register.COLUMN_TARGETS + " LIKE '%" + tg + "%' ";
                    }
                    targetQ += " AND LENGTH(" + Register.COLUMN_TARGETS + ") =" + tgt.length();
                }
                String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + Register.COLUMN_REFACTOR + " = ? " + " AND " +
                        Register.COLUMN_SYSTEM + " = ?" + sourceQ + targetQ;
                query += mth.isEmpty() ? "" : " AND " + Register.COLUMN_METHOD + "= ? ";
                query += fld.isEmpty() ? "" : " AND " + Register.COLUMN_FIELD + "= ?";
                query += " ORDER BY " + Register.COLUMN_CLASS;


                int index = 1;
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(index++, refactorID);
                statement.setString(index++, system);
                if (!mth.isEmpty()) statement.setString(index++, mth);
                if (!fld.isEmpty()) statement.setString(index, fld);
                ResultSet resultSet = statement.executeQuery();


                while (resultSet.next()) {
                    results.add(resultEntity(resultSet));
                }

                resultSet.close();
                statement.close();
                //connection.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return results;
    }

    public List<Register> getRegistersByClass(RefKey refKey) {

        return getRegistersByClass(refKey.getRefactorID(), refKey.getSrc(), refKey.getTgt(),
                refKey.getMth(), refKey.getFld(), refKey.getSystem());
    }

    public void truncateTable(){
        getConnection();
        if (connection != null) {
            try {
                String query = "TRUNCATE TABLE " + TABLE_NAME ;
                PreparedStatement statement = connection.prepareStatement(query);
                statement.executeUpdate();
                statement.close();
                //connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}
