package sftp_java;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.SftpATTRS;

public final class MySFTP {
	static String _root = "/";
	static com.jcraft.jsch.Session _sshSession;

	public static ChannelSftp connect(String host, String userName,
			String password, int port, String root) throws Exception {
		ChannelSftp sftp = null;
		try {
			JSch jsch = new JSch();
			_sshSession = jsch.getSession(userName, host, port);
			// System.out.println("Session created.");
			_sshSession.setPassword(password);
			Properties sshConfig = new Properties();
			sshConfig.put("StrictHostKeyChecking", "no");
			_sshSession.setConfig(sshConfig);
			_sshSession.connect();
			System.out.println("Session connected.");
			System.out.println("Opening Channel.");
			Channel channel = _sshSession.openChannel("sftp");
			channel.connect();
			sftp = (ChannelSftp) channel;
			System.out.println("Connected to " + host + ".");
			System.out.println("链接到SFTP成功》》》++++++" + host);
			sftp.cd(root);
			_root += root;
			return sftp;
		} catch (Exception e) {
			throw e;
		}
	}

	public static void disconnect(ChannelSftp sftp) {
		if (sftp != null) {
			if (sftp.isConnected()) {
				sftp.disconnect();
				System.out.println("sftp关闭连接！！！！！====" + sftp);
			} else if (sftp.isClosed()) {
			}
		}
		if (_sshSession != null && _sshSession.isConnected()) {
			_sshSession.disconnect();
		}
		System.out.println("sftp 已经关闭");
	}

	public boolean isConnected(ChannelSftp sftp) {
		return (sftp != null) && sftp.isConnected() && !sftp.isClosed()
				&& (_sshSession != null) && _sshSession.isConnected();
	}

	private static Vector _list(String dir, ChannelSftp sftp) {
		try {
			return sftp.ls(dir);
		} catch (Exception e) {
			return null;
		}
	}

	private static Vector _list(ChannelSftp sftp) {
		try {
			return sftp.ls(sftp.pwd());
		} catch (Exception e) {
			return null;
		}
	}

	public static List list(String dir, ChannelSftp sftp) {
		try {
			Vector ls = _list(dir, sftp);
			return _buildFiles(ls);
		} catch (Exception e) {
			return null;
		}
	}

	private static List _buildFiles(Vector ls) throws Exception {
		if (ls != null && ls.size() >= 0) {
			List list = new ArrayList();
			for (int i = 0; i < ls.size(); i++) {
				LsEntry f = (LsEntry) ls.get(i);
				String nm = f.getFilename();
				if (nm.equals(".") || nm.equals(".."))
					continue;
				SftpATTRS attr = f.getAttrs();
				Map m = new HashMap();
				if (attr.isDir()) {
					m.put("dir", new Boolean(true));
				} else {
					m.put("dir", new Boolean(false));
				}
				m.put("name", nm);
				list.add(m);
			}
			return list;
		}
		return null;
	}

	public static List list(ChannelSftp sftp) {
		try {
			Vector ls = _list(sftp);
			return _buildFiles(ls);
		} catch (Exception e) {
			return null;
		}
	}

	public static boolean cd(String dirs, ChannelSftp sftp) throws Exception {
		try {
			String path = dirs;
			if (path.indexOf("\\") != -1) {
				path = dirs.replaceAll("\\", "/");
			}
			String pwd = sftp.pwd();
			if (pwd.equals(path))
				return true;

			sftp.cd(_root);
			
			if(_root.equals(dirs))return true;
			
			String[] paths = path.split("/");
			for (int i = 0; i < paths.length; i++) {
				String dir = paths[i];
				if(isEmpty(dir))continue;
				sftp.cd(dir);
			}
			return true;
		} catch (Exception e) {
			throw e;
		}
	}

	public static boolean isExist(String root, String fileOrDir,
			ChannelSftp sftp) throws Exception {
		try {
			boolean exist = false;
			boolean cdflg = false;
			String pwd = sftp.pwd();
			if (pwd.indexOf(root) == -1) {
				cdflg = true;
				sftp.cd(root);
			}
			Vector ls = _list(root, sftp);
			if (ls == null || ls.size() <= 0) {
				for (int i = 0; i < ls.size(); i++) {
					LsEntry f = (LsEntry) ls.get(i);
					String nm = f.getFilename();
					if (nm.equals(fileOrDir)) {
						exist = true;
						break;
					}
				}
			}
			if (cdflg) {
				sftp.cd("..");
			}
			return exist;
		} catch (Exception e) {
			throw e;
		}
	}

	public static boolean isEmpty(String s) {
		return s == null || "".equals(s.trim());
	}

	public static boolean upload(String uploadFile, ChannelSftp sftp)
			throws Exception {
		java.io.InputStream is = null;
		try {
			File file = new File(uploadFile);
			is = new FileInputStream(file);
			sftp.put(is, file.getName());
			return true;
		} catch (Exception e) {
			throw e;
		}finally{
			try {
				if(is!=null){
					is.close();
					is=null;
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	public static void download(String dir, String downloadFile,
			String saveFile, ChannelSftp sftp) {
		try {
			boolean isCd = false;
			if (!isEmpty(dir)) {
				sftp.cd(dir);
				isCd = true;
			}
			// File file = new File(saveFile);
			// sftp.get(downloadFile, new FileOutputStream(file));
			sftp.get(downloadFile, saveFile);

			if (isCd)
				sftp.cd("..");

			System.out.println("下载文件成功！！！！！====" + sftp);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("下载文件失败！！！！！=*******" + sftp);
		}
	}

	public static boolean mkDir(String filepath, ChannelSftp sftp)
			throws Exception {
		try {
			String path = filepath;
			if (path.indexOf("\\") != -1) {
				path = filepath.replaceAll("\\", "/");
			}
			String[] paths = path.split("/");
			for (int i = 0; i < paths.length; i++) {
				String dir = paths[i];
				Vector ls = _list(dir, sftp);
				if (ls == null || ls.size() <= 0) {
					sftp.mkdir(dir);
				}
				sftp.cd(dir);
			}
		} catch (Exception e1) {
			throw e1;
		}
		return true;
	}

	public static boolean rm(String deleteFile, ChannelSftp sftp)
			throws Exception {
		try {
			Vector ls = _list(sftp);
			if (ls != null && ls.size() > 0) {
				for (int i = 0; i < ls.size(); i++) {
					LsEntry f = (LsEntry) ls.get(i);
					String nm = f.getFilename();
					if (!nm.equals(deleteFile)) {
						continue;
					}
					SftpATTRS attr = f.getAttrs();
					if (attr.isDir()) {
						if (rmdir(nm, sftp)) {
							sftp.rmdir(nm);
						}
					} else {
						sftp.rm(nm);
					}
				}
			}
			return true;
		} catch (Exception e) {
			throw e;
		}
	}

	private static boolean rmdir(String dir, ChannelSftp sftp) throws Exception {
		try {
			sftp.cd(dir);
			Vector ls = _list(sftp);
			if (ls != null && ls.size() > 0) {
				for (int i = 0; i < ls.size(); i++) {
					LsEntry f = (LsEntry) ls.get(i);
					String nm = f.getFilename();
					if (nm.equals(".") || nm.equals(".."))
						continue;
					SftpATTRS attr = f.getAttrs();
					if (attr.isDir()) {
						if (rmdir(nm, sftp)) {
							sftp.rmdir(nm);
						}
					} else {
						sftp.rm(nm);
					}
				}
			}
			sftp.cd("..");
			return true;
		} catch (Exception e) {
			throw e;
		}
	}
}


