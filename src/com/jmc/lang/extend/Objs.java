package com.jmc.lang.extend;

import com.jmc.chatserver.*;
import com.jmc.io.*;
import java.io.*;


/**
 * <p>作者：Jmc
 * <p>时间：2020.3.26
 * <p>功能：Object类扩展
 */
public class Objs 
{
	public static boolean orEquals(Object o, Object... a) {
		for (var t : a) if (o.equals(t)) return true;
		return false;
	}

	//不支持数组
	public static boolean nullOrEmpty(Object... objs) {
		for (Object o : objs) {
			if (o == null) return true;
			
			if (o instanceof String) {
				if (((String) o).isEmpty()) return true; 
			}
		}
		
		return false;
	};
	
	public static void throwsIfNullOrEmpty(Object... objs) {
		if (nullOrEmpty(objs)) throw new NullPointerException();
	}
	
	public static byte[] outObjToBytes(Object o) {
		ByteArrayOutputStream baos = null;
		ObjectOutputStream out = null;
		try {
			baos = new ByteArrayOutputStream();
			out = new ObjectOutputStream(baos);
			out.writeObject(o);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			CloseUtils.closeAll(out);
		}

		return baos.toByteArray();
	}

	public static void outObj(Object o, File f) {
		Files.out(outObjToBytes(o), f, false);
	}
	public static void outObj(Object o, String filePath) {
		outObj(o, new File(filePath));
	}

	public static void outObj(Object o, OutputStream out, boolean closeOut) {
		try {
			out.write(outObjToBytes(o));
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (closeOut) CloseUtils.closeAll(out);
		}
	}

	@SuppressWarnings("unchecked")
	public static<T> T readObj(byte[] b, Class<T> c) {
		ObjectInputStream in = null;

		try {
			in = new ObjectInputStream(new ByteArrayInputStream(b));
			return (T) in.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			CloseUtils.closeAll(in);
		}

		return null;
	}

	public static Object readObj(byte[] b) {
		return readObj(b, Object.class);
	}

	public static<T> T readObj(InputStream in, Class<T> c) {
		return readObj(Streams.read(in), c);
	}

	public static Object readObj(InputStream in) {
		return readObj(Streams.read(in), Object.class);
	}

	public static<T> T readObj(File f, Class<T> c) {
		try {
			return readObj(new FileInputStream(f), c);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static<T> T readObj(String filePath, Class<T> c) {
		return readObj(new File(filePath), c);
	}

	public static Object readObj(File f) {
		return readObj(f, Object.class);
	}

	public static Object readObj(String filePath) {
		return readObj(new File(filePath));
	}
}

