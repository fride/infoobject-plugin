package org.infoobject.core.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;


/**
 *
 *
 */
public class Digest {

	public static String md5(String... text) {
		StringBuffer in = new StringBuffer();
		for (String str : text) {
			in.append(str);
		}
		StringBuffer result = new StringBuffer(32);
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(in.toString().getBytes());
			Formatter f = new Formatter(result);
			for (byte b : md5.digest()) {
				f.format("%02x", b);
			}
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		}
		return result.toString();
	}

	public static String sha1(String... text) {
		StringBuffer in = new StringBuffer();
		for (String str : text) {
			in.append(str);
		}
		StringBuffer result = new StringBuffer(32);
		try {
			MessageDigest md5 = MessageDigest.getInstance("sha1");
			md5.update(in.toString().getBytes());
			Formatter f = new Formatter(result);
			for (byte b : md5.digest()) {
				f.format("%02x", b);
			}
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		}
		return result.toString();
	}
}
