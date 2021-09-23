package com.experis.de.ChinookTunes.DataAccess;
import com.experis.de.ChinookTunes.Logging.Logger;

import java.sql.*;

public class SqlLiteHelper {
    // Setup
    Logger logger;
    String URL = "jdbc:sqlite::resource:Chinook_Sqlite.sqlite";
    Connection conn = null;

    public SqlLiteHelper(Logger logger) {
        this.logger = logger;
    }

    public Integer executeUpdate(String sql, Object... params) {
        Integer result = -1;

        try {
            // Open Connection
            openConnetion();

            // Execute Statement
            result = getPreparedStatement(sql, params).executeUpdate();

            logger.log("updateQuery() has been successfully executed.");

        } catch (Exception ex) {
            logger.log("updateQuery() failed.");
            logger.log(ex);
        }

        return result;
    }

    public ResultSet executeQuery(String sql, Object... params) {

        ResultSet resultSet = null;

        try {
            // Open Connection
            openConnetion();

            // Execute Statement
            resultSet = getPreparedStatement(sql, params).executeQuery();

            logger.log("Query has been executed successfully.");
            logger.log(sql);

        } catch (Exception ex) {
             logger.log("executeQuery() failed: "+ex);
        }

        return resultSet;
    }

    public void closeConnection() {
        try {
            // Close Connection
            if (conn != null || !conn.isClosed()) {
                conn.close();
                logger.log("Connection to SQLite has been closed.");
            }
        } catch (Exception ex) {
             logger.log("Something went wrong while closing connection.");
             logger.log(ex);
        }
    }

    public void openConnetion() {
        try {
            // Open Connection
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(URL);
                logger.log("Connection to SQLite has been established.");
            }
        } catch (Exception ex) {
             logger.log("Something went wrong while opening connection.");
             logger.log(ex);
        }
    }

    public String prepareSql(String sql, Integer limit, Integer offset, String orderBy) {

        if ((orderBy != null) && (orderBy.length() > 0)) {
            sql+= " order by "+orderBy;
        }
        if (limit >= 0) {
            sql+= " limit "+limit;
        }
        if (offset >= 0) {
            sql+= " offset "+offset;
        }

        return sql;
    }

    public String prepareSql(String sql, Integer limit, Integer offset) {
        return prepareSql(sql, limit, offset, null);
    }

    public boolean hasColumn(ResultSet rs, String columnName) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        int columns = rsmd.getColumnCount();
        for (int x = 1; x <= columns; x++) {
            if (columnName.equals(rsmd.getColumnLabel(x))) {
                return true;
            }
        }
        return false;
    }

    public String tryGetString(ResultSet rs, String columnName) throws SQLException {
        if (hasColumn(rs, columnName))
            return rs.getString(columnName);
        else
            return null;
    }

    private PreparedStatement getPreparedStatement(String sql, Object... params) throws SQLException {
        // Prepare Statement
        PreparedStatement preparedStatement = conn.prepareStatement(sql);

        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                if (params[i] instanceof String) {
                    preparedStatement.setString(i + 1, (String) params[i]);
                }
                if (params[i] instanceof Integer) {
                    preparedStatement.setInt(i + 1, (Integer) params[i]);
                }
            }
        }

        return preparedStatement;
    }



}
