package com.sherold.ninjagold;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(MainController.class)
public class MainControllerTests{
	
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void testIndexRuteBezSesije() throws Exception {
		mockMvc.perform(get("/"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("ninja"))
			.andExpect(view().name("index.jsp"));
	}
	
	@Test
	public void testIndexRuteSaSesijom() throws Exception {
	    mockMvc.perform(get("/")
	           .sessionAttr("ninja", new Ninja()))
	           .andExpect(status().isOk())
	           .andExpect(model().attributeExists("ninja"))
	           .andExpect(view().name("index.jsp"));
	}
	
	@Test
	public void testIndexRouteDebt() throws Exception {
	    Ninja ninjaWithDebt = new Ninja();
	    ninjaWithDebt.setGold(-200); // ili neki drugi negativan iznos

	    mockMvc.perform(get("/")
	           .sessionAttr("ninja", ninjaWithDebt))
	           .andExpect(status().isOk())
	           .andExpect(view().name("prison.jsp"));
	}
	
	@Test
	public void testProcessRoute() throws Exception {
	    mockMvc.perform(post("/process/someEvent")
	           .sessionAttr("ninja", new Ninja()))
	           .andExpect(status().is3xxRedirection())
	           .andExpect(redirectedUrl("/"));

	    // Proveri da li je događaj obradjen i da li se zlato ažuriralo kako treba
	}
	
	@Test
	public void testResetRoute() throws Exception {
	    mockMvc.perform(post("/reset")
	           .sessionAttr("ninja", new Ninja()))
	           .andExpect(status().is3xxRedirection())
	           .andExpect(redirectedUrl("/"));

	    // Proveri da li je sesija resetovana
	}
	
	
	
}