package cn.nizuge.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class RuntimeLocal {
	private static Logger logger  = LoggerFactory.getLogger(RuntimeLocal.class);
	private static final Runtime runtime = Runtime.getRuntime();
	private Process process = null;

	public String execute(String[] cmd) {
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader br = null;
		  try {
		  	logger.info("本地进程执行中。。。");
			  process = runtime.exec(cmd);
			  String tmp = null;
			  br = new BufferedReader(new InputStreamReader(
					  process.getInputStream(), "utf-8"));
			  while ((tmp = br.readLine()) != null) {
				  stringBuilder.append(tmp).append("\n");
			  }
			  br = new BufferedReader(new InputStreamReader(
					  process.getErrorStream(), "utf-8"));
			  while ((tmp = br.readLine()) != null) {
				  stringBuilder.append(tmp).append("\n");
			  }

		  } catch (IOException e) {
		  		e.printStackTrace();
		  }finally {
		  	if(br!=null)
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		  }
		  return stringBuilder.toString();
	}
	public void closeProcess(){
		while(process.isAlive()){
			process.destroy();
		}
	}
	public boolean isAlive(){
		return process.isAlive();
	}
}
