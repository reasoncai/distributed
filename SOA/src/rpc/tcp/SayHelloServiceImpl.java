package rpc.tcp;

public class SayHelloServiceImpl implements SayHelloService {

	@Override
	public String sayHello(String helloArg) {
		if("hello".equalsIgnoreCase(helloArg)){
			return "hello";
		}else{
			return "bye bye";
	    }
	}
}
