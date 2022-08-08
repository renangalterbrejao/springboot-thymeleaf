package curso.springboot.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.ModelAndView;

import curso.springboot.model.MensagemUsuario;
import curso.springboot.model.RotaHelper;
import curso.springboot.model.enums.MensagemUsuarioHelper;

public abstract class AbstractController {
	
	@Autowired
	protected RotaHelper rotaHelper;
	
	protected MensagemUsuarioHelper mensagemUsuarioHelper;
	
	private static String VARIAVEL_MENSAGEM_USUARIO = "msg";
	protected static String VARIAVEL_MENSAGEM_ERRO = "ERRO";
	protected static String VARIAVEL_MENSAGEM_AVISO = "AVISO";

	@SuppressWarnings("unchecked")
	protected void adicionaMensagemUsuario(ModelAndView modelAndView, MensagemUsuarioHelper mensagem) {
		
		MensagemUsuario mensagemUsuario = new MensagemUsuario(mensagem.getMensagem(), mensagem.getTipo());
		
		List<MensagemUsuario> listaMensagens = manipulaListaMensagem(modelAndView, mensagemUsuario);

		modelAndView.addObject(VARIAVEL_MENSAGEM_USUARIO, listaMensagens);

	}
	
	@SuppressWarnings("unchecked")
	protected void adicionaMensagemUsuario(ModelAndView modelAndView, String mensagem, String tipo) {
		
		MensagemUsuario mensagemUsuario = new MensagemUsuario(mensagem, tipo);
		
		List<MensagemUsuario> listaMensagens = manipulaListaMensagem(modelAndView, mensagemUsuario);

		modelAndView.addObject(VARIAVEL_MENSAGEM_USUARIO, listaMensagens);

	}

	@SuppressWarnings("unchecked")
	protected void adicionaMensagemUsuarioBindingResult(ModelAndView modelAndView, BindingResult bindingResult) {

		for (ObjectError objError : bindingResult.getAllErrors()) {
			adicionaMensagemUsuario(modelAndView, objError.getDefaultMessage(), VARIAVEL_MENSAGEM_ERRO);
		}

	}
	
	private List<MensagemUsuario> manipulaListaMensagem(ModelAndView modelAndView, MensagemUsuario mensagemUsuario) {
		
		List<MensagemUsuario> listaMensagens = new ArrayList<>();

		if (modelAndView.getModelMap().get(VARIAVEL_MENSAGEM_USUARIO) != null) {

			if (modelAndView.getModelMap().get(VARIAVEL_MENSAGEM_USUARIO) instanceof ArrayList) {

				List<MensagemUsuario> listaMsg = (List<MensagemUsuario>) modelAndView.getModelMap().get(VARIAVEL_MENSAGEM_USUARIO);

				for (MensagemUsuario objsMsg : listaMsg) {
					listaMensagens.add(objsMsg);
				}

				listaMensagens.add(mensagemUsuario);
			}

		} else {
			listaMensagens.add(mensagemUsuario);
		}
		
		return listaMensagens;
		
	}

}
