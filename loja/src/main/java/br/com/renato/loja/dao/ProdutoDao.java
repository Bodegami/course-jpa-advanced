package br.com.renato.loja.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.renato.loja.modelo.Produto;

public class ProdutoDao {
	
	private EntityManager em;
	
	// Design Pattern DAO
	public ProdutoDao(EntityManager em) {
		this.em = em;
	}
	
	public void cadastrar(Produto produto) {
		this.em.persist(produto);
	}
	
	public void atualizar(Produto produto) {
		this.em.merge(produto);
	}
	
	public void remover(Produto produto) {
		produto = em.merge(produto);
		this.em.remove(produto);
	}
	
	public Produto buscarPorId(Long id) {
		return em.find(Produto.class, id);
	}
	
	public List<Produto> listarTodos() {
		String jpql = "SELECT p FROM Produto p";
		return em.createQuery(jpql, Produto.class).getResultList();
	}
	
	//JPQL utilizando o Named Parameters
	public List<Produto> buscarPorNome(String nome) {
		String jpql = "SELECT p FROM Produto p WHERE p.nome = :pNome";
		return em.createQuery(jpql, Produto.class)
				.setParameter("pNome", nome)
				.getResultList();
	}
	
	//JPQL utilizando o Position Parameters
	public List<Produto> buscarPorNomeDaCategoria(String categoria) {
		String jpql = "SELECT p FROM Produto p WHERE p.categoria.nome = ?1";
		return em.createQuery(jpql, Produto.class)
				.setParameter(1, categoria)
				.getResultList();
	}
		

	//JPQL retornando apenas um campo do produto da tabela
	public BigDecimal buscarPrecoDoProdutoComNome(String nome) {
		String jpql = "SELECT p.preco FROM Produto p WHERE p.nome = :pNome";
		return em.createQuery(jpql, BigDecimal.class)
				.setParameter("pNome", nome)
				.getSingleResult();
	}	
}
