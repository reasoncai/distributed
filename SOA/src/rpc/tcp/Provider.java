package rpc.tcp;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Provider {
	public static void main(String[] args) throws Exception{
		//将提供的服务实例化后放到map中
		Map<String,Object> services = new HashMap<String,Object>();
		SayHelloService helloServiceImpl = new SayHelloServiceImpl();
		services.put("rpc.tcp.SayHelloService", helloServiceImpl);
		ServerSocket server = new ServerSocket(1234);
		while(true){
			Socket socket = server.accept();
			//读取服务信息
			ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
			String interfaceName = input.readUTF();//接口名称
			String methodName = input.readUTF();//方法名称
			Class<?>[] parameterTypes = (Class<?>[])input.readObject();//参数类型
			Object[] arguments = (Object[])input.readObject();//参数对象
			
			//执行调用
			Class<?> serviceInterfaceClass = Class.forName(interfaceName);//得到接口的class
			Object service = services.get(interfaceName);//取得服务实现的对象
			Method method = serviceInterfaceClass.getMethod(methodName, parameterTypes);//获得要调用的方法
			Object result = method.invoke(service, arguments);
			
			ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
			output.writeObject(result);
		}
	}
}
