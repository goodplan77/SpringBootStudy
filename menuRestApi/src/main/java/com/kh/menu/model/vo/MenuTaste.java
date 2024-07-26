package com.kh.menu.model.vo;

import com.fasterxml.jackson.annotation.JsonValue;

public enum MenuTaste {
	
	MILD("mild") , HOT("hot");
	
	private String value;
	
	@JsonValue
	public String getValue() {
		return this.value;
	}
	
	MenuTaste(String value){
		this.value = value;
	}
	
	public static MenuTaste menuTasteValueOf(String value) {
		MenuTaste[] menus = MenuTaste.values();
		for(MenuTaste mt : menus) {
			if(mt.value.equals(value)) return mt;
		}
		throw new AssertionError("Unknown Type : " + value);
	}
}
