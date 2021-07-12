package br.com.renato.loja.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "categorias")
public class Categoria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	
	
	// JPA only
	public Categoria() {
	}

	public Categoria(String nome) {
		this.nome = nome;
	}
	
	public String getNome() {
		return nome;
	}

	@Override
	public String toString() {
		return "Categoria [nome= " + nome + "]";
	}
	
	
	
}
