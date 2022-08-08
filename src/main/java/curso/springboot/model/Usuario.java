package curso.springboot.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class Usuario implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "usuario_id")
	private Long id;

	@Column(name = "usuario_login")
	private String login;

	@Column(name = "usuario_senha")
	private String senha;
	
	/* Esta opção de código para criação de tabelas de relacionamento cria uma unique key no campo de role_id
	   da tabela de relacionamento (usuario_roles), proibindo 2 registros com o campo role_id com ids iguais*/
	/*@OneToMany(fetch = FetchType.EAGER) // Trás obrigatoriamente o retorno de TODAS as roles
	@JoinTable(name = "usuario_roles",
			joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "usuario_id",
						table = "usuario"), // Cria uma tabela intermediária de relacionamento entre Usuários e Roles
			
			inverseJoinColumns = @JoinColumn(name = "role_id", 
						referencedColumnName = "role_id", 
						table = "role"))*/
	
	/* Esta opção de código para criação de tabelas de relacionamento cria um relacionamento sem uniques keys,
	   permitindo qualquer duplicação de combinação de chaves */
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "usuario_roles", joinColumns = @JoinColumn(name = "usuario_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
	private List<Role> roles = new ArrayList<Role>();

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
	}

	@Override
	public String getPassword() {
		return senha;
	}

	@Override
	public String getUsername() {
		return login;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
