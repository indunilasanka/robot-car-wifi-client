package com.thermalshutdown.android.androidsocketclient;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import com.javacodegeeks.android.androidsocketclient.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main extends Activity {
	
	private static Socket socket;
	private static PrintWriter out;
	private static int SERVERPORT;
	private static String SERVER_IP;
    Button connect;
    EditText ipAdr,port;
    boolean valid,available;
    Thread t;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);	
		Intent intent=getIntent(); 
		String message = intent.getStringExtra("data");
		
		ipAdr = (EditText) findViewById(R.id.EditText01);
		port = (EditText) findViewById(R.id.editText1);
		connect = (Button) findViewById(R.id.button1);
		if(message!=null){
			String parts[]=message.split("-");
			ipAdr.setText(parts[0]);
			port.setText(parts[1]);
		}
		addListenerOnButton();
	}
	
	public void addListenerOnButton() {
		
		connect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				valid=false;	
				valid=validate();
				
				if((valid)){
					t=new Thread(new ClientThread());
					t.start();
					SystemClock.sleep(100);
					try{
						if(socket.isConnected()){
							Toast.makeText(Main.this,"connected",Toast.LENGTH_SHORT).show();
							Intent intent = new Intent(Main.this, Control.class);
				            startActivity(intent);
						}
					}
					catch(NullPointerException e)
			        {
						Toast.makeText(Main.this,"No listening server found",Toast.LENGTH_SHORT).show();
			        }
				}		
			}
		});
	}
	
	public boolean validate(){
		String IP,pt;
		IP=ipAdr.getText().toString();
		pt=port.getText().toString();
		
		if(IP.trim().length() > 0){
			boolean valid;
			IpValidator a=new IpValidator();
			valid=a.validate(IP);
			if(valid){		
				if(pt.trim().length() > 0){
					if (pt.matches("[0-9]+") && pt.length() > 0) {
						SERVERPORT=Integer.parseInt(pt);
						SERVER_IP=IP;
						return true;
					}
					else{
						Toast.makeText(Main.this,"Port should be an integer",Toast.LENGTH_SHORT).show();
						return false;
					}
				}
				else{
					Toast.makeText(Main.this,"No port",Toast.LENGTH_SHORT).show();
					return false;
				}
			}
			else{
				Toast.makeText(Main.this,"IP is not valid",Toast.LENGTH_SHORT).show();
				return false;
			}
		}
		else{
			Toast.makeText(Main.this,"No IP address",Toast.LENGTH_SHORT).show();
			return false;
		}
	}
	
	public Socket getSocket(){
		return socket;
	}
	
	public PrintWriter getPrintWriter(){
		return out;
	}
	
	public String getIP(){
		return SERVER_IP;
	}
	
	public int getPort(){
		return SERVERPORT;
	}
	
	class ClientThread implements Runnable {
		@Override
		public void run() {
			try {		
				InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
				socket = new Socket(serverAddr, SERVERPORT);
				out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
								
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}