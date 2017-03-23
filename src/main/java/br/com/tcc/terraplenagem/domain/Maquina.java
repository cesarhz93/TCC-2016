package br.com.tcc.terraplenagem.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@Entity(name = "maquinas")
public class Maquina extends GenericDomain {

	@Column(name = "descricao", length = 100, nullable = false)
	private String descricao;

	@Column(name = "placa", length = 8, nullable = true)
	private String placa;

	@Column(name = "marca", length = 40, nullable = false)
	private String marca;

	@Column(name = "modelo", length = 40, nullable = false)
	private String modelo;

	@Column(name = "status", nullable = false)
	private Character status;

	@Column(name = "capacidade_metros_cubicos", scale = 2, precision = 8, nullable = true)
	private BigDecimal capacidadeMetrosCubicos;

	@Column(name = "valor_unitario_locacao", scale = 2, precision = 8, nullable = true)
	private BigDecimal valorUnitarioLocacao;

	@Column(name = "valor_pago", scale = 2, precision = 8, nullable = false)
	private BigDecimal valorPago;

	@Temporal(TemporalType.DATE)
	@Column(name = "data_compra", length = 10, nullable = true)
	private Date dataCompra;

	@Column(name = "combustivel", length = 25, nullable = false)
	private String combustivel;

	@Temporal(TemporalType.DATE)
	@Column(name = "data_limite_garantia", length = 10, nullable = true)
	private Date dataLimiteGarantia;

	@Column(name = "profundidade_maxima_escavacao", scale = 2, precision = 8, nullable = true)
	private BigDecimal profundidadeMaximaEscavacao;

	@Temporal(TemporalType.DATE)
	@Column(name = "data_cadastro", length = 10, nullable = true)
	private Date dataCadastro;

	@ManyToOne
	@JoinColumn(nullable = false)
	private TipoMaquina tipoMaquina;

	@Transient
	private String caminho;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public Character getStatus() {
		return status;
	}

	public void setStatus(Character status) {
		this.status = status;
	}

	public BigDecimal getCapacidadeMetrosCubicos() {
		return capacidadeMetrosCubicos;
	}

	public void setCapacidadeMetrosCubicos(BigDecimal capacidadeMetrosCubicos) {
		this.capacidadeMetrosCubicos = capacidadeMetrosCubicos;
	}

	public BigDecimal getValorPago() {
		return valorPago;
	}

	public void setValorPago(BigDecimal valorPago) {
		this.valorPago = valorPago;
	}

	public Date getDataCompra() {
		return dataCompra;
	}

	public void setDataCompra(Date dataCompra) {
		this.dataCompra = dataCompra;
	}

	public String getCombustivel() {
		return combustivel;
	}

	public void setCombustivel(String combustivel) {
		this.combustivel = combustivel;
	}

	public Date getDataLimiteGarantia() {
		return dataLimiteGarantia;
	}

	public void setDataLimiteGarantia(Date dataLimiteGarantia) {
		this.dataLimiteGarantia = dataLimiteGarantia;
	}

	public BigDecimal getProfundidadeMaximaEscavacao() {
		return profundidadeMaximaEscavacao;
	}

	public void setProfundidadeMaximaEscavacao(BigDecimal profundidadeMaximaEscavacao) {
		this.profundidadeMaximaEscavacao = profundidadeMaximaEscavacao;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public TipoMaquina getTipoMaquina() {
		return tipoMaquina;
	}

	public void setTipoMaquina(TipoMaquina tipoMaquina) {
		this.tipoMaquina = tipoMaquina;
	}

	public BigDecimal getValorUnitarioLocacao() {
		return valorUnitarioLocacao;
	}

	public void setValorUnitarioLocacao(BigDecimal valorUnitarioLocacao) {
		this.valorUnitarioLocacao = valorUnitarioLocacao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((capacidadeMetrosCubicos == null) ? 0 : capacidadeMetrosCubicos.hashCode());
		result = prime * result + ((combustivel == null) ? 0 : combustivel.hashCode());
		result = prime * result + ((dataCadastro == null) ? 0 : dataCadastro.hashCode());
		result = prime * result + ((dataCompra == null) ? 0 : dataCompra.hashCode());
		result = prime * result + ((dataLimiteGarantia == null) ? 0 : dataLimiteGarantia.hashCode());
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((marca == null) ? 0 : marca.hashCode());
		result = prime * result + ((modelo == null) ? 0 : modelo.hashCode());
		result = prime * result + ((placa == null) ? 0 : placa.hashCode());
		result = prime * result + ((profundidadeMaximaEscavacao == null) ? 0 : profundidadeMaximaEscavacao.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((tipoMaquina == null) ? 0 : tipoMaquina.hashCode());
		result = prime * result + ((valorPago == null) ? 0 : valorPago.hashCode());
		result = prime * result + ((valorUnitarioLocacao == null) ? 0 : valorUnitarioLocacao.hashCode());
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
		Maquina other = (Maquina) obj;
		if (capacidadeMetrosCubicos == null) {
			if (other.capacidadeMetrosCubicos != null)
				return false;
		} else if (!capacidadeMetrosCubicos.equals(other.capacidadeMetrosCubicos))
			return false;
		if (combustivel == null) {
			if (other.combustivel != null)
				return false;
		} else if (!combustivel.equals(other.combustivel))
			return false;
		if (dataCadastro == null) {
			if (other.dataCadastro != null)
				return false;
		} else if (!dataCadastro.equals(other.dataCadastro))
			return false;
		if (dataCompra == null) {
			if (other.dataCompra != null)
				return false;
		} else if (!dataCompra.equals(other.dataCompra))
			return false;
		if (dataLimiteGarantia == null) {
			if (other.dataLimiteGarantia != null)
				return false;
		} else if (!dataLimiteGarantia.equals(other.dataLimiteGarantia))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (marca == null) {
			if (other.marca != null)
				return false;
		} else if (!marca.equals(other.marca))
			return false;
		if (modelo == null) {
			if (other.modelo != null)
				return false;
		} else if (!modelo.equals(other.modelo))
			return false;
		if (placa == null) {
			if (other.placa != null)
				return false;
		} else if (!placa.equals(other.placa))
			return false;
		if (profundidadeMaximaEscavacao == null) {
			if (other.profundidadeMaximaEscavacao != null)
				return false;
		} else if (!profundidadeMaximaEscavacao.equals(other.profundidadeMaximaEscavacao))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (tipoMaquina == null) {
			if (other.tipoMaquina != null)
				return false;
		} else if (!tipoMaquina.equals(other.tipoMaquina))
			return false;
		if (valorPago == null) {
			if (other.valorPago != null)
				return false;
		} else if (!valorPago.equals(other.valorPago))
			return false;
		if (valorUnitarioLocacao == null) {
			if (other.valorUnitarioLocacao != null)
				return false;
		} else if (!valorUnitarioLocacao.equals(other.valorUnitarioLocacao))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Maquina [descricao=" + descricao + ", placa=" + placa + ", marca=" + marca + ", modelo=" + modelo
				+ ", status=" + status + ", capacidadeMetrosCubicos=" + capacidadeMetrosCubicos
				+ ", valorUnitarioLocacao=" + valorUnitarioLocacao + ", valorPago=" + valorPago + ", dataCompra="
				+ dataCompra + ", combustivel=" + combustivel + ", dataLimiteGarantia=" + dataLimiteGarantia
				+ ", profundidadeMaximaEscavacao=" + profundidadeMaximaEscavacao + ", dataCadastro=" + dataCadastro
				+ ", tipoMaquina=" + tipoMaquina + "]";
	}

	public String getCaminho() {
		return caminho;
	}

	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}

}
