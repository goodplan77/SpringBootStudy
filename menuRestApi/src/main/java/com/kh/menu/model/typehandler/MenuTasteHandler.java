package com.kh.menu.model.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import com.kh.menu.model.vo.MenuTaste;

@MappedTypes(MenuTaste.class) // java 에서의 자료형
@MappedJdbcTypes(JdbcType.VARCHAR) // db 자료형
public class MenuTasteHandler extends BaseTypeHandler<MenuTaste>{

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, MenuTaste parameter, JdbcType jdbcType)
			throws SQLException {
		ps.setString(i,parameter.getValue());
		
	}

	@Override
	public MenuTaste getNullableResult(ResultSet rs, String columnName) throws SQLException {
		return MenuTaste.menuTasteValueOf(rs.getString(columnName));
	}

	@Override
	public MenuTaste getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		return MenuTaste.menuTasteValueOf(rs.getString(columnIndex));
	}

	@Override
	public MenuTaste getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		return MenuTaste.menuTasteValueOf(cs.getString(columnIndex));
	}

}
