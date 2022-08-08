package curso.springboot.Exception;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private Object objeto;
	
	public BusinessException(String mensagem) {
		super(mensagem);
		System.out.println(mensagem);
	}
	
	public BusinessException(String mensagem, Throwable causa) {
		super(mensagem, causa);
		System.out.println(mensagem);
		System.out.println(causa.getStackTrace());
	}
	
	public BusinessException(String mensagem, Throwable causa, Object objeto) {
		super(mensagem, causa);
		this.objeto = objeto;
		System.out.println(mensagem);
		System.out.println(causa.getStackTrace());
	}

	public Object getObjeto() {
		return objeto;
	}

	public void setObjeto(Object objeto) {
		this.objeto = objeto;
	}

}
