package common.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Application Lifecycle Listener implementation class SessionCounterListener
 *
 */
//@WebListener
public class SessionCounterListener implements HttpSessionListener {

	// 접속하고 있는 사용자 수
	private static int activeSessions;
    /**
     * Default constructor. 
     */
    public SessionCounterListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     * session이 생성되었을 때
     */
    public void sessionCreated(HttpSessionEvent se)  { 
    	activeSessions++;
    	System.out.println("> 세션 생성! 접속 사용자 수 :" + activeSessions);
    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     * session이 사라졌을 때
     */
    public void sessionDestroyed(HttpSessionEvent se)  { 
    	if(activeSessions > 0)
    		activeSessions--;
    	System.out.println("> 세션 폐기! 접속 사용자 수 :" + activeSessions);
    }
	
}
