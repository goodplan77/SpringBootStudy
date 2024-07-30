package com.kh.menu.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.kh.menu.model.dao.MenuDao;
import com.kh.menu.model.vo.Menu;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService{

	private final MenuDao menuDao;
	
	@Override
	public List<Menu> selectMenus() {
		return menuDao.selectMenus();
	}

	@Override
	public int insertMenu(Menu menu) {
		return menuDao.insertMenu(menu);
	}

	@Override
	public List<Menu> selectMenuList(Map<String, Object> param) {
		return menuDao.selectMenuList(param);
	}

	@Override
	public Menu selectOneMenu(String id) {
		return menuDao.selectOneMenu(id);
	}

	@Override
	public int updateMenu(Menu menu) {
		return menuDao.updateMenu(menu);
	}

	@Override
	public int deleteMenu(String id) {
		return menuDao.deleteMenu(id);
	}

}
