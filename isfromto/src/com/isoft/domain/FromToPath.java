package com.isoft.domain;

public class FromToPath {
	private String from;
	private String to;
	private boolean delete;
	private String logPath;
	private int interval;
	private boolean transAll; //是否搬迁全部
	private String transAllName;//搬迁的用户名
	private boolean blankFlag;//白名单
	private boolean blackFlag;//黑名单
	private String blankFileList;//白名单列表
	private String blackFileList;//黑名单列表
	private boolean md5;//选择md5加密
	private boolean crc32;//选择crc32加密
	
	
	public boolean isMd5() {
		return md5;
	}
	public void setMd5(boolean md5) {
		this.md5 = md5;
	}
	public void setMd5(String md5){
		if(md5.equals("true")){
			this.md5=true;
		}else{
			this.md5=false;
		}
	}
	public boolean isCrc32() {
		return crc32;
	}
	public void setCrc32(boolean crc32) {
		this.crc32 = crc32;
	}
	public void setCrc32(String crc32){
		if(crc32.equals("true")){
			this.crc32=true;
		}else{
			this.crc32=false;
		}
	}
	public boolean isBlankFlag() {
		return blankFlag;
	}
	public void setBlankFlag(String blankFlag){
		if(blankFlag.equalsIgnoreCase("true")){
			this.blankFlag=true;
		}else{
			this.blankFlag=false;
		}
	}
	public void setBlankFlag(boolean blankFlag) {
		this.blankFlag = blankFlag;
	}

	public boolean isBlackFlag() {
		return blackFlag;
	}
	public void setBlackFlag(String blackFlag){
		if(blackFlag.equalsIgnoreCase("true")){
			this.blackFlag=true;
		}else{
			this.blankFlag=false;
		}
	}
	public void setBlackFlag(boolean blackFlag) {
		this.blackFlag = blackFlag;
	}

	public String getBlankFileList() {
		return blankFileList;
	}

	public void setBlankFileList(String blankFileList) {
		this.blankFileList = blankFileList;
	}

	public String getBlackFileList() {
		return blackFileList;
	}

	public void setBlackFileList(String blackFileList) {
		this.blackFileList = blackFileList;
	}

	public boolean isTransAll() {
		return transAll;
	}

	public void setTransAll(boolean transAll) {
		this.transAll = transAll;
	}

	public String getTransAllName() {
		return transAllName;
	}

	public void setTransAllName(String transAllName) {
		this.transAllName = transAllName;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public String getLogPath() {
		return logPath;
	}

	public void setLogPath(String logPath) {
		this.logPath = logPath;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public boolean isDelete() {
		return delete;
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}

	public void setDelete(String flag) {
		if (flag.equals("false")) {
			setDelete(false);
		} else if (flag.equals("true")) {
			setDelete(true);
		}
	}
	public void setTransAll(String transAll){
		if(transAll.equals("false")){
			setTransAll(false);
		}else if(transAll.equals("true")){
			setTransAll(true);
		}
	}
}
