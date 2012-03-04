package com.lordofthejars.querydslplugin.util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class JarFileClassLoader extends URLClassLoader {

	public JarFileClassLoader(URL[] urls, ClassLoader parentClassLoader) {
        super(urls, parentClassLoader);
        Thread.currentThread().setContextClassLoader(this);
    }
 
    public JarFileClassLoader(URL[] urls) {
        this(urls, Thread.currentThread().getContextClassLoader());
    }
 
    public JarFileClassLoader() {
        this(new URL[0]);
    }
 
 
    public void loadJarFiles(String jarFile) throws MalformedURLException {
        File file = new File(jarFile);
       
        this.addURLFile(file.toURI().toURL());
    }
 
 
    private void addURLFile(URL path) throws MalformedURLException {
        URL urls[] = this.getURLs();
        for (int i = 0; i < urls.length; i++) {
            if (urls[i].equals(path)) {
                return;
            }
        }
 
        super.addURL(path);
    }
 
 
    public String toString() {
        return this.getClass().getName() + " (" + this.getClass() + " Loaded by " + this.getClass().getClassLoader() + ")";
    }

}
