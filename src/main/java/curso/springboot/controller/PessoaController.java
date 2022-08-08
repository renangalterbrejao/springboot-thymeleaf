package curso.springboot.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import curso.springboot.Exception.BusinessException;
import curso.springboot.model.Pessoa;
import curso.springboot.model.RotaHelper;
import curso.springboot.model.Telefone;
import curso.springboot.model.TipoTelefone;
import curso.springboot.model.enums.Cargo;
import curso.springboot.model.enums.MensagemUsuarioHelper;
import curso.springboot.repository.PessoaRepository;
import curso.springboot.service.CadastroPessoaService;

@Controller
public class PessoaController extends AbstractController {

	@Autowired
	private CadastroPessoaService pessoaService;
	
	@Autowired
	private PessoaRepository pessoaRepository;

	@RequestMapping(method = RequestMethod.GET, value = "/cadastropessoa")
	public ModelAndView inicio() {

		ModelAndView modelAndView = listarPessoas();
		modelAndView.addObject("pessoaobj", new Pessoa());
		
		buscarCargos(modelAndView);
		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/salvarpessoa")
	public ModelAndView salvar(@Valid Pessoa pessoa, BindingResult bindingResult) {

		ModelAndView modelAndView = new ModelAndView(RotaHelper.CADASTRO_USUARIOS);

		if (!bindingResult.hasErrors()) {

			try {
				
				boolean usuarioExiste = false; 
				
				if(pessoa.getId() != null) {
					usuarioExiste = true;
				}
				
				pessoaService.salvarPessoa(pessoa);
				modelAndView = listarPessoas();
				modelAndView.addObject("pessoaobj", new Pessoa());
				
				if(usuarioExiste) {
					adicionaMensagemUsuario(modelAndView, MensagemUsuarioHelper.USUARIO_ALTERADO_SUCESSO);
				} else {
					adicionaMensagemUsuario(modelAndView, MensagemUsuarioHelper.USUARIO_INCLUIDO_SUCESSO);
				}
				
				buscarCargos(modelAndView);
				return modelAndView;
				
			} catch (Exception e) {
				modelAndView = listarPessoas();
				adicionaMensagemUsuario(modelAndView, e.getMessage(), VARIAVEL_MENSAGEM_ERRO);
				modelAndView.addObject("pessoaobj", pessoa);
			}

		} else {
			modelAndView = listarPessoas();
			adicionaMensagemUsuarioBindingResult(modelAndView, bindingResult);
			modelAndView.addObject("pessoaobj", pessoa);
		}
		
		buscarCargos(modelAndView);
		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/listapessoas")
	public ModelAndView listarPessoas() {
		ModelAndView modelAndView = new ModelAndView(RotaHelper.CADASTRO_USUARIOS);

		try {
			modelAndView.addObject("pessoas", pessoaService.buscarTodasPessoasPorPaginacao(0));
			
		} catch (BusinessException e) {
			adicionaMensagemUsuario(modelAndView, e.getMessage(), VARIAVEL_MENSAGEM_ERRO);
			modelAndView.addObject("pessoas", new ArrayList<Pessoa>());
			
		} catch (Exception e) {
			modelAndView.addObject("pessoas", new ArrayList<Pessoa>());
		}

		modelAndView.addObject("pessoaobj", new Pessoa());
		buscarCargos(modelAndView);
		return modelAndView;
	}
	
	/*Teste! Refatorar depois! ================================================================================*/
	@GetMapping("/listapessoaspaginadas")
	public ModelAndView listarPessoasPaginadas(@PageableDefault(size = 5) Pageable pageable, 
			ModelAndView modelAndView, String nomePesquisa, String sexoPesquisa) {
		
		Page<Pessoa> pagePessoas = null;
	
		try {
			//pagePessoas = pessoaService.buscarTodasPessoasPorPaginacao(pageable.getPageNumber());
			pagePessoas = pessoaService.buscarPessoaPorNomeSexo(pageable.getPageNumber(), nomePesquisa, sexoPesquisa);
			
		} catch (BusinessException e) {
			adicionaMensagemUsuario(modelAndView, e.getMessage(), VARIAVEL_MENSAGEM_ERRO);
			modelAndView.addObject("pessoas", new ArrayList<Pessoa>());
			
		} catch (Exception e) {
			modelAndView.addObject("pessoas", new ArrayList<Pessoa>());
		}
			
		modelAndView.addObject("pessoas", pagePessoas);
		modelAndView.addObject("pessoaobj", new Pessoa());
		buscarCargos(modelAndView);
		modelAndView.addObject("nomePesquisa", nomePesquisa);
		modelAndView.addObject("sexoPesquisa", sexoPesquisa);
		modelAndView.addObject("paginaAtual", pageable.getPageNumber());
		modelAndView.setViewName(RotaHelper.CADASTRO_USUARIOS);
		
		return modelAndView;
		
	}

	@GetMapping("/editarpessoa/{idpessoa}")
	public ModelAndView editar(@PathVariable("idpessoa") Long idPessoa) {

		ModelAndView modelAndView = listarPessoas();

		try {
			Optional<Pessoa> pessoa = pessoaService.buscarPessoaPorId(idPessoa);
			modelAndView.addObject("pessoaobj", pessoa.get());
			adicionaMensagemUsuario(modelAndView, MensagemUsuarioHelper.USUARIO_EM_EDICAO);

		} catch (Exception e) {

			adicionaMensagemUsuario(modelAndView, e.getMessage(), VARIAVEL_MENSAGEM_ERRO);
			modelAndView.addObject("pessoaobj", new Pessoa());
		}
		
		buscarCargos(modelAndView);
		return modelAndView;
	}

	@GetMapping("/excluirpessoa/{idpessoa}")
	public ModelAndView excluir(@PathVariable("idpessoa") Long idPessoa) {

		String possivelErro = null;

		if (idPessoa != null) {
			try {
				pessoaService.excluirPessoa(idPessoa);
				
			} catch (Exception e) {
				possivelErro = e.getMessage();
			}
		}

		ModelAndView modelAndView = listarPessoas();

		if (possivelErro != null) {
			adicionaMensagemUsuario(modelAndView, possivelErro, VARIAVEL_MENSAGEM_ERRO);
		} else {
			adicionaMensagemUsuario(modelAndView, MensagemUsuarioHelper.USUARIO_EXCLUIDO_SUCESSO);
		}
		
		buscarCargos(modelAndView);
		return modelAndView;
	}

	@PostMapping("/pesquisarpessoa")
	public ModelAndView pesquisarPorNome(@RequestParam("nomePesquisa") String nome,
			@RequestParam("pesqSexo") String pesqSexo) {

		ModelAndView modelAndView = new ModelAndView(RotaHelper.CADASTRO_USUARIOS);

		try {
			Page<Pessoa> listaPessoas = pessoaService.buscarPessoaPorNomeSexo(0, nome, pesqSexo);
			modelAndView.addObject("pessoas", listaPessoas);
			
		} catch (Exception e) {
			
			adicionaMensagemUsuario(modelAndView, e.getMessage(), VARIAVEL_MENSAGEM_ERRO);
			modelAndView.addObject("pessoas", new ArrayList<Pessoa>());
		}
		
		buscarCargos(modelAndView);
		modelAndView.addObject("nomePesquisa", nome);
		modelAndView.addObject("sexoPesquisa", pesqSexo);
		modelAndView.addObject("paginaAtual", 0);
		modelAndView.addObject("pessoaobj", new Pessoa());

		return modelAndView;
	}

	@GetMapping("/telefones/{idpessoa}")
	public ModelAndView telefones(@PathVariable("idpessoa") Long idPessoa) {

		ModelAndView modelAndView = new ModelAndView(RotaHelper.CADASTRO_TELEFONES);

		try {
			Optional<Pessoa> pessoa = pessoaService.buscarPessoaPorId(idPessoa);
			modelAndView.addObject("pessoaobj", pessoa.get());
			buscarTelefonesPorPessoa(modelAndView, pessoa.get());
			
		} catch (Exception e) {
			
			modelAndView = listarPessoas();
			adicionaMensagemUsuario(modelAndView, e.getMessage(), VARIAVEL_MENSAGEM_ERRO);
			modelAndView.addObject("pessoaobj", new Pessoa());
			
		}
		
		buscarTiposTelefones(modelAndView);
		
		return modelAndView;
	}

	@PostMapping("/addfonepessoa/{idpessoa}")
	public ModelAndView addFonePessoa(Telefone telefone, @PathVariable("idpessoa") Long idPessoa) {

		ModelAndView modelAndView = new ModelAndView(RotaHelper.CADASTRO_TELEFONES);

		try {

			Telefone telefoneSalvo = pessoaService.salvarTelefone(telefone, idPessoa);
			buscarTelefonesPorPessoa(modelAndView, telefoneSalvo.getPessoa());
			modelAndView.addObject("pessoaobj", telefoneSalvo.getPessoa());
			adicionaMensagemUsuario(modelAndView, MensagemUsuarioHelper.TELEFONE_INCLUIDO_SUCESSO);

		} catch (Exception e) {
			
			adicionaMensagemUsuario(modelAndView, e.getMessage(), VARIAVEL_MENSAGEM_ERRO);
			Optional<Pessoa> pessoa = null;
			
			try {
				pessoa = pessoaService.buscarPessoaPorId(idPessoa);
				modelAndView.addObject("pessoaobj", pessoa.get());
				buscarTelefonesPorPessoa(modelAndView, pessoa.get());
				
			} catch (Exception e1) {
				modelAndView = listarPessoas();
			}
			
		}
		
		buscarTiposTelefones(modelAndView);

		return modelAndView;
	}

	@GetMapping("/excluirtelefone/{idtelefone}")
	public ModelAndView excluirTelefone(@PathVariable("idtelefone") Long idTelefone) {

		ModelAndView modelAndView = new ModelAndView(RotaHelper.CADASTRO_TELEFONES);

		try {
			Optional<Telefone> telefone = pessoaService.excluirTelefone(idTelefone);
			modelAndView.addObject("pessoaobj", telefone.get().getPessoa());
			buscarTelefonesPorPessoa(modelAndView, telefone.get().getPessoa());
			adicionaMensagemUsuario(modelAndView, MensagemUsuarioHelper.TELEFONE_EXCLUIDO_SUCESSO);
			
			buscarTiposTelefones(modelAndView);

			return modelAndView;
			
		} catch (Exception e) {
			adicionaMensagemUsuario(modelAndView, e.getMessage(), VARIAVEL_MENSAGEM_ERRO);
		}

		/*
		 * Construir pedaço de código que irá recuperar o idPessoa da sessão ou que irá
		 * receber o idpessoa na url, via thymeleaf
		 */

		modelAndView = listarPessoas();

		return modelAndView;
	}

	private void buscarTelefonesPorPessoa(ModelAndView modelAndView, Pessoa pessoa) {

		try {
			ArrayList<Telefone> listaTelefones = pessoaService.buscarTelefonesPorPessoa(pessoa);
			modelAndView.addObject("telefones", listaTelefones);
			
		} catch (Exception e) {
			
			adicionaMensagemUsuario(modelAndView, e.getMessage(), "ERRO");
			modelAndView = new ModelAndView(RotaHelper.CADASTRO_USUARIOS);
		}

	}
	
	private void buscarTiposTelefones(ModelAndView modelAndView) {
		
		List<TipoTelefone> tiposTelefones = null;
		
		try {
			tiposTelefones = pessoaService.buscarTiposTelefones();
			
		} catch (Exception e) {
			
			modelAndView = listarPessoas();
			adicionaMensagemUsuario(modelAndView, e.getMessage(), VARIAVEL_MENSAGEM_ERRO);
			modelAndView.addObject("pessoaobj", new Pessoa());
			
		}
		
		modelAndView.addObject("tiposTelefones", tiposTelefones);
	}
	
	private void buscarCargos(ModelAndView modelAndView) {
		
		List<Cargo> cargos = null;
		
		try {
			cargos = pessoaService.buscarCargos();
			
		} catch (Exception e) {
			
			modelAndView = listarPessoas();
			adicionaMensagemUsuario(modelAndView, e.getMessage(), VARIAVEL_MENSAGEM_ERRO);
			modelAndView.addObject("pessoaobj", new Pessoa());
			
		}
		
		modelAndView.addObject("cargos", cargos);
	}

}
