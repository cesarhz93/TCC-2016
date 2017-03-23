package br.com.tcc.terraplenagem.bean;

import java.io.Serializable;
import java.sql.Connection;
import java.util.Date;
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

import br.com.tcc.terraplenagem.dao.ClienteFisicoDAO;
import br.com.tcc.terraplenagem.domain.ClienteFisico;
import br.com.tcc.terraplenagem.util.FacesUtil;
import br.com.tcc.terraplenagem.util.HibernateUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ClienteFisicoBean implements Serializable {
	private ClienteFisico cliente;
	private List<ClienteFisico> clientes;
	private String acao;

	public void setAcao(String acao) {
		this.acao = acao;
	}

	public String getAcao() {
		return acao;
	}

	public ClienteFisico getCliente() {
		if (cliente == null) {
			cliente = new ClienteFisico();
		}
		return cliente;
	}

	public void setCliente(ClienteFisico cliente) {
		this.cliente = cliente;
	}

	public List<ClienteFisico> getClientes() {
		return clientes;
	}

	public void setClientes(List<ClienteFisico> clientes) {
		this.clientes = clientes;
	}

	@PostConstruct
	public void listar() {
		try {
			ClienteFisicoDAO clienteFisicoDAO = new ClienteFisicoDAO();
			clientes = clienteFisicoDAO.listar();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os clientes!");
			erro.printStackTrace();
		}

	}

	@PostConstruct
	public void novo() {
		cliente = new ClienteFisico();
		cliente.setDataCadastro(new Date());
	}
	
	public void limpar() {
		cliente = new ClienteFisico();
	}

	public void salvar() {
		try {
			ClienteFisicoDAO clienteFisicoDAO = new ClienteFisicoDAO();
			clienteFisicoDAO.salvar(cliente);
			novo();
			Messages.addGlobalInfo("Cliente salvo com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar incluir o cliente!");
			erro.printStackTrace();
		}

	}

	public void excluir(ActionEvent evento) {
		try {
			cliente = (ClienteFisico) evento.getComponent().getAttributes().get("clienteSelecionado");
			ClienteFisicoDAO clienteFisicoDAO = new ClienteFisicoDAO();
			clienteFisicoDAO.excluir(cliente);

			clientes = clienteFisicoDAO.listar();
			Messages.addGlobalInfo("Cliente excluído com sucesso!");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir o cliente!");
			erro.printStackTrace();
		}
	}

	public void editar() {
		try {
			ClienteFisicoDAO clienteFisicoDAO = new ClienteFisicoDAO();
			clienteFisicoDAO.editar(cliente);

			Messages.addGlobalInfo("Cliente alterado com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar editar o cliente!");
			erro.printStackTrace();
		}
	}

	public void carregarCadastro() {
		try {
			String valor = FacesUtil.getParam("clienteFisicoSelecionado");

			if (valor != null) {
				Long codigo = Long.parseLong(valor);

				ClienteFisicoDAO clienteFisicoDAO = new ClienteFisicoDAO();

				cliente = clienteFisicoDAO.buscar(codigo);
			}

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar obter os dados do cliente!");
			erro.printStackTrace();
		}
	}
	
	public void imprimir() {
		try {
			DataTable tabela = (DataTable) Faces.getViewRoot().findComponent("formConsulta:tabela");
			Map<String, Object> filtros = tabela.getFilters();

			String nomeCliente = (String) filtros.get("nome");
			String cpfCliente = (String) filtros.get("cpf");

			String caminho = Faces.getRealPath("/relatorios/clientesFisicos.jasper");

			Map<String, Object> parametros = new HashMap<>();
			if (nomeCliente == null) {
				parametros.put("NOME_CLIENTE", "%%");
			} else {
				parametros.put("NOME_CLIENTE", "%" + nomeCliente + "%");
			}
			if (cpfCliente == null) {
				parametros.put("CPF_CLIENTE", "%%");
			} else {
				parametros.put("CPF_CLIENTE", "%" + cpfCliente + "%");
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
