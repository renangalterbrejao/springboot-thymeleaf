package curso.springboot.model;

import java.io.Serializable;

public final class MensagemUsuario implements Serializable, Comparable<MensagemUsuario> {
	
	private static final long serialVersionUID = 1L;
	private String mensagem;
	private String tipo;
	
	public MensagemUsuario(String mensagem, String tipo) {
		this.mensagem = mensagem;
		this.tipo = tipo;
	}
	
	public String getMensagem() {
		return mensagem;
	}
	
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mensagem == null) ? 0 : mensagem.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MensagemUsuario other = (MensagemUsuario) obj;
		if (mensagem == null) {
			if (other.mensagem != null)
				return false;
		} else if (!mensagem.equals(other.mensagem))
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		return true;
	}
	
	@Override
	public int compareTo(MensagemUsuario other) {
		return mensagem.compareTo(other.getMensagem());
	}
	
	@Override
	public String toString() {
		return "MensagemUsuario [mensagem=" + mensagem + ", tipo=" + tipo + "]";
	}
	
}
