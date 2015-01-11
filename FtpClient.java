//FtpClient.java

import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.lang.*;
class FtpClient extends JFrame implements ActionListener
{
 String fn,filenm;
 String fc;
 String dirn="c:/FTP CLIENT DIRECTORY";
 JPanel pnl;
 JLabel lbltle,lblud;
 Font fnt;
 JTextField txtfn;
 JButton btnu,btnd;
 Socket s;
 InputStreamReader in;
 OutputStream out;
 BufferedReader br;
 PrintWriter pw;
 public FtpClient()
 {
  super("FTP CLIENT");

  pnl=new JPanel(null);

  fnt=new Font("Times New Roman",Font.BOLD,25);

  lbltle=new JLabel("FTP CLIENT");
  lbltle.setFont(fnt);
  lbltle.setBounds(225,35,200,30);
  pnl.add(lbltle);

  lblud=new JLabel("ENTER  FILE-NAME :");
  lblud.setBounds(100,100,150,35);
  pnl.add(lblud);

  txtfn=new JTextField();
  txtfn.setBounds(300,100,200,25);
  pnl.add(txtfn);

  btnu=new JButton("UPLOAD");
  btnu.setBounds(150,200,120,35);
  pnl.add(btnu);


  btnd=new JButton("DOWNLOAD");
  btnd.setBounds(320,200,120,35);

  pnl.add(btnd);

  btnu.addActionListener(this);
  btnd.addActionListener(this);
  getContentPane().add(pnl);

  try
  {
  s=new Socket("localhost",100);
  br=new BufferedReader(new InputStreamReader(s.getInputStream()));
  pw=new PrintWriter(s.getOutputStream(),true);
  out=s.getOutputStream();
  }
  catch(Exception e)
  {
   System.out.println("ExCEPTION :"+e.getMessage());
  }
 }
 public void actionPerformed(ActionEvent e)
 {
  if(e.getSource()==btnu)
  {
   try
   {
   filenm=txtfn.getText();
   pw.println(filenm);
   FileInputStream  fis=new FileInputStream(filenm);
   byte[] buffer=new byte[1024];
   int bytes=0;

   while((bytes=fis.read(buffer))!=-1)
   {
    out.write(buffer,0,bytes);
   }
   fis.close();
  }
  catch(Exception exx)
  {
   System.out.println(exx.getMessage());
  }
  }

  if(e.getSource()==btnd)
  {
   try
   {
   File dir=new File(dirn);
   if(!dir.exists())
   {
    dir.mkdir();
   }
   else{}
   boolean done=true;
   filenm=txtfn.getText();
   fn=new String("#"+filenm+"#");
   //System.out.println(filenm);
   pw.println(fn);
   File f=new File(dir,filenm);
   FileOutputStream fos=new FileOutputStream(f);
   DataOutputStream dops=new DataOutputStream(fos);
   while(done)
   {
     fc=br.readLine();
     if(fc==null)
     {
     done=false;
     }
    else
        {
          dops.writeChars(fc);
       //  System.out.println(fc);

        }
    }
   fos.close();
  }

  catch(Exception exx)
  {}

  }
 }
 public static void main(String args[])
 {
  FtpClient ftpc=new FtpClient();
  ftpc.setSize(600,300);
  ftpc.show();
 }
}