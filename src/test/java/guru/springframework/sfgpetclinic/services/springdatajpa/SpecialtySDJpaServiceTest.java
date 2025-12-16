package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Specialty;
import guru.springframework.sfgpetclinic.repositories.SpecialtyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.*;
import static org.mockito.quality.Strictness.LENIENT;

@ExtendWith(MockitoExtension.class)
class SpecialtySDJpaServiceTest {

    @Mock(lenient = true)
    SpecialtyRepository specialtyRepository;

    @InjectMocks
    SpecialtySDJpaService specialtySDJpaService;

    final Long TEST_ID = 1L;
    final Long FAIL_ID = 5L;

    @Test
    void testDeleteById() {
        specialtySDJpaService.deleteById(TEST_ID);
        specialtySDJpaService.deleteById(TEST_ID);
        verify(specialtyRepository, times(2)).deleteById(anyLong());
    }

    @Test
    void testDeleteByIdAtLeastOnce() {
        specialtySDJpaService.deleteById(TEST_ID);
        specialtySDJpaService.deleteById(TEST_ID);
        verify(specialtyRepository, atLeastOnce()).deleteById(TEST_ID);
    }

    @Test
    void testDeleteByIdAtMost() {
        specialtySDJpaService.deleteById(TEST_ID);
        specialtySDJpaService.deleteById(TEST_ID);
        specialtySDJpaService.deleteById(TEST_ID);
        verify(specialtyRepository, atMost(5)).deleteById(TEST_ID);
    }

    @Test
    void testDeleteByIdAtMostOnce() {
        specialtySDJpaService.deleteById(TEST_ID);
        verify(specialtyRepository, atMostOnce()).deleteById(TEST_ID);
    }

    @Test
    void testDeleteByIdNever() {
        specialtySDJpaService.deleteById(TEST_ID);
        specialtySDJpaService.deleteById(TEST_ID);
        specialtySDJpaService.deleteById(TEST_ID);
        verify(specialtyRepository, atMost(5)).deleteById(TEST_ID);

        verify(specialtyRepository, never()).deleteById(FAIL_ID);
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
        when(specialtyRepository.findById(TEST_ID)).thenReturn(Optional.of(specialty));
        Specialty returned = specialtySDJpaService.findById(TEST_ID);
        assertThat(returned).isNotNull();
        verify(specialtyRepository).findById(TEST_ID);
    }

    @Test
    void testDeleteObject() {
        Specialty specialty = new Specialty();
        specialtySDJpaService.delete(specialty);
        verify(specialtyRepository).delete(any(Specialty.class));
    }

    ///
    /// Behavior-Driven Development
    ///

    @Test
    void testBDDFindById() {
        // given
        Specialty specialty = new Specialty();
        given(specialtyRepository.findById(TEST_ID)).willReturn(Optional.of(specialty));
        // when
        Specialty result = specialtySDJpaService.findById(TEST_ID);

        // then
        assertThat(result).isNotNull();
        then(specialtyRepository).should().findById(anyLong());
        then(specialtyRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void testBDDDeleteById() {
        // given - is null

        // when
        specialtySDJpaService.deleteById(TEST_ID);
        specialtySDJpaService.deleteById(TEST_ID);

        // then
        then(specialtyRepository).should(times(2)).deleteById(anyLong());
    }

    @Test
    void testBDDDeleteByIdAtLeastOnce() {
        // given -- is null

        // when
        specialtySDJpaService.deleteById(TEST_ID);
        specialtySDJpaService.deleteById(TEST_ID);

        // then
        then(specialtyRepository).should(atLeastOnce()).deleteById(anyLong());
    }

    @Test
    void testBDDDeleteByIdAtMost() {
        // given --

        // when
        specialtySDJpaService.deleteById(TEST_ID);
        specialtySDJpaService.deleteById(TEST_ID);
        specialtySDJpaService.deleteById(TEST_ID);

        // then
        then(specialtyRepository).should(atMost(5)).deleteById(anyLong());
    }

    @Test
    void testBDDDeleteByIdNever() {
        // given ---

        // when
        specialtySDJpaService.deleteById(TEST_ID);
        specialtySDJpaService.deleteById(TEST_ID);
        specialtySDJpaService.deleteById(TEST_ID);

        // then
        then(specialtyRepository).should(never()).deleteById(FAIL_ID);
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

    ///
    /// THROWS
    ///

    @Test
    void testThrowSimpleException() {
        doThrow(new RuntimeException("See? Simple.")).when(specialtyRepository).delete(any());
        assertThrows(RuntimeException.class, () -> specialtyRepository.delete(new Specialty()));
        verify(specialtyRepository).delete(any());
    }

    @Test
    void testBDDThrowSimpleException() {
        given(specialtyRepository.findById(TEST_ID)).willThrow(new RuntimeException("OUCH!"));
        assertThrows(RuntimeException.class, () -> specialtySDJpaService.findById(TEST_ID));
        then(specialtyRepository).should().findById(TEST_ID);
    }

    @Test
    void testBDDSlightlyMoreComplicatedThrowException() {
        willThrow(new RuntimeException("OUCH")).given(specialtyRepository).delete(any());
        assertThrows(RuntimeException.class, () -> specialtyRepository.delete(new Specialty()));
        then(specialtyRepository).should().delete(any());
    }

    /// LAMBDA MATCHING
    ///

    @Test
    void testSaveLambda() {
        // given
        final String MATCH_ME = "MATCH_ME";
        Specialty specialty = new Specialty();
        specialty.setDescription(MATCH_ME);

        Specialty saved = new Specialty();
        saved.setId(TEST_ID);

        given(specialtyRepository.save(argThat(arg -> arg.getDescription().equals(MATCH_ME)))).willReturn(saved);

        // when
        Specialty result = specialtySDJpaService.save(specialty);

        // then
        assertThat(result.getId()).isEqualTo(TEST_ID);
    }

    @MockitoSettings(strictness = LENIENT)
    @Test
    void testSaveLambdaFail() {
        // given
        final String MATCH_ME = "MATCH_ME";
        Specialty specialty = new Specialty();
        specialty.setDescription("MITCH_MY");

        Specialty saved = new Specialty();
        saved.setId(TEST_ID);

        given(specialtyRepository.save(argThat(arg -> arg.getDescription().equals(MATCH_ME)))).willReturn(saved);

        // when
        Specialty result = specialtySDJpaService.save(specialty);

        // then
        assertNull(result);
    }


}