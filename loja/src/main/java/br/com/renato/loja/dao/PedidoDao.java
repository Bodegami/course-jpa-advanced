package br.com.renato.loja.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.renato.loja.modelo.Pedido;
import br.com.renato.loja.vo.RelatorioDeVendasVO;

public class PedidoDao {
	
	private EntityManager em;
	
	// Design Pattern DAO
	public PedidoDao(EntityManager em) {
		this.em = em;
	}
	
	public void cadastrar(Pedido pedido) {
		this.em.persist(pedido);
	}
	
	public BigDecimal valorTotalVendido() {
		String jpql = "SELECT SUM(p.valorTotal) FROM Pedido p";
		return em.createQuery(jpql, BigDecimal.class)
				.getSingleResult();
	}
	
	/*
		O SELECT new é um comando da JPA que permite a criacao da instancia 
		de uma classe para retornar como objeto, ao inves de usar um array 
		do tipo Object que represente varios tipos de elementos.
		Para isso a classe referenciada deve conter um construtor com
		os parametros compativeis com os dados dada busca.
	*/
	
	public List<RelatorioDeVendasVO> relatorioDeVendas() {
		String jpql = "SELECT new br.com.renato.loja.vo.RelatorioDeVendasVO "
				+ "(produto.nome, SUM(item.quantidade), MAX(pedido.data)) "
				+ "FROM Pedido pedido JOIN pedido.itens item JOIN item.produto produto "
				+ "GROUP BY produto.nome ORDER BY item.quantidade DESC";
		return em.createQuery(jpql, RelatorioDeVendasVO.class)
				.getResultList();
	}
	
	/*
		Quando temos um relacionamento que por padrao esta setado 
		como LAZY e precisamos carregar essa informacao do relacionamento,
		utilizamos uma estrategia conhecida como Query Planejada.
		Basicamente criamos a nossa query padrao e utilizamos as tags
		JOIN FETCH apontando para qual informacao do relaciomento a 
		JPA deve trazer junto com a busca principal. 
		Repare que no metodo abaixo poderiamos apenas usar o find do 
		EntityManager, porem como precisamos da informacao do cliente
		que esta atrelado ao relacionamento, usei a abordagem da 
		Query Planejada. Dessa forma não precisei mudar a propriedade
		do relacionamento da classe para EAGER.
	*/
	
	public Pedido buscarPedidoComCliente(Long id) {
		return em.createQuery("SELECT p FROM Pedido p JOIN FETCH p.cliente WHERE p.id =:id", Pedido.class)
				.setParameter("id", id)
				.getSingleResult();
	}
	
}
	
	