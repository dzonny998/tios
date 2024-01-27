package com.example.demo;

import com.example.demo.model.Sud;
import com.example.demo.repository.SudRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@DataJpaTest
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class RepositoryUnitTests {

    @Autowired
    SudRepository sudRepository;

    @BeforeEach
    public void setUp() {
        var sud1 = new Sud();
        sud1.setAdresa("Beogradska 123");
        sud1.setNaziv("Visi apelacioni sud");
        var sud2 = new Sud();
        sud2.setAdresa("Carigradska 255");
        sud2.setNaziv("Vrhovni sud");

        sudRepository.save(sud1);
        sudRepository.save(sud2);
    }

    @AfterEach
    public void destroy() {
        sudRepository.deleteAll();
    }

    @Test
    public void testGetAllSud() {
        var sudovi = sudRepository.findAll();
        Assertions.assertThat(sudovi.size()).isEqualTo(2);
        Assertions.assertThat(sudovi.get(0).getNaziv()).isEqualTo("Visi apelacioni sud");
        Assertions.assertThat(sudovi.get(1).getNaziv()).isEqualTo("Vrhovni sud");
    }

    @Test
    public void testGetInvalidSud() {
        var sud = sudRepository.findById(999);
        Assertions.assertThat(sud.isEmpty()).isTrue();
    }

    @Test
    public void testGetValidSud() {
        var sudovi = sudRepository.findAll();
        var existingId = sudovi.get(0).getId();

        var sud = sudRepository.findById(existingId);
        Assertions.assertThat(sud.isEmpty()).isFalse();
        Assertions.assertThat(sud.get().getNaziv()).isEqualTo("Visi apelacioni sud");
    }

    @Test
    public void testCreateSud() {
        var sud = new Sud();
        sud.setAdresa("Zlotska 15");
        sud.setNaziv("Osnovni sud");
        var newSud = sudRepository.save(sud);

        var sudovi = sudRepository.findAll();
        Assertions.assertThat(sudovi.size()).isEqualTo(3);

        Assertions.assertThat(newSud).isNotNull();
        Assertions.assertThat(newSud.getId()).isNotZero();
        Assertions.assertThat(newSud.getId()).isNotNegative();
        Assertions.assertThat(newSud.getNaziv()).isEqualTo("Osnovni sud");
    }

    @Test
    public void testUpdateSud() {
        var sudovi = sudRepository.findAll();
        var existingId = sudovi.get(0).getId();

        var sud = sudRepository.findById(existingId).get();
        sud.setNaziv("Test naziv");
        sud.setAdresa("Test adresa");
        var newSud = sudRepository.save(sud);

        sudovi = sudRepository.findAll();
        Assertions.assertThat(sudovi.size()).isEqualTo(2);
        Assertions.assertThat(newSud).isNotNull();
        Assertions.assertThat(newSud.getId()).isEqualTo(existingId);
        Assertions.assertThat(newSud.getNaziv()).isEqualTo("Test naziv");
        Assertions.assertThat(newSud.getAdresa()).isEqualTo("Test adresa");
    }

    @Test
    public void testDeleteOrder() {
        var sudovi = sudRepository.findAll();
        var existingId = sudovi.get(0).getId();

        var sud = sudRepository.findById(existingId).get();

        sudRepository.delete(sud);

        sudovi = sudRepository.findAll();
        Assertions.assertThat(sudovi.size()).isEqualTo(1);
    }

}
