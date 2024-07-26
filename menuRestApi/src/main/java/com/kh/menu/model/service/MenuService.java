package com.kh.menu.model.service;

import java.util.List;

import com.kh.menu.model.vo.Menu;

public interface MenuService {

	List<Menu> selectMenus();

	int insertMenu(Menu menu);

}
