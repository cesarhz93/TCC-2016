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
import br.com.tcc.terraplenagem.domain.Funcionario;
import br.com.tcc.terraplenagem.util.FacesUtil;
import br.com.tcc.terraplenagem.util.HibernateUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class FuncionarioBean implements Serializable {
	private Funcionario funcionario;
	private List<Funcionario> funcionarios;
	private String acao;

	public void setAcao(String acao) {
		this.acao = acao;
	}

	public String getAcao() {
		return acao;
	}

	public Funcionario getFuncionario() {
		if (funcionario == null) {
			funcionario = new Funcionario();
		}
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
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
			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			funcionarios = funcionarioDAO.listar();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os funcionários!");
			erro.printStackTrace();
		}

	}

	@PostConstruct
	public void novo() {
		funcionario = new Funcionario();
	}

	public void salvar() {
		try {
			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			funcionarioDAO.salvar(funcionario);
			novo();
			Messages.addGlobalInfo("Funcionário salvo com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar incluir o funcionário!");
			erro.printStackTrace();
		}

	}

	public void excluir(ActionEvent evento) {
		try {
			funcionario = (Funcionario) evento.getComponent().getAttributes().get("funcionarioSelecionado");
			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			funcionarioDAO.excluir(funcionario);
			
			funcionarios = funcionarioDAO.listar();
			Messages.addGlobalInfo("Funcionario excluído com sucesso!");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir o funcionario!");
			erro.printStackTrace();
		}
	}

	public void editar() {
		try {
			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			funcionarioDAO.editar(funcionario);

			Messages.addGlobalInfo("Funcionario alterado com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar alterar o funcionario!");
			erro.printStackTrace();
		}
	}

	public void carregarCadastro() {
		try {
			String valor = FacesUtil.getParam("funcSelecionado");

			if (valor != null) {
				Long codigo = Long.parseLong(valor);

				FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

				funcionario = funcionarioDAO.buscar(codigo);
			}

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar obter os dados do funcionário!");
			erro.printStackTrace();
		}
	}
	
	public void imprimir() {
		try {
			DataTable tabela = (DataTable) Faces.getViewRoot().findComponent("formConsulta:tabela");
			Map<String, Object> filtros = tabela.getFilters();

			String nomeFuncionario = (String) filtros.get("nome");
			String cpfFuncionario = (String) filtros.get("cpf");

			String caminho = Faces.getRealPath("/relatorios/funcionarios.jasper");

			Map<String, Object> parametros = new HashMap<>();
			if (nomeFuncionario == null) {
				parametros.put("NOME_FUNCIONARIO", "%%");
			} else {
				parametros.put("NOME_FUNCIONARIO", "%" + nomeFuncionario + "%");
			}
			if (cpfFuncionario == null) {
				parametros.put("CPF_FUNCIONARIO", "%%");
			} else {
				parametros.put("CPF_FUNCIONARIO", "%" + cpfFuncionario + "%");
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
