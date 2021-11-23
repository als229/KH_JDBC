package com.kh.run;

public class MVC {

/* 1. MVC 패턴
Model1 : 데이터와 관련된 역할(데이터를 담는다거나, DB에 접근해서 데이터 입출력)
View : 사용자가 보게 될 시작적인 요소 / 화면 (입력, 출력)
Controller : 사용자의 요청을 받아서 처리 후 응답화면을 지정하는 역할
* View 단에서만 출력문(System.out.println)을 사용한다.
* Model의 DAO(Data Access Object)단에서만
  DB에 직접적으로 접근한 후 해당 SQL문 실행 및 결과를 받는다.	
  
  
2. ojdbc6.jar
JRE System Library [JavaSE - 1.8] 추가하는 방법
프로젝트 우클릭 properties -> Resource -> Java Build Path -> Libraries
-> Add External JARs -> dev에 있는 ojdbc6.jar 추가
  
 Referenced Libraries에 ojdbc6 추가 확인
 
 ojdbc6.jar 파일을 추가하지 않으면 ClassNotFoundException이 발생
 
 
 3. Statement란?
 
 Statement = 해당 DB에 SQL문을 전달하고 실행한 후 결과를 받아주는 객체
 			Connection 클래스에 createStatement()메서드를 호출하여
 			생성 실행시 SQL문을 매개변수로 전달하여 질의를 수행
*/
}
