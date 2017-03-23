package br.com.tcc.terraplenagem.bean;

import java.io.Serializable;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.primefaces.component.datatable.DataTable;

import br.com.tcc.terraplenagem.dao.FuncionarioDAO;
import br.com.tcc.terraplenagem.dao.MotoristaDAO;
import br.com.tcc.terraplenagem.domain.Funcionario;
import br.com.tcc.terraplenagem.domain.Motorista;
import br.com.tcc.terraplenagem.util.HibernateUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class MotoristaBean implements Serializable {
	private Motorista motorista;
	private List<Motorista> motoristas;
	private List<Funcionario> funcionarios;
	private String acao;

	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}

	public Motorista getMotorista() {
		return motorista;
	}

	public void setMotorista(Motorista motorista) {
		this.motorista = motorista;
	}

	public List<Motorista> getMotoristas() {
		return motoristas;
	}

	public void setMotoristas(List<Motorista> motoristas) {
		this.motoristas = motoristas;
	}

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

	@PostConstruct
	public void listar() {
		try {
			MotoristaDAO motoristaDAO = new MotoristaDAO();
			motoristas = motoristaDAO.listar();
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar listar os motoristas");
			erro.printStackTrace();
		}
	}

	public void novo() {
		try {
			motorista = new Motorista();

			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			funcionarios = funcionarioDAO.listar();
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao gerar um novo motorista");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {
			MotoristaDAO motoristaDAO = new MotoristaDAO();
			motoristaDAO.merge(motorista);

			motorista = new Motorista();

			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			funcionarios = funcionarioDAO.listar();

			motoristas = motoristaDAO.listar();

			Messages.addGlobalInfo("Motorista salvo com sucesso");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar salvar um novo motorista");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {
		try {
			motorista = (Motorista) evento.getComponent().getAttributes().get("motoristaSelecionado");

			MotoristaDAO motoristaDAO = new MotoristaDAO();
			motoristaDAO.excluir(motorista);

			motoristas = motoristaDAO.listar();

			Messages.addGlobalInfo("Motorista removido com sucesso");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("O motorista não pode ser excluído pois possui vínculos no sistema");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			motorista = (Motorista) evento.getComponent().getAttributes().get("motoristaSelecionado");

			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			funcionarios = funcionarioDAO.listar();

		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar selecionar o motorista");
			erro.printStackTrace();
		}
	}

	public void imprimir() {
		try {
			DataTable tabela = (DataTable) Faces.getViewRoot().findComponent("formConsulta:tabela");
			Map<String, Object> filtros = tabela.getFilters();

			String nomeMotorista = (String) filtros.get("funcionario.nome");

			String caminho = Faces.getRealPath("/relatorios/motoristas.jasper");

			Map<String, Object> parametros = new HashMap<>();
			if (nomeMotorista == null) {
				parametros.put("NOME_MOTORISTA", "%%");
			} else {
				parametros.put("NOME_MOTORISTA", "%" + nomeMotorista + "%");
			}

			Connection conexao = HibernateUtil.getConexao();

			JasperPrint relatorio = JasperFillManager.fillReport(caminho, parametros, conexao);

			JasperPrintManager.printReport(relatorio, true);
		} catch (JRException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar gerar o relatório");
			erro.printStackTrace();
		}
	}
}