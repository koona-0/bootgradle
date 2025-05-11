package kr.co.koo;

public class javastatic {

	public static void main(String[] args) {
//		javatest jt = new javatest();
//		System.out.println(jt.test1(10));
//		System.out.println(jt.test2(20));
		
		System.out.println(javatest.test1(10));				//static 메소드는 새로 로드할 때 new를 사용하지 않음 
		System.out.println(new javatest().test2(10));		//일반 메소드는 new를 사용해야만 쓸 수 있음 
		
		System.out.println(javatest.test1(10));			 
		System.out.println(new javatest().test2(10));
		
	}

}
                            
class javatest{
	static int sum = 0;

	public static int test1(int aa) {
		sum += aa; 
		return sum;
	}
	
	public int test2(int aa) {
		sum += aa; 
		return sum;
	}
	
}