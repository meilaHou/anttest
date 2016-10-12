package JUintTest;

import static org.junit.Assert.*;

import org.junit.Test;
import static org.junit.Assert.*;  

import org.junit.After;  
import org.junit.AfterClass;  
import org.junit.Before;  
import org.junit.BeforeClass;  
import org.junit.Ignore;  
import org.junit.Test;

public class testjuint {
/*
 * 
 * 1. @Test : 测试方法，测试程序会运行的方法，后边可以跟参数代表不同的测试，如(expected=XXException.class) 异常测试，(timeout=xxx)超时测试
 * 2. @Ignore : 被忽略的测试方法
3. @Before: 每一个测试方法之前运行
4. @After : 每一个测试方法之后运行
5. @BeforeClass: 所有测试开始之前运行
6. @AfterClass: 所有测试结束之后运行
 * */
	private HelloWorld helloc = new HelloWorld();
	@Test
	public void testMain() {
		fail("Not yet implemented");
	}     
	 @Test  
	    public void testTest() {  
	        System.out.println("@Test");//调用自己要测试的方法 
	        
	    }  
	      
	    @Test  
	    public void testAssert() {  
	    	helloc.plus(1, 2);
//	    	assertEquals(Object A,  Object B) 的比较逻辑：
//	    	如果 A,B都是Null,返回true。否则调用 A.equals(B)来判断。
//
//	    	assertSame(Object A, Object B)的比较逻辑：
//	    	以A == B运算的结果来判断。
	    	 System.out.println(helloc.get_result());
	    	assertSame(3.0,helloc.get_result()); //assertEquals测试2个参数是否相等 
	    }  
	      
	    @Test(timeout=1)  
	    public void testTimeout() {  
	        System.out.println("超时测试");  
	    }  
	  
	    @Before  
	    public void testBefore(){  
	        System.out.println("@Before");  
	    }  
	      
	    @BeforeClass  
	    public static void testBeforeClass(){//必须为静态方法  
	        System.out.println("@BeforeClass");  
	    }  
	      
	    @After  
	    public void testAfter(){  
	        System.out.println("@After");  
	    }  
	      
	    @AfterClass  
	    public static void testAfterClass(){//必须为静态方法  
	        System.out.println("@AfterClass");  
	    }  
	      
	    @Ignore  
	    public void testIgnore(){  
	        System.out.println("@Ignore");  
	    }  

}
