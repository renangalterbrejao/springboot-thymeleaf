package curso.springboot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import curso.springboot.model.Usuario;
import curso.springboot.repository.UsuarioRepository;

@Service
@Transactional
public class UserDetailServiceImpl implements UserDetailsService{
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String loginUsuario) throws UsernameNotFoundException {
		
		Usuario usuario = usuarioRepository.findUsuarioByLogin(loginUsuario);
		
		if(usuario == null) {
			throw new UsernameNotFoundException("O usuário não foi encontrado!");
		}
		
		return new User(usuario.getLogin(), 
				usuario.getPassword(), 
				usuario.isEnabled(),
				//usuario.isAccountNonExpired(),
				//usuario.isCredentialsNonExpired(), 
				//usuario.isAccountNonLocked(), 
				true, true, true,
				usuario.getAuthorities());
	}

}
