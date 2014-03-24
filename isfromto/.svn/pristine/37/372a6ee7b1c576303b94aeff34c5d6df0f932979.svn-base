 package com.isoft.main;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.dom4j.Document;

import com.isoft.domain.FromToPath;
import com.isoft.domain.Log;
import com.isoft.domain.PriorityManager;
import com.isoft.service.FileSort;
import com.isoft.service.MoveFile;
import com.isoft.service.TransLog;
import com.isoft.util.MD5;
import com.isoft.util.Tool;
import com.isoft.xmlparser.ReadXML;

public class FromToManager {
public void fromToRunManager(){
	System.out.println("开始迁徙文件");
	URL url=null;
	try {
		String xmlDir=(Tool.getAppPath(FromToManager.class)+File.separator+"transcachemanager.xml").trim().toString();
		//xmlDir=xmlDir.replace("/", File.separator);
		//String xmlDir=System.getProperty("user.dir")+"/transcachemanager.xml";
		url=new URL("file:///"+xmlDir);
		//System.out.println(xmlDir);
	} catch (MalformedURLException e) {
		e.printStackTrace();
	}
	//读取xml
	Document document =ReadXML.loadFile(url);
	//Document document =ReadXML.loadFile(Run.class.getClassLoader().getResource("transcachemanager.xml"));
	//解析xml
	PriorityManager pm=ReadXML.parseXML(document);
	//对所有文件排序
	FileSort fs=new FileSort();
//	System.out.println("开始排序");
	List<String> list=fs.sortAllFile(pm);
	
	//迁徙文件
	MoveFile mf=new MoveFile();
	boolean flag=false;
	//获得from路径
	String from=pm.getAllPath().getFrom();
	boolean delete=pm.getAllPath().isDelete();
	//记录日志
	Log log=null;
	TransLog tl=null;
	String logPath=pm.getAllPath().getLogPath();
	List<String> transPath=new ArrayList<String>();
	for(String str:list){
		if(toPath(pm, str)){
			transPath.add(str);
		}
	}
	//System.out.println("开始传输：");
	for(String str:transPath){
		flag=mf.transFile(pm, str);
		log=new Log();
		File file=new File(str);
		log.setFileSize(String.valueOf(file.length())+"字节");
		if(delete&&flag){
			mf.deleteFile(str);
		}
		if(flag){
			log.setResult("成功");
		}else{
			log.setResult("失败");
		}
		log.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		log.setFileName(str);
		String a=str.substring(from.length());
		String workName=a.substring(0, a.indexOf(File.separator));
		log.setWorkName(workName);
		tl=new TransLog();
		tl.writeLog(log, logPath);
		if(pm.getAllPath().isDelete()&&flag){
		//	System.out.println(from+workName+File.separator);
			mf.deleteContent(new File(from+workName+File.separator+"incoming"+File.separator));
			mf.deleteContent(new File(from+workName+File.separator+"outgoing"+File.separator));
		}
	}
	System.out.println("本次搬迁完毕，正在等待下次搬迁");
}
/**
 * 目的文件夹下已经有这个文件 返回false  没有返回true
 * @param pm
 * @param str
 * @return
 */
  public boolean toPath(PriorityManager pm,String str){
		 boolean flag=true;
		 FromToPath ftp=pm.getAllPath();
		 String from=ftp.getFrom();
		 String to=ftp.getTo();
		 boolean crc32=ftp.isCrc32();
		 boolean md5=ftp.isMd5();
		 String tempWorkName=str.substring(from.length());
		 String workName=tempWorkName.substring(0, tempWorkName.indexOf(File.separator));
		 //把源路径换成目的路径
		 String toPath=str;
		 toPath=toPath.replace(from, to);
		 File path=new File(toPath);
		// System.out.println(toPath);
		 try {
			 if(path.exists()){//已经存在这个名字的文件了，检查内容是否相同，不相同就重命名
				 String a1=MD5.getHash(str, "MD5");
			//	 System.out.println(a1);
				 String a2=MD5.getHash(toPath, "MD5");
			//	 System.out.println(a2);
				 if(a1.equals(a2)){
					 flag=false;
				 }
			 }
		} catch (Exception e) {
			// TODO: handle exception
		}
	  return flag;
  }
}
