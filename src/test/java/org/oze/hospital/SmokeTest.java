package org.oze.hospital;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.oze.hospital.controllers.StaffController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SmokeTest {

	@Autowired StaffController staffController;
	
	@Test
	public void contextsLoad() {
		
		assertThat(staffController).isNot(null);
	}
	@Test
	public void contextsLoad2() {
		
		assertEquals(1, 1);
	}
}
