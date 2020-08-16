package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.repositories.SpecialtyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpecialitySDJpaServiceTest {

    @InjectMocks
    SpecialitySDJpaService specialitySDJpaService;

    @Mock
    SpecialtyRepository specialtyRepository;

    @Test
    void deleteById() {
        specialitySDJpaService.deleteById(5l);
        specialitySDJpaService.deleteById(5l);

        //verify(specialtyRepository).deleteById(5l);
        verify(specialtyRepository, times(2)).deleteById(5l);
        verify(specialtyRepository, atMost(2)).deleteById(5l);
        verify(specialtyRepository, atLeast(1)).deleteById(5l);
        verify(specialtyRepository, never()).deleteById(2l);
    }

    @Test
    void delete() {
        specialitySDJpaService.delete(new Speciality());
    }

    @Test
    void findByIdTest() {

        when(specialtyRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(new Speciality("pet")));

        Speciality speciality = specialitySDJpaService.findById(1l);

        assertNotNull(speciality);
        assertEquals("pet", speciality.getDescription());
    }

    @Test
    void findByIdNull() {
        when(specialtyRepository.findById(1l)).thenReturn(Optional.ofNullable(null));
        Speciality speciality = specialitySDJpaService.findById(1l);

        assertNull(speciality);
    }

}