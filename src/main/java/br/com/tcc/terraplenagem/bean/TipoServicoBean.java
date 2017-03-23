package br.com.tcc.terraplenagem.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.tcc.terraplenagem.dao.TipoServicoDAO;
import br.com.tcc.terraplenagem.domain.TipoServico;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class TipoServicoBean implements Serializable {

	private TipoServico tipoServico;
	private List<TipoServico> tipoServicos;
	private String acao;
	
	public void setAcao(String acao) {
		this.acao = acao;
	}
	public String getAcao() {
		return acao;
	}

	public TipoServico getTipoServico() {
		return tipoServico;
	}

	public void setTipoServico(TipoServico tipoServico) {
		this.tipoServico = tipoServico;
	}

	public List<TipoServico> getTipoServicos() {
		return tipoServicos;
	}

	public void setTipoServicos(List<TipoServico> tipoServicos) {
		this.tipoServicos = tipoServicos;
	}

	@PostConstruct
	public void listar() {
		try {
			TipoServicoDAO tipoServicoDAO = new TipoServicoDAO();
			tipoServicos = tipoServicoDAO.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os tipos de serviços!");
			erro.printStackTrace();
		}

	}

	public void novo() {
		tipoServico = new TipoServico();
	}

	public void salvar() {
		try {
			TipoServicoDAO tipoServicoDAO = new TipoServicoDAO();
			tipoServicoDAO.merge(tipoServico);
			tipoServico = new TipoServico();
			tipoServicos = tipoServicoDAO.listar();
			Messages.addGlobalInfo("Tipo de Serviço salvo com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar incluir o tipo de serviço!");
			erro.printStackTrace();
		}

	}

	public void excluir(ActionEvent evento) {
		try {
			tipoServico = (TipoServico) evento.getComponent().getAttributes().get("tipoServicoSelecionado");
			TipoServicoDAO tipoServicoDAO = new TipoServicoDAO();
			tipoServicoDAO.excluir(tipoServico);
			tipoServicos = tipoServicoDAO.listar();
			Messages.addGlobalInfo("Tipo Serviço excluído com sucesso!");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir o tipo de serviço!");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		tipoServico = (TipoServico) evento.getComponent().getAttributes().get("tipoServicoSelecionado");
	}

}
