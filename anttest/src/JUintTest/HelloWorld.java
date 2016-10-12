package JUintTest;


public class HelloWorld {
private double  _result = 0;
	public static void main(String args[]){
	System.out.println("test junit hello world!");
}

public void plus(double num1,double  num2){
	_result =  num1+num2;
}

public double get_result() {
	return _result;
}
}
