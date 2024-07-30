package com.kh.menu.model.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.kh.menu.model.vo.Menu;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MenuDaoImpl implements MenuDao{
	
	private final SqlSessionTemplate session;

	@Override
	public List<Menu> selectMenus() {
		return session.selectList("menu.selectMenus");
	}

	@Override
	public int insertMenu(Menu menu) {
		return session.insert("menu.insertMenu" , menu);
	}

	@Override
	public List<Menu> selectMenuList(Map<String, Object> param) {
		return session.selectList("menu.selectMenuList" , param);
	}

	@Override
	public Menu selectOneMenu(String id) {
		return session.selectOne("menu.selectOneMenu" , id);
	}

	@Override
	public int updateMenu(Menu menu) {
		return session.update("menu.updateMenu" , menu);
	}

	@Override
	public int deleteMenu(String id) {
		return session.delete("menu.deleteMenu" , id);
	}

}
