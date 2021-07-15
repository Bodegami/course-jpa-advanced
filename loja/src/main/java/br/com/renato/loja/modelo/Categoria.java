package br.com.renato.loja.modelo;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "categorias")
public class Categoria {

	@EmbeddedId
	private CategoriaId id;

	// JPA only
	public Categoria() {
	}

	public Categoria(String nome) {
		this.id = new CategoriaId(nome, "xpto");
	}

	public String getNome() {
		return id.nome;
	}

	@Override
	public String toString() {
		return "Categoria [nome= " + id.nome + "]";
	}

}
