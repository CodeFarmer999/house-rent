package com.turing.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
	public static void main(String[] args){
		try {
			// 创建serverSocket对象
			ServerSocket serverSocket = new ServerSocket(7962);
			// 创建线程池
			ExecutorService pool = Executors.newCachedThreadPool();
			// 得到配置文件的输入流
			InputStream inputStream = Server.class.getClassLoader().getResourceAsStream("rpc.properties");
			// 创建Properties对象
			Properties properties = new Properties();
			// 加载配置文件
			properties.load(inputStream);
			while (true){
				Socket socket = serverSocket.accept();
				pool.execute(new Runnable() {
					@Override
					public void run() {
						try {
							// 得到io流
							ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
							ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
							// 从输入流读取数据
							String serviceName = in.readUTF();//接口名(服务名)
							String methodName = in.readUTF();//方法名
							Class[] parameterTypes= (Class[]) in.readObject();
							Object[] args= (Object[]) in.readObject();
							// 从properties根据serviceName取得实现类全路径名
							String implStr = properties.getProperty(serviceName);
							// 使用反射得到实现类的Class对象
							Class<?> implClass = Class.forName(implStr);
							// 使用反射得到实现类的方法对象
							Method method = implClass.getDeclaredMethod(methodName, parameterTypes);
							method.setAccessible(true);
							// 调用方法
							Object o = method.invoke(implClass.newInstance(), args);
							// 通过输出流将返回值写到客户端
							out.writeObject(o);
							out.flush();
							socket.close();
						} catch (IOException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
							e.printStackTrace();
						}
					}
				});
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
