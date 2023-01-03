package edu.kh.network.model.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TDPServer {
	
	// Server : 서비스를 제공한느 프로그램 또는 컴퓨터
	// client : 서버의 서비스를 요청하여 사용하는 프로그램 또는 컴퓨터
	
	// TCP Socket 프로그래밍
	// TCP : 데이터 전달에 신뢰성을 최대한 보장하는 방식(연결 지향형 통신)
	// 		 순자척으로 데이터를 전달하고 확인 및 오류시 재전송
	
	// Socket : 프로세스의 통신에 사용되는 양 끝단.
	//			서버와 클라이언트가 통신을 하기위한 매개체
	
	//ServerSocket : 서버용 소켓
	// - port와 연결되어 외부 요청을 기다리는 역할
	// -> 클라이언트 요청이 올 경우 이를 수락(accept)하고
	//    클라이언트가 사용 할 수 있는 소켓을 생성.
	// 	---> 서버 소켓과 클라이언트 소켓이 연결되어 데이터 통신이 가능해진다.
	
	public void serverStart() {
		
	    //1.서버의 포트번호 정함
		int port = 8600; // port번호는 0 - 65535 사이 지정이 가능
							//단 1023번 이하는 사용중인 경우가 많아 제외
							//8600번으로 port번호를 지정한것
		
		// *사용할 변수를 미리 선언*
		ServerSocket serverSocket = null; // 서버 소켓 저장 변수
		Socket clientSocket = null;// 클라이언트 소켓 저장 변수
		
		InputStream is = null; // 클라이언트 -> 서버 입력용 스트림 변수-흐름
		BufferedReader br = null;//입력용 보조 스트림
		
		OutputStream os = null;	//서버 -> 클라이언트 출력용 스트림 변수
		PrintWriter pw = null;//출력용 보조 스트림		
		//BufferedWriter와 PrintWriter
		//PrintWriter에는 println, print, printf등 다양한 출력 함수를 제공해 출력이 편해진다
		
		try {
			//2.서버용 소켓 객체 생성
			serverSocket = new ServerSocket(port);//서버용 소켓을 생성후 port와 결합
			
			//3.클라이언트 쪽에서 접속 요청이 오길 기다림
			// - 서버용 소켓은 생성되면 클라이언트 요청이 오기 전까지 
			//다음 코드를 수행하지 않고 대기중임
			System.out.println("[Server]");
			System.out.println("클라이언트 요청을 기다리는중....");
			
			
			//4.접속 요청이 오면 요청 수락 후 해당 클라이언트에 대한 소켓 객체 생성
			// -> 요청을 수락하면 자동으로 Socket객체가 얻어와진다
			clientSocket = serverSocket.accept();
			
			//접속한 클라이언트의 IP를 얻어와 출력
			String clientIP = clientSocket.getInetAddress().getHostAddress();
			//IP주소 형태 만들기
			
			System.out.println(clientIP + "가 연결을 요청함....");
			
			
			//5.연결된 클라이언트와 입출력 스트림 생성
			is = clientSocket.getInputStream(); // clientSocket에서 제공하는 스트림
			os = clientSocket.getOutputStream(); // clientSocket에서 제공 하는 스트림
			
			//6.보조 스트림을 통해 성능 개선
			br = new BufferedReader(new InputStreamReader(is));
			//InputStreamReader : 바이트기반 스트림과 문자기반의 스트림 연결에 사용하는 스트림
			
			pw = new PrintWriter(os); // 따로 필요없음
			
			
			//7.스트림을 통해 읽고 쓰기
			pw.println("[서버 접속 성공]");
			pw.flush(); //버퍼에 남아있는 문자를 모두 밀어냄
			//close()도 밀어내는게 가능하지만 스트림을 닫는다
			//스트림을 닫지 않고 비우고 싶다면 flush를사용
			
			//7-2. 클라이언트 -> 서버에게 입력(메세지 전송받기)
			String message = br.readLine();//readLine: 클라이언트메세지를 한줄 읽어옴
			System.out.println(clientIP + "가 보낸 메세지 : " + message);
			
			
			
			
		}catch(IOException e) {
			e.printStackTrace();//에외 추적
		} finally {
			
			
			//8.통신 종료
			// 사용한 스트림, 소켓 자원을 모두 반환(close)
			try {//!= null : 작동중일때만 닫는다
				//보조 스트림 close시 연결된 기반스트림(is,os)도 같이 close됨
				if(pw != null) pw.close();
				if(br != null) br.close();
				
				if(serverSocket != null) serverSocket.close();
				if(clientSocket != null) clientSocket.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		
		
		
		
		
		
		
		
	}
	

}
