package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.fauxspring.Model;
import guru.springframework.sfgpetclinic.model.PersonRepeatedTests;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.map.PetMapService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VisitControllerTest {

    @InjectMocks
    VisitController visitController;

    //@Mock
    //PetService petService;

    @Spy
    PetMapService petMapService;

    @Test
    void loadPetWithVisit() {

        Pet pet = new Pet(2l);
        Pet pet2 = new Pet(3l);

        petMapService.save(pet);
        petMapService.save(pet2);

        when(petMapService.findById(anyLong())).thenCallRealMethod();

        Visit visit = visitController.loadPetWithVisit(2l, new HashMap<String, Object>());

        assertEquals(Long.valueOf(2l), visit.getPet().getId());
    }
}