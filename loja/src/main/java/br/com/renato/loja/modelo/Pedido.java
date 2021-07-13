package br.com.renato.loja.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "pedidos")
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private BigDecimal valorTotal;
	
	private LocalDate data = LocalDate.now();

	@ManyToOne
	private Cliente cliente;
	
	/*
		Sempre que trabalhamos com um relacionamento
		bidirecional, precisamos colocar o mappedBy
		apontando para atributo em questao na outra 
		classe do relacionamento.
		
		É uma boa pratica sempre inicializar
		a lista para evitar mais if's no codigo
	*/
	@OneToMany(mappedBy = "pedido")
	private List<ItemPedido> itens = new ArrayList<>();

	// JPA ONLY
	public Pedido() {
	}

	public Pedido(Cliente cliente) {
		this.cliente = cliente;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	/*
		É considerado uma boa pratica criar um método utilitario que
		tem como responsabilidade setar o item a lista de itensPedido e 
		por ser um relacionamento bidirecional, fazer o mesmo do outro
		lado como no exemplo abaixo.
	*/
	
	public void adicionarItem(ItemPedido item) {
		item.setPedido(this);
		this.itens.add(item);
	}

}
