package com.test.ORM.SORM.Core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public interface CallBack {
    Object doExecute(Connection conn, PreparedStatement ps, ResultSet rs) throws Exception;
}
