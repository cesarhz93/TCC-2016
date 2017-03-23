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

import br.com.tcc.terraplenagem.dao.MaquinaDAO;
import br.com.tcc.terraplenagem.dao.TipoMaquinaDAO;
import br.com.tcc.terraplenagem.domain.Maquina;
import br.com.tcc.terraplenagem.domain.TipoMaquina;
import br.com.tcc.terraplenagem.util.FacesUtil;
import br.com.tcc.terraplenagem.util.HibernateUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class MaquinaBean implements Serializable {
	private Maquina maquina;
	private List<Maquina> maquinas;
	private List<TipoMaquina> tiposMaquina;
	private String acao;

	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}

	public Maquina getMaquina() {
		return maquina;
	}

	public void setMaquina(Maquina maquina) {
		this.maquina = maquina;
	}

	public List<Maquina> getMaquinas() {
		return maquinas;
	}

	public void setMaquinas(List<Maquina> maquinas) {
		this.maquinas = maquinas;
	}

	public List<TipoMaquina> getTiposMaquina() {
		return tiposMaquina;
	}

	public void setTiposMaquina(List<TipoMaquina> tiposMaquina) {
		this.tiposMaquina = tiposMaquina;
	}

	@PostConstruct
	public void listar() {
		try {
			MaquinaDAO maquinaDAO = new MaquinaDAO();
			maquinas = maquinaDAO.listar();

		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar listar as maquinas");
			erro.printStackTrace();
		}
	}

	public void novo() {
		try {
			maquina = new Maquina();

			TipoMaquinaDAO tipoMaquinaDAO = new TipoMaquinaDAO();
			tiposMaquina = tipoMaquinaDAO.listar();

		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao gerar uma nova máquina");
			erro.printStackTrace();
		}
	}

	public void carregarCadastro() {
		try {
			String valor = FacesUtil.getParam("maquinaSelecionada");

			if (valor != null) {
				Long codigo = Long.parseLong(valor);

				MaquinaDAO maquinaDAO = new MaquinaDAO();
				maquina = maquinaDAO.buscar(codigo);
			} else {
				maquina = new Maquina();

			}
			TipoMaquinaDAO tipoMaquinaDAO = new TipoMaquinaDAO();
			tiposMaquina = tipoMaquinaDAO.listar();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar obter os dados da máquina!");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {
			MaquinaDAO maquinaDAO = new MaquinaDAO();
			maquinaDAO.salvar(maquina);

			maquina = new Maquina();

			TipoMaquinaDAO tipoMaquinaDAO = new TipoMaquinaDAO();
			tiposMaquina = tipoMaquinaDAO.listar();

			maquinas = maquinaDAO.listar();
			novo();

			Messages.addGlobalInfo("Maquina salva com sucesso");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar salvar uma nova maquina");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {
		try {
			maquina = (Maquina) evento.getComponent().getAttributes().get("maquinaSelecionada");

			MaquinaDAO maquinaDAO = new MaquinaDAO();
			maquinaDAO.excluir(maquina);

			maquinas = maquinaDAO.listar();

			Messages.addGlobalInfo("Maquina removida com sucesso");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar remover a maquina");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			maquina = (Maquina) evento.getComponent().getAttributes().get("maquinaSelecionada");

			TipoMaquinaDAO tipoMaquinaDAO = new TipoMaquinaDAO();
			tiposMaquina = tipoMaquinaDAO.listar();

		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar selecionar a maquina");
			erro.printStackTrace();
		}
	}

	public void imprimir() {
		try {
			DataTable tabela = (DataTable) Faces.getViewRoot().findComponent("formConsulta:tabela");
			Map<String, Object> filtros = tabela.getFilters();

			String marcaMaquina = (String) filtros.get("marca");
			String tipoMaquina = (String) filtros.get("tipoMaquina.tipo");
			String modeloMaquina = (String) filtros.get("modelo");

			String caminho = Faces.getRealPath("/relatorios/maquinas.jasper");

			Map<String, Object> parametros = new HashMap<>();
			if (marcaMaquina == null) {
				parametros.put("MARCA_MAQUINA", "%%");
			} else {
				parametros.put("MARCA_MAQUINA", "%" + marcaMaquina + "%");
			}
			if (tipoMaquina == null) {
				parametros.put("TIPO_MAQUINA", "%%");
			} else {
				parametros.put("TIPO_MAQUINA", "%" + tipoMaquina + "%");
			}
			if (modeloMaquina == null) {
				parametros.put("MODELO_MAQUINA", "%%");
			} else {
				parametros.put("MODELO_MAQUINA", "%" + modeloMaquina + "%");
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