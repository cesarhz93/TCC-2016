package br.com.tcc.terraplenagem.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity(name = "motoristas")
public class Motorista extends GenericDomain {

	@Column(name = "numero_cnh", nullable = false, length = 11)
	private String numeroCNH;

	@Column(name = "categoria", length = 10, nullable = false)
	private String categoria;

	@Temporal(TemporalType.DATE)
	@Column(name = "data_validade_cnh", nullable = false, length = 10)
	private Date dataValidadeCNH;

	@Temporal(TemporalType.DATE)
	@Column(name = "data_cadastro", nullable = false, length = 10)
	private Date dataCadastro;

	@OneToOne
	@JoinColumn(nullable = false)
	private Funcionario funcionario;

	public String getNumeroCNH() {
		return numeroCNH;
	}

	public void setNumeroCNH(String numeroCNH) {
		this.numeroCNH = numeroCNH;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public Date getDataValidadeCNH() {
		return dataValidadeCNH;
	}

	public void setDataValidadeCNH(Date dataValidadeCNH) {
		this.dataValidadeCNH = dataValidadeCNH;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((categoria == null) ? 0 : categoria.hashCode());
		result = prime * result + ((dataCadastro == null) ? 0 : dataCadastro.hashCode());
		result = prime * result + ((dataValidadeCNH == null) ? 0 : dataValidadeCNH.hashCode());
		result = prime * result + ((funcionario == null) ? 0 : funcionario.hashCode());
		result = prime * result + ((numeroCNH == null) ? 0 : numeroCNH.hashCode());
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
		Motorista other = (Motorista) obj;
		if (categoria == null) {
			if (other.categoria != null)
				return false;
		} else if (!categoria.equals(other.categoria))
			return false;
		if (dataCadastro == null) {
			if (other.dataCadastro != null)
				return false;
		} else if (!dataCadastro.equals(other.dataCadastro))
			return false;
		if (dataValidadeCNH == null) {
			if (other.dataValidadeCNH != null)
				return false;
		} else if (!dataValidadeCNH.equals(other.dataValidadeCNH))
			return false;
		if (funcionario == null) {
			if (other.funcionario != null)
				return false;
		} else if (!funcionario.equals(other.funcionario))
			return false;
		if (numeroCNH == null) {
			if (other.numeroCNH != null)
				return false;
		} else if (!numeroCNH.equals(other.numeroCNH))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Motorista [getNumeroCNH()=" + getNumeroCNH() + ", getCategoria()=" + getCategoria()
				+ ", getDataValidadeCNH()=" + getDataValidadeCNH() + ", getDataCadastro()=" + getDataCadastro()
				+ ", getFuncionario()=" + getFuncionario() + ", hashCode()=" + hashCode() + ", getCodigo()="
				+ getCodigo() + ", toString()=" + super.toString() + ", getClass()=" + getClass() + "]";
	}

}
