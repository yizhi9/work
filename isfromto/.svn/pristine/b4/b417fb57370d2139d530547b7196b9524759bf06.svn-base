package com.isoft.service;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.isoft.domain.FromToPath;
import com.isoft.domain.PersonPriority;
import com.isoft.domain.PriorityManager;

public class FileSort {
	
	/**
	 * 对所有文件排序
	 * @param pm 从xml文件读取的迁徙目录 和 路径
	 * @return
	 */
	public List<String> sortAllFile(PriorityManager pm) {
		List<String> fileList = new ArrayList<String>();
		boolean flag=pm.getAllPath().isTransAll();
		//System.out.println(flag);
		if(flag){//根目录下的都搬迁过去
			String root=pm.getAllPath().getFrom();
			File file=new File(root);
			for(File temp:file.listFiles()){
				boolean flagpm=true;
				for(PersonPriority pp:pm.getPersonList()){
					if(temp.getName().trim().equals(pp.getName().trim())){
						flagpm=false;
					}
				}
				if(temp.isDirectory()&&flagpm==true){
					PersonPriority tempPp=new PersonPriority();
					tempPp.setName(temp.getName());
					tempPp.setContents("");
					tempPp.setPriorityDegree(2);
					tempPp.setFile("");
					tempPp.setSize(1024);
					pm.getPersonList().add(tempPp);
				}
			}
			
		}else{//只搬迁transname配置了的
			String names=pm.getAllPath().getTransAllName();
			String nameAll[]=names.split(";");
			for(int i=0;i<nameAll.length;i++){
				boolean flagpm=true;
				for(PersonPriority pp:pm.getPersonList()){
					if(nameAll[i].equals(pp.getName())){
						flagpm=false;
					}
				}
				if(flagpm){
					PersonPriority tempPp=new PersonPriority();
					tempPp.setName(nameAll[i]);
					tempPp.setContents("");
					tempPp.setPriorityDegree(2);
					tempPp.setFile("");
					tempPp.setSize(1024);
					pm.getPersonList().add(tempPp);
				}
			}
		}
		//搬迁哪些用户
		/*for(PersonPriority p:pm.getPersonList()){
			System.out.println("变迁的用户名："+p.getName());
		}*/
		pm=personSort(pm);//对工作用户排序
		List<PersonPriority> ppList=pm.getPersonList();
		FromToPath ftp=pm.getAllPath();
		List<String> tempPath=null;
		for(PersonPriority pp:ppList){
			tempPath=new ArrayList<String>();
			tempPath=getFilesInPath(ftp.getFrom()+pp.getName()+File.separator);//工作用户下所有文件
			tempPath=fileSort(pp, tempPath);//当前工作用户下的所有文件排序
			fileList.addAll(tempPath);
		}
		//黑名单处理  文件后缀名
		boolean blackFlag=pm.getAllPath().isBlackFlag();
		if(blackFlag){
			List<String> blackList=new ArrayList<String>();
			String blackFileList=pm.getAllPath().getBlackFileList();
			String blackStr[]=blackFileList.split(";");
			for(String s:fileList){
				for(String str:blackStr){
					if(s.contains(".")){
						if(s.substring(s.lastIndexOf(".")).equals(str)){
							blackList.add(s);
						}
					}
				}
			}
			fileList.removeAll(blackList);
		}
		//白名单处理 文件后缀名
		boolean blankFlag=pm.getAllPath().isBlankFlag();
		if(blankFlag){
			String blankFileList=pm.getAllPath().getBlankFileList();
			List<String> blankList=new ArrayList<String>();
			String blankStr[]=blankFileList.split(";");
			for(String s:fileList){
				for(String str:blankStr){
					if(s.contains(".")){
						if(s.substring(s.lastIndexOf(".")).equals(str)){
							blankList.add(s);
						}
					}
					
				}
			}
			return blankList;
		}
		
		return fileList;
	}

	/**
	 * 根据用户的personpriority对工作用户排序
	 * 
	 * @param pm
	 * @return
	 */
	private PriorityManager personSort(PriorityManager pm) {
		List<PersonPriority> persons = pm.getPersonList();
		PersonPriority[] pp = (PersonPriority[])persons.toArray(new PersonPriority[persons.size()]);
		for (int i = 0; i < pp.length; i++) {
			for (int j = 0; j < pp.length - i-1; j++) {
				if (pp[j].getPriorityDegree() > pp[j + 1].getPriorityDegree()) {
					PersonPriority temp = pp[j];
					pp[j] = pp[j + 1];
					pp[j + 1] = temp;
				}
			}
		}
		pm.setPersonList(Arrays.asList(pp));
		return pm;
	}

	/**
	 * 对某人的所有文件排序
	 * 
	 * @param pm
	 * @return
	 */
	private List<String> fileSort(PersonPriority pp,List<String> fileList) {
		String content=pp.getContents();
		if(content!=null){
		String[] contents=content.split(";");
		for(int i=contents.length-1;i>-1;i--){
			fileList=contentSort(fileList, contents[i]);
		}
		}
		String file=pp.getFile();
		if(file!=null){
			String[] files=file.split(";");
			for(int i=files.length-1;i>=0;i--){
				fileList=contentSort(fileList,files[i]);
			}
		}
		int size=pp.getSize();
		fileList=sizeSort(fileList, size);
		return fileList;
	}

	/**
	 * 得到目录下的全部文件（包括子目录下的文件）
	 * 
	 * @param path
	 * @param matched
	 * @return
	 */
	private  static List<String> getFilesInPath(String path) {
		List<String> fileList = new ArrayList<String>();

		if (path == null || "".equals(path))
			return fileList;

		File file = new File(path);

		if (!file.exists()) {
			// log.error("指定的目录 " + path + " 不存在");
			return fileList;
		}

		File[] childFiles = file.listFiles();

		try {
			for (int i = 0; i < childFiles.length; i++) {
				String filePath = childFiles[i].getAbsolutePath();
				// 如果子目录是文件夹，递归
				if (childFiles[i].isDirectory()) {
					fileList.addAll(getFilesInPath(filePath));
				} else {
					fileList.add(filePath);

				}
			}
		} catch (Exception e) {
			// log.error("读取目录 " + fullPath.toString() + " 下文件出错", e);
			return fileList;
		}

		return fileList;
	}
	
	/**
	 * 带str字符串的优先 目录优先\ftp\  文件优先.txt
	 * @param filePath
	 * @param str
	 * @return
	 */
	private List<String> contentSort(List<String> filePath,String str){
		List<String> removeList=new ArrayList<String>();
		for(String s:filePath){
			if(s.contains(str)){
				removeList.add(s);
			}
		}
		for(String s:removeList){
			filePath.remove(s);
		}
		removeList.addAll(filePath);
		return removeList;
	}
	
	/**
	 * 对size排序
	 * @param filePath
	 * @param size
	 * @return
	 */
	private List<String> sizeSort(List<String> filePath,int size){
		List<String> removeList=new ArrayList<String>();
		File file=null;
		for(String s:filePath){
			file=new File(s); 
			if(file.length()<=size){
				removeList.add(s);
				
			}
		}
		for(String s:removeList){
			filePath.remove(s);
		}
		removeList.addAll(filePath);
		return removeList;
	}
}
