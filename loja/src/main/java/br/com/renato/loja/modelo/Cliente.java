package br.com.renato.loja.modelo;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "clientes")
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Embedded
	private DadosPessoais dadosPessoais;

	// JPA ONLY
	public Cliente() {
	}

	public Cliente(String nome, String cpf) {
		this.dadosPessoais = new DadosPessoais(nome, cpf);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DadosPessoais getDadosPessoais() {
		return dadosPessoais;
	}

	public String getNome() {
		return this.dadosPessoais.getNome();
	}

	public String getCpf() {
		return this.dadosPessoais.getCpf();
	}

	@Override
	public String toString() {
		return "Cliente [id=" + id + ", nome=" + this.dadosPessoais.getNome() + ", cpf=" + this.dadosPessoais.getCpf()
				+ "]";
	}

}
