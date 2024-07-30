package com.kh.chatApp.chat.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {
	private int userNo;
	private String email;
	private String userPwd;
	private String nickName;
	private String enrollDate;
	private String modifyDate;
	private String status;
	private String profile;

}
