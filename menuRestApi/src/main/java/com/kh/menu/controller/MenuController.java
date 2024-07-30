package com.kh.menu.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	
	@CrossOrigin(origins = {"*"}) // 모든 경로 허용?
	@GetMapping("/searchMenu/type/{type}/taste/{taste}")
	public List<Menu> searchMenu(
			@PathVariable String type ,
			@PathVariable String taste 
			) {
		log.debug("type = {} , taste = {}" , type , taste);
		Map<String , Object> param = new HashMap<>();
		param.put("type", type);
		param.put("taste", taste);
		
		List<Menu> list = menuService.selectMenuList(param);
		
		return list;
	}
	
	@CrossOrigin(origins = {"*"})
	@GetMapping("/menu/{id}")
	public Menu selectOneMenu(
			@PathVariable String id
			) {
		Menu menu = menuService.selectOneMenu(id);
		return menu;
	}
	
	@CrossOrigin(origins = {"*"})
	@PutMapping("/menu/{id}")
	public ResponseEntity<String> updateMenu(
			@RequestBody Menu menu
			){
		int result = menuService.updateMenu(menu);
		if(result > 0) {
			return ResponseEntity
					.ok()
					.body("수정성공");
		}else {
			return ResponseEntity.notFound().build();
		}
		
	}
	
	@CrossOrigin(origins = {"*"})
	@DeleteMapping("/menu/{id}")
	public String deleteMenu(
			@PathVariable String id
			){
		
		int result = menuService.deleteMenu(id);
		
		if(result > 0) {
			return "삭제 성공했습니다";
		}else {
			return "존재하지 않는 번호입니다";
		}
		
		
	}
}
