package com.kh.animal;
/**
 * reset : 지정버젼으로 돌아감
 * 		- hard : 이후 변경사항 모두 제거
 *  	- mixed : 이후 변경사항을 unstaged changes에서 관리
 *  	- soft : 이후 변경사항을 staging area에 추가
 *  
 *  revert : 지정한 버젼의 취소버젼을 새로 추가
 *  	- history를 변경할 수 없는 경우 유용하다.
 *  	- conflict 발생 가능성 O
 *
 */

public class Cow {
	private String name;

	public void run() {
		System.out.println("소가 달립니다.");
	}
	
}
