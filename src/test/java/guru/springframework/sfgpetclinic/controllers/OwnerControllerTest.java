package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.fauxspring.BindingResult;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {
    final Long TEST_ID = 1L;
    final Long OTHER_TEST_ID = 5L;
    final String FORM_RESULT = "owners/createOrUpdateOwnerForm";
    final String REDIRECT_RESULT = "redirect:/owners/5";
    final String SEARCH_STRING1 = "%Buck%";

    @Mock
    OwnerService ownerService;

    @InjectMocks
    OwnerController ownerController;

    @Mock
    BindingResult bindingResult;

    @Captor
    ArgumentCaptor<String> stringArgumentCaptor;

    @BeforeEach
    void setUp() {
        // Unable to use this format because it's deprecated in java 21!
//        given(ownerService.findAllByLastNameLike(stringArgumentCaptor.capture()))
//            .willAnswer(invocation -> {
//                String name = invocation.getArgument(0);
//                if(name.equals(SEARCH_STRING1)) {
//                    return List.of(new Owner(TEST_ID, "Joe", "Buck")).toArray();
//                }
//                throw new RuntimeException("Invalid Argument Passed to Captor");
//            });
    }

    @Test
    void testProcessCreationFormHasErrors() {
        // given
        Owner owner = new Owner(TEST_ID, "Jack", "Buck");
        given(bindingResult.hasErrors()).willReturn(true);

        // when
        String viewName = ownerController.processCreationForm(owner, bindingResult);

        // then
        assertThat(viewName).isEqualToIgnoringCase(FORM_RESULT);
    }

    @Test
    void testProcessCreationFormHasNoErrors() {
        // given
        Owner owner = new Owner(OTHER_TEST_ID, "Al", "Bundy");
        given(bindingResult.hasErrors()).willReturn(false);
        given(ownerService.save(any())).willReturn(owner);

        // when
        String viewName = ownerController.processCreationForm(owner, bindingResult);

        // then
        assertThat(viewName).isEqualToIgnoringCase(REDIRECT_RESULT);
    }

    @Test
    void testProcessFindFormUsesCorrectWildcardSyntaxAndClassCaptor() {
        // given
        Owner owner = new Owner(TEST_ID, "Joe", "Buck");
        given(ownerService.findAllByLastNameLike(stringArgumentCaptor.capture()))
            .willReturn(List.of(new Owner(TEST_ID, "Joe", "Buck")));

        // when
        String viewName = ownerController.processFindForm(owner, bindingResult, null);

        // then
        assertThat(SEARCH_STRING1).isEqualToIgnoringCase(stringArgumentCaptor.getValue());
    }


}