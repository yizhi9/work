package com.isoft.util;

import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
/**
 * 
 * @author yizhi
 *
 */
public class ChecksumCRC32 {

	public  static long doChecksum(String fileName) {
		long checksum=0;
		try {
			CheckedInputStream cis = null;
			long fileSize = 0;
			try {
				cis = new CheckedInputStream(
				new FileInputStream(fileName), new CRC32());
				fileSize = new File(fileName).length();
			} catch (FileNotFoundException e) {
				System.err.println("File not found.");
				System.exit(1);
			}
			byte[] buf = new byte[128];
			while (cis.read(buf) >= 0) {
			}
		     checksum = cis.getChecksum().getValue();
			//System.out.println(checksum + " " + fileSize + " " + fileName);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return  checksum;

	}
    


}
