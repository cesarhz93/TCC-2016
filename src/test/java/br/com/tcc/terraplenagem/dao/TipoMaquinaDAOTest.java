package br.com.tcc.terraplenagem.dao;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import br.com.tcc.terraplenagem.domain.TipoMaquina;

public class TipoMaquinaDAOTest {

	@Test
	
	public void salvar() {
		TipoMaquina tipoMaquina = new TipoMaquina();
		tipoMaquina.setTipo("Retroescavadeira");
		TipoMaquinaDAO tipoMaquinaDAO = new TipoMaquinaDAO();
		tipoMaquinaDAO.salvar(tipoMaquina);
	}

	@Ignore
	@Test
	public void listar() {
		TipoMaquinaDAO dao = new TipoMaquinaDAO();
		List<TipoMaquina> resultado = dao.listar();

		for (TipoMaquina estadoCorrente : resultado) {
			System.out.println(estadoCorrente.toString());
		}
	}

	@Ignore
	@Test
	public void buscar() {
		Long codigo = 1L;

		TipoMaquinaDAO dao = new TipoMaquinaDAO();
		TipoMaquina tipoMaquina = dao.buscar(codigo);

		if (tipoMaquina == null) {
			System.out.println("Registro não encontrado");
		} else {
			System.out.println(tipoMaquina.toString());
		}
	}

	@Test
	@Ignore
	public void excluir() {
		Long codigo = 1L;
		TipoMaquinaDAO dao = new TipoMaquinaDAO();
		TipoMaquina tipoMaquina = dao.buscar(codigo);

		if (tipoMaquina == null) {
			System.out.println("Registro não encontrado");
		} else {
			dao.excluir(tipoMaquina);
			System.out.println("Registro excluido!");
			System.out.println(tipoMaquina.toString());
		}
	}

	@Test
	@Ignore
	public void editar() {
		Long codigo = 1L;
		TipoMaquinaDAO dao = new TipoMaquinaDAO();
		TipoMaquina tipoMaquina = dao.buscar(codigo);

		if (tipoMaquina == null) {
			System.out.println("Registro não encontrado");
		} else {
			tipoMaquina.setTipo("Caminhão Caçamba");
			dao.editar(tipoMaquina);
			System.out.println("Registro Alterado!");
			System.out.println(tipoMaquina.toString());

		}
	}

}
