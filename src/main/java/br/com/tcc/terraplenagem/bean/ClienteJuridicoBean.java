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

import br.com.tcc.terraplenagem.dao.ClienteJuridicoDAO;
import br.com.tcc.terraplenagem.domain.ClienteJuridico;
import br.com.tcc.terraplenagem.util.FacesUtil;
import br.com.tcc.terraplenagem.util.HibernateUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ClienteJuridicoBean implements Serializable {
	private ClienteJuridico cliente;
	private List<ClienteJuridico> clientes;
	private String acao;

	public void setAcao(String acao) {
		this.acao = acao;
	}

	public String getAcao() {
		return acao;
	}

	public ClienteJuridico getCliente() {
		if (cliente == null) {
			cliente = new ClienteJuridico();
		}
		return cliente;
	}

	public void setCliente(ClienteJuridico cliente) {
		this.cliente = cliente;
	}

	public List<ClienteJuridico> getClientes() {
		return clientes;
	}

	public void setClientes(List<ClienteJuridico> clientes) {
		this.clientes = clientes;
	}

	@PostConstruct
	public void listar() {
		try {
			ClienteJuridicoDAO clienteJuridicoDAO = new ClienteJuridicoDAO();
			clientes = clienteJuridicoDAO.listar();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os clientes!");
			erro.printStackTrace();
		}

	}

	@PostConstruct
	public void novo() {
		
		cliente = new ClienteJuridico();
		cliente.setDataCadastro(new Date());
		
	}
	
	public void limpar(){
		cliente.setNomeFantasia(null);
		cliente.setCnpj(null);
	}

	public void salvar() {
		try {
			
			ClienteJuridicoDAO clienteJuridicoDAO = new ClienteJuridicoDAO();
			clienteJuridicoDAO.salvar(cliente);
			novo();
			Messages.addGlobalInfo("Cliente salvo com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar incluir o cliente!");
			erro.printStackTrace();
		}

	}

	public void excluir(ActionEvent evento) {
		try {
			cliente = (ClienteJuridico) evento.getComponent().getAttributes().get("clienteSelecionado");
			ClienteJuridicoDAO clienteJuridicoDAO = new ClienteJuridicoDAO();
			clienteJuridicoDAO.excluir(cliente);

			clientes = clienteJuridicoDAO.listar();
			Messages.addGlobalInfo("Cliente excluído com sucesso!");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir o cliente!");
			erro.printStackTrace();
		}
	}

	public void editar() {
		try {
			ClienteJuridicoDAO clienteJuridicoDAO = new ClienteJuridicoDAO();
			clienteJuridicoDAO.editar(cliente);

			Messages.addGlobalInfo("Cliente alterado com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar editar o cliente!");
			erro.printStackTrace();
		}
	}

	public void carregarCadastro() {
		try {
		
			String valor = FacesUtil.getParam("clienteJuridicoSelecionado");

			if (valor != null) {
				Long codigo = Long.parseLong(valor);

				ClienteJuridicoDAO clienteJuridicoDAO = new ClienteJuridicoDAO();

				cliente = clienteJuridicoDAO.buscar(codigo);
				cliente.setDataCadastro(new Date());
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

			String nomeFantasiaCliente = (String) filtros.get("nomeFantasia");
			String cnpjCliente = (String) filtros.get("cnpj");

			String caminho = Faces.getRealPath("/relatorios/clientesJuridicos.jasper");

			Map<String, Object> parametros = new HashMap<>();
			if (nomeFantasiaCliente == null) {
				parametros.put("NOMEFANTASIA_CLIENTE", "%%");
			} else {
				parametros.put("NOMEFANTASIA_CLIENTE", "%" + nomeFantasiaCliente + "%");
			}
			if (cnpjCliente == null) {
				parametros.put("CNPJ_CLIENTE", "%%");
			} else {
				parametros.put("CNPJ_CLIENTE", "%" + cnpjCliente + "%");
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

