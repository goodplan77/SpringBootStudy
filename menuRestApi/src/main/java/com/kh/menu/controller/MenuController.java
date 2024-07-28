package com.kh.menu.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.menu.model.service.MenuService;
import com.kh.menu.model.vo.Menu;
import com.kh.menu.model.vo.MenuTaste;
import com.kh.menu.model.vo.MenuType;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController // Controller + ResponseBody
@RequiredArgsConstructor // 생성자 자동 생성
public class MenuController {
	
	private final MenuService menuService;
	
	@GetMapping("/menus")
	public List<Menu> menus(HttpServletResponse response){
		
		// 1. 업무 로직
		List<Menu> list = menuService.selectMenus();
		log.debug("list - {}" , list);
		
		// 응답의 헤더 설정
		// CORS 요청 설정
		// http://localhost:3000 도메인에서 오는 요청을 허용한다
		response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "http://localhost:3000");
		return list;
	}
	
	@CrossOrigin(origins = {"http://localhost:3000"})
	// Origin 에 대한 응답 허용
	@PostMapping("/menu")
	public Map<String , Object> insertMenu(@RequestBody Menu menu){ // 전달되는 데이터 형태가 JSON 일때 사용 + 커맨드 객체?
		// 1. 업무로직
		log.debug("menu {}" , menu);
		int result = menuService.insertMenu(menu);
		Map<String , Object> map = new HashMap<>();
		
		if(result > 0) {
			map.put("msg" , "메뉴 등록 성공");
		} else {
			map.put("msg" , "메뉴 등록 실패");
		}
		
		return map;
	}
	
	@GetMapping("/searchMenu")
	public List<Menu> searchMenu(HttpServletResponse response, @RequestParam String type,
			@RequestParam String taste) {

		Menu selectMenu = new Menu();
		selectMenu.setType(MenuType.menuTypeValueOf(type));
		selectMenu.setTaste(MenuTaste.menuTasteValueOf(taste));
		List<Menu> list = menuService.searchMenu(selectMenu);

		response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "http://localhost:3000");
		return list;
	}
}
