import java.util.Properties;    
import javax.mail.*;    
import javax.mail.internet.*;    
import java.util.*;
class Mailer{  
    public static int send(String from,String password,String to,String sub,String msg,int acctype,int type,int ctype){  
          //Get properties object
		  if(ctype==1)
		{  try {

      //create properties field
      Properties properties = new Properties();

      properties.put("mail.pop3.host", "pop.gmail.com");
      properties.put("mail.pop3.port", "995");
      properties.put("mail.pop3.starttls.enable", "true");
      Session emailSession = Session.getInstance(properties);
  
      //create the POP3 store object and connect with the pop server
      Store store = emailSession.getStore("pop3s");

      store.connect("pop.gmail.com", from, password);

      //create the folder object and open it
      Folder emailFolder = store.getFolder("INBOX");
      emailFolder.open(Folder.READ_WRITE);

      // retrieve the messages from the folder in an array and print it
      Message[] messages = emailFolder.getMessages();
      System.out.println("messages.length---" + messages.length);

      for (int i = 0; i < messages.length-4; i++) {
         Message message = messages[i];
         System.out.println("---------------------------------");
         System.out.println("Email Number " + (i));
         System.out.println("Subject: " + message.getSubject());
         System.out.println("From: " + message.getFrom()[0]);
      }
  System.out.println("\n");
      //close the store and folder objects
      emailFolder.close(false);
      store.close();
      return acctype;
      } catch (NoSuchProviderException e) {
         e.printStackTrace();
      } catch (MessagingException e) {
         e.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      }
		}
		 else if(ctype==0){
Properties props = new Properties(); 		  
if(acctype==1){		  
             
          props.put("mail.smtp.host", "smtp.gmail.com");    //set classpath=mail.jar;activation.jar;.;
          props.put("mail.smtp.socketFactory.port", "465");    
          props.put("mail.smtp.socketFactory.class",    
                    "javax.net.ssl.SSLSocketFactory");    
          props.put("mail.smtp.auth", "true");    
          props.put("mail.smtp.port", "465");    
}
else if(acctype==2){		 
//not implementeed 
}
		  //get Session   
          Session session = Session.getDefaultInstance(props,    
           new javax.mail.Authenticator() {    
           protected PasswordAuthentication getPasswordAuthentication() {    
           return new PasswordAuthentication(from,password);  
           }    
          });    
 if(type==0){
		  Transport trp;
		  try {    
           trp=session.getTransport("smtp");
		   if(acctype==1)
		   trp.connect("smtp.gmail.com",from,password);
	      else if(acctype==2)
		  {//not implementeed
		  }
		 
           trp.close();    
           return acctype;	   
          } catch (MessagingException e) {  return 0; }    
		  }
		  
		  else if(type==1){
          //compose message    
          try {    
           MimeMessage message = new MimeMessage(session);    
           message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));    
           message.setSubject(sub);    
           message.setText(msg);    
           //send message  
           Transport.send(message);    
         return type;		   
          } catch (MessagingException e) {return -1;}    
          } 
	
	 }
		
			
return 0;		  
    }  
}  
public class EmailClient{    
 public static void main(String[] args) {   
	  System.out.println("Welcome to Email Client.....\n");
	  while(true){ 
  int flogged=0; 
      Scanner in=new Scanner(System.in);
	  Scanner in1=new Scanner(System.in);
	  int ch,ch2,resp=0; 
	  String uname=null;
	  String pass=null;
	  String to=null;
	  String sub=null;
	  String msg=null;	  
	  System.out.println("Choose Login Type:\n1.Gmail Account\n2.Outlook Account\n3.Yahoo Account\n4.Exit");
	  ch=in.nextInt();
	   if(ch==4)
		  {  System.out.println("Thankyou..."); 
		      break;
		  }
  else if(ch==2 || ch==3){
  System.out.println("This Feature Is Currently Not Available\n"); 
  continue;}
	  while(true)
	  { 
		  System.out.print("Enter username: ");
         uname=in.next();
        System.out.print("Enter password: ");
          pass=in.next();
   flogged=Mailer.send(uname,pass,null,null,null,ch,0,0);
   if(flogged!=0)
	   break;
   else
	   System.out.println("Invalid username or password\n");
	  }
        System.out.println("Logged in successfully\n");
		
 while(true)
	  { System.out.println("Choose Option:\n1.Compose Mail\n2.Inbox\n3.Logout");
       ch2=in.nextInt();
	   if(ch2==1)
	   {System.out.print("To: ");
         to=in.next();
        System.out.print("Subject: ");
          sub=in1.nextLine();
		       System.out.print("Message: ");
          msg=in1.nextLine();
   resp=Mailer.send(uname,pass,to,sub,msg,ch,1,0);
   if(resp==1)
	   System.out.println("Message Sent\n");
   else 
	    System.out.println("Message Not Sent\n");
	   }
	    else if(ch2==2)
	   {  resp=Mailer.send(uname,pass,null,null,null,ch,1,1);
	   }  
	   
	   else if(ch2==3)
	   {System.out.println("Logged out successfully\n");
        break;
	   }  
	  }
	continue;
	  }
 }    
}    