package com.rinit.debugger.server.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectsBytesUtils {
	
	public static byte[] objectToBytes(Object obj) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream out = null;
		byte[] bytes = null;
		try {
		  out = new ObjectOutputStream(bos);   
		  out.writeObject(obj);
		  out.flush();
		  bytes = bos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		  try {
		    bos.close();
		  } catch (IOException ex) {
		    // ignore close exception
		  }
		}
		return bytes;
	}

	public static Object objectFromBytes(byte[] bytes) {
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		ObjectInput in = null;
		Object obj = null;
		try {
		  try {
			in = new ObjectInputStream(bis);
			obj = in.readObject(); 
			  
		    } catch (IOException | ClassNotFoundException | IllegalArgumentException | SecurityException e) {
				e.printStackTrace();
			}
		} finally {
		  try {
		    if (in != null) {
		      in.close();
		    }
		  } catch (IOException ex) {}
		}
		return obj;
	}
	
}
