package com.rinit.debugger.server.client.utils;

import com.rinit.debugger.server.utils.ObjectsBytesUtils;

public class BytesClassLoader extends ClassLoader {
	
   public BytesClassLoader(ClassLoader parent) {
       super(parent);      
   }
   
   public Class<?> findClass(String name, byte[] classBytes) {
	   return this.defineClass(name, classBytes, 0, classBytes.length);
   }
}
