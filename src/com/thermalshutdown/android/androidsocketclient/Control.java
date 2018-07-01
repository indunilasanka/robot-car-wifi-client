package com.thermalshutdown.android.androidsocketclient;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import com.javacodegeeks.android.androidsocketclient.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.view.View.OnClickListener;

public class Control extends Activity {

	private Socket socket;
	private String SERVER_IP;
	private int SERVERPORT;
	private PrintWriter out;
	private Main main;
    ImageButton left,right,brk;
    SeekBar acc;
    TextView msg,info;
    Server server;
    Button disconnect;
    ToggleButton ls,rs;
    long sTime,lTime,iTime;
     
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.control);	

        left = (ImageButton) findViewById(R.id.imageButton1);
        left.setBackgroundColor(0xffcccccc);
		right = (ImageButton) findViewById(R.id.imageButton2);
		right.setBackgroundColor(0xffcccccc);
		brk = (ImageButton) findViewById(R.id.imageButton3);
		brk.setBackgroundColor(0xffcccccc);
		
		disconnect=(Button)findViewById(R.id.button1);
		ls=(ToggleButton)findViewById(R.id.toggleButton2);
	    rs=(ToggleButton)findViewById(R.id.toggleButton1);
		acc=(SeekBar) findViewById(R.id.seekBar1);
		msg=(TextView) findViewById(R.id.textView4);
		info=(TextView) findViewById(R.id.textView2);
		
		main=new Main();
	    this.socket=main.getSocket();
		out=main.getPrintWriter();
		SERVER_IP=main.getIP();
		SERVERPORT=main.getPort();
		
		server = new Server(this);
		info.setText(server.getIpAddress()+":"+server.getPort()+" listening...");	
		addSeekBarListener();
		addButtonsListener();
		addDisconnectListener();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	    	
	        return true;
	    }

	    return super.onKeyDown(keyCode, event);
	}	
	
	@SuppressLint("ClickableViewAccessibility")
	public void addButtonsListener() {
		
		left.setOnTouchListener(new View.OnTouchListener() {        
		    @Override
		    public boolean onTouch(View v, MotionEvent event) {
		        switch(event.getAction()) {
		            case MotionEvent.ACTION_DOWN:
		            left.setBackgroundColor(0xffffff00);
		            	try {
							String str = "L";
							out.println(str);
						}
		            	catch (Exception e) {
							e.printStackTrace();
						}
		                return true; // if you want to handle the touch event
		                
		            case MotionEvent.ACTION_UP:
		            	left.setBackgroundColor(0xffcccccc);
		            	try {
							String str = "E";
							out.println(str);
						} 
		            	catch (Exception e) {
							e.printStackTrace();
						}
		                return true; // if you want to handle the touch event
		        }
		        return false;
		    }
		});
		
		
		right.setOnTouchListener(new View.OnTouchListener() {        
		    @Override
		    public boolean onTouch(View v, MotionEvent event) {
		        switch(event.getAction()) {
		            case MotionEvent.ACTION_DOWN:
		            right.setBackgroundColor(0xffffff00);
			            try {
							String str = "R";
							out.println(str);
						}
		            	catch (Exception e) {
							e.printStackTrace();
						}
		                return true; // if you want to handle the touch event
		                
		            case MotionEvent.ACTION_UP:
		            	right.setBackgroundColor(0xffcccccc);
		            	try {
							String str = "E";
							out.println(str);
						}
		            	catch (Exception e) {
							e.printStackTrace();
						}
		                return true; // if you want to handle the touch event
		        }
		        return false;
		    }
		});
		
		brk.setOnTouchListener(new View.OnTouchListener() {        
		    @Override
		    public boolean onTouch(View v, MotionEvent event) {
		        switch(event.getAction()) {
		            case MotionEvent.ACTION_DOWN:
		            brk.setBackgroundColor(0xffffff00);
			            try {
							String str = "B";
							out.println(str);
						}
		            	catch (Exception e) {
							e.printStackTrace();
						}
		                return true; // if you want to handle the touch event
		                
		            case MotionEvent.ACTION_UP:
		            	brk.setBackgroundColor(0xffcccccc);
		            	try {
							String str = "E";
							out.println(str);
						}
		            	catch (Exception e) {
							e.printStackTrace();
						}
		                return true; // if you want to handle the touch event
		        }
		        return false;
		    }
		});
		
		ls.setOnClickListener(new OnClickListener() {
			 
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                 if (ls.isChecked()) {
                	 try {
							String str = "X";
							out.println(str);
						}
		            	catch (Exception e) {
							e.printStackTrace();
						}
                    } 
                 else {
                	 try {
							String str = "Z";
							out.println(str);
						}
		            	catch (Exception e) {
							e.printStackTrace();
						}
                    }
            }
        });
		
		rs.setOnClickListener(new OnClickListener() {
			 
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                 if (rs.isChecked()) {
                	 try {
							String str = "Y";
							out.println(str);
						}
		            	catch (Exception e) {
							e.printStackTrace();
						}
                    } 
                 else {
                	 try {
							String str = "Z";
							out.println(str);
						}
		            	catch (Exception e) {
							e.printStackTrace();
						}
                    }
            }
        });
	}
	
	public void addSeekBarListener() {
		
		acc.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			//long lTime=System.currentTimeMillis();
			
			int progress = 0;

			@Override
			public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
	              progress = progresValue;
	              try {
					String str = ""+progress;
					out.println(str);
	              } 
	              catch (Exception e) {
					e.printStackTrace();
	              }
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}
			
			@Override
            public void onStopTrackingTouch(SeekBar seekBar) {	
				seekBar.setProgress(0);
			}
			
		});
	}
	
	public void addDisconnectListener() {
		
		disconnect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				out.close();
		    	try {
					socket.close();
					server.destroyServerSocket();
		    	} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	String s=""+SERVERPORT;
				Intent intent = new Intent(Control.this, Main.class);
				intent.putExtra("data", SERVER_IP+"-"+s);
				startActivity(intent);
				
			}
		});
	}

}