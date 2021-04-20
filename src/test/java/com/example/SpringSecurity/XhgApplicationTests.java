package com.example.SpringSecurity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringSecurityApplicationTests {

	@Test
	void contextLoads() {
	}


	@Test
	public void test1(){

		Demo(2);

	}

	public static void Demo(Integer num){

		try {

			Thread.sleep(1000);
			System.out.println("Demo");
			if(num == 1){
				return;
			}else{
				num = 1/0;
			}
			System.out.println("return");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("catch");
		} finally {

			System.out.println("finally");

		}

		System.out.println("Demo2");
	}

}
