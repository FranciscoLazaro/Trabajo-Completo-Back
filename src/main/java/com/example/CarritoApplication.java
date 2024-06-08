package com.example;

import com.example.Entity.Categoria;
import com.example.Entity.Instrumento;
import com.example.Entity.Usuario;
import com.example.Enum.Rol;
import com.example.Repository.CategoriaRepository;
import com.example.Repository.InstrumentoRepository;
import com.example.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@SpringBootApplication
public class CarritoApplication {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private InstrumentoRepository instrumentoRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	public static void main(String[] args) {
		SpringApplication.run(CarritoApplication.class, args);
	}


	@Bean
	public CommandLineRunner init() {
		return args -> {
			if (usuarioRepository.count() == 0) {
				Usuario admin = new Usuario();
				admin.setNombreUsuario("admin");
				admin.setClave(MD5Encriptador("123")); // Usa un método seguro para encriptar
				admin.setRol(Rol.ADMIN);
				usuarioRepository.save(admin);
				System.out.println("Usuario admin creado");

				Usuario operador = new Usuario();
				operador.setNombreUsuario("operador");
				operador.setClave(MD5Encriptador("123"));
				operador.setRol(Rol.OPERADOR);
				usuarioRepository.save(operador);
				System.out.println("Usuario operdor creado");

				Usuario visor = new Usuario();
				visor.setNombreUsuario("visor");
				visor.setClave(MD5Encriptador("123"));
				visor.setRol(Rol.VISOR);
				usuarioRepository.save(visor);
				System.out.println("Usuario visor creado");

			}
			// Crear y guardar una categoría de ejemplo
			Categoria categoria = new Categoria();
			categoria.setDenominacion("Guitarras");
			categoriaRepository.save(categoria);

			// Crear y guardar el instrumento hardcodeado
			Instrumento instrumento = new Instrumento();
			instrumento.setInstrumento("Guitarra Eléctrica");
			instrumento.setMarca("Fender");
			instrumento.setModelo("Stratocaster");
			instrumento.setImagen("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRa4BcTuEu72lZcLNqLHtPMD2zaIoFy1mZ49Q&s");
			instrumento.setPrecio(1500.0);
			instrumento.setCostoEnvio("Gratis");
			instrumento.setCantidadVendida(10);
			instrumento.setDescripcion("Una guitarra eléctrica de alta calidad con sonido excepcional.");
			instrumento.setCategoria(categoria);

			instrumentoRepository.save(instrumento);
		};
	}

	private String MD5Encriptador(String clave) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] digest = md.digest(clave.getBytes());
			StringBuilder sb = new StringBuilder();
			for (byte b : digest) {
				sb.append(String.format("%02x", b));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

}
