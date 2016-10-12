import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPSClient;
//需要commons-net-3.5.jar
public class TestFtp {
public static void main(String args[]){
	putFile("114.119.40.123",9981,"javaftp","hQaLbEzNh6eZinouoIrv","F:/workspace/EGameClient6_config/复制文件/C_1608240535.zip","/client/C_1608240535.zip");
}
  public static void putFile(String host,
                      int port,
                      String username,
                      String password,
                      String localFilename,
                      String remoteFilename) {
    try {
      FTPSClient ftpClient = new FTPSClient(false);
      // Connect to host
      ftpClient.connect(host, port);
      int reply = ftpClient.getReplyCode();
      if (FTPReply.isPositiveCompletion(reply)) {

        // Login
        if (ftpClient.login(username, password)) {

          // Set protection buffer size
          ftpClient.execPBSZ(0);
          // Set data channel protection to private
          ftpClient.execPROT("P");
          // Enter local passive mode
          ftpClient.enterLocalPassiveMode();

          // Store file on host
	  InputStream is = new FileInputStream(localFilename);
	  if (ftpClient.storeFile(remoteFilename, is)) {
	    is.close();
	  } else {
	    System.out.println("Could not store file");
	  }
	  // Logout
	  ftpClient.logout();

        } else {
          System.out.println("FTP login failed");
        }

        // Disconnect
    	ftpClient.disconnect();

      } else {
        System.out.println("FTP connect to host failed");
      }
    }catch (IOException ioe) {
      System.out.println("FTP client received network error"+ioe);
    }
  }
}