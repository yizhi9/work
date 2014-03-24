package com.isoft.xmlparser;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.isoft.domain.FromToPath;
import com.isoft.domain.PersonPriority;
import com.isoft.domain.PriorityManager;

public class ReadXML {
	public static  Document loadFile(URL url){
		Document document=null;
		SAXReader saxReader=new SAXReader();
		try {
			document=saxReader.read(url);
		} catch (DocumentException e) {
			try {
				Thread.sleep(500);
				loadFile(url);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return document;
	}
	/**
	 * 解析xml
	 * @param document 整个文本
	 * @return
	 */
	public static PriorityManager parseXML(Document document){
		Element root=document.getRootElement();
		Element ele=(Element)root.elementIterator("transcacheconfig").next(); //取唯一的一个transcacheconfig
		PriorityManager pm=new PriorityManager();
		Element fromtocache=(Element) ele.elementIterator("fromtocache").next(); //取得一个fromtocache
		String transAll=fromtocache.elementTextTrim("transall");
		String transAllName=fromtocache.elementTextTrim("transname");
		String from=fromtocache.elementTextTrim("from");
		String to=fromtocache.elementTextTrim("to");
		String delete=fromtocache.elementTextTrim("delete");
		String interval=fromtocache.elementTextTrim("interval");
		String logPath=fromtocache.elementTextTrim("logpath");
		String blackflag=fromtocache.elementTextTrim("blackflag");
		String blackfilelist=fromtocache.elementTextTrim("blackfilelist");
		String blankflag=fromtocache.elementTextTrim("blankflag");
		String blankfilelist=fromtocache.elementTextTrim("blankfilelist");
		String md5=fromtocache.elementTextTrim("md5");
		String crc32=fromtocache.elementTextTrim("crc32");
		Iterator<Element> persons=ele.elementIterator("transperson"); //取得多个transperson
		PersonPriority pp=null;
		List<PersonPriority> list=new ArrayList<PersonPriority>();
		while(persons.hasNext()){
			Element pers=persons.next();
			String name=pers.elementTextTrim("person");
			String priorityDegree=pers.elementTextTrim("personpriority");
			Element businesspriority=pers.element("businesspriority");
			String contents=businesspriority.elementTextTrim("contents");
			String file=businesspriority.elementTextTrim("files");
			Element inlaypriority=pers.element("inlaypriority");
			String size=inlaypriority.elementTextTrim("size");
			pp=new PersonPriority();
			pp.setContents(contents);
			pp.setName(name);
			pp.setFile(file);
			pp.setPriorityDegree(priorityDegree);
			pp.setSize(size);
			list.add(pp);
		}
		FromToPath ftp=new FromToPath();
		ftp.setTransAll(transAll);
		ftp.setTransAllName(transAllName);
		ftp.setDelete(delete);
		ftp.setFrom(from);
		ftp.setTo(to);
		ftp.setLogPath(logPath);
		ftp.setBlackFileList(blackfilelist);
		ftp.setBlackFlag(blackflag);
		ftp.setBlankFileList(blankfilelist);
		ftp.setBlankFlag(blankflag);
		ftp.setInterval(Integer.valueOf(interval));
		ftp.setCrc32(crc32);
		ftp.setMd5(md5);
		pm.setAllPath(ftp);
		pm.setPersonList(list);
			
		return pm;
	}
	
}
