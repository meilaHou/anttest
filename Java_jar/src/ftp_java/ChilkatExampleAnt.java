package ftp_java;
import com.chilkatsoft.*;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task; 

public class ChilkatExampleAnt extends Task {

  static {
	  System.out.println( System.getProperty("java.library.path"));
    try {
        System.loadLibrary("chilkat");
    } catch (UnsatisfiedLinkError e) {
      System.err.println("Native code library failed to load.\n" + e);
      System.exit(1);
    }
  }
private String username;
private String pwd;
private String port;
private String file;
private String tofile;
private String ip;
//ant task 
  public void execute() throws BuildException { 
      try { 
    	  start();
      } catch (Exception e) { 
          e.printStackTrace(); 
      } 

  } 
  private void start()
  {
    CkFtp2 ftp = new CkFtp2();

    boolean success;

    //  Any string unlocks the component for the 1st 30-days.
    success = ftp.UnlockComponent("Anything for 30-day trial");
    if (success != true) {
        System.out.println(ftp.lastErrorText());
        return;
    }

    ftp.put_Hostname(ip);
    ftp.put_Username(username);
    ftp.put_Password(pwd);
    ftp.put_Port(Integer.parseInt(port));

    //  Establish an AUTH SSL secure channel after connection
    //  on the standard FTP port 21.
    ftp.put_AuthTls(true);

    //  The Ssl property is for establishing an implicit SSL connection
    //  on port 990.  Do not set it.
    ftp.put_Ssl(false);

    //  Connect and login to the FTP server.
    success = ftp.Connect();
    if (success != true) {
        System.out.println(ftp.lastErrorText());
        return;
    }
    else {
        //  LastErrorText contains information even when
        //  successful. This allows you to visually verify
        //  that the secure connection actually occurred.
        System.out.println(ftp.lastErrorText());
    }

    System.out.println("Secure FTP Channel Established!");

    //  Do whatever you're doing to do ...
    //  upload files, download files, etc...
	Boolean sendfinsh = ftp.AppendFile(file, tofile);
	if(sendfinsh){
		System.out.println("文件上传完毕...");
	}  
	success = ftp.Disconnect();
  }
  //username="" pwd="" port="" file="" tofile=""
	  public void setUsername(String username){
		  this.username = username;
	  }
	  public void setPwd(String pwd){
		  this.pwd = pwd;
	  }
	  public void setPort(String port){
		  this.port = port;
	  }
	  public void setFile(String file){
		  this.file = file;
	  }
	  public void setTofile(String tofile){
		  this.tofile = tofile;
	  }
	  public void setIp(String ip){
		  this.ip =  ip;
	  }
}