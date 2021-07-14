package br.com.renato.loja.dao;

import java.math.BigDecimal;

import javax.persistence.EntityManager;

import br.com.renato.loja.modelo.Pedido;

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
	
}
	
	