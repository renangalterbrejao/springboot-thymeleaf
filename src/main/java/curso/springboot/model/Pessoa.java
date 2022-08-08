package curso.springboot.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import curso.springboot.model.enums.Cargo;

@Entity
public class Pessoa implements Serializable, Comparable<Pessoa> {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "pessoa_id")
	private Long id;
	
	@NotNull(message = "O nome da pessoa não pode ser nulo")
	@NotEmpty(message = "O nome da pessoa não pode ser vazio")
	@Column(name = "pessoa_nome")
	private String nome;
	
	@NotNull(message = "O sobrenome da pessoa não pode ser nulo")
	@NotEmpty(message = "O sobrenome da pessoa não pode ser vazio")
	@Column(name = "pessoa_sobrenome")
	private String sobrenome;
	
	@Min(value = 18, message = "Idade Inválida")
	@NotNull(message = "A idade da pessoa não pode ser nula")
	@Column(name = "pessoa_idade")
	private Integer idade;
	
	@NotNull(message = "O campo sexo não pode ser nulo")
	@NotEmpty(message = "O campo sexo não pode ser nulo")
	@Column(name = "pessoa_sexo")
	private String sexo;
	
	@Column(name = "pessoa_cargo")
	@Enumerated(EnumType.STRING)
	private Cargo cargo;
	
	@OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL)
	private List<Telefone> listaTelefones = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public Integer getIdade() {
		return idade;
	}

	public void setIdade(Integer idade) {
		this.idade = idade;
	}

	public List<Telefone> getListaTelefones() {
		return listaTelefones;
	}

	public void setListaTelefones(List<Telefone> listaTelefones) {
		this.listaTelefones = listaTelefones;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idade == null) ? 0 : idade.hashCode());
		result = prime * result + ((listaTelefones == null) ? 0 : listaTelefones.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((sobrenome == null) ? 0 : sobrenome.hashCode());
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
		Pessoa other = (Pessoa) obj;
		if (idade == null) {
			if (other.idade != null)
				return false;
		} else if (!idade.equals(other.idade))
			return false;
		if (listaTelefones == null) {
			if (other.listaTelefones != null)
				return false;
		} else if (!listaTelefones.equals(other.listaTelefones))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (sobrenome == null) {
			if (other.sobrenome != null)
				return false;
		} else if (!sobrenome.equals(other.sobrenome))
			return false;
		return true;
	}
	
	@Override
	public int compareTo(Pessoa other) {
		return nome.compareTo(other.getNome());
	}

	@Override
	public String toString() {
		return "Pessoa [id=" + id + ", nome=" + nome + ", sobrenome=" + sobrenome + ", idade=" + idade
				+ ", listaTelefones=" + listaTelefones + "]";
	}

}
