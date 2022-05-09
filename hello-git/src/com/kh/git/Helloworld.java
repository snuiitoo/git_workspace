package com.kh.git;
/**
 * SCM software Configuration Management
 *  - 코드추적(버전관리)외에 build, packaging, deploy 등 프로젝트 관련 프로세스를 관리
 * 
 * VCS Version Control System
 *  - 프로젝트 작성/수정/보완에 따른 변경 내역을 버젼으로 관리
 *  
 * 버젼관리 프로그램
 *  - server-client : 중앙 저장소를 공유한 각각의 클라이언트(개발자)에서 저장소의 일부만 가져와서 작업 후, 중앙 저장소에 반영하는 상태
 *  	- subversion
 *  - 분산형 : 참여하는 각각의 클라이언트에서 전체 저장소에 대한 복제본을 가지고 작업.
 *  	- git
 */
public class Helloworld {
	
	public static void main(String[] args) {
		System.out.println("hello world!");
	}

}
