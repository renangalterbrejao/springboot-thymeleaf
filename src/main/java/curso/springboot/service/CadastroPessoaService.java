package curso.springboot.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import curso.springboot.Exception.BusinessException;
import curso.springboot.model.Pessoa;
import curso.springboot.model.Telefone;
import curso.springboot.model.TipoTelefone;
import curso.springboot.model.enums.Cargo;
import curso.springboot.repository.PessoaRepository;
import curso.springboot.repository.TelefoneRepository;
import curso.springboot.repository.TipoTelefoneRepository;

@Service
public class CadastroPessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private TelefoneRepository telefoneRepository;

	@Autowired
	private TipoTelefoneRepository tipoTelefoneRepository;

	public Page<Pessoa> buscarTodasPessoasPorPaginacao(int pagina) throws BusinessException, Exception {

		Page<Pessoa> listaPessoas = pessoaRepository.findAll(PageRequest.of(pagina, 5, Sort.by(Sort.Direction.ASC, "nome")));
		//listaPessoas.sort((o1, o2) -> o1.compareTo(o2));

		return listaPessoas;
	}

	public Optional<Pessoa> buscarPessoaPorId(Long idPessoa) throws BusinessException, Exception {

		Optional<Pessoa> pessoa;

		if (idPessoa != null) {
			pessoa = pessoaRepository.findById(idPessoa);
		} else {
			throw new BusinessException("O parâmetro idPessoa não pode ser nulo!");
		}

		return pessoa;
	}

	public Page<Pessoa> buscarPessoaPorNomeSexo(int pagina, String nome, String sexo) throws Exception {

		Page<Pessoa> listaPessoas = null;

		if (sexo.isEmpty()) {
			listaPessoas = pessoaRepository.findPessoaByNomeContainingIgnoreCaseOrSobrenomeContainingIgnoreCase(nome, 
					nome, PageRequest.of(pagina, 5, Sort.by(Sort.Direction.ASC, "nome")));
		} else {

			if (nome.isEmpty()) {
				listaPessoas = pessoaRepository.findPessoaBySexo(sexo.toUpperCase(), 
						PageRequest.of(pagina, 5, Sort.by(Sort.Direction.ASC, "nome")));
			} else {
				listaPessoas = pessoaRepository.findPessoaPorNomeSobrenomeSexo(nome.toUpperCase(), nome.toUpperCase(),
						sexo.toUpperCase(), PageRequest.of(pagina, 5, Sort.by(Sort.Direction.ASC, "nome")));
			}

		}

		//listaPessoas.sort((o1, o2) -> o1.compareTo(o2));

		return listaPessoas;
	}

	public Pessoa salvarPessoa(Pessoa pessoa) throws BusinessException, Exception {

		if (pessoa != null) {
			pessoa = pessoaRepository.save(pessoa);
		} else {
			throw new BusinessException("O parâmetro pessoa não pode ser nulo!");
		}

		return pessoa;
	}

	public void excluirPessoa(Long idPessoa) throws BusinessException, Exception {

		if (!buscarPessoaPorId(idPessoa).isEmpty()) {
			pessoaRepository.deleteById(idPessoa);
		} else {
			throw new BusinessException("O parâmetro idPessoa deve ser informado!");
		}

	}

	public Optional<Telefone> buscarTelefonePorId(Long idTelefone) throws BusinessException, Exception {
		return telefoneRepository.findById(idTelefone);
	}

	public ArrayList<Telefone> buscarTelefonesPorPessoa(Pessoa pessoa) throws BusinessException, Exception {

		ArrayList<Telefone> listaTelefones = null;

		if (pessoa != null) {

			listaTelefones = new ArrayList<>();

			for (Telefone telefone : telefoneRepository.findTelefonesByPessoa(pessoa)) {
				listaTelefones.add(telefone);
			}

		} else {
			throw new BusinessException(
					"O parâmetro pessoa deve ser informado para que seja possível a busca de seus telefones!");
		}

		return listaTelefones;
	}

	public Telefone salvarTelefone(Telefone telefone, Long idPessoa) throws BusinessException, Exception {

		if (telefone != null && !telefone.getNumero().isEmpty()) {

			if (telefone.getTipoTelefone() != null) {

				Optional<Pessoa> pessoa = null;

				/* Verifica se foi escolhido uma pessoa para salvar o telefone */
				if (idPessoa != null) {
					pessoa = buscarPessoaPorId(idPessoa);

					if (!pessoa.isEmpty()) {
						telefone.setPessoa(pessoa.get());
						return telefoneRepository.save(telefone);
					} else {
						throw new BusinessException("A pessoa passada no parâmetro não foi encontrada!");
					}

					/* Caso contrário, tenta salvar o telefone com o objeto pessoa implícito */
				} else {
					return telefoneRepository.save(telefone);
				}

			} else {
				throw new BusinessException("O Tipo do telefone não pode estar vazio!");
			}

		} else {
			throw new BusinessException("O Número de telefone não pode estar vazio!");
		}

	}

	public Optional<Telefone> excluirTelefone(Long idTelefone) throws BusinessException, Exception {

		Optional<Telefone> telefone = buscarTelefonePorId(idTelefone);

		if (!telefone.isEmpty()) {
			telefoneRepository.deleteById(idTelefone);
		} else {
			throw new BusinessException("O Número de telefone não existe ou já foi excluído!");
		}

		return telefone;
	}

	public List<TipoTelefone> buscarTiposTelefones() throws BusinessException, Exception {

		List<TipoTelefone> tipoTelefone = tipoTelefoneRepository.findAll();

		if (tipoTelefone.isEmpty()) {
			throw new Exception("Não há tipos de telefones cadastrados!");
		}

		return tipoTelefone;

	}
	
	public List<Cargo> buscarCargos() throws BusinessException, Exception {
		return Arrays.asList(Cargo.values());
	}

}
