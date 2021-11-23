package com.kh.controller;

import java.util.ArrayList;

import com.kh.model.dao.MemberDao;
import com.kh.model.vo.Member;
import com.kh.view.MemberView;

public class MemberController {

	/*
	 * Controller : View 를 통해서 요청한 기능을 처리하는 담당 해당 메서드로 전달된 데이터를 가공처리 한 후 Dao 메서드 호출시
	 * 전달 Dao로부터 변환받은 결과에 따라 사용자가 보게될 View(응답화면)을 결정(View 메서드 호출)
	 */

	/**
	 * 사용자의 회원 추가 요청을 처리해주는 메서드
	 * 
	 * @param userId
	 * @param userPwd
	 * @param userName
	 * @param gender
	 * @param age
	 * @param email
	 * @param phone
	 * @param address
	 * @param hobby    => 사용자가 요청 시 입력했던 값들
	 */
	public void insertMember(String userId, String userPwd, String userName, String gender, int age, String email,
			String phone, String address, String hobby) {

		Member m = new Member(userId, userPwd, userName, gender, age, email, phone, address, hobby);

		int result = new MemberDao().inserMember(m);

		if (result > 0) { // 성공했을 경우
			new MemberView().displaySuccess("회원 추가 성공");
		} else { // 실패했을 경우
			new MemberView().displayFail("회원 추가 실패");
		}
	}

	/**
	 * 사용자의 회원 전체 요청을 처리해주는 메서드
	 */
	public void selectAll() {

		// 결과값을 담을 변수
		// SELECT -> ResultSet -> ArrayList<Member>

		// ArrayList<Member> list = new ArrayList<>();
		// list = new Member().selectAll();
		// => 한줄로하면 밑에

		ArrayList<Member> list = new MemberDao().selectAll();

		// 조회 결과가 있는지 없는지 판단 후 사욪아가 보게 될 View 화면 지정
		if (list.isEmpty()) {
			new MemberView().displayNodata("전체 조회 결과가 없습니다");
		} else { // 요소가 존재함 => 조회결과가 있음
			new MemberView().displayList(list);
		}

	}

	/**
	 * 사용자의 아이디로 검색 요청을 처리해주는 메서드
	 * @param userId : 사용자가 입력한 검색하고자 하는 아이디
	 */
	public void selectByUserId(String userId) { // SELECT 문 => ResultSet(1행)
		// SELECT 문 => ResultSet(1행) => Member

		Member m = new MemberDao().selectByUserId(userId);

		// 조회 결과가 있는지 없는지 판단 후 사용자가 보게 될 View 지정

		if (m == null) { // 조회 결과가 없는 경우
			new MemberView().displayNodata(userId + "에 대한 검색 결과가 없습니다.");
		} else { // 조회 결과가 있는 경우
			new MemberView().displayOne(m);
		}
	}

	/**
	 * 사용자 이름의 keyword로 검색 요청을 처리해주는 메서드
	 * @param keyword : 사용자가 입력한 검색하고자 하는 이름 keyword
	 */
	public void selectByKeyword(String keyword) {
		// SELECT 문 => ResultSet(1행) => Member
		 
		ArrayList<Member> m = new MemberDao().selectByKeyword(keyword);
		
		// 조회 결과가 있는지 없는지 판단 후 사용자가 보게 될 View 지정
		
		if(m == null) {
			new MemberView().displayNodata(keyword + "에 대한 검색 결과가 없습니다.");
		} else { // 조회 결과가 있는 경우
			new MemberView().displayList(m);
		}
		
	}
	
	public void updateMember(String userId, String userPwd, String userName, String gender, int age, String email,
			String phone, String address, String hobby) {

		Member m = new Member(userId, userPwd, userName, gender, age, email, phone, address, hobby);

		int result = new MemberDao().updateMember(m);
		
		if(result > 0) {
			new MemberView().displaySuccess(userId + "님의 정보를 수정하였습니다.");
		}else {
			new MemberView().displayFail("회원 수정 실패 !!!!");
		}
		
	}
	
	

	/**
	 * 바꾸고 싶은 ID를 받아 수정할 메서드
	 * @param userId : 사용자가 바꾸고싶은 회원의 Id
	 */
	public int updateLogin(String inputUserId,String inputUserPwd) {
		
		int count = 0;
		
		Member m = new MemberDao().selectByUserId(inputUserId);
		
		
		if(m == null) {
			return ;
		}else {
			new MemberView().displayFail("아이디 비번 불일치");
		}
		
		return count;
	}
	
}
