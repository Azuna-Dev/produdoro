package dev.wakandaacademy.produdoro.usuario.application.api;

import javax.validation.Valid;

import dev.wakandaacademy.produdoro.config.security.service.TokenService;
import dev.wakandaacademy.produdoro.handler.APIException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import dev.wakandaacademy.produdoro.usuario.application.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.UUID;

@RestController
@Validated
@Log4j2
@RequiredArgsConstructor
public class UsuarioController implements UsuarioAPI {
	private final UsuarioService usuarioService;
	private final TokenService tokenService;

	@Override
	public UsuarioCriadoResponse postNovoUsuario(@Valid UsuarioNovoRequest usuarioNovo) {
		log.info("[inicia] UsuarioController - postNovoUsuario");
		UsuarioCriadoResponse usuarioCriado = usuarioService.criaNovoUsuario(usuarioNovo);
		log.info("[finaliza] UsuarioController - postNovoUsuario");
		return usuarioCriado;
	}

	@Override
	public UsuarioCriadoResponse buscaUsuarioPorId(UUID idUsuario) {
		log.info("[inicia] UsuarioController - buscaUsuarioPorId");
		log.info("[idUsuario] {}", idUsuario);
		UsuarioCriadoResponse buscaUsuario = usuarioService.buscaUsuarioPorId(idUsuario);
		log.info("[finaliza] UsuarioController - buscaUsuarioPorId");
		return buscaUsuario;
	}

	@Override
	public void mudarStatusParaPausaCurta(String token, UUID idUsuario) {
		log.info("[inicia] UsuarioController - mudarStatusParaPausaCurta");
		String email = getUsuarioByToken(token);
		usuarioService.mudarStatusParaPausaCurta(email, idUsuario);
		log.info("[finaliza] UsuarioController - mudarStatusParaPausaCurta");
	}

	@Override
	public void mudarStatusParaFoco(String token, UUID idUsuario) {
		log.info("[inicia] UsuarioController - mudarStatusParaFoco");
		String email = getUsuarioByToken(token);
		usuarioService.mudarStatusParaFoco(email, idUsuario);
		log.info("[finaliza] UsuarioController - mudarStatusParaFoco");
	}

	@Override
	public void mudarStatusParaPausaLonga(String token, UUID idUsuario) {
		log.info("[inicia] UsuarioController - mudarStatusParaPausaLonga");
		String email = getUsuarioByToken(token);
		usuarioService.mudarStatusParaPausaLonga(email, idUsuario);
		log.info("[finaliza] UsuarioController - mudarStatusParaPausaLonga");
	}

	private String getUsuarioByToken(String token) {
		log.debug("[token] {}", token);
		String usuario = tokenService.getUsuarioByBearerToken(token)
				.orElseThrow(() -> APIException.build(HttpStatus.UNAUTHORIZED, token));
		log.info("[usuario] {}", usuario);
		return usuario;
	}
}
