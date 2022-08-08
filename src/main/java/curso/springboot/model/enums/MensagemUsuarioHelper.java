package curso.springboot.model.enums;

public enum MensagemUsuarioHelper {
	
	USUARIO_INCLUIDO_SUCESSO("Usuário incluido com sucesso!", "AVISO"),
	USUARIO_ALTERADO_SUCESSO("Usuário alterado com sucesso!", "AVISO"),
	USUARIO_EXCLUIDO_SUCESSO("Usuário excluido com sucesso!", "AVISO"),
	USUARIO_EM_EDICAO("Atenção: Usuário em Edição!", "AVISO"),
	USUARIOS_CARREGADOS("Usuários Carregados!", "AVISO"),
	TELEFONE_INCLUIDO_SUCESSO("Telefone incluido com sucesso!", "AVISO"),
	TELEFONE_EXCLUIDO_SUCESSO("Telefone excluido com sucesso!", "AVISO"),
	
	//MENSAGEM_LOGIN_NAO_ENCONTRADO("Login ou senha não encontrados!"),
	//MENSAGEM_ERRO_LOGIN("Informe o login e senha corretamente!"),
	//MENSAGEM_LOGIN_NOVAMENTE("Por favor, realize o login!"),
	//MENSAGEM_LOGIN_JA_EXISTE("Este Login já existe em nosso sistema. Por favor, digite outro login!"),
	MENSAGEM_ERRO_SISTEMA("Houve um erro no sistema! Por favor, tente novamente mais tarde!", "ERRO"),
	MENSAGEM_USUARIO_JA_CADASTRADO("Já existe um usuário com este login. Utilize outro!", "ERRO"),
	MENSAGEM_TELEFONE_JA_CADASTRADO("Este número de telefone já existe!", "ERRO");
	
	private String mensagem;
	private String tipo;
	
	
	MensagemUsuarioHelper(String mensagem, String tipo) {
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

}
