package com.kh.menu.model.dao;

import java.util.List;

import com.kh.menu.model.vo.Menu;

public interface MenuDao {

	List<Menu> selectMenus();

	int insertMenu(Menu menu);

}
