package br.com.renato.loja.dao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

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
	
	/*
	//JPQL utilizando o Position Parameters
	public List<Produto> buscarPorNomeDaCategoria(String categoria) {
		String jpql = "SELECT p FROM Produto p WHERE p.categoria.nome = ?1";
		return em.createQuery(jpql, Produto.class)
				.setParameter(1, categoria)
				.getResultList();
	}
	*/
	
	//JPQL utilizando o Named Query
		public List<Produto> buscarPorNomeDaCategoria(String nome) {
			return em.createNamedQuery("Produto.produtosPorCategoria", Produto.class)
					.setParameter("pNome", nome)
					.getResultList();
		}
		

	//JPQL retornando apenas um campo do produto da tabela
	public BigDecimal buscarPrecoDoProdutoComNome(String nome) {
		String jpql = "SELECT p.preco FROM Produto p WHERE p.nome = :pNome";
		return em.createQuery(jpql, BigDecimal.class)
				.setParameter("pNome", nome)
				.getSingleResult();
	}	
	
	/*
		Exemplo de gambiarra para fazer um busca dinamica. 
		Apesar de deixar o codigo muito poluido, � muito 
		comum se deparar com esse tipo de codigo em 
		sistemas legados.
	*/
	
	public List<Produto> buscarPorParametros(String nome, BigDecimal preco, LocalDate dataCadastro) {
		String jpql = "SELECT p FROM Produto p WHERE 1=1 ";
		if (nome != null && !nome.trim().isEmpty()) {
			jpql = " AND p.nome = :nome ";
		}
		if (preco != null) {
			jpql = " AND p.preco = :preco ";
		}
		if (dataCadastro != null) {
			jpql = " AND p.dataCadastro = :dataCadastro ";
		}
		TypedQuery<Produto> query = em.createQuery(jpql, Produto.class);	
		if (nome != null && !nome.trim().isEmpty()) {
			query.setParameter("nome", nome);
		}
		if (preco != null) {
			query.setParameter("preco", preco);
		}
		if (dataCadastro != null) {
			query.setParameter("dataCadastro", dataCadastro);
		}
		
		return query.getResultList();
	}
	
	
}
