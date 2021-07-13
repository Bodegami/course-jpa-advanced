package br.com.renato.loja.dao;

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
	
}
	
	