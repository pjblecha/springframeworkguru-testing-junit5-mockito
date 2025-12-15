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
    void findAll() {
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
    void findById() {
        Visit visit = new Visit();
        when(mockVisitRepository.findById(testId))
            .thenReturn(Optional.of(visit));
        Visit result = mockVisitSDJpaService.findById(testId);
        verify(mockVisitRepository).findById(testId);
        assertThat(result).isNotNull();
    }

    @Test
    void save() {
        Visit visit = new Visit();
        when(mockVisitRepository.save(any(Visit.class)))
            .thenReturn(visit);
        Visit result = mockVisitSDJpaService.save(new Visit());
        verify(mockVisitRepository).save(any(Visit.class));
        assertThat(result).isInstanceOf(Visit.class);
        assertThat(result).isEqualTo(visit);
    }

    @Test
    void delete() {
        Visit visit = new Visit();
        mockVisitSDJpaService.delete(visit);
        verify(mockVisitRepository).delete(visit);
    }

    @Test
    void deleteById() {
        mockVisitSDJpaService.deleteById(testId);
        verify(mockVisitRepository).deleteById(testId);
    }
}