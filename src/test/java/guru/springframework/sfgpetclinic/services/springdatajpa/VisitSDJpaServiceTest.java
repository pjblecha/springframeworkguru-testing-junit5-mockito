package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.repositories.VisitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VisitSDJpaServiceTest {

    @Mock
    VisitRepository mockVisitRepository;
    @InjectMocks
    VisitSDJpaService mockVisitSDJpaService;
    Long testId = 1L;

    @BeforeEach
    void setUp() {
        System.out.println("*** TEST STAND BACK TEST ***");
    }

    @Test
    void testFindAll() {
        Visit visit = new Visit();
        Set<Visit> visitSet = new HashSet<>();
        visitSet.add(visit);
        when(mockVisitRepository.findAll())
            .thenReturn(visitSet);
        Set<Visit> result = mockVisitSDJpaService.findAll();
        verify(mockVisitRepository).findAll();
        assertThat(result).isNotNull();
        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(1);
        assertThat(result.iterator().next()).isInstanceOf(Visit.class);
    }

    @Test
    void testFindById() {
        Visit visit = new Visit();
        when(mockVisitRepository.findById(testId))
            .thenReturn(Optional.of(visit));
        Visit result = mockVisitSDJpaService.findById(testId);
        verify(mockVisitRepository).findById(testId);
        assertThat(result).isNotNull();
    }

    @Test
    void testSave() {
        Visit visit = new Visit();
        when(mockVisitRepository.save(any(Visit.class)))
            .thenReturn(visit);
        Visit result = mockVisitSDJpaService.save(new Visit());
        verify(mockVisitRepository).save(any(Visit.class));
        assertThat(result).isInstanceOf(Visit.class);
        assertThat(result).isEqualTo(visit);
    }

    @Test
    void testDelete() {
        Visit visit = new Visit();
        mockVisitSDJpaService.delete(visit);
        verify(mockVisitRepository).delete(visit);
    }

    @Test
    void testDeleteById() {
        mockVisitSDJpaService.deleteById(testId);
        verify(mockVisitRepository).deleteById(testId);
    }

    @Test
    void testBDDFindAll() {
        // given
        Visit visit = new Visit();
        Set<Visit> visitSet = new HashSet<>();
        visitSet.add(visit);
        given(mockVisitRepository.findAll()).willReturn(visitSet);

        // when
        Set<Visit> result = mockVisitSDJpaService.findAll();

        // then
        then(mockVisitRepository).should().findAll();
        then(mockVisitRepository).shouldHaveNoMoreInteractions();
        assertThat(result).isNotNull();
        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(1);
        assertThat(result.iterator().next()).isInstanceOf(Visit.class);
    }

    @Test
    void testBDDFindById() {
        // given
        Visit visit = new Visit();
        given(mockVisitRepository.findById(anyLong())).willReturn(Optional.of(visit));

        // when
        Visit result = mockVisitSDJpaService.findById(testId);

        // then
        then(mockVisitRepository).should().findById(anyLong());
        then(mockVisitRepository).shouldHaveNoMoreInteractions();
        assertThat(result).isNotNull();
        assertThat(result).isInstanceOf(Visit.class);
    }

    @Test
    void testBDDSave() {
        // given
        Visit visit = new Visit();
        given(mockVisitRepository.save(any(Visit.class))).willReturn(visit);

        // when
        Visit result = mockVisitSDJpaService.save(new Visit());

        // then
        then(mockVisitRepository).should().save(any(Visit.class));
        then(mockVisitRepository).shouldHaveNoMoreInteractions();
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(visit);
        assertThat(result).isInstanceOf(Visit.class);
    }

    @Test
    void testBDDDelete() {
        // given
        Visit visit = new Visit();

        // when
        mockVisitSDJpaService.delete(visit);

        // then
        then(mockVisitRepository).should().delete(visit);
        then(mockVisitRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void testBDDDeleteById() {
        // given ---

        // when
        mockVisitSDJpaService.deleteById(testId);

        // then
        then(mockVisitRepository).should().deleteById(anyLong());
        then(mockVisitRepository).shouldHaveNoMoreInteractions();
    }



}