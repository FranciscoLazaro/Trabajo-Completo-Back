package com.example.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Instrumento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String instrumento;
    private String marca;
    private String modelo;
    @Column(length = 1024)
    private String imagen;
    private double precio;
    private String costoEnvio;
    private int cantidadVendida;
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;
}