package com.example.transfer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.http.MediaType;

@SpringBootTest(classes = com.example.DemoApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class TransferApiIT {
	@Autowired MockMvc mvc;
	
	@Test @DisplayName("POST /api/transfers 201성공 검증")
	void create_valid_201() throws Exception {
	    mvc.perform(post("/api/transfers")
	        .contentType(MediaType.APPLICATION_JSON)
	        .content("""
	          {"fromId":"u1","toId":"u2","amount":500}
	        """))
	      .andExpect(status().isCreated())
	      .andExpect(jsonPath("$.transferId").exists());
	  }
	
	@Test @DisplayName("POST /api/transfers 400 실패")
	void create_validation_400() throws Exception {
	    mvc.perform(post("/api/transfers")
	        .contentType(MediaType.APPLICATION_JSON)
	        .content("""
	          {"fromId":"","toId":"u2","amount":0}
	        """))
	      .andExpect(status().isBadRequest());
	  }
	
	
	@Test @DisplayName("GET /api/transfers?userId=u1 : 200")
	  void list_ok_200() throws Exception {
	    mvc.perform(get("/api/transfers").param("userId","u1"))
	      .andExpect(status().isOk());
	  }

}
