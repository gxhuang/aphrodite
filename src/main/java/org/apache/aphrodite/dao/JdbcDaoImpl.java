package org.apache.aphrodite.dao;

import org.apache.aphrodite.dataset.Field;
import org.apache.aphrodite.dataset.PageView;
import org.apache.aphrodite.dataset.Record;
import org.apache.aphrodite.dataset.SqlContext;
import org.apache.aphrodite.exception.DaoException;
import org.apache.aphrodite.util.Constants;
import org.apache.aphrodite.util.DateUtil;
import org.apache.aphrodite.util.ObjectTableUtil;
import org.apache.aphrodite.util.PageViewUtil;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 类描述：
 *
 * @author: huang.yuewen
 * <p>
 * History:  2015年05月07日 15:33   huang.yuewen   Created.
 */
public class JdbcDaoImpl implements JdbcDao {

    private DataSource ds;

    private ThreadLocal<Connection> tlocals = new ThreadLocal<Connection>();


    public void begin(){
        try {
            getConnection().setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void rollback(){
        try {
            getConnection().rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void commit(){
        try {
            getConnection().commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close(){
        try {
            getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int insert(PageView pv) {
        //tableName
        PreparedStatement pstmt = null;
        try {
            SqlContext sqlContext = PageViewUtil.getSql(PageViewUtil.SqlType.INSERT, pv);
            pstmt = getConnection().prepareStatement(String.format(Constants.INSERT_FORMAT, ObjectTableUtil.toTableFieldFormat(pv.getName()), sqlContext.getHead(), sqlContext.getTail()));
            setInsertParam(sqlContext, pv, pstmt);
            int[] results = pstmt.executeBatch();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        } finally {
            close(null, pstmt);
        }

        return 0;
    }

    private void setInsertParam(SqlContext sqlContext, PageView pv, PreparedStatement pstmt) throws SQLException {
        List<Record> records = pv.getGrid().getRecords();
        String[] fieldNames = sqlContext.getFieldNames();
        for (int index = 0, max = records.size() - 1; index <= max; index++) {
            Map<String, String> values = records.get(index).getRecordVal();
            for (int j = 1, len = fieldNames.length; j <= len; j++) {
                setParam(pstmt, j, pv.getField(fieldNames[j - 1]), values.get(fieldNames[j - 1]));
            }
            pstmt.addBatch();
        }
    }

    private void setParam(PreparedStatement pstmt, int index, Field field, String value) throws SQLException {
        if ("date".equals(field.getDataType())) {
            if (Constants.DATE_FORMAT.equals(field.getFormat())) {
                pstmt.setDate(index, new Date(DateUtil.toDate(value).getTime()));
            } else if (Constants.DATETIME_FORMAT.equals(field.getFormat())) {
                pstmt.setDate(index, new Date(DateUtil.toDateTime(value).getTime()));
            }
        } else if ("bigdecimal".equals(field.getDataType())) {
            pstmt.setBigDecimal(index, new BigDecimal(value));
        } else {
            pstmt.setString(index, value);
        }
    }

    //如果字段多，无论有没有变更均更新的情况下是否会影响性能
    public int update(PageView pv) {
        PreparedStatement pstmt = null;
        SqlContext sqlContext = PageViewUtil.getSql(PageViewUtil.SqlType.UPDATE, pv);
        try {
            pstmt = getConnection().prepareStatement(String.format(Constants.UPDATE_FORMAT, ObjectTableUtil.toTableFieldFormat(pv.getName()), sqlContext.getHead(), sqlContext.getTail()));
            setInsertParam(sqlContext, pv, pstmt);
            int[] results = pstmt.executeBatch();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        } finally {
            close(null, pstmt);
        }

        return 0;
    }


    public int delete(PageView pv) {

        //更改一下记录的状态
        return 0;
    }

    public void select(PageView pv) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        SqlContext sqlContext = PageViewUtil.getSql(PageViewUtil.SqlType.SELECT, pv);
        try {
            pstmt = getConnection().prepareStatement(String.format(Constants.SELECT_FORMAT, ObjectTableUtil.toTableFieldFormat(pv.getName()), sqlContext.getHead(), sqlContext.getTail()));
            setInsertParam(sqlContext, pv, pstmt);
            rs = pstmt.executeQuery();
            toPageView(rs, pv);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        } finally {
            close(rs, pstmt);
        }
    }

    private void toPageView(ResultSet rs, PageView pv) throws SQLException {
        List<Field> fields = pv.getFields();

        List<Record> records = new ArrayList<Record>();
        Record record = null;
        while (rs.next()) {
            record = new Record();
            for (Field field : fields) {
                if ("date".equals(field.getDataType())) {
                    Date date = rs.getDate(field.getName());
                    record.addRecordVal(field.getName(), DateUtil.toString(field.getFormat(), date));
                } else if ("bigdecimal".equals(field.getDataType())) {
                    record.addRecordVal(field.getName(), rs.getBigDecimal(field.getName()).toString());
                } else {
                    record.addRecordVal(field.getName(), rs.getString(field.getName()));
                }
            }
            records.add(record);
        }
        pv.getGrid().setRecords(records);
    }

    private void close(ResultSet rs, PreparedStatement pstmt) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                throw new DaoException(e.getMessage(), e);
            }
        }
    }

    private Connection getConnection() throws SQLException {
        Connection conn = tlocals.get();
        if (conn == null) {
            conn = ds.getConnection();
            tlocals.set(conn);
        }
        return conn;
    }



    public static void main(String[] args) {
        String format = "INSERT %1s";
        System.out.println(String.format(format, "TABLE"));
    }
}
