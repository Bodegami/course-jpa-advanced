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
	
}
	
	