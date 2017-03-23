package br.com.tcc.terraplenagem.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@SuppressWarnings("serial")
@Entity(name = "item_locacao_maquina")
public class ItemLocacaoMaquina extends GenericDomain {

	@Column(name = "quantidade", nullable = false)
	private Integer quantidade;

	@Column(name = "valor_unitario", precision = 8, scale = 2, nullable = false)
	private BigDecimal valorUnitario;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Maquina maquina;

	@ManyToOne
	@JoinColumn(nullable = false)
	private LocacaoMaquina locacaoMaquina;

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public Maquina getMaquina() {
		return maquina;
	}

	public void setMaquina(Maquina maquina) {
		this.maquina = maquina;
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
		result = prime * result + ((locacaoMaquina == null) ? 0 : locacaoMaquina.hashCode());
		result = prime * result + ((maquina == null) ? 0 : maquina.hashCode());
		result = prime * result + ((quantidade == null) ? 0 : quantidade.hashCode());
		result = prime * result + ((valorUnitario == null) ? 0 : valorUnitario.hashCode());
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
		ItemLocacaoMaquina other = (ItemLocacaoMaquina) obj;
		if (locacaoMaquina == null) {
			if (other.locacaoMaquina != null)
				return false;
		} else if (!locacaoMaquina.equals(other.locacaoMaquina))
			return false;
		if (maquina == null) {
			if (other.maquina != null)
				return false;
		} else if (!maquina.equals(other.maquina))
			return false;
		if (quantidade == null) {
			if (other.quantidade != null)
				return false;
		} else if (!quantidade.equals(other.quantidade))
			return false;
		if (valorUnitario == null) {
			if (other.valorUnitario != null)
				return false;
		} else if (!valorUnitario.equals(other.valorUnitario))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ItemLocacaoMaquina [getQuantidade()=" + getQuantidade() + ", getValorUnitario()=" + getValorUnitario()
				+ ", getMaquina()=" + getMaquina() + ", getLocacaoMaquina()=" + getLocacaoMaquina() + ", hashCode()="
				+ hashCode() + ", getCodigo()=" + getCodigo() + ", toString()=" + super.toString() + ", getClass()="
				+ getClass() + "]";
	}

}
