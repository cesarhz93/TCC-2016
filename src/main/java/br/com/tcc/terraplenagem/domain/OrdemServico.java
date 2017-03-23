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
@Entity(name = "ordem_servico")
public class OrdemServico extends GenericDomain implements Serializable {

	@Column(name = "tipo_cliente", nullable = false, length = 2)
	private String tipoCliente;

	@Temporal(TemporalType.DATE)
	@Column(name = "data_realizacao", length = 10, nullable = false)
	private Date dataRealizacao;

	@Temporal(TemporalType.DATE)
	@Column(name = "data_cadastro", length = 10, nullable = false)
	private Date dataCadastro;

	@Column(name = "valor_total", precision = 8, scale = 2, nullable = false)
	private BigDecimal valorTotal;

	@ManyToOne
	@JoinColumn(nullable = true)
	private ClienteFisico clienteFisico;

	@ManyToOne
	@JoinColumn(nullable = true)
	private ClienteJuridico clienteJuridico;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Funcionario funcionario;

	@ManyToOne
	@JoinColumn(nullable = false)
	private LocacaoMaquina locacaoMaquina;

	@Transient
	private List<ItemOrdemServico> itensOrdemServico;

	public List<ItemOrdemServico> getItensOrdemServico() {
		return itensOrdemServico;
	}

	public void setItensOrdemServico(List<ItemOrdemServico> itensOrdemServico) {
		this.itensOrdemServico = itensOrdemServico;
	}

	public String getTipoCliente() {
		return tipoCliente;
	}

	public void setTipoCliente(String tipoCliente) {
		this.tipoCliente = tipoCliente;
	}

	public Date getDataRealizacao() {
		return dataRealizacao;
	}

	public void setDataRealizacao(Date dataRealizacao) {
		this.dataRealizacao = dataRealizacao;
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

	public LocacaoMaquina getLocacaoMaquina() {
		return locacaoMaquina;
	}

	public void setLocacaoMaquina(LocacaoMaquina locacaoMaquina) {
		this.locacaoMaquina = locacaoMaquina;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((clienteFisico == null) ? 0 : clienteFisico.hashCode());
		result = prime * result + ((clienteJuridico == null) ? 0 : clienteJuridico.hashCode());
		result = prime * result + ((dataCadastro == null) ? 0 : dataCadastro.hashCode());
		result = prime * result + ((dataRealizacao == null) ? 0 : dataRealizacao.hashCode());
		result = prime * result + ((funcionario == null) ? 0 : funcionario.hashCode());
		result = prime * result + ((itensOrdemServico == null) ? 0 : itensOrdemServico.hashCode());
		result = prime * result + ((locacaoMaquina == null) ? 0 : locacaoMaquina.hashCode());
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
		OrdemServico other = (OrdemServico) obj;
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
		if (dataRealizacao == null) {
			if (other.dataRealizacao != null)
				return false;
		} else if (!dataRealizacao.equals(other.dataRealizacao))
			return false;
		if (funcionario == null) {
			if (other.funcionario != null)
				return false;
		} else if (!funcionario.equals(other.funcionario))
			return false;
		if (itensOrdemServico == null) {
			if (other.itensOrdemServico != null)
				return false;
		} else if (!itensOrdemServico.equals(other.itensOrdemServico))
			return false;
		if (locacaoMaquina == null) {
			if (other.locacaoMaquina != null)
				return false;
		} else if (!locacaoMaquina.equals(other.locacaoMaquina))
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
		return "OrdemServico [tipoCliente=" + tipoCliente + ", dataRealizacao=" + dataRealizacao + ", dataCadastro="
				+ dataCadastro + ", valorTotal=" + valorTotal + ", clienteFisico=" + clienteFisico
				+ ", clienteJuridico=" + clienteJuridico + ", funcionario=" + funcionario + ", locacaoMaquina="
				+ locacaoMaquina + ", itensOrdemServico=" + itensOrdemServico + "]";
	}

}
