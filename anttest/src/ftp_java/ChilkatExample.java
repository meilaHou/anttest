package ftp_java;
import com.chilkatsoft.*;
//需要chilkat.jar ,并且将chilkat.dll加入到环境变量path类库中
public class ChilkatExample {

  static {
	  System.out.println( System.getProperty("java.library.path"));
    try {
        System.loadLibrary("chilkat");
    } catch (UnsatisfiedLinkError e) {
      System.err.println("Native code library failed to load.\n" + e);
      System.exit(1);
    }
  }

  public static void main(String argv[])
  {
    CkFtp2 ftp = new CkFtp2();

    boolean success;

    //  Any string unlocks the component for the 1st 30-days.
    success = ftp.UnlockComponent("Anything for 30-day trial");
    if (success != true) {
        System.out.println(ftp.lastErrorText());
        return;
    }

    ftp.put_Hostname("114.119.40.123");
    ftp.put_Username("javaftp");
    ftp.put_Password("hQaLbEzNh6eZinouoIrv");
    ftp.put_Port(9981);

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
	Boolean sendfinsh = ftp.AppendFile("F:/workspace/EGameClient6_config/复制文件/C_1608240535.zip", "/client/C_1608240535.zip");
	if(sendfinsh){
		System.out.println("文件上传完毕...");
	}  
	success = ftp.Disconnect();


  }
}