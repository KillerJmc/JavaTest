package com.test.mysql.sorm.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Be responsible for CallBack.
 */
public interface CallBack {
    Object doExecute(Connection conn, PreparedStatement ps, ResultSet rs) throws Exception;
}
