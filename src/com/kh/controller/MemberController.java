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
	 * 
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
	 * 
	 * @param keyword : 사용자가 입력한 검색하고자 하는 이름 keyword
	 */
	public void selectByKeyword(String keyword) {
		// SELECT 문 => ResultSet(1행) => Member

		ArrayList<Member> m = new MemberDao().selectByKeyword(keyword);

		// 조회 결과가 있는지 없는지 판단 후 사용자가 보게 될 View 지정

		if (m == null) {
			new MemberView().displayNodata(keyword + "에 대한 검색 결과가 없습니다.");
		} else { // 조회 결과가 있는 경우
			new MemberView().displayList(m);
		}

	}

	/**
	 * 업데이트할 메서드
	 * 
	 * @param inputUserId : WHERE 에 집어넣을 입력받은 ID
	 * @param userId
	 * @param userPwd
	 * @param userName
	 * @param gender
	 * @param age
	 * @param email
	 * @param phone
	 * @param address
	 * @param hobby
	 */
	public void updateMember(String inputUserId, String userId, String userPwd, String userName, String gender, int age,
			String email, String phone, String address, String hobby) {

		Member m = new Member(inputUserId, userId, userPwd, userName, gender, age, email, phone, address, hobby);

		int result = new MemberDao().updateMember(m);

		if (result > 0) {
			new MemberView().displaySuccess(userId + "님의 정보를 수정하였습니다.");
		} else {
			new MemberView().displayFail("회원 수정 실패 !!!!");
		}

	}

	/**
	 * 비밀번호 아이디 확인하는 메서드
	 * 
	 * @param inputUserId  : 바꾸고싶은 ID
	 * @param inputUserPwd : 바꾸고시은 ID 비밀번호
	 */
	public int updateLoginId(String inputUserId, String inputUserPwd) {

		Member m = new MemberDao().selectByUserId(inputUserId);

		if (m == null) {
			new MemberView().displayFail("조회하신 아이디가 없슴다.");
			return 1;
		}

		if (m.getUserPwd().equals(inputUserPwd)) {
			new MemberView().displaySuccess("감사합니다");
			return 2;
		} else {
			System.out.println("비밀번호가 틀렸슴다.");
			return 1;
		}

	}

	/**
	 * @param userId
	 */
	public void deleteMember(String userId) {

		int result = new MemberDao().deleteMember(userId);

		if (result > 0) {
			new MemberView().displaySuccess("회원 삭제 성공");
		} else {
			new MemberView().displayFail("회원 삭제 실패");
		}

	}

}
