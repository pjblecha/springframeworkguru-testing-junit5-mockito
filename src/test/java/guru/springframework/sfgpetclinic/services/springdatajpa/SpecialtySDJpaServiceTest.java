package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Specialty;
import guru.springframework.sfgpetclinic.repositories.SpecialtyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpecialtySDJpaServiceTest {

    @Mock
    SpecialtyRepository specialtyRepository;

    @InjectMocks
    SpecialtySDJpaService specialtySDJpaService;

    Long testId = 1L;
    Long failId = 5L;

    @Test
    void testDeleteById() {
        specialtySDJpaService.deleteById(testId);
        specialtySDJpaService.deleteById(testId);
        verify(specialtyRepository, times(2)).deleteById(anyLong());
    }

    @Test
    void testDeleteByIdAtLeastOnce() {
        specialtySDJpaService.deleteById(testId);
        specialtySDJpaService.deleteById(testId);
        verify(specialtyRepository, atLeastOnce()).deleteById(testId);
    }

    @Test
    void testDeleteByIdAtMost() {
        specialtySDJpaService.deleteById(testId);
        specialtySDJpaService.deleteById(testId);
        specialtySDJpaService.deleteById(testId);
        verify(specialtyRepository, atMost(5)).deleteById(testId);
    }

    @Test
    void testDeleteByIdAtMostOnce() {
        specialtySDJpaService.deleteById(testId);
        verify(specialtyRepository, atMostOnce()).deleteById(testId);
    }

    @Test
    void testDeleteByIdNever() {
        specialtySDJpaService.deleteById(testId);
        specialtySDJpaService.deleteById(testId);
        specialtySDJpaService.deleteById(testId);
        verify(specialtyRepository, atMost(5)).deleteById(testId);

        verify(specialtyRepository, never()).deleteById(failId);
    }

    @Test
    void testDelete() {
        // given
        Specialty specialty = new Specialty();

        // when
        specialtySDJpaService.delete(specialty);

        // then
        then(specialtyRepository).should().delete(any(Specialty.class));
    }

    @Test
    void testFindById() {
        Specialty specialty = new Specialty();
        when(specialtyRepository.findById(testId)).thenReturn(Optional.of(specialty));
        Specialty returned = specialtySDJpaService.findById(testId);
        assertThat(returned).isNotNull();
        verify(specialtyRepository).findById(testId);
    }

    @Test
    void testDeleteObject() {
        Specialty specialty = new Specialty();
        specialtySDJpaService.delete(specialty);
        verify(specialtyRepository).delete(any(Specialty.class));
    }

    @Test
    void testBDDFindById() {
        // given
        Specialty specialty = new Specialty();
        given(specialtyRepository.findById(testId)).willReturn(Optional.of(specialty));
        // when
        Specialty result = specialtySDJpaService.findById(testId);

        // then
        assertThat(result).isNotNull();
        then(specialtyRepository).should().findById(anyLong());
        then(specialtyRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void testBDDDeleteById() {
        // given - is null

        // when
        specialtySDJpaService.deleteById(testId);
        specialtySDJpaService.deleteById(testId);

        // then
        then(specialtyRepository).should(times(2)).deleteById(anyLong());
    }

    @Test
    void testBDDDeleteByIdAtLeastOnce() {
        // given -- is null

        // when
        specialtySDJpaService.deleteById(testId);
        specialtySDJpaService.deleteById(testId);

        // then
        then(specialtyRepository).should(atLeastOnce()).deleteById(anyLong());
    }

    @Test
    void testBDDDeleteByIdAtMost() {
        // given --

        // when
        specialtySDJpaService.deleteById(testId);
        specialtySDJpaService.deleteById(testId);
        specialtySDJpaService.deleteById(testId);

        // then
        then(specialtyRepository).should(atMost(5)).deleteById(anyLong());
    }

    @Test
    void testBDDDeleteByIdNever() {
        // given ---

        // when
        specialtySDJpaService.deleteById(testId);
        specialtySDJpaService.deleteById(testId);
        specialtySDJpaService.deleteById(testId);

        // then
        then(specialtyRepository).should(never()).deleteById(failId);
    }

    @Test
    void testBDDDeleteObject() {
        // given
        Specialty specialty = new Specialty();

        // when
        specialtySDJpaService.delete(specialty);

        // then
        then(specialtyRepository).should().delete(any(Specialty.class));
    }

}