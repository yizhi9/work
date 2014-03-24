package com.isoft.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import com.isoft.domain.FromToPath;
import com.isoft.domain.PriorityManager;
import com.isoft.util.ChecksumCRC32;
import com.isoft.util.MD5;

public class MoveFile {
	/*
	 * ����һ���ļ�
	 */
 public boolean transFile(PriorityManager pm,String str){
	 Long a=System.nanoTime();
	// System.out.println(a);
	 boolean flag=false;
	 FromToPath ftp=pm.getAllPath();
	 String from=ftp.getFrom();
	 String to=ftp.getTo();
	 boolean crc32=ftp.isCrc32();
	 boolean md5=ftp.isMd5();
	 String tempWorkName=str.substring(from.length());
	 String workName=tempWorkName.substring(0, tempWorkName.indexOf(File.separator));
	 //��Դ·������Ŀ��·��
	 String toPath=str;
	 toPath=toPath.replace(from, to);
	 File toFile=new File(toPath);
	 BufferedOutputStream bops=null;
	 //FileOutputStream bops=null;
	 BufferedInputStream bips=null;
	// FileInputStream bips=null;
	 System.out.println("���ڰ�Ǩ��"+str);
	 String checkToPath=toPath;
	 if(!new File(str).exists()){
		 System.out.println(str+"�ļ�������");
	 }else{
			 try {
				 if(toFile.exists()){
					 checkToPath=toPath+"["+System.currentTimeMillis()+"]";
					 toFile=new File(checkToPath);
				 }
				 if(!checkToPath.endsWith(".temp")){
					 checkToPath=checkToPath+".temp";
				 }
				 
				 toFile=new File(checkToPath);  
				  
				 if(!toFile.exists()){
						toFile.getParentFile().mkdirs();
						toFile.createNewFile();
				 }
				
				  bips=new BufferedInputStream(new FileInputStream(str));
				// bips=new FileInputStream(str);
				  bops=new BufferedOutputStream(new FileOutputStream(toFile));
				// bops=new FileOutputStream(toFile);
				  System.out.println(toFile);
				 byte[] bit=new byte[512];
				 int len;
				 while((len=bips.read(bit))!=-1){
					 bops.write(bit, 0, len);
				 }
				 bops.flush();
				 flag=true;
				
			} catch (Exception e) {
				System.out.println("������");
			}finally{
				try {
					bips.close();
					bops.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		 
	 }
	 if(crc32){
		// long time1=System.nanoTime();
		 long a1=ChecksumCRC32.doChecksum(str);
		 long a2=ChecksumCRC32.doChecksum(checkToPath);
		// System.out.println("crc32:"+(System.nanoTime()-time1));
		 if(a1!=a2){
			 flag=false;
			 System.out.println("���ļ��������ݳ���");
		 }
	 }
	 if(md5){
		 try {
			 //long time1=System.nanoTime();
			 String a1=MD5.getHash(str, "MD5");
			 String a2=MD5.getHash(checkToPath, "MD5");
			// System.out.println("md5:"+(System.nanoTime()-time1));
			 if(!a1.equals(a2)){
				 flag=false;
				 System.out.println("���ļ��������ݳ���");
			 }
		} catch (Exception e) {
			
		}
		
	 }
	 if(!flag){//����������ɾ�������ļ�
		 if(toFile.exists()){
			 System.out.println("ɾ���ļ�"+checkToPath.toString());
			 deleteFile(checkToPath);
		 }
	 }
	 
	 if(flag){
		 reNameDelTemp(toFile);
	 }
	 System.out.println("��Ǩ����"+checkToPath.substring(0,checkToPath.length()-5 )+"\t"+"time:"+(System.nanoTime()-a)/1000+"��s");
	 System.out.println();
	 return flag;
 }
 
 
	/**
	 * ɾ���ļ�
	 * @param path ɾ���ļ�
	 * @return
	 */
	public  boolean deleteFile(String path){
		boolean flag=false;
		File file=new File(path);
		if(file.exists()){
			file.delete();
			flag=true;
		}
		return flag;
	}
	/**
	 * ɾ����Ŀ¼�µĿ��ļ���
	 * @param path Ŀ¼·��
	 * @return
	 */
	public void deleteContent(File file){
		if(file.isDirectory()&&file.list().length>0){
			for(File f:file.listFiles()){
				if(f.isDirectory()&&f.list().length==0){
					f.delete();
				}else{
					deleteContent(f);
				}
			}
		}
	}
	/**
	 * �޸�Ϊtemp�ļ�
	 */
	public void reNameToTemp(File file){
		String name=file.getAbsolutePath();
		file.renameTo(new File(name+".temp"));
	}
	/**
	 * ȥ��.temp��׺
	 * @param temp
	 */
	public void reNameDelTemp(File temp){
		//System.out.println(temp.toString());
		String name=temp.getAbsolutePath();
		//System.out.println(name);
		temp.renameTo(new File(name.substring(0, name.length()-5)));
	}
}