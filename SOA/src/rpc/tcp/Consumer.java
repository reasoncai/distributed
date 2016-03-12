package rpc.tcp;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;

public class Consumer {
	public static void main(String[] args) throws Exception{
		//接口名称
		String interfaceName = SayHelloService.class.getName();
		//需要远程执行的方法
		Method method = SayHelloService.class.getMethod("sayHello", java.lang.String.class);
		//需要传到远端的参数
		Object[] arguments = {"hello"};
		
		Socket socket = new Socket("127.0.0.1", 1234);
		
		//将方法名称和参数传到远端
		ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
		output.writeUTF(interfaceName);//接口名称
		output.writeUTF(method.getName());//方法名称
		output.writeObject(method.getParameterTypes());//参数类型
		output.writeObject(arguments);//参数对象
		
		//从远端读取方法执行结果
		ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
		Object result = input.readObject();
		System.out.println(result.toString());
	}
}
