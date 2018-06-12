package com.test;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class HadoopControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void testAddStudent() throws Exception {
		String url = "http://192.168.56.200:8080/addStudent";
		HttpEntity<String> requestEntity = new HttpEntity<String>("/usr/student/student.txt");
		HttpStatus status = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class).getStatusCode();
		Assert.assertTrue("ok", status.value() == 200);
	}

}
