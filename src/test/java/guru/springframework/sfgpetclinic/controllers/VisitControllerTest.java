package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.VisitService;
import guru.springframework.sfgpetclinic.services.map.PetMapService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class VisitControllerTest {

    public static final long TEST_ID = 1L;
    public static final long NEW_TEST_ID = 3L;


    @Mock
    VisitService visitService;

    @Spy  //@Mock
    //PetService petService;
    PetMapService petService;

    @InjectMocks
    VisitController visitController;

    @Test
    void testLoadPetWithVisit() {
        // given
        Map<String, Object> model = new HashMap<>();
        Pet pet = new Pet(TEST_ID);
        Pet nextPet = new Pet(NEW_TEST_ID);

        given(petService.findById(anyLong()))
//            .willReturn(pet);
            .willCallRealMethod();

        petService.save(pet);
        petService.save(nextPet);

        // when
        Visit visit = visitController.loadPetWithVisit(TEST_ID, model);
        Visit otherVisit = visitController.loadPetWithVisit(NEW_TEST_ID, model);

        // then
        assertThat(visit).isNotNull();
        assertThat(visit.getPet()).isNotNull();
        assertThat(visit.getPet().getId()).isEqualTo(TEST_ID);

        assertThat(otherVisit.getPet().getId()).isEqualTo(NEW_TEST_ID);

        verify(petService, times(2)).findById(anyLong());
    }

    @Test
    void testLoadPetWithVisitWithStubbing() {
        // given
        Map<String, Object> model = new HashMap<>();
        Pet pet = new Pet(TEST_ID);
        Pet nextPet = new Pet(NEW_TEST_ID);

        given(petService.findById(anyLong()))
//            .willReturn(pet);
            .willCallRealMethod(); // means we are hitting the actual method!
//            .willReturn(nextPet);

        petService.save(pet);
        petService.save(nextPet);

        // when
        Visit visit = visitController.loadPetWithVisit(TEST_ID, model);
        Visit otherVisit = visitController.loadPetWithVisit(NEW_TEST_ID, model);

        // then
        assertThat(visit).isNotNull();
        assertThat(visit.getPet()).isNotNull();
        assertThat(visit.getPet().getId()).isEqualTo(TEST_ID);

        assertThat(otherVisit.getPet().getId()).isEqualTo(NEW_TEST_ID);

        verify(petService, times(2)).findById(anyLong());
    }


}