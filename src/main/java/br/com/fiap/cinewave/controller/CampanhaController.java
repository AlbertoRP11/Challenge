package br.com.fiap.cinewave.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;


import java.util.List;

import br.com.fiap.cinewave.service.CampanhaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.cinewave.model.Campanha;
import br.com.fiap.cinewave.repository.CampanhaRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("campanha")
@Slf4j
@Tag(name = "campanhas", description = "Endpoint relacionado com as campanhas do CineWave")
public class CampanhaController {

    @Autowired
    private CampanhaRepository campanhaRepository;

    @Autowired
    private CampanhaService campanhaService;

    @GetMapping
    @Operation(summary = "Lista todas as campanhas cadastradas no sistema.",
            description = "Endpoint que retorna um array de objetos do tipo campanhas")
    public List<Campanha> index() {
        return campanhaRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca uma campanha pelo id.",
            description = "Endpoint que retorna uma campanha com base em seu id.")
    public ResponseEntity<Campanha> get(@PathVariable Long id) {
        log.info("Buscar por id: {}", id);

        return campanhaRepository
                .findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{usuarioId}")
    public ResponseEntity<List<Campanha>> buscarCampanhasPorUsuarioId(@PathVariable Long usuarioId) {
        List<Campanha> campanhas = campanhaService.buscarCampanhasPorUsuarioId(usuarioId);
        return ResponseEntity.ok(campanhas);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Cadastra uma campanha no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Erro de validação da categoria"),
            @ApiResponse(responseCode = "201", description = "Campanha cadastrada com sucesso")
    })
    public ResponseEntity<Campanha> criarCampanha(@RequestBody @Valid Campanha campanha) {
        Campanha novaCampanha = campanhaService.criarCampanha(campanha);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaCampanha);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Apaga uma campanha do sistema.")
    public void destroy(@PathVariable Long id) {
        log.info("apagando campanha {}", id);

        verificarSeExisteCampanha(id);
        campanhaRepository.deleteById(id);
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualiza os dados de uma campanha no sistema com base no id.")
    public Campanha update(@PathVariable Long id, @RequestBody Campanha campanha){
        log.info("atualizando campanha id {} para {}", id, campanha);
        
        verificarSeExisteCampanha(id);

        campanha.setId(id);
        return campanhaRepository.save(campanha);

    }

    private void verificarSeExisteCampanha(Long id) {
        campanhaRepository
            .findById(id)
            .orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "campanha não encontrada" )
            );
    }
}
