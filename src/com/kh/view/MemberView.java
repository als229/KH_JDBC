package com.kh.view;

import java.util.ArrayList;
import java.util.Scanner;

import com.kh.controller.MemberController;
import com.kh.model.vo.Member;

// View : 사용자가 보게 될 시각적인 요소 (화면) (입력 및 출력)
public class MemberView {

	// 전역으로 다 쓸 수 있도록 Scanner 객체 생성
	private Scanner sc = new Scanner(System.in);
	
	// 전역으로 바로 MemberController 요청할 수 있게끔 객체 생성
	private MemberController mc = new MemberController();
	
	/**
	 * 사용자가 보게 될 첫 화면(메인화면)
	 */
	// ctrl shift j 누르면 위에 에너테이션 생김
	public void mainMenu() {
		
		while(true) {
			System.out.println("***** 회원 관리 프로그램 *****");
			System.out.println("1. 회원 추가");
			System.out.println("2. 회원 전체 조회");
			System.out.println("3. 회원 아이디로 검색");
			System.out.println("4. 회원 이름 키워드로 검색");
			System.out.println("5. 회원 정보 변경");
			System.out.println("6. 회원 탈퇴");
			System.out.println("0. 프로그램 종료");
			System.out.print("이용할 메뉴 선택 > ");
			int menu = sc.nextInt();
			sc.nextLine();
			
			switch(menu) {
			case 1 :  insertMember(); break;
			case 2 :  selectALL(); break;
			case 3 :  selectByUserId(); break;
			case 4 :  selectByKeyword(); break;
			case 5 :  updateMember(); break;
			case 6 :  break;
			case 0 :  System.out.println("프로그램을 종료합니다."); return;
			default : System.out.println("번호를 잘못 입력했습니다.");
			}
		}
		
	}


	/**
	 * 사용자에게 검색할 회원 이름의 keyword를 입력받은 후 조회 요청하는 메서드
	 */
	private void selectByKeyword() {
		System.out.println("---- 회원 이름 키워드로 검색 -----");
		System.out.println("키워드를 입력하라 >");
		String keyword = sc.nextLine();
		
		mc.selectByKeyword(keyword);
	}

	/**
	 * 사용자에게 검색할 회원의 아이디를 입력받은 후 조회 요청하는 메서드
	 */
	private void selectByUserId() {
		
		System.out.println("---- 회원 아이디로 검색 ----");
		
		System.out.print("아이디 입력해라 > ");
		String userId = sc.nextLine();
		
		// 입력한 아이디를 회원 아이디 검색 요청시 같이 넘김
		
		mc.selectByUserId(userId);
		
	}

	private void selectALL() {
		
		System.out.println("----- 회원 전체 조회 -----");
		mc.selectAll();
		
	}

	/**
	 * 회원 추가용 화면
	 * 추가하고자 하는 회원의 정보를 입력받아서 추가 요청할 수 있는 화면
	 */
	public void insertMember() {

		System.out.println("----- 회원 추가 -----");
		
		System.out.print("아이디 > ");
		String userId = sc.nextLine();
		
		System.out.print("비밀번호 > ");
		String userPwd = sc.nextLine();
		
		System.out.print("이름 > ");
		String userName = sc.nextLine();
		
		System.out.print("성별 (M / F)> ");
		String gender = sc.nextLine().toUpperCase();
		
		System.out.print("나이 > ");
		int age = sc.nextInt();
		sc.nextLine();
		
		System.out.print("이메일 > ");
		String email = sc.nextLine();
		
		System.out.print("전화번호 (숫자만) > ");
		String phone = sc.nextLine();
		
		System.out.print("주소 > ");
		String address = sc.nextLine();
		
		System.out.print("취미 (, 로 공백 없이 나열) > ");
		String hobby = sc.nextLine();
		
		// 회원 추가 요청 => Controller의 어떤 메서드 호출
		mc.insertMember(userId, userPwd, userName, gender, age,
				email, phone, address, hobby);
		
		
	}
	
	public void updateMember() {

		System.out.println("----- 회원 수정 -----");
		
		System.out.print("수정하고싶은 유저의 아이디 > ");
		String inputUserId = sc.nextLine();

		System.out.print("비밀번호 > ");
		String inputUserPwd = sc.nextLine();

		System.out.print("아이디 > ");
		String userId = sc.nextLine();
		
		System.out.print("비밀번호 > ");
		String userPwd = sc.nextLine();
		
		System.out.print("이름 > ");
		String userName = sc.nextLine();
		
		System.out.print("성별 (M / F)> ");
		String gender = sc.nextLine().toUpperCase();
		
		System.out.print("나이 > ");
		int age = sc.nextInt();
		sc.nextLine();
		
		System.out.print("이메일 > ");
		String email = sc.nextLine();
		
		System.out.print("전화번호 (숫자만) > ");
		String phone = sc.nextLine();
		
		System.out.print("주소 > ");
		String address = sc.nextLine();
		
		System.out.print("취미 (, 로 공백 없이 나열) > ");
		String hobby = sc.nextLine();
		
		// 회원 추가 요청 => Controller의 어떤 메서드 호출
		mc.updateMember(userId, userPwd, userName, gender, age,
				email, phone, address, hobby);
		
		
	}
	
	// -------------------------------------------------------------
	// 서비스 요청 처리 후 사용자가 보게 될 응답화면들
	
	/**
	 * 서비스 요청 성공 시 보게 될 화면
	 * @param message 성공메세지
	 */
	public void displaySuccess(String message) {
		System.out.println("\n서비스 요청 성공 : " + message);
	}
	/**
	 * 서비스 요청 실패 시 보게 될 화면
	 * @param message 실패 메세지
	 */
	public void displayFail(String message) {
		System.out.println("\n서비스 요청 실패 : " + message);
	}
	
	/**
	 * 조회 서비스 요청 시 조회결과가 없을때 보게 될 화면
	 * @param message : 사용자에게 보여질 메세지
	 */
	public void displayNodata(String message) {
		System.out.println(message);
	}

	/**
	 * 조회서비스 요청 시 여러 행이 조회된 결과를 받아서 보게 될 화면
	 * @param list : 여러 행이 조회된 결과
	 */
	public void displayList(ArrayList<Member> list) {
		System.out.println("\n 조회된 데이터는 " + list.size() + " 건입니다. \n");
		for(int i = 0; i < list.size() ; i++) {
			System.out.println(list.get(i));
		}
	}
	
	public void displayOne(Member m) {
		
		System.out.println("\n조회된 데이터는 다음과 같슴다.");
		
		System.out.println(m);
	}
	
	
}
