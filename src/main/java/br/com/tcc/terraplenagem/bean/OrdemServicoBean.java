package br.com.tcc.terraplenagem.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
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
import br.com.tcc.terraplenagem.dao.ClienteJuridicoDAO;
import br.com.tcc.terraplenagem.dao.FuncionarioDAO;
import br.com.tcc.terraplenagem.dao.ItemOrdemServicoDAO;
import br.com.tcc.terraplenagem.dao.LocacaoMaquinaDAO;
import br.com.tcc.terraplenagem.dao.OrdemServicoDAO;
import br.com.tcc.terraplenagem.dao.TipoServicoDAO;
import br.com.tcc.terraplenagem.domain.ClienteFisico;
import br.com.tcc.terraplenagem.domain.ClienteJuridico;
import br.com.tcc.terraplenagem.domain.Funcionario;
import br.com.tcc.terraplenagem.domain.ItemOrdemServico;
import br.com.tcc.terraplenagem.domain.LocacaoMaquina;
import br.com.tcc.terraplenagem.domain.Maquina;
import br.com.tcc.terraplenagem.domain.OrdemServico;
import br.com.tcc.terraplenagem.domain.TipoServico;
import br.com.tcc.terraplenagem.util.HibernateUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class OrdemServicoBean implements Serializable {
	private OrdemServico ordemServico;
	private List<OrdemServico> listaOrdemServico;
	private List<ItemOrdemServico> itensOrdemServico;
	private List<Maquina> maquinas;
	private List<ClienteFisico> clienteFisico;
	private List<ClienteJuridico> clienteJuridico;
	private List<Funcionario> funcionarios;
	private List<LocacaoMaquina> listaLocacaoMaquina;
	private List<TipoServico> tiposServico;
	private LocacaoMaquina locacaoMaquina;
	private ItemOrdemServico itemOrdemServico;

	public ItemOrdemServico getItemOrdemServico() {
		return itemOrdemServico;
	}

	public void setItemOrdemServico(ItemOrdemServico itemOrdemServico) {
		this.itemOrdemServico = itemOrdemServico;
	}

	public LocacaoMaquina getLocacaoMaquina() {
		return locacaoMaquina;
	}

	public void setLocacaoMaquina(LocacaoMaquina locacaoMaquina) {
		this.locacaoMaquina = locacaoMaquina;
	}

	public List<TipoServico> getTiposServico() {
		return tiposServico;
	}

	public void setTiposServico(List<TipoServico> tiposServico) {
		this.tiposServico = tiposServico;
	}

	public OrdemServico getOrdemServico() {
		return ordemServico;
	}

	public void setOrdemServico(OrdemServico ordemServico) {
		this.ordemServico = ordemServico;
	}

	public List<OrdemServico> getListaOrdemServico() {
		return listaOrdemServico;
	}

	public void setListaOrdemServico(List<OrdemServico> listaOrdemServico) {
		this.listaOrdemServico = listaOrdemServico;
	}

	public List<ItemOrdemServico> getItensOrdemServico() {
		return itensOrdemServico;
	}

	public void setItensOrdemServico(List<ItemOrdemServico> itensOrdemServico) {
		this.itensOrdemServico = itensOrdemServico;
	}

	public List<Maquina> getMaquinas() {
		return maquinas;
	}

	public void setMaquinas(List<Maquina> maquinas) {
		this.maquinas = maquinas;
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

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

	public List<LocacaoMaquina> getListaLocacaoMaquina() {
		return listaLocacaoMaquina;
	}

	public void setListaLocacaoMaquina(List<LocacaoMaquina> listaLocacaoMaquina) {
		this.listaLocacaoMaquina = listaLocacaoMaquina;
	}

	@PostConstruct
	public void novo() {
		try {
			ordemServico = new OrdemServico();
			ordemServico.setValorTotal(new BigDecimal("0.00"));

			TipoServicoDAO servicoDAO = new TipoServicoDAO();
			tiposServico = servicoDAO.listar();

			LocacaoMaquinaDAO locacaoMaquinaDAO = new LocacaoMaquinaDAO();
			listaLocacaoMaquina = locacaoMaquinaDAO.listarOrdenado();

			itensOrdemServico = new ArrayList<>();

		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar listar os serviços");
			erro.printStackTrace();
		}
	}

	public void adicionar(ActionEvent evento) {
		TipoServico tipoServico = (TipoServico) evento.getComponent().getAttributes().get("tipoServicoSelecionado");

		int achou = -1;
		for (int posicao = 0; posicao < itensOrdemServico.size(); posicao++) {
			if (itensOrdemServico.get(posicao).getTipoServico().equals(tipoServico)) {
				achou = posicao;
			}
		}

		if (achou < 0) {
			ItemOrdemServico itemOrdemServico = new ItemOrdemServico();
			itemOrdemServico.setValorUnitario(tipoServico.getValorUnitario());
			itemOrdemServico.setTipoServico(tipoServico);
			itemOrdemServico.setQuantidade(1);

			itensOrdemServico.add(itemOrdemServico);
		} else {
			ItemOrdemServico itemOrdemServico = itensOrdemServico.get(achou);
			itemOrdemServico.setQuantidade(itemOrdemServico.getQuantidade() + 1);
			itemOrdemServico.setValorUnitario(
					tipoServico.getValorUnitario().multiply(new BigDecimal(itemOrdemServico.getQuantidade())));
		}

		calcular();
	}

	public void remover(ActionEvent evento) {
		ItemOrdemServico itemOrdemServico = (ItemOrdemServico) evento.getComponent().getAttributes()
				.get("itemSelecionado");

		int achou = -1;
		for (int posicao = 0; posicao < itensOrdemServico.size(); posicao++) {
			if (itensOrdemServico.get(posicao).getTipoServico().equals(itemOrdemServico.getTipoServico())) {
				achou = posicao;
			}
		}

		if (achou > -1) {
			itensOrdemServico.remove(achou);
		}

		calcular();

	}

	public void calcular() {
		ordemServico.setValorTotal(new BigDecimal("0.00"));

		for (int posicao = 0; posicao < itensOrdemServico.size(); posicao++) {
			ItemOrdemServico itemOrdemServico = itensOrdemServico.get(posicao);
			ordemServico.setValorTotal(ordemServico.getValorTotal().add(itemOrdemServico.getValorUnitario()));
		}
	}

	public void finalizar() {
		try {
			ordemServico.setDataCadastro(new Date());

			ordemServico.setClienteFisico(null);
			ordemServico.setClienteJuridico(null);
			ordemServico.setFuncionario(null);

			ClienteFisicoDAO clienteFisicoDAO = new ClienteFisicoDAO();
			clienteFisico = clienteFisicoDAO.listar();

			ClienteJuridicoDAO clienteJuridicoDAO = new ClienteJuridicoDAO();
			clienteJuridico = clienteJuridicoDAO.listar();

			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			funcionarios = funcionarioDAO.listar();
			
			ordemServico.setValorTotal(ordemServico.getLocacaoMaquina().getValorTotal().add(ordemServico.getValorTotal()));

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar finalizar a ordem de Serviço!");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {
			// converte para um numero inteiro
			if (ordemServico.getValorTotal().signum() == 0) {
				Messages.addGlobalError("Informe pelo menos um serviço para a ordem!");
				return;
			}

			BigDecimal soma = ordemServico.getLocacaoMaquina().getValorTotal().add(ordemServico.getValorTotal());
			ordemServico.setValorTotal(soma);
			
			OrdemServicoDAO ordemServicoDAO = new OrdemServicoDAO();
			ordemServicoDAO.salvar(ordemServico);

			ordemServico = ordemServicoDAO.buscar(ordemServico.getCodigo());

			ItemOrdemServicoDAO itemOrdemServicoDAO = new ItemOrdemServicoDAO();
			for (int posicao = 0; posicao < itensOrdemServico.size(); posicao++) {
				ItemOrdemServico itemOrdemServico = itensOrdemServico.get(posicao);
				itemOrdemServico.setOrdemServico(ordemServico);
				itemOrdemServicoDAO.salvar(itemOrdemServico);
			}

			ordemServico = new OrdemServico();
			

			TipoServicoDAO tipoServicoDAO = new TipoServicoDAO();
			tiposServico = tipoServicoDAO.listar();

			itensOrdemServico = new ArrayList<>();
			
			locacaoMaquina = new LocacaoMaquina();

			Messages.addGlobalInfo("Ordem de Serviço salva com sucesso!");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar a ordem de serviço!");
			erro.printStackTrace();
		}
	}

	// @PostConstruct
	public void listar() {
		try {
			OrdemServicoDAO ordemServicoDAO = new OrdemServicoDAO();
			listaOrdemServico = ordemServicoDAO.listarOrdenado();

			ItemOrdemServicoDAO itemOrdemServicoDAO = new ItemOrdemServicoDAO();
			for (OrdemServico ordemServico : listaOrdemServico) {
				ordemServico.setItensOrdemServico(
						itemOrdemServicoDAO.buscarPorCodigoOrdemServico(ordemServico.getCodigo()));
			}

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar as ordens de serviço!");
			erro.printStackTrace();
		}

	}

	public void imprimir() {
		try {
			DataTable tabela = (DataTable) Faces.getViewRoot().findComponent("formConsulta:tabela");
			Map<String, Object> filtros = tabela.getFilters();

			String clienteFisico = (String) filtros.get("clienteFisico.nome)");
			String clienteJuridico = (String) filtros.get("clienteJuridico.nome");

			String caminho = Faces.getRealPath("/relatorios/ordemServico.jasper");

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

}
