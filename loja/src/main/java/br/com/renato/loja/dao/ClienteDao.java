package br.com.renato.loja.dao;

import javax.persistence.EntityManager;

import br.com.renato.loja.modelo.Cliente;

public class ClienteDao {
	
	private EntityManager em;
	
	// Design Pattern DAO
	public ClienteDao(EntityManager em) {
		this.em = em;
	}
	
	public void cadastrar(Cliente cliente) {
		this.em.persist(cliente);
	}
	
	public Cliente buscarPorId(Long id) {
		return em.find(Cliente.class, id);
	}
	
}
	
	