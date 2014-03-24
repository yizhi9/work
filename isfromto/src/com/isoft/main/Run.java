package com.isoft.main;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;

import org.dom4j.Document;

import com.isoft.domain.PriorityManager;
import com.isoft.util.Tool;
import com.isoft.xmlparser.ReadXML;


public class Run {
	public static void main(String[] args) {
		/*MyThread my=new MyThread();
		Thread thread=new Thread(my);
		thread.start();*/
		
		System.out.println("程序已经启动：");
		URL url=null;
		String xmlDir=(Tool.getAppPath(MyThread.class)+File.separator+"transcachemanager.xml").trim().toString();
		xmlDir=xmlDir.replace("/", File.separator);
		
		try {
			url=new URL("file:///"+xmlDir);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		Document document =ReadXML.loadFile(url);
		PriorityManager pm=ReadXML.parseXML(document);
		Timer timer=new Timer();
		timer.schedule(new MyWork(), 100, pm.getAllPath().getInterval());
	}
}
