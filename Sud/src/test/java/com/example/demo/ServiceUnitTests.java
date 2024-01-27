package com.example.demo;

import com.example.demo.model.Sud;
import com.example.demo.repository.SudRepository;
import com.example.demo.service.SudService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class ServiceUnitTests {

    @Mock
    SudRepository sudRepository;

    @InjectMocks
    SudService sudService;

    @Test
    public void testGetAllSudovi() {
        var sud1 = new Sud();
        sud1.setId(123);
        sud1.setAdresa("Beogradska 123");
        sud1.setNaziv("Visi apelacioni sud");
        var sud2 = new Sud();
        sud2.setId(233);
        sud2.setAdresa("Carigradska 255");
        sud2.setNaziv("Vrhovni sud");

        when(sudRepository.findAll()).thenReturn(Arrays.asList(sud1, sud2));

        var sudovi = sudService.getAllSudovi();

        assertEquals(2, sudovi.size());
        assertEquals("Visi apelacioni sud",sudovi.get(0).getNaziv());
        assertEquals("Vrhovni sud", sudovi.get(1).getNaziv());
    }

    @Test
    public void testGetSudById() {
        var sud1 = new Sud();
        sud1.setId(123);
        sud1.setAdresa("Beogradska 123");
        sud1.setNaziv("Visi apelacioni sud");

        when(sudRepository.findById(any(Integer.class))).thenReturn(Optional.of(sud1));

        var sud = sudService.getSudById(5);

        assertEquals("Visi apelacioni sud", sud.get().getNaziv());
    }

    @Test
    public void testCreateSud() {
        var sud1 = new Sud();
        sud1.setId(123);
        sud1.setAdresa("Beogradska 123");
        sud1.setNaziv("Visi apelacioni sud");

        sudService.addSud(sud1);
        verify(sudRepository, times(1)).save(sud1);

        var sudArgumentCaption = ArgumentCaptor.forClass(Sud.class);
        verify(sudRepository).save(sudArgumentCaption.capture());
        var createdSud = sudArgumentCaption.getValue();

        assertEquals("Visi apelacioni sud", createdSud.getNaziv());
    }

    @Test
    public void testUpdateSud() {
        var sud1 = new Sud();
        sud1.setId(123);
        sud1.setAdresa("Beogradska 123");
        sud1.setNaziv("Visi apelacioni sud");

        when(sudRepository.findById(any(Integer.class))).thenReturn(Optional.of(sud1));
        sudService.updateSud(sud1.getId(), sud1);
        verify(sudRepository, times(1)).save(sud1);

        var sudArgumentCaption = ArgumentCaptor.forClass(Sud.class);
        verify(sudRepository).save(sudArgumentCaption.capture());
        var updateSud = sudArgumentCaption.getValue();

        assertEquals("Visi apelacioni sud", updateSud.getNaziv());
    }

    @Test
    public void testDeleteSud() {
        var sud1 = new Sud();
        sud1.setId(123);
        sud1.setAdresa("Beogradska 123");
        sud1.setNaziv("Visi apelacioni sud");

        sudService.deleteById(sud1.getId());
        verify(sudRepository, times(1)).deleteById(sud1.getId());

        var sudArgumentCaption = ArgumentCaptor.forClass(Integer.class);
        verify(sudRepository).deleteById(sudArgumentCaption.capture());
        var deleteSudId = sudArgumentCaption.getValue();

        assertEquals(123, deleteSudId);
    }

}
