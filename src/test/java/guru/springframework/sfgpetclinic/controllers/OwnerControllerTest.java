package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.fauxspring.BindingResult;
import guru.springframework.sfgpetclinic.fauxspring.Model;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

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

    @Captor
    ArgumentCaptor<String> stringArgumentCaptor;

    @Mock
    Model model;

    @BeforeEach
    void beforeEach() {
        lenient().when(ownerService.findAllByLastNameLike(stringArgumentCaptor.capture()))
                .thenAnswer(invocationOnMock -> {

                    List<Owner> results = new ArrayList<>();

                    String name = invocationOnMock.getArgument(0);
                    if ("%Arora%".equalsIgnoreCase(name)) {
                        // return results with one element in it
                        results.add(new Owner(1l, "Jatin", "Arora"));
                        return results;
                    } else if ("%Aurora%".equalsIgnoreCase(name)) {
                        return results;
                    } else if ("%Miglani%".equalsIgnoreCase(name)) {
                        results.add(new Owner(1l, "Preeti", "Miglani"));
                        results.add(new Owner(2l, "Manish", "Miglani"));
                        results.add(new Owner(3l, "Rohan", "Miglani"));
                        return results;
                    }

                    throw new RuntimeException("Invalid Argument");
                });
    }

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

    @Test
    void processFindFormWildcardString() {

        Owner owner = new Owner(1l, "Jatin", "Arora");
        List<Owner> results = new ArrayList<>();
        //when(ownerService.findAllByLastNameLike(stringArgumentCaptor.capture()))
          //      .thenReturn(results);

        ownerController.processFindForm(owner, bindingResult, null);
        assertEquals("%Arora%", stringArgumentCaptor.getValue());
    }

    @Test
    void processFindFormWithOneResult() {

        Owner owner = new Owner(1l, "Jatin", "Arora");

        String result = ownerController.processFindForm(owner, bindingResult, model);
        assertEquals("redirect:/owners/1", result);
    }

    @Test
    void processFindFormWithNoResult() {

        Owner owner = new Owner(1l, "Jatin", "Aurora");

        String result = ownerController.processFindForm(owner, bindingResult, model);
        assertEquals("owners/findOwners", result);
    }

    @Test
    void processFindFormWithManyResult() {

        Owner owner = new Owner(2l, "Preeti", "Miglani");
        InOrder inorder = inOrder(ownerService, model);

        String result = ownerController.processFindForm(owner, bindingResult, model);
        assertEquals("owners/ownersList", result);

        // verify order in which mock is called
        inorder.verify(ownerService).findAllByLastNameLike(anyString());
        inorder.verify(model).addAttribute(anyString(), anyList());
    }
}