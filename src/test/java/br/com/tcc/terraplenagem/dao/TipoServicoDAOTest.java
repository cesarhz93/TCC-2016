package br.com.tcc.terraplenagem.dao;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import br.com.tcc.terraplenagem.domain.TipoServico;

public class TipoServicoDAOTest {
@Test
	
	public void salvar() {
		TipoServico tipoServico = new TipoServico();
		tipoServico.setTipo("Aterro");
		TipoServicoDAO tipoServicoDAO = new TipoServicoDAO();
		tipoServicoDAO.salvar(tipoServico);
	}

	@Ignore
	@Test
	public void listar() {
		TipoServicoDAO dao = new TipoServicoDAO();
		List<TipoServico> resultado = dao.listar();

		for (TipoServico tipoServicoCorrente : resultado) {
			System.out.println(tipoServicoCorrente.toString());
		}
	}

	@Ignore
	@Test
	public void buscar() {
		Long codigo = 1L;

		TipoServicoDAO dao = new TipoServicoDAO();
		TipoServico tipoServico = dao.buscar(codigo);

		if (tipoServico == null) {
			System.out.println("Registro não encontrado");
		} else {
			System.out.println(tipoServico.toString());
		}
	}

	@Test
	@Ignore
	public void excluir() {
		Long codigo = 1L;
		TipoServicoDAO dao = new TipoServicoDAO();
		TipoServico tipoServico = dao.buscar(codigo);

		if (tipoServico == null) {
			System.out.println("Registro não encontrado");
		} else {
			dao.excluir(tipoServico);
			System.out.println("Registro excluido!");
			System.out.println(tipoServico.toString());
		}
	}

	@Test
	@Ignore
	public void editar() {
		Long codigo = 1L;
		TipoServicoDAO dao = new TipoServicoDAO();
		TipoServico tipoServico = dao.buscar(codigo);

		if (tipoServico == null) {
			System.out.println("Registro não encontrado");
		} else {
			tipoServico.setTipo("Caminhão Caçamba");
			dao.editar(tipoServico);
			System.out.println("Registro Alterado!");
			System.out.println(tipoServico.toString());

		}
	}


}
