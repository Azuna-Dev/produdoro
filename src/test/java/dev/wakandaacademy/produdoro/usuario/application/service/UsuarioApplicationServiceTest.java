package dev.wakandaacademy.produdoro.usuario.application.service;

import dev.wakandaacademy.produdoro.DataHelper;
import dev.wakandaacademy.produdoro.handler.APIException;
import dev.wakandaacademy.produdoro.tarefa.application.repository.TarefaRepository;
import dev.wakandaacademy.produdoro.tarefa.application.service.TarefaApplicationService;
import dev.wakandaacademy.produdoro.usuario.application.repository.UsuarioRepository;
import dev.wakandaacademy.produdoro.usuario.domain.StatusUsuario;
import dev.wakandaacademy.produdoro.usuario.domain.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioApplicationServiceTest {

    @InjectMocks
    UsuarioApplicationService usuarioApplicationService;

    @Mock
    UsuarioRepository usuarioRepository;

    @Test
    void deveMudarStatusParaFoco_QuandoOStatusEstiverDiferenteDeFoco(){
        Usuario usuario = DataHelper.createUsuario();

        when(usuarioRepository.buscaUsuarioPorId(any())).thenReturn(usuario);
        when(usuarioRepository.buscaUsuarioPorEmail(anyString())).thenReturn(usuario);
        usuarioApplicationService.mudarStatusParaFoco(usuario.getEmail(), usuario.getIdUsuario());

        assertEquals(StatusUsuario.FOCO, usuario.getStatus());
        verify(usuarioRepository, times(1)).salva(usuario);
    }
    @Test
    void deveNaoMudarStatusParaFoco_QuandoOUsuarioForDiferente(){
        Usuario usuario = DataHelper.createUsuario();
        UUID idUsuario = UUID.randomUUID();

        when(usuarioRepository.buscaUsuarioPorEmail(anyString())).thenReturn(usuario);
        APIException ex = assertThrows(APIException.class,
                () -> usuarioApplicationService.mudarStatusParaFoco(usuario.getEmail(), idUsuario));

        assertNotEquals(idUsuario, usuario.getIdUsuario());
        assertEquals(HttpStatus.UNAUTHORIZED, ex.getStatusException());
    }
    @Test
    void deveMudarStatusParaPausaCurta_QuandoOStatusEstiverDiferenteDePausaCurta(){
        Usuario usuario = DataHelper.createUsuario();

        when(usuarioRepository.buscaUsuarioPorId(any())).thenReturn(usuario);
        when(usuarioRepository.buscaUsuarioPorEmail(anyString())).thenReturn(usuario);
        usuarioApplicationService.mudarStatusParaPausaCurta(usuario.getEmail(), usuario.getIdUsuario());

        assertEquals(StatusUsuario.PAUSA_CURTA, usuario.getStatus());
        verify(usuarioRepository, times(1)).salva(usuario);
    }
    @Test
    void deveNaoMudarStatusParaPausaCurta_QuandoOUsuarioForDiferente(){
        Usuario usuario = DataHelper.createUsuario();
        UUID idUsuario = UUID.randomUUID();

        when(usuarioRepository.buscaUsuarioPorEmail(anyString())).thenReturn(usuario);
        APIException ex = assertThrows(APIException.class,
                () -> usuarioApplicationService.mudarStatusParaPausaCurta(usuario.getEmail(), idUsuario));

        assertNotEquals(idUsuario, usuario.getIdUsuario());
        assertEquals(HttpStatus.UNAUTHORIZED, ex.getStatusException());
    }
}