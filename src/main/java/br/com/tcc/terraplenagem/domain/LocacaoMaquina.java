package br.com.tcc.terraplenagem.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@Entity(name = "locacao_maquina")
public class LocacaoMaquina extends GenericDomain implements Serializable {

	@Column(name = "descricao", nullable = false, length = 150)
	private String descricao;

	@Column(name = "tipo_cliente", nullable = false, length = 2)
	private String tipoCliente;

	@Temporal(TemporalType.DATE)
	@Column(name = "data_locacao", length = 10, nullable = false)
	private Date dataLocacao;

	@Temporal(TemporalType.DATE)
	@Column(name = "data_devolucao", length = 10, nullable = false)
	private Date dataDevolucao;

	@Temporal(TemporalType.DATE)
	@Column(name = "data_cadastro", length = 10, nullable = false)
	private Date dataCadastro;

	@Column(name = "valor_total", precision = 8, scale = 2, nullable = false)
	private BigDecimal valorTotal;

	@ManyToOne
	@JoinColumn(nullable = true)
	private Motorista motorista;

	@ManyToOne
	@JoinColumn(nullable = true)
	private ClienteFisico clienteFisico;

	@ManyToOne
	@JoinColumn(nullable = true)
	private ClienteJuridico clienteJuridico;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Funcionario funcionario;

	@Transient
	private List<ItemLocacaoMaquina> itensLocacao;

	public List<ItemLocacaoMaquina> getItensLocacao() {
		return itensLocacao;
	}

	public void setItensLocacao(List<ItemLocacaoMaquina> itensLocacao) {
		this.itensLocacao = itensLocacao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getDataLocacao() {
		return dataLocacao;
	}

	public void setDataLocacao(Date dataLocacao) {
		this.dataLocacao = dataLocacao;
	}

	public Date getDataDevolucao() {
		return dataDevolucao;
	}

	public void setDataDevolucao(Date dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Motorista getMotorista() {
		return motorista;
	}

	public void setMotorista(Motorista motorista) {
		this.motorista = motorista;
	}

	public ClienteFisico getClienteFisico() {
		return clienteFisico;
	}

	public void setClienteFisico(ClienteFisico clienteFisico) {
		this.clienteFisico = clienteFisico;
	}

	public ClienteJuridico getClienteJuridico() {
		return clienteJuridico;
	}

	public void setClienteJuridico(ClienteJuridico clienteJuridico) {
		this.clienteJuridico = clienteJuridico;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public String getTipoCliente() {
		return tipoCliente;
	}

	public void setTipoCliente(String tipoCliente) {
		this.tipoCliente = tipoCliente;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((clienteFisico == null) ? 0 : clienteFisico.hashCode());
		result = prime * result + ((clienteJuridico == null) ? 0 : clienteJuridico.hashCode());
		result = prime * result + ((dataCadastro == null) ? 0 : dataCadastro.hashCode());
		result = prime * result + ((dataDevolucao == null) ? 0 : dataDevolucao.hashCode());
		result = prime * result + ((dataLocacao == null) ? 0 : dataLocacao.hashCode());
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((funcionario == null) ? 0 : funcionario.hashCode());
		result = prime * result + ((motorista == null) ? 0 : motorista.hashCode());
		result = prime * result + ((tipoCliente == null) ? 0 : tipoCliente.hashCode());
		result = prime * result + ((valorTotal == null) ? 0 : valorTotal.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		LocacaoMaquina other = (LocacaoMaquina) obj;
		if (clienteFisico == null) {
			if (other.clienteFisico != null)
				return false;
		} else if (!clienteFisico.equals(other.clienteFisico))
			return false;
		if (clienteJuridico == null) {
			if (other.clienteJuridico != null)
				return false;
		} else if (!clienteJuridico.equals(other.clienteJuridico))
			return false;
		if (dataCadastro == null) {
			if (other.dataCadastro != null)
				return false;
		} else if (!dataCadastro.equals(other.dataCadastro))
			return false;
		if (dataDevolucao == null) {
			if (other.dataDevolucao != null)
				return false;
		} else if (!dataDevolucao.equals(other.dataDevolucao))
			return false;
		if (dataLocacao == null) {
			if (other.dataLocacao != null)
				return false;
		} else if (!dataLocacao.equals(other.dataLocacao))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (funcionario == null) {
			if (other.funcionario != null)
				return false;
		} else if (!funcionario.equals(other.funcionario))
			return false;
		if (motorista == null) {
			if (other.motorista != null)
				return false;
		} else if (!motorista.equals(other.motorista))
			return false;
		if (tipoCliente == null) {
			if (other.tipoCliente != null)
				return false;
		} else if (!tipoCliente.equals(other.tipoCliente))
			return false;
		if (valorTotal == null) {
			if (other.valorTotal != null)
				return false;
		} else if (!valorTotal.equals(other.valorTotal))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "LocacaoMaquina [descricao=" + descricao + ", tipoCliente=" + tipoCliente + ", dataLocacao="
				+ dataLocacao + ", dataDevolucao=" + dataDevolucao + ", dataCadastro=" + dataCadastro + ", valorTotal="
				+ valorTotal + ", motorista=" + motorista + ", clienteFisico=" + clienteFisico + ", clienteJuridico="
				+ clienteJuridico + ", funcionario=" + funcionario + "]";
	}

}
