package com.isoft.domain;

public class PersonPriority {
	private String name; // �����û���
	private int priorityDegree; // �û����ȼ� 0ϵͳ�� �� 1 һ�㡢 2�ϸ�
	private String contents; // Ŀ¼����
	private String file;// �ļ�����
	private int size; // ����ļ���С����

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPriorityDegree() {
		return priorityDegree;
	}

	public void setPriorityDegree(int priorityDegree) {
		this.priorityDegree = priorityDegree;
	}
	public void setPriorityDegree(String priorityDegree){
		setPriorityDegree(Integer.valueOf(priorityDegree));
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	public void setSize(String size){
		setSize(Integer.valueOf(size));
	}

}
