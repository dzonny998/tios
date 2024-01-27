package com.example.demo;

import com.example.demo.ctrls.SudController;
import com.example.demo.model.Sud;
import com.example.demo.service.SudService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(SudController.class)
public class ControllerUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SudService sudService;

    @Test
    public void testGetAllSudovi() throws Exception {
        var sud1 = new Sud();
        sud1.setAdresa("Beogradska 123");
        sud1.setNaziv("Visi apelacioni sud");
        var sud2 = new Sud();
        sud2.setAdresa("Carigradska 255");
        sud2.setNaziv("Vrhovni sud");
        List<Sud> sudovi = new ArrayList<>();
        sudovi.add(sud1);
        sudovi.add(sud2);
        when(sudService.getAllSudovi()).thenReturn(sudovi);
        var result = mockMvc.perform(get("/sud"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        var mapper = new ObjectMapper();
        var response = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Sud>>() {});
        Assertions.assertThat(response.size()).isEqualTo(2);
    }

    @Test
    public void testGetSudByIdSuccess() throws Exception {
        var sud1 = new Sud();
        sud1.setId(811);
        sud1.setAdresa("Beogradska 123");
        sud1.setNaziv("Visi apelacioni sud");
        when(sudService.getSudById(any(Integer.class))).thenReturn(Optional.of(sud1));
        when(sudService.existsById(any(Integer.class))).thenReturn(true);
        var result = mockMvc.perform(get("/sud/811"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        var mapper = new ObjectMapper();
        var response = mapper.readValue(result.getResponse().getContentAsString(), Sud.class);
        Assertions.assertThat(response.getId()).isEqualTo(811);
    }

    @Test
    public void testGetSudByIdFailed() throws Exception {
        when(sudService.existsById(any(Integer.class))).thenReturn(false);
        mockMvc.perform(get("/sud/811"))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    @Test
    public void testCreateSud() throws Exception {
        var sud1 = new Sud();
        sud1.setId(811);
        sud1.setAdresa("Beogradska 123");
        sud1.setNaziv("Visi apelacioni sud");

        var objectMapper = new ObjectMapper();
        when(sudService.addSud(any(Sud.class))).thenReturn(sud1);

        var result = mockMvc.perform(post("/sud")
                        .content(objectMapper.writeValueAsString(sud1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();


        var mapper = new ObjectMapper();
        var response = mapper.readValue(result.getResponse().getContentAsString(), Sud.class);
        Assertions.assertThat(response.getId()).isEqualTo(811);
    }

    @Test
    public void testUpdateSudNotFound() throws Exception {
        var objectMapper = new ObjectMapper();
        var sud1 = new Sud();
        sud1.setId(811);
        sud1.setAdresa("Beogradska 123");
        sud1.setNaziv("Visi apelacioni sud");

        when(sudService.updateSud(any(Integer.class), any(Sud.class))).thenReturn(null);

        mockMvc.perform(put("/sud/811")
                        .content(objectMapper.writeValueAsString(sud1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateSudSuccess() throws Exception {
        var sud1 = new Sud();
        sud1.setId(811);
        sud1.setAdresa("Beogradska 123");
        sud1.setNaziv("Visi apelacioni sud");

        var objectMapper = new ObjectMapper();
        when(sudService.updateSud(any(Integer.class), any(Sud.class))).thenReturn(sud1);

        var result = mockMvc.perform(put("/sud/811")
                        .content(objectMapper.writeValueAsString(sud1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();


        var mapper = new ObjectMapper();
        var response = mapper.readValue(result.getResponse().getContentAsString(), Sud.class);
        Assertions.assertThat(response.getNaziv()).isEqualTo("Visi apelacioni sud");
    }

    @Test
    public void testDeleteSudFails() throws Exception {
        mockMvc.perform(delete("/sud/811"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteSudSuccess() throws Exception {
        when(sudService.existsById(any(Integer.class))).thenReturn(true);

        mockMvc.perform(delete("/sud/811"))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
