package br.com.fiap.cinewave.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
public class Campanha {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String titulo_filme;
    
    @NotBlank
    private String genero_filme;

    @NotBlank
    private String faixa_etaria_filme;

    @NotBlank
    private float budget;

    @NotBlank
    private int expectativa_alcance;

    @ManyToOne
    private Usuario usuario;
}
