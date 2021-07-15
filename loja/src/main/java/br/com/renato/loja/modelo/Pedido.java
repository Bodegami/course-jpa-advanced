package br.com.renato.loja.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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

	@Column(name = "valor_total")
	private BigDecimal valorTotal = BigDecimal.ZERO;

	private LocalDate data = LocalDate.now();
	
	/*
		Todo relacionamento toOne, o padrao de carregamento
		é EAGER, ou seja, ele faz o carregamento antecipado
		mesmo que não seja a informacao que buscamos no 
		metodo que chamou a classe.
		A boa pratica diz que em todo carregamento toOne, 
		coloque o fetch para LAZY, assim ganhamos em performance.
	*/

	@ManyToOne(fetch = FetchType.LAZY)
	private Cliente cliente;

	/*
	 * Sempre que trabalhamos com um relacionamento bidirecional, precisamos colocar
	 * o mappedBy apontando para atributo em questao na outra classe do
	 * relacionamento.
	 * 
	 * É uma boa pratica sempre inicializar a lista para evitar mais if's no codigo.
	 * 
	 * O cascade cria um efeito cascata nas operações realizadas em uma entidade.
	 * 
	 * Utilizamos o cascade type ALL, para que quando um pedido for salvo, alterado
	 * ou excluido do banco, o mesmo se replique na classe ItemPedido, dessa forma
	 * garantimos que a consistencia das informações.
	 * 
	 * Todo os relacionamentos toMany, por padrao seguem o carregamento LAZY, ou
	 * seja, é o carregamento preguiço ou tardio.
	 */
	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
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

	public List<ItemPedido> getItens() {
		return itens;
	}

	/*
	 * É considerado uma boa pratica criar um método utilitario que tem como
	 * responsabilidade setar o item a lista de itensPedido e por ser um
	 * relacionamento bidirecional, fazer o mesmo do outro lado como no exemplo
	 * abaixo.
	 */

	@Override
	public String toString() {
		return "Pedido [id=" + id + ", valorTotal=" + valorTotal + ", data=" + data + ", cliente=" + cliente
				+ ", itens=" + itens + "]";
	}

	public void adicionarItem(ItemPedido item) {
		item.setPedido(this);
		this.itens.add(item);
		this.valorTotal = this.valorTotal.add(item.getValor());
	}

}
