package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.repositories.VetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VetSDJpaServiceTest {

    @Mock
    VetRepository mockVetRepository;

    @InjectMocks
    VetSDJpaService mockVetSDJpaService;

    @Test
    void testDeleteById() {
        mockVetSDJpaService.deleteById(1L);
        verify(mockVetRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteByIdAtLeastOnce() {
        mockVetSDJpaService.deleteById(1L);
        verify(mockVetRepository, atLeastOnce()).deleteById(1l);
    }

    @Test
    void testDeleteByIdAtMost() {
        mockVetSDJpaService.deleteById(1L);
        mockVetSDJpaService.deleteById(1L);
        mockVetSDJpaService.deleteById(1L);
        mockVetSDJpaService.deleteById(1L);
        verify(mockVetRepository, atMost(5)).deleteById(1L);

        verify(mockVetRepository, never()).deleteById(5L);
    }

}