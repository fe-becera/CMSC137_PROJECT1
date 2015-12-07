/*
Becera, Felicitas R.
Project 2
 
 TO COMPILE:

Open terminal and compile

> Javac MiniWebServer.java
> Java MIniWebServer


open a new window in your browser then type

>localhost:PortNumber
(in this code its localhost:1000 , but you can change it using any port number
go to MiniWebServer.java edit line 33 with the port number you want)


*/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class MiniWebServer {

	//you can enter any port number here

	private static final int PORT = 1000;

	public static void main(String[] args) {

		try {
				ServerSocket server = new ServerSocket(PORT);
				System.out.println("Server active " + PORT); 
  				while (true) {
  					new ThreadSocket(server.accept());
  			}
		} catch (Exception e) {
			}
	}
}

class ThreadSocket extends Thread {
	private Socket insocket;
	ThreadSocket(Socket insocket) {
		this.insocket = insocket;
		this.start();
	}

	public void run() {

 	try {
  			InputStream is = insocket.getInputStream();
  			PrintWriter out = new PrintWriter(insocket.getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        
        String line;
	             line = in.readLine();
	             String request_method = line;
	             System.out.println("HTTP-HEADER: " + line);
	             line = "";
	             // looks for post data
	             int postDataI = -1;
                  while ((line = in.readLine()) != null && (line.length() != 0)) {
    	              	System.out.println("HTTP-HEADER: " + line);
    	              	if (line.indexOf("Content-Length:") > -1) {
    	              	  postDataI = new Integer(
    	              	  line.substring(
    	              	  line.indexOf("Content-Length:") + 16,
    	              	  line.length())).intValue();
    	              	}
              	}
              	
              	String postData = "";
              	 // read the post data
              	  if (postDataI > 0) {
              	     char[] charArray = new char[postDataI];
              	    in.read(charArray, 0, postDataI);
              	    postData = new String(charArray);
              		//    postData2 = new String(charArray);
              		}

              	out.println("HTTP/1.0 200 OK");
              	out.println("Content-Type: text/html; charset=utf-8");
              	out.println("Server: MINISERVER");
              	   // this blank line signals the end of the headers
              	out.println("");
              	      // Sends the HTML page
                      out.println("<H1 style =\"color:green;\">*****************************************");
              	      out.println("<H1 style =\"color:blue;\"> *************** Mini Server*************** </H1> " );
                      out.println("<H1 style =\"color:green;\">*****************************************");
              	      out.println("<H2 style = \"color:blue;\">Request Method->" + request_method + "</H2>");
                      out.println("<H1 style =\"color:green;\">*****************************************");
              	      out.println("<H2 style = \"color:blue;\" >Post->" + postData + "</H2>");
                      out.println("<H1 style =\"color:green;\">*****************************************");
              	     	out.println("<form name=\"input\" action=\"form_submited\" method=\"post\" style = \" style =\"position:center\">");
              	   //  out.println("************************************");
                      out.println("<h2 style = \"color:blue;\" >Username: <input type=\"text\" name=\"user\" style =\"color:blue;\"><input type=\"submit\" value=\"Submit\" style =\"color:blue;\" > </form> ");
              	       // out.println("Address: <input type=\"text\" name=\"address\"><input type=\"submit\" value=\"Submit\"></form>");
              	      out.close();
              	      insocket.close();
              	    } catch (IOException e) {
              	     e.printStackTrace();
              	 }
              	  }
  }