package com.isoft.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.Timer;

import org.dom4j.Document;

import com.isoft.domain.PriorityManager;
import com.isoft.util.Tool;
import com.isoft.xmlparser.ReadXML;

public class MyThread implements Runnable{

	@Override
	public void run() {
		System.out.println("程序已经启动：");
		boolean flag=true;
		URL url=null;
		//xml文件在jar包外面
		String xmlDir=(Tool.getAppPath(MyThread.class)+File.separator+"transcachemanager.xml").trim().toString();
		//String xmlDir=System.getProperty("user.dir")+"/transcachemanager.xml";
		xmlDir=xmlDir.replace("/", File.separator);
		
		try {
			url=new URL("file:///"+xmlDir);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		//读取xml
		Document document =ReadXML.loadFile(url);
		//xml在jar包里面
		//Document document =ReadXML.loadFile(Run.class.getClassLoader().getResource("transcachemanager.xml"));
		//解析xml
		PriorityManager pm=ReadXML.parseXML(document);
	//	try {
			//ServerSocket ss=new ServerSocket(10001);
			Timer timer=new Timer();
			//System.out.println("---------10S后开始搬迁--------");
			timer.schedule(new MyWork(), 100, pm.getAllPath().getInterval());
			/*String str="";
			PrintWriter out=null;
			while(flag){
				MyWork myThread=new MyWork();
				Thread tt=new Thread(myThread);
				tt.start();
				Socket socket=ss.accept();
				BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			    out=new PrintWriter(socket.getOutputStream());
			    str=br.readLine();
				if(str.equals("stop")){
					out.println("isfromto is stop success ");
					out.flush();
					timer.cancel();
					flag=false;
				}
				
			}
			out.close();
			ss.close();*/
	//	} catch (Exception e) {
	//	}
		
	}

}
