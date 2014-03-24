package com.isoft.service;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.isoft.domain.Log;

public class TransLog {
     
	private static final int LOGSIZE=1024*1024; //1MB
	private static final String FILENAME="transcachemanager";
	/**
	 * 把日志写入日子文件
	 * @param log 日志内容
	 * @param logPath 日子文件路径
	 */
	public void writeLog(Log log,String logPath ){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String date=sdf.format(new Date());
		String name=FILENAME+date+".log";
	//	boolean flag=findLogFile(logPath, name);
		int currentLog=currentLogFile(date,logPath);
		long size=0;
		if(currentLog!=0){
			File file=null;
			if(currentLog==1){
				file=new File("file:///"+logPath+name);
				size=(new File(logPath+name)).length();
				
			}else{
				file =new File("file:///"+logPath+FILENAME+date+"."+currentLog+".log");
				size=new File(logPath+FILENAME+date+"."+currentLog+".log").length();
			}
			
			//System.out.println(currentLog+"/t"+size);
			if(size>LOGSIZE){
					name=FILENAME+date+"."+(currentLog+1)+".log";
			}else{
				if(currentLog!=1){
					name=FILENAME+date+"."+currentLog+".log";
				}
			}
		}
		RandomAccessFile raf=null;
		try {
			File file=new File(logPath+name);
			if(!file.exists()){
				file.getParentFile().mkdirs();
				file.createNewFile();
			}
		    raf=new RandomAccessFile(file, "rw");
			long len=raf.length();
			raf.seek(len);
			raf.write(log.toString().getBytes());
		} catch (Exception e) {
			System.out.println("日志记录出错");
		}finally{
			try {
				raf.close();
			} catch (IOException e) {
				System.out.println("流关闭出错");
				e.printStackTrace();
			}
		}
			
	}
	/**
	 * 查找日志路径下有对应名字的log
	 * @param logPath 日志路径
	 * @param file 日志名
	 * @return
	 */
	private boolean findLogFile(String logPath,String file){
		boolean flag=false;
		File logPathFile=new File(logPath);
		File[] files=logPathFile.listFiles();
		String name="";
		for(File f:files){
			name=f.getName();
			if(name.equals(file)){
				flag=true;
			}
		}
		return flag;
	}
	
	/**
	 * 查找当天日期的日志个数
	 * @param date 当天日期
	 * @param logPath 日志根路径
	 * @return
	 */
	private int currentLogFile(String date,String logPath){
		List<String> list=new ArrayList<String>();
		//当天日期日志个数
		int currentDateLogSize=0;
			File logContent=new File(logPath);
			try {
				if(!logContent.exists()){
					logContent.getParentFile().mkdirs();
					logContent.mkdirs();
				}
			} catch (Exception e) {
				System.out.println("file of log create error");
			}
			String[] logs=logContent.list();
			for(String str:logs){
				if(str.contains(date)){
					list.add(str);
				}
			}
			currentDateLogSize=list.size();
		return currentDateLogSize;
	}

}
