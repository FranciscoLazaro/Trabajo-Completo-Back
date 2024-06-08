package com.example.Entity;

import com.example.Enum.Rol;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    private String nombreUsuario;
    @NotBlank(message = "La clave no puede estar vacía")
    private String clave;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "El rol no puede ser nulo")
    private Rol rol;

}
