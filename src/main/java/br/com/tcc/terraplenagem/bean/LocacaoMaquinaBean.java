package br.com.tcc.terraplenagem.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.primefaces.component.datatable.DataTable;

import br.com.tcc.terraplenagem.dao.ClienteFisicoDAO;
import br.com.tcc.terraplenagem.dao.ClienteJuridicoDAO;
import br.com.tcc.terraplenagem.dao.FuncionarioDAO;
import br.com.tcc.terraplenagem.dao.ItemLocacaoMaquinaDAO;
import br.com.tcc.terraplenagem.dao.LocacaoMaquinaDAO;
import br.com.tcc.terraplenagem.dao.MaquinaDAO;
import br.com.tcc.terraplenagem.dao.MotoristaDAO;
import br.com.tcc.terraplenagem.domain.ClienteFisico;
import br.com.tcc.terraplenagem.domain.ClienteJuridico;
import br.com.tcc.terraplenagem.domain.Funcionario;
import br.com.tcc.terraplenagem.domain.ItemLocacaoMaquina;
import br.com.tcc.terraplenagem.domain.LocacaoMaquina;
import br.com.tcc.terraplenagem.domain.Maquina;
import br.com.tcc.terraplenagem.domain.Motorista;
import br.com.tcc.terraplenagem.filter.LocacaoMaquinaFilter;
import br.com.tcc.terraplenagem.util.FacesUtil;
import br.com.tcc.terraplenagem.util.HibernateUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class LocacaoMaquinaBean implements Serializable {
	private List<Maquina> maquinas;
	private List<ItemLocacaoMaquina> itensLocacao;
	private LocacaoMaquina locacaoMaquina;
	private List<ClienteFisico> clienteFisico;
	private List<ClienteJuridico> clienteJuridico;
	private List<Motorista> motoristas;
	private List<Funcionario> funcionarios;
	private LocacaoMaquinaFilter filtro;
	private List<LocacaoMaquina> listaLocacao;

	public void carregarCadastro() {
		try {
			String valor = FacesUtil.getParam("locacaoMaquinaSelecionada");

			if (valor != null) {
				Long codigo = Long.parseLong(valor);

				LocacaoMaquinaDAO locacaoMaquinaDAO = new LocacaoMaquinaDAO();
				locacaoMaquina = locacaoMaquinaDAO.buscar(codigo);

			} else {
				locacaoMaquina = new LocacaoMaquina();

			}
			MotoristaDAO motoristaDAO = new MotoristaDAO();
			motoristas = motoristaDAO.listarOrdenado();

			ClienteFisicoDAO clienteFisicoDAO = new ClienteFisicoDAO();
			clienteFisico = clienteFisicoDAO.listar();

			ClienteJuridicoDAO clienteJuridicoDAO = new ClienteJuridicoDAO();
			clienteJuridico = clienteJuridicoDAO.listar();

			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			funcionarios = funcionarioDAO.listar();

			ItemLocacaoMaquinaDAO itemLocacaoMaquinaDAO = new ItemLocacaoMaquinaDAO();
			itensLocacao = itemLocacaoMaquinaDAO.listar();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar obter os dados da locação!");
			erro.printStackTrace();
		}
	}

	public List<LocacaoMaquina> getListaLocacao() {
		return listaLocacao;
	}

	public void setListaLocacao(List<LocacaoMaquina> listaLocacao) {
		this.listaLocacao = listaLocacao;
	}

	public LocacaoMaquinaFilter getFiltro() {
		if (filtro == null) {
			filtro = new LocacaoMaquinaFilter();
		}
		return filtro;
	}

	public void setFiltro(LocacaoMaquinaFilter filtro) {
		this.filtro = filtro;
	}

	public List<ClienteFisico> getClienteFisico() {
		return clienteFisico;
	}

	public void setClienteFisico(List<ClienteFisico> clienteFisico) {
		this.clienteFisico = clienteFisico;
	}

	public List<ClienteJuridico> getClienteJuridico() {
		return clienteJuridico;
	}

	public void setClienteJuridico(List<ClienteJuridico> clienteJuridico) {
		this.clienteJuridico = clienteJuridico;
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

	public LocacaoMaquina getLocacaoMaquina() {
		return locacaoMaquina;
	}

	public void setLocacaoMaquina(LocacaoMaquina locacaoMaquina) {
		this.locacaoMaquina = locacaoMaquina;
	}

	public List<ItemLocacaoMaquina> getItensLocacao() {
		return itensLocacao;
	}

	public void setItensLocacao(List<ItemLocacaoMaquina> itensLocacao) {
		this.itensLocacao = itensLocacao;
	}

	public List<Maquina> getMaquinas() {
		return maquinas;
	}

	public void setMaquinas(List<Maquina> maquinas) {
		this.maquinas = maquinas;
	}

	public void novo() {
		try {
			locacaoMaquina = new LocacaoMaquina();
			locacaoMaquina.setValorTotal(new BigDecimal("0.00"));

			MaquinaDAO maquinaDAO = new MaquinaDAO();
			
			maquinas = maquinaDAO.listar();

			itensLocacao = new ArrayList<>();

		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar listar as maquinas");
			erro.printStackTrace();
		}
	}

	public void adicionar(ActionEvent evento) {
		Maquina maquina = (Maquina) evento.getComponent().getAttributes().get("maquinaSelecionada");

		// Local Mexido
		int achou = -1;
		for (int posicao = 0; posicao < itensLocacao.size(); posicao++) {
			if (itensLocacao.get(posicao).getMaquina().equals(maquina)) {
				achou = posicao;
			}
		}

		if (achou < 0) {
			ItemLocacaoMaquina itemLocacao = new ItemLocacaoMaquina();
			itemLocacao.setValorUnitario(maquina.getValorUnitarioLocacao());
			itemLocacao.setMaquina(maquina);
			itemLocacao.setQuantidade(1);

			itensLocacao.add(itemLocacao);

		} else {
			ItemLocacaoMaquina itemLocacao = itensLocacao.get(achou);
			itemLocacao.setQuantidade(itemLocacao.getQuantidade() + 1);
			itemLocacao.setValorUnitario(
					maquina.getValorUnitarioLocacao().multiply(new BigDecimal(itemLocacao.getQuantidade())));
		}

		calcular();
	}

	public void remover(ActionEvent evento) {
		ItemLocacaoMaquina itemLocacao = (ItemLocacaoMaquina) evento.getComponent().getAttributes()
				.get("itemSelecionado");
		int achou = -1;
		for (int posicao = 0; posicao < itensLocacao.size(); posicao++) {
			if (itensLocacao.get(posicao).getMaquina().equals(itemLocacao.getMaquina())) {
				achou = posicao;
			}
		}

		if (achou > -1) {
			itensLocacao.remove(achou);
		}

		calcular();

	}

	public void calcular() {
		locacaoMaquina.setValorTotal(new BigDecimal("0.00"));

		for (int posicao = 0; posicao < itensLocacao.size(); posicao++) {
			ItemLocacaoMaquina itemLocacao = itensLocacao.get(posicao);
			locacaoMaquina.setValorTotal(locacaoMaquina.getValorTotal().add(itemLocacao.getValorUnitario()));
		}
	}

	public void finalizar() {
		try {
			locacaoMaquina.setDataCadastro(new Date());

			locacaoMaquina.setClienteFisico(null);
			locacaoMaquina.setClienteJuridico(null);
			locacaoMaquina.setFuncionario(null);
			locacaoMaquina.setMotorista(null);

			MotoristaDAO motoristaDAO = new MotoristaDAO();
			motoristas = motoristaDAO.listarOrdenado();

			ClienteFisicoDAO clienteFisicoDAO = new ClienteFisicoDAO();
			clienteFisico = clienteFisicoDAO.listar();

			ClienteJuridicoDAO clienteJuridicoDAO = new ClienteJuridicoDAO();
			clienteJuridico = clienteJuridicoDAO.listar();

			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			funcionarios = funcionarioDAO.listar();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar finalizar a locação!");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {
			// converte para um numero inteiro
			if (locacaoMaquina.getValorTotal().signum() == 0) {
				Messages.addGlobalError("Informe pelo menos uma máquina para a locação!");
				return;
			}
			
			

			LocacaoMaquinaDAO locacaoMaquinaDAO = new LocacaoMaquinaDAO();
			locacaoMaquinaDAO.salvar(locacaoMaquina);
			
			locacaoMaquina = locacaoMaquinaDAO.buscar(locacaoMaquina.getCodigo());
			
			ItemLocacaoMaquinaDAO itemLocacaoMaquinaDAO = new ItemLocacaoMaquinaDAO();
			for (int posicao = 0; posicao < itensLocacao.size(); posicao++) {
				ItemLocacaoMaquina itemLocacao = itensLocacao.get(posicao);
				itemLocacao.setLocacaoMaquina(locacaoMaquina);
				itemLocacaoMaquinaDAO.salvar(itemLocacao);
			}
			
			locacaoMaquina = new LocacaoMaquina();
			locacaoMaquina.setValorTotal(new BigDecimal("0.00"));
			
			

			MaquinaDAO maquinaDAO = new MaquinaDAO();
			maquinas = maquinaDAO.listar();

			itensLocacao = new ArrayList<>();

			Messages.addGlobalInfo("Locação de Máquina salva com sucesso!");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar a locação!");
			erro.printStackTrace();
		}
	}

	// @PostConstruct
	public void listar() {
		try {
			LocacaoMaquinaDAO locacaoMaquinaDAO = new LocacaoMaquinaDAO();
			listaLocacao = locacaoMaquinaDAO.listarOrdenado();

			ItemLocacaoMaquinaDAO itemLocacaoMaquinaDAO = new ItemLocacaoMaquinaDAO();
			for (LocacaoMaquina locacaoMaquina : listaLocacao){
				locacaoMaquina.setItensLocacao(itemLocacaoMaquinaDAO.buscarPorCodigoLocacaoMaquina(locacaoMaquina.getCodigo()));
			}

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar as locações de máquinas!");
			erro.printStackTrace();
		}

	}

	public void imprimir() {
		try {
			DataTable tabela = (DataTable) Faces.getViewRoot().findComponent("formConsulta:tabela");
			Map<String, Object> filtros = tabela.getFilters();

			String clienteFisico = (String) filtros.get("clienteFisico.nome)");
			String clienteJuridico = (String) filtros.get("clienteJuridico.nome");

			String caminho = Faces.getRealPath("/relatorios/locacaoMaquina.jasper");

			Map<String, Object> parametros = new HashMap<>();
			if (clienteFisico == null) {
				parametros.put("CLIENTE_FISICO", "%%");
			} else {
				parametros.put("CLIENTE_FISICO", "%" + clienteFisico + "%");
			}
			if (clienteJuridico == null) {
				parametros.put("CLIENTE_JURIDICO", "%%");
			} else {
				parametros.put("CLIENTE_JURIDICO", "%" + clienteJuridico + "%");
			}

			Connection conexao = HibernateUtil.getConexao();

			JasperPrint relatorio = JasperFillManager.fillReport(caminho, parametros, conexao);

			JasperPrintManager.printReport(relatorio, true);
		} catch (JRException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar gerar o relatório");
			erro.printStackTrace();
		}
	}

	public void carregarMaquinas() {
		MaquinaDAO maquinaDAO = new MaquinaDAO();
		maquinas = maquinaDAO.listar();
	}

}
