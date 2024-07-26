package com.kh.menu.model.dao;

import java.util.List;

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

}
