package dao;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBOperational extends DBManager {

	private Connection con = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	// 查询所有数据方法
	public List selectAll(String sql, Class clazz) {
		List list = new ArrayList();
		con = super.getConnection();

		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();

			Field fields[] = clazz.getDeclaredFields();
			int cols = rsmd.getColumnCount();

			while (rs.next()) {
				Object obj = clazz.newInstance();
				for (int i = 0; i < fields.length; i++) {
					String fieldName = fields[i].getName();
					for (int j = 1; j <= cols; j++) {
						String colName = rsmd.getColumnName(j);
						if (fieldName.toLowerCase().equals(
								colName.toLowerCase())) {
							fields[i].setAccessible(true);
							fields[i].set(obj, rs.getObject(colName));
						}
					}
				}

				list.add(obj);

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			super.closeAll(rs, ps, con);
		}

		return list;
	}
	public int selectAll(String sql) {
		List list = new ArrayList();
		con = super.getConnection();
		int cols=0;
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				cols = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			super.closeAll(rs, ps, con);
		}

		return cols;
	}

	// 增删改方法
	public int update(String sql) {
		con = super.getConnection();
		int i = 0;
		try {
			ps = con.prepareStatement(sql);
			i = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			super.closeAll(rs, ps, con);
		}

		return i;
	}

}
