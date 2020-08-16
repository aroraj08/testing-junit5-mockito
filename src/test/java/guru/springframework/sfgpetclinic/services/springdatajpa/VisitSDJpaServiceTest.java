package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.repositories.VisitRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VisitSDJpaServiceTest {

    @InjectMocks
    VisitSDJpaService visitSDJpaService;

    @Mock
    VisitRepository visitRepository;

    @Test
    void findAll() {

        Set<Visit> visits = new HashSet<>();
        visits.add(new Visit(1l));
        visits.add(new Visit(2l));

        when(visitRepository.findAll()).thenReturn(visits);
        Set<Visit> visitsReturned = visitSDJpaService.findAll();

        verify(visitRepository, times(1)).findAll();
        assertEquals(2, visitsReturned.size());
    }

    @Test
    void findById() {

        Visit visit = new Visit(2l);

        when(visitRepository.findById(anyLong())).thenReturn(Optional.ofNullable(visit));

        Visit visitReturned = visitSDJpaService.findById(2l);
        verify(visitRepository, times(1)).findById(2l);

        assertEquals(visit.getId(), visitReturned.getId());

    }

    @Test
    void save() {

        Visit visit = new Visit(2l);
        when(visitRepository.save(any())).thenReturn(visit);

        Visit returnedVisit = visitSDJpaService.save(new Visit());
        assertEquals(visit.getId(), returnedVisit.getId());
    }

    @Test
    void delete() {
        visitSDJpaService.delete(any());
        verify(visitRepository, times(1)).delete(any());
    }

    @Test
    void deleteById() {
        visitSDJpaService.deleteById(1l);
        visitSDJpaService.deleteById(2l);
        verify(visitRepository, times(1)).deleteById(1l);
        verify(visitRepository, times(1)).deleteById(2l);
    }
}