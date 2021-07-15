package br.com.renato.loja.testes;

import java.math.BigDecimal;

import javax.persistence.EntityManager;

import br.com.renato.loja.dao.CategoriaDao;
import br.com.renato.loja.dao.ClienteDao;
import br.com.renato.loja.dao.PedidoDao;
import br.com.renato.loja.dao.ProdutoDao;
import br.com.renato.loja.modelo.Categoria;
import br.com.renato.loja.modelo.Cliente;
import br.com.renato.loja.modelo.ItemPedido;
import br.com.renato.loja.modelo.Pedido;
import br.com.renato.loja.modelo.Produto;
import br.com.renato.loja.util.JPAUtil;

public class PerformanceConsultas {
	
	public static void main(String[] args) {
		
		popularBancoDeDadosComProdutos();
		popularBancoDeDadosComPedidos();
		EntityManager em = JPAUtil.getEntityManager();
		//Pedido pedido = em.find(Pedido.class, 1l);
		PedidoDao pedidoDao = new PedidoDao(em);
		Pedido pedidoComCliente = pedidoDao.buscarPedidoComCliente(1l);
		em.close();	
		//System.out.println(pedido.getCliente().getNome());
		System.out.println(pedidoComCliente.getCliente().getNome());
	}
	
	
	private static void popularBancoDeDadosComProdutos() {
		Categoria celulares = new Categoria("CELULARES");
		Categoria videogames = new Categoria("VIDEOGAMES");
		Categoria informatica = new Categoria("INFORMATICA");
		
		Produto celular = new Produto("Xiaomi Redmi", "Muito legal", new BigDecimal("800"), celulares);
		Produto videogame = new Produto("PS5", "Playstation 5", new BigDecimal("4800"), videogames);
		Produto macbook = new Produto("MacBook", "Macbook pro retina", new BigDecimal("6000"), informatica);
		
		Cliente cliente = new Cliente("Renato", "123456");

		EntityManager em = JPAUtil.getEntityManager();
		
		ProdutoDao produtoDao = new ProdutoDao(em);
		CategoriaDao categoriaDao = new CategoriaDao(em);
		ClienteDao clienteDao = new ClienteDao(em);		

		em.getTransaction().begin();
		
		categoriaDao.cadastrar(celulares);
		categoriaDao.cadastrar(videogames);
		categoriaDao.cadastrar(informatica);
		
		produtoDao.cadastrar(celular);
		produtoDao.cadastrar(videogame);
		produtoDao.cadastrar(macbook);
		
		clienteDao.cadastrar(cliente);
		
		em.getTransaction().commit();
		em.close();
	}

	private static void popularBancoDeDadosComPedidos() {

		EntityManager em = JPAUtil.getEntityManager();
		
		ProdutoDao produtoDao = new ProdutoDao(em);
		ClienteDao clienteDao = new ClienteDao(em);
		
		Produto produto = produtoDao.buscarPorId(1l);
		Produto produto2 = produtoDao.buscarPorId(2l);
		Produto produto3 = produtoDao.buscarPorId(3l);
		
		Cliente cliente = clienteDao.buscarPorId(1l);
		
		em.getTransaction().begin();

		Pedido pedido = new Pedido(cliente);
		pedido.adicionarItem(new ItemPedido(10, pedido, produto));
		pedido.adicionarItem(new ItemPedido(40, pedido, produto2));
		
		Pedido pedido2 = new Pedido(cliente);
		pedido.adicionarItem(new ItemPedido(2, pedido, produto3));
		
		
		PedidoDao pedidoDao = new PedidoDao(em);
		pedidoDao.cadastrar(pedido);
		pedidoDao.cadastrar(pedido2);
		
		em.getTransaction().commit();
		
	}
	
}
