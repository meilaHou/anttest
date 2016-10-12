import java.io.File; 

import org.apache.tools.ant.BuildException; 
import org.apache.tools.ant.Task; 

public class AntUtil extends Task { 

    private String lib; 

    private String classPath = ""; 

    private String fileType = ".jar"; 

    // ant task 
    public void execute() throws BuildException { 
        try { 
            this.classPath(dir(lib)); 
            this.getOwningTarget().getProject().setProperty("class-path", 
                    classPath); 
            System.out.println("class-path:"+classPath);
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 

    } 

    // 根据路径获得目录 
    private File dir(String path) throws Exception { 
        return dir(path, false); 
    } 

    private File dir(String path, boolean bCreate) throws Exception { 
        File dir = new File(path); 
        if (dir.exists()) { 
            if (!dir.isDirectory()) { 
                throw new Exception(dir.getName() + "is not a directory !"); 
            } 
        } else { 
            if (bCreate) { // 新建 
                if (!dir.mkdirs()) { 
                    throw new Exception("can not create the directory \"" 
                            + dir.getName() + "\"!"); 
                } 
            } 
        } 
        return dir; 
    } 

    // 生成classpath字符串 
    private void classPath(File dir) { 
        classPath(dir, lib); 
        classPath = classPath.trim(); 
    } 

    private void classPath(File dir, String parentPath) { 
        // 处理目录 
        if (dir.isDirectory()) { 
            File[] subs = dir.listFiles(); 
            for (int i = 0, len = subs.length; i < len; i++) { 
                if (subs[i].isFile()) { // 文件 
                    String name = subs[i].getName(); 
                    // 处理.jar文件 
                    if (name.length() > fileType.length() 
                            && fileType.equals(name.substring(name.length() 
                                    - fileType.length(), name.length()))) { 
                        classPath += parentPath + path(subs[i]) + " "; 
                        continue; 
                    } 
                } 
                if (subs[i].isDirectory()) { // 文件夹 
                    classPath(subs[i], parentPath + path(subs[i])); 
                } 
            } 

        } else { 
            // 不是目录 
            System.out.println(dir.getPath() + dir.getName() 
                    + "is not a directory !"); 
        } 
    } 

    // 文件路径 
    private String path(File f) { 
        return "/" + f.getName(); 
    } 

    // 字符串 -- > 路径 
    @SuppressWarnings("unused") 
    private String path(String path) { 
        path = path.replaceAll("\\\\", "/"); 
        if (!"/".equals(path.substring(0, 1))) { 
            path = "/" + path; 
        } 
        return path; 
    } 

    public void setLib(String lib) { 
        this.lib = lib; 
    }
}


