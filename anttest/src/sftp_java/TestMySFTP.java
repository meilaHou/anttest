package sftp_java;


import java.util.List;
import java.util.Map;

import com.jcraft.jsch.ChannelSftp;

public class TestMySFTP {
	static String userName = "javaftp2"; // FTP 登录用户名
	static String password = "168TYFzmN4aY4j[JIAvJ"; // FTP 登录密码
	static String host = "180.178.62.226"; // FTP 服务器地址IP地址
	static int port = 9981; // FTP 端口
	static String root = "/egameentrance/client";
	static TestMySFTP app = new TestMySFTP();
	public static void main(String[] args) throws Exception {
		
		ChannelSftp sftp = MySFTP.connect(host, userName, password, port, root);
		 
		app.upload(sftp);

		//app.removeFile(sftp);
		
		//app.download(sftp);
		MySFTP.disconnect(sftp);
	}
	
	void upload(ChannelSftp sftp){
		try {
			String dir = "./";
			boolean flg = MySFTP.mkDir(dir, sftp);
			if(flg){
				String pwd = sftp.pwd();
				System.out.println(pwd);
				System.out.println(sftp.getHome());
				System.out.println(sftp.lpwd());
				//sftp.cd(".");
				//sftp.cd("..");
				String uploadFile = "F:/workspace/EGameClient6_config/build/mayayl/game/Game.swf";
				MySFTP.upload(uploadFile, sftp);//上传
				System.out.println("上传-成功.");
			}
//			dir = "a";
//			flg = MySFTP.cd(dir, sftp);
//			if(flg){
//				String uploadFile = "d:/dwsgsDelDup12.sql";
//				MySFTP.upload(uploadFile, sftp);//上传
//				System.out.println("上传-成功.");
//			}
		} catch (Exception e) {
			System.out.println("--上传失败..");
			e.printStackTrace();
		}
	}
	void removeFile(ChannelSftp sftp){
		try {
			String dirs = "a/b";
			boolean flg = MySFTP.cd(dirs, sftp);
			if(flg){
				String deleteFile = "c";
				MySFTP.rm(deleteFile, sftp);
				System.out.println("删除-成功.");
			}
		} catch (Exception e) {
			System.out.println("--删除失败..");
			e.printStackTrace();
		}
	}
	
	void download(ChannelSftp sftp){
		try {
			boolean flg = MySFTP.cd("/gzfw", sftp);
			if(flg){
				List files = MySFTP.list(sftp);
				if(files!=null&&files.size()>0){
					for(int i=0;i<files.size();i++){
						Map item = (Map)files.get(i);
						String f = (String)item.get("name");
						boolean isDir = ((Boolean)item.get("dir")).booleanValue();
						System.out.println("---本目录---"+f+" 是:"+isDir);
						if(isDir){
							List subfiles = MySFTP.list(f,sftp);
							for(int j=0;j<subfiles.size();j++){
								Map item1 = (Map)subfiles.get(j);
								boolean isDir1 = ((Boolean)item1.get("dir")).booleanValue();
								if(isDir1)continue;
								String ff = (String)item1.get("name");
								System.out.println("---2----"+ff);
								MySFTP.download(f,ff, "e:/ftp/"+ff, sftp);
							}
						}
						MySFTP.rm(f, sftp);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
