package com.example.demo;

import com.example.demo.model.Sud;
import com.example.demo.repository.SudRepository;
import com.example.demo.service.SudService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApiIntegrationTests {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private SudRepository sudRepository;

    @Autowired
    private SudService sudService;

    @Test
    @Sql(statements = "INSERT INTO sud (id, naziv, adresa) VALUES (881, 'Visi apelacioni', 'Pariska 12')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM sud", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testAllSudovi() {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        var entity = new HttpEntity<>(null, headers);
        ResponseEntity<List<Sud>> response = restTemplate.exchange(
                "http://localhost:" + port + "/sud",
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<Sud>>(){});
        var sudovi = response.getBody();
        assertNotEquals(sudovi, null);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(sudovi.size(), sudService.getAllSudovi().size());
        assertEquals(sudovi.size(), sudRepository.findAll().size());
    }

    @Test
    @Sql(statements = "INSERT INTO sud (id, naziv, adresa) VALUES (881, 'Visi apelacioni', 'Pariska 12')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM sud", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testGetSudById() {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        var entity = new HttpEntity<>(null, headers);
        ResponseEntity<Sud> response = restTemplate.exchange(
                "http://localhost:" + port + "/sud/881",
                HttpMethod.GET,
                entity,
                Sud.class);
        var sud = response.getBody();
        assertNotEquals(sud, null);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(sud.getNaziv(), sudService.getSudById(881).get().getNaziv());
        assertEquals(sud.getAdresa(), sudRepository.findById(881).get().getAdresa());
    }

    @Test
    @Sql(statements = "INSERT INTO sud (id, naziv, adresa) VALUES (881, 'Visi apelacioni', 'Pariska 12')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM sud", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testGetSudByIdFailed() {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        var entity = new HttpEntity<>(null, headers);
        ResponseEntity<?> response = restTemplate.exchange(
                "http://localhost:" + port + "/sud/882",
                HttpMethod.GET,
                entity, byte[].class);
        assertTrue(response.getStatusCode().is4xxClientError());
        assertTrue(sudService.getSudById(882).isEmpty());
        assertTrue(sudRepository.findById(882).isEmpty());
    }

    @Test
    @Sql(statements = "INSERT INTO sud (id, naziv, adresa) VALUES (881, 'Visi apelacioni', 'Pariska 12')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM sud", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testCreateSud() {
        var sud2 = new Sud();
        sud2.setAdresa("Carigradska 255");
        sud2.setNaziv("Vrhovni sud");

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        var entity = new HttpEntity<>(sud2, headers);
        ResponseEntity<Sud> response = restTemplate.exchange(
                "http://localhost:" + port + "/sud",
                HttpMethod.POST,
                entity,
                Sud.class);
        var sud = response.getBody();
        assertNotEquals(sud, null);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(sud.getNaziv(), sudService.getSudById(sud.getId()).get().getNaziv());
        assertEquals(sud.getAdresa(), sudRepository.findById(sud.getId()).get().getAdresa());
        assertEquals(2, sudService.getAllSudovi().size());
        assertEquals(2, sudRepository.findAll().size());
    }

    @Test
    @Sql(statements = "INSERT INTO sud (id, naziv, adresa) VALUES (881, 'Visi apelacioni', 'Pariska 12')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM sud", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testUpdateSud() {
        var sud2 = new Sud();
        sud2.setAdresa("Carigradska 255");
        sud2.setNaziv("Vrhovni sud");

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        var entity = new HttpEntity<>(sud2, headers);
        ResponseEntity<Sud> response = restTemplate.exchange(
                "http://localhost:" + port + "/sud/881",
                HttpMethod.PUT,
                entity,
                Sud.class);
        var sud = response.getBody();
        assertNotEquals(sud, null);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(sud.getNaziv(), sudService.getSudById(881).get().getNaziv());
        assertEquals(sud.getAdresa(), sudRepository.findById(881).get().getAdresa());
        assertEquals(1, sudService.getAllSudovi().size());
        assertEquals(1, sudRepository.findAll().size());
    }

    @Test
    @Sql(statements = "INSERT INTO sud (id, naziv, adresa) VALUES (881, 'Visi apelacioni', 'Pariska 12')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM sud", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testUpdateSudFailed() {
        var sud2 = new Sud();
        sud2.setAdresa("Carigradska 255");
        sud2.setNaziv("Vrhovni sud");

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        var entity = new HttpEntity<>(sud2, headers);
        ResponseEntity<?> response = restTemplate.exchange(
                "http://localhost:" + port + "/sud/882",
                HttpMethod.PUT,
                entity,
                byte[].class);
        assertTrue(response.getStatusCode().is4xxClientError());
    }

    @Test
    @Sql(statements = "INSERT INTO sud (id, naziv, adresa) VALUES (881, 'Visi apelacioni', 'Pariska 12')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM sud", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testDeleteSudFailed() {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        var entity = new HttpEntity<>(null, headers);
        ResponseEntity<?> response = restTemplate.exchange(
                "http://localhost:" + port + "/sud/882",
                HttpMethod.DELETE,
                entity,
                byte[].class);
        assertTrue(response.getStatusCode().is4xxClientError());
    }

    @Test
    @Sql(statements = "INSERT INTO sud (id, naziv, adresa) VALUES (881, 'Visi apelacioni', 'Pariska 12')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM sud", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testDeleteSudSuccess() {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        var entity = new HttpEntity<>(null, headers);
        ResponseEntity<?> response = restTemplate.exchange(
                "http://localhost:" + port + "/sud/881",
                HttpMethod.DELETE,
                entity,
                byte[].class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(0, sudRepository.findAll().size());
        assertEquals(0, sudRepository.findAll().size());
    }
}
