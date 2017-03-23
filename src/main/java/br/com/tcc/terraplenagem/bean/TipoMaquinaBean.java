package br.com.tcc.terraplenagem.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.tcc.terraplenagem.dao.TipoMaquinaDAO;
import br.com.tcc.terraplenagem.domain.TipoMaquina;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class TipoMaquinaBean implements Serializable {

	private TipoMaquina tipoMaquina;
	private List<TipoMaquina> tipoMaquinas;
	private String acao;
	
	public String getAcao() {
		return acao;
	}
	
	public void setAcao(String acao) {
		this.acao = acao;
	}

	public TipoMaquina getTipoMaquina() {
		return tipoMaquina;
	}

	public void setTipoMaquina(TipoMaquina tipoMaquina) {
		this.tipoMaquina = tipoMaquina;
	}

	public List<TipoMaquina> getTipoMaquinas() {
		return tipoMaquinas;
	}

	public void setTipoMaquinas(List<TipoMaquina> tipoMaquinas) {
		this.tipoMaquinas = tipoMaquinas;
	}

	@PostConstruct
	public void listar() {
		try {
			TipoMaquinaDAO tipoMaquinaDAO = new TipoMaquinaDAO();
			tipoMaquinas = tipoMaquinaDAO.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os tipos de máquinas!");
			erro.printStackTrace();
		}

	}

	public void novo() {
		tipoMaquina = new TipoMaquina();
	}

	public void salvar() {
		try {
			TipoMaquinaDAO tipoMaquinaDAO = new TipoMaquinaDAO();
			tipoMaquinaDAO.merge(tipoMaquina);
			
			tipoMaquina = new TipoMaquina();

			tipoMaquinas = tipoMaquinaDAO.listar();
			Messages.addGlobalInfo("Tipo Máquina salvo com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar incluir o tipo de máquina!");
			erro.printStackTrace();
		}

	}

	public void excluir(ActionEvent evento) {
		try {
			tipoMaquina = (TipoMaquina) evento.getComponent().getAttributes().get("tipoMaquinaSelecionado");
			TipoMaquinaDAO tipoMaquinaDAO = new TipoMaquinaDAO();
			tipoMaquinaDAO.excluir(tipoMaquina);
			tipoMaquinas = tipoMaquinaDAO.listar();
			Messages.addGlobalInfo("Tipo Máquina excluído com sucesso!");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir o tipo de máquina!");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		tipoMaquina = (TipoMaquina) evento.getComponent().getAttributes().get("tipoMaquinaSelecionado");
	}

}
