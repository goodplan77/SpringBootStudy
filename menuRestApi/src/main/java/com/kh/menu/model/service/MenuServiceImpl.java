package com.kh.menu.model.service;

import java.util.List;

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
	public List<Menu> searchMenu(Menu menu) {
		return menuDao.searchMenu(menu);
	}

}
