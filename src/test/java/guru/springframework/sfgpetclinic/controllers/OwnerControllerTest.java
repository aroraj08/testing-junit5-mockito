package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.fauxspring.BindingResult;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    @InjectMocks
    OwnerController ownerController;

    @Mock
    OwnerService ownerService;

    @Mock
    BindingResult bindingResult;

    @Test
    void processCreationFormBindingError() {

        final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "owners/createOrUpdateOwnerForm";
        Owner owner = new Owner(1l, "Jatin", "Arora");

        when(bindingResult.hasErrors()).thenReturn(true);

        String response = ownerController.processCreationForm(owner, bindingResult);

        assertEquals(VIEWS_OWNER_CREATE_OR_UPDATE_FORM, response);
        verify(ownerService, never()).save(any());
    }

    @Test
    void processCreationFormNoError() {

        when(bindingResult.hasErrors()).thenReturn(false);

        Owner owner = new Owner(null, "Preeti", "Arora");
        Owner savedOwner = new Owner(5l,"Preeti", "Arora");
        when(ownerService.save(owner)).thenReturn(savedOwner);

        String returnedView = ownerController.processCreationForm(owner, bindingResult);
        assertEquals("redirect:/owners/5", returnedView);
    }

}