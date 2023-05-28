package xyz.cherish.utils;

import xyz.cherish.core.io.ClassPathResource;
import xyz.cherish.exception.BeansException;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 类扫描器
 */
public class ClassScanner {
    private String packageName; // 包名称
    private String packageDirName; // 包路径
    private String packagePath; // 包路径，在Linux路径下与packageDirPath相同
    private Filter<Class<?>> classFilter; // 类过滤器
    private Set<Class<?>> classes = new HashSet<>(); // 成功过滤的类
    private String packageNameWithDot;

    public ClassScanner(String packageName, Filter<Class<?>> classFilter) {
        packageDirName = packageName.replace(".", File.separator);
        packagePath = packageName.replace(".", "/");
        this.packageName = packageName;
        this.classFilter = classFilter;
        this.packageNameWithDot = packageName + ".";
    }

    /**
     * 扫描获得匹配类
     *
     * @return 匹配类的Set
     */
    public Set<Class<?>> scan() {
        try {
            Enumeration<URL> resourceURLs = new ClassPathResource(packagePath).getResourceURLs();
            while (resourceURLs.hasMoreElements()) {
                URL url = resourceURLs.nextElement();
                switch (url.getProtocol()) {
                    case "file" -> scanFile(new File(url.getFile()), null);
                    case "jar" -> scanJar(getJarFile(url));
                }
            }
        } catch (IOException e) {
            throw new BeansException("can not access package " + this.packageName, e);
        }
        return Collections.unmodifiableSet(this.classes);
    }

    public JarFile getJarFile(URL url) {
        try {
            JarURLConnection urlConnection = (JarURLConnection) url.openConnection();
            return urlConnection.getJarFile();
        } catch (IOException e) {
            throw new BeansException("can not get jar file");
        }
    }

    private void scanFile(File file, String rootDir) {
        if (file.isFile()) {
            String fileName = file.getAbsolutePath();
            if (fileName.endsWith(".class")) {
                String className = fileName.substring(rootDir.length(), fileName.length() - 6)
                        .replace(File.separatorChar, '.');// 获取类名
                addIfAccept(className); // 添加当匹配时
            } else if (fileName.endsWith(".jar")) {
                try {
                    scanJar(new JarFile(file));
                } catch (IOException e) {
                    throw new BeansException("can not fetch jar file", e);
                }
            }
        } else if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File subFile : files) {
                    scanFile(subFile, (null == rootDir) ? subPathBeforePackage(file) : rootDir);
                }
            }
        }
    }

    private void scanJar(JarFile jarFile) {
        String name;
        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            JarEntry jarEntry = entries.nextElement();
            name = jarEntry.getName();
            if (name.startsWith("/")) {
                name = name.substring(1);
            }
            if (name.startsWith(this.packagePath)) {
                if (name.endsWith(".class") && !jarEntry.isDirectory()) {
                    String className = name.substring(0, name.length() - 6).replace('/', '.');
                    addIfAccept(className);
                }
            }
        }
    }

    private String subPathBeforePackage(File file) {
        String filePath = file.getAbsolutePath();
        if (!packageDirName.isEmpty()) {
            int pos = filePath.lastIndexOf(packageDirName);
            filePath = pos == -1 ? filePath : (pos == 0 ? "" : filePath.substring(0, pos));
        }
        return filePath.endsWith(File.separator) ? filePath : filePath + File.separator;
    }

    private void addIfAccept(String className) {
        if (className.isBlank()) {
            return;
        }
        int classNameLen = className.length();
        int packageLen = this.packageName.length();
        if (packageLen == classNameLen) {
            if (packageName.equals(className)) {
                addIfAccept(loadClass(className));
            }
        } else if (packageLen < classNameLen) {
            if (className.startsWith(this.packageNameWithDot)) {
                addIfAccept(loadClass(className));
            }
        }

    }

    private Class<?> loadClass(String className) {
        Class<?> clazz = null;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new BeansException("scan class not found exception " + className, e);
        }
        return clazz;
    }

    private void addIfAccept(Class<?> clazz) {
        if (clazz != null) {
            Filter<Class<?>> classFilter = this.classFilter;
            if (classFilter == null || classFilter.accept(clazz)) {
                this.classes.add(clazz);
            }
        }
    }

}
