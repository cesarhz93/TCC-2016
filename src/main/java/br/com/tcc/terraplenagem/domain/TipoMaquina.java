package br.com.tcc.terraplenagem.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

@SuppressWarnings("serial")
@Entity(name = "tipos_maquina")
public class TipoMaquina extends GenericDomain {

	@Column(name = "tipo", length = 40, nullable = false)
	private String tipo;

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
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
		TipoMaquina other = (TipoMaquina) obj;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TipoMaquina [getTipo()=" + getTipo() + ", hashCode()=" + hashCode() + ", getCodigo()=" + getCodigo()
				+ ", toString()=" + super.toString() + ", getClass()=" + getClass() + "]";
	}

}
