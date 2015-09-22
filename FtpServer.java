//FtpServer.java
import java.io.*;
import java.net.*;
 
public class FtpServer
{
     public static void main(String [] args)
     {
          int i=1;

System.out.println("******************************************************
**************************");
          System.out.println("******************************   FTP 
SERVER
***********************************");

System.out.println("******************************************************
**************************");
          System.out.println("Server Started...");
          System.out.println("Waiting for connections...");
          System.out.println("
");
          try
          {

               ServerSocket s = new ServerSocket(100);
               for(;;)
               {
                    Socket incoming = s.accept();
                    System.out.println("New Client Connected with id " 
+ i
+" from "+incoming.getInetAddress().getHostName()+"..." );
                    Thread t = new ThreadedServer(incoming,i);
                    i++;
                    t.start();
               }
          }
          catch(Exception e)
          {
               System.out.println("Error: " + e);
          }
     }
}

class ThreadedServer extends Thread
{
     int n;
     String c,fn,fc;
     String filenm;
     Socket incoming;
     int counter;
     String dirn="c:/FTP SERVER DIRECTORY";
     public ThreadedServer(Socket i,int c)
     {
          incoming=i;
          counter=c;
     }

     public void run()
     {
          try
          {

               BufferedReader in =new BufferedReader(new
InputStreamReader(incoming.getInputStream()));
               PrintWriter out = new
PrintWriter(incoming.getOutputStream(), true);
               OutputStream output=incoming.getOutputStream();
               fn=in.readLine();
               c=fn.substring(0,1);

               if(c.equals("#"))
               {
               n=fn.lastIndexOf("#");
               filenm=fn.substring(1,n);
               FileInputStream fis=null;
               boolean filexists=true;
               System.out.println("Request to download file "+filenm+"
recieved from "+incoming.getInetAddress().getHostName()+"...");
               try
                 {
                  fis=new FileInputStream(filenm);
                 }
               catch(FileNotFoundException exc)
                 {
                  filexists=false;
                  System.out.println("FileNotFoundException:
"+exc.getMessage());
                 }
                if(filexists)
                {
                 sendBytes(fis, output) ;
		 fis.close();
                }
               }
              else
                 {
                  try
                  {
                  boolean done=true;
                  System.out.println("Request to upload file " +fn+"
recieved from "+incoming.getInetAddress().getHostName()+"...");

                  File dir=new File(dirn);
                  if(!dir.exists())
                  {
                   dir.mkdir();
                  }
                  else
                  {}
                    File f=new File(dir,fn);
                    FileOutputStream fos=new FileOutputStream(f);
                    DataOutputStream dops=new DataOutputStream(fos);

                   while(done)
                   {
                    fc=in.readLine();
                    if(fc==null)
                    {
                     done=false;
                    }
                    else
                    {
                     dops.writeChars(fc);
                    // System.out.println(fc);

                    }
                 }
                 fos.close();
                 }
                 catch(Exception ecc)
                 {
                  System.out.println(ecc.getMessage());
                 }
                }
               incoming.close();
          }
          catch(Exception e)
          {
               System.out.println("Error: " + e);
          }
     }
     private static void sendBytes(FileInputStream f,OutputStream op)
throws Exception
     {
      byte[] buffer=new byte[1024];
      int bytes=0;

      while((bytes=f.read(buffer))!=-1)
      {
       op.write(buffer,0,bytes);
      }
     }
}
