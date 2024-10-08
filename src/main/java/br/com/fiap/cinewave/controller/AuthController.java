package br.com.fiap.cinewave.controller;

import br.com.fiap.cinewave.model.LoginRequest;
import br.com.fiap.cinewave.model.Usuario;
import br.com.fiap.cinewave.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestBody LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String senha = loginRequest.getSenha();

        Usuario usuario = usuarioService.autenticarUsuario(email, senha);

        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
