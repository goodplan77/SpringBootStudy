package com.kh.menu.model.dao;

import java.util.List;
import java.util.Map;

import com.kh.menu.model.vo.Menu;

public interface MenuDao {

	List<Menu> selectMenus();

	int insertMenu(Menu menu);

	List<Menu> selectMenuList(Map<String, Object> param);

	Menu selectOneMenu(String id);

	int updateMenu(Menu menu);

	int deleteMenu(String id);

}
