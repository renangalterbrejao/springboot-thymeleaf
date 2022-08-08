package curso.springboot.model.enums;

public enum Cargo {
	
	JUNIOR("Júnior"),
	PLENO("Pleno"),
	SENIOR("Sênior");
	
	private String nome;
	
	private Cargo(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
