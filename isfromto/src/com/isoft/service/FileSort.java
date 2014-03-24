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
	 * �������ļ�����
	 * @param pm ��xml�ļ���ȡ��Ǩ��Ŀ¼ �� ·��
	 * @return
	 */
	public List<String> sortAllFile(PriorityManager pm) {
		List<String> fileList = new ArrayList<String>();
		boolean flag=pm.getAllPath().isTransAll();
		//System.out.println(flag);
		if(flag){//��Ŀ¼�µĶ���Ǩ��ȥ
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
			
		}else{//ֻ��Ǩtransname�����˵�
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
		//��Ǩ��Щ�û�
		/*for(PersonPriority p:pm.getPersonList()){
			System.out.println("��Ǩ���û�����"+p.getName());
		}*/
		pm=personSort(pm);//�Թ����û�����
		List<PersonPriority> ppList=pm.getPersonList();
		FromToPath ftp=pm.getAllPath();
		List<String> tempPath=null;
		for(PersonPriority pp:ppList){
			tempPath=new ArrayList<String>();
			tempPath=getFilesInPath(ftp.getFrom()+pp.getName()+File.separator);//�����û��������ļ�
			tempPath=fileSort(pp, tempPath);//��ǰ�����û��µ������ļ�����
			fileList.addAll(tempPath);
		}
		//����������  �ļ���׺��
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
		//���������� �ļ���׺��
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
	 * �����û���personpriority�Թ����û�����
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
	 * ��ĳ�˵������ļ�����
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
	 * �õ�Ŀ¼�µ�ȫ���ļ���������Ŀ¼�µ��ļ���
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
			// log.error("ָ����Ŀ¼ " + path + " ������");
			return fileList;
		}

		File[] childFiles = file.listFiles();

		try {
			for (int i = 0; i < childFiles.length; i++) {
				String filePath = childFiles[i].getAbsolutePath();
				// �����Ŀ¼���ļ��У��ݹ�
				if (childFiles[i].isDirectory()) {
					fileList.addAll(getFilesInPath(filePath));
				} else {
					fileList.add(filePath);

				}
			}
		} catch (Exception e) {
			// log.error("��ȡĿ¼ " + fullPath.toString() + " ���ļ�����", e);
			return fileList;
		}

		return fileList;
	}
	
	/**
	 * ��str�ַ��������� Ŀ¼����\ftp\  �ļ�����.txt
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
	 * ��size����
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
