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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpecialtySDJpaServiceTest {

    @Mock
    SpecialtyRepository specialtyRepository;

    @InjectMocks
    SpecialtySDJpaService specialtySDJpaService;

    Long testId = 1L;

    @Test
    void testDeleteById() {
        specialtySDJpaService.deleteById(1L);
        specialtySDJpaService.deleteById(1L);
        verify(specialtyRepository, times(2)).deleteById(1L);
    }

    @Test
    void testDeleteByIdAtLeastOnce() {
        specialtySDJpaService.deleteById(1L);
        specialtySDJpaService.deleteById(1L);
        verify(specialtyRepository, atLeastOnce()).deleteById(1L);
    }

    @Test
    void testDeleteByIdAtMost() {
        specialtySDJpaService.deleteById(1L);
        specialtySDJpaService.deleteById(1L);
        specialtySDJpaService.deleteById(1L);
        verify(specialtyRepository, atMost(5)).deleteById(1L);
    }

    @Test
    void testDeleteByIdAtMostOnce() {
        specialtySDJpaService.deleteById(1L);
        verify(specialtyRepository, atMostOnce()).deleteById(1L);
    }

    @Test
    void testDeleteByIdNever() {
        specialtySDJpaService.deleteById(1L);
        specialtySDJpaService.deleteById(1L);
        specialtySDJpaService.deleteById(1L);
        verify(specialtyRepository, atMost(5)).deleteById(1L);

        verify(specialtyRepository, never()).deleteById(5L);
    }

    @Test
    void testDelete() {
        specialtySDJpaService.delete(new Specialty());
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
    }
}