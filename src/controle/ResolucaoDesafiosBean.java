package controle;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.model.ListDataModel;

import org.primefaces.event.FlowEvent;

import dao.ComentarioDAO;
import dao.DesafioDAO;
import dao.PesquisadorDAO;
import dao.RespostaDAO;
import modelo.Comentario;
import modelo.Desafio;
import modelo.Pesquisador;
import modelo.Resposta;
import modelo.Usuario;
import util.EmailUtil;
import util.FabricaConexao;
import util.JSFUtil;
import util.SessaoUtil;
import util.TipoObjeto;
import util.TipoOperacao;

@ManagedBean
public class ResolucaoDesafiosBean {

	Desafio desafio;
	private List<Desafio> listaDesafios;
	private boolean desafioSelecionado = false;
	
	private Resposta novaResposta;
	private Resposta respostaSelecionada;
	private ListDataModel<Resposta> respostas;
	
	private boolean skip;
	
	private Comentario comentario;
	private List<Comentario> listaComentarios;
	
	//INICIO DOS METODOS GETS E SETS
	
	public Desafio getDesafio() {
		return desafio;
	}

	public void setDesafio(Desafio desafio) {
		this.desafio = desafio;
		//this.respostas = new ListDataModel<Resposta>(this.desafio.getRespostas());
		this.desafioSelecionado = true;
	}
	
	public ListDataModel<Resposta> getRespostas() {
		return respostas;
	}

	public void setRespostas(ListDataModel<Resposta> respostas) {
		this.respostas = respostas;
	}
	
	public List<Desafio> getListaDesafios() {
		return listaDesafios;
	}

	public void setListaDesafios(List<Desafio> listaDesafios) {
		this.listaDesafios = listaDesafios;
	}
	
	public boolean isDesafioSelecionado() {
		return desafioSelecionado;
	}
	
	public void setDesafioSelecionado(boolean desafioSelecionado) {
		this.desafioSelecionado = desafioSelecionado;
	}
	
	public Resposta getNovaResposta() {
		return novaResposta;
	}

	public void setNovaResposta(Resposta novaResposta) {
		this.novaResposta = novaResposta;
	}
	
	public Resposta getRespostaSelecionada() {
		return respostaSelecionada;
	}
	
	public void setRespostaSelecionada(Resposta respostaSelecionada) {
		this.respostaSelecionada = respostaSelecionada;
	}
	
	public boolean isSkip() {
		return skip;
	}

	public void setSkip(boolean skip) {
		this.skip = skip;
	}
	
	public Comentario getComentario() {
		return comentario;
	}

	public void setComentario(Comentario comentario) {
		this.comentario = comentario;
	}

	public List<Comentario> getListaComentarios() {
		return listaComentarios;
	}

	public void setListaComentarios(List<Comentario> listaComentarios) {
		this.listaComentarios = listaComentarios;
	}
	
	//FIM DOS METODOS GETS E SETS

	public void CarregarRespostasDoDesafioSelecionado() {
		try {
						
			this.respostas = new ListDataModel<Resposta>(this.desafio.getRespostas());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void PrepararNovaResposta() {
		
		this.novaResposta = new Resposta();
		
		try {
			
			FabricaConexao fabrica = new FabricaConexao();
			Connection conexao = fabrica.fazerConexao();

			RespostaDAO dao = new RespostaDAO(conexao);
			
			this.novaResposta.setId(dao.PegarID());
			this.novaResposta.setDataCadastro(new Date());
			this.novaResposta.setStatus(false);
			this.novaResposta.setValidacoes(0);
			this.novaResposta.setDesafio(this.desafio.getId());
			Pesquisador pesquisador = new Pesquisador(SessaoUtil.pegarUsuarioSessao());
			this.novaResposta.setPesquisador(pesquisador);
			
		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
	}
	
	public void CadastrarResposta() {        
		try {
			
			FabricaConexao fabrica = new FabricaConexao();
			Connection conexao = fabrica.fazerConexao();

			RespostaDAO dao = new RespostaDAO(conexao);
			dao.Inserir(this.novaResposta);
			
			//EnviarEmail();

			this.desafio.getRespostas().add(this.novaResposta);		
			this.respostas = new ListDataModel<Resposta>(this.desafio.getRespostas());
			
			PesquisarDesafios();

			fabrica.fecharConexao();
			
			EnviarEmailPesquisadoresDoProjeto(TipoOperacao.CREATE, TipoObjeto.RESPOSTA);

			JSFUtil.adicionarMensagemSucesso("Answer successfully registered!");
			
		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
    }
	
	public void PrepararAtualizarResposta() {
		try {
			this.respostaSelecionada = respostas.getRowData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void PrepararExcluirResposta() {
		try {
			this.respostaSelecionada = respostas.getRowData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void AtualizarResposta() {
		try {
			
			FabricaConexao fabrica = new FabricaConexao();
			Connection conexao = fabrica.fazerConexao();

			RespostaDAO dao = new RespostaDAO(conexao);
			dao.Atualizar(this.respostaSelecionada);
				
			this.respostas = new ListDataModel<Resposta>(this.desafio.getRespostas());
			
			PesquisarDesafios();

			fabrica.fecharConexao();

			//EnviarEmailPesquisadoresDoProjeto(TipoOperacao.DELETE, TipoObjeto.RESPOSTA);
			
			JSFUtil.adicionarMensagemSucesso("Answer updated successfully!");
			
		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro("Check if there are no registered comments for this answer!");
		}
	}
	
	public void ExcluirResposta() {
		try {
			
			FabricaConexao fabrica = new FabricaConexao();
			Connection conexao = fabrica.fazerConexao();

			RespostaDAO dao = new RespostaDAO(conexao);
			dao.Excluir(this.respostaSelecionada);
			
			this.desafio.getRespostas().remove(this.respostaSelecionada);		
			this.respostas = new ListDataModel<Resposta>(this.desafio.getRespostas());
			
			PesquisarDesafios();

			fabrica.fecharConexao();

			EnviarEmailPesquisadoresDoProjeto(TipoOperacao.DELETE, TipoObjeto.RESPOSTA);
			
			JSFUtil.adicionarMensagemSucesso("Answer deleted successfully!");
			
		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro("Check if there are no registered comments for this answer!");
		}
	}
	
	public void PrepararAbrirComentarios() {
		
		try {
			
			this.respostaSelecionada = respostas.getRowData();
			
			CarregarComentariosDaResposta();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void CarregarComentariosDaResposta() {
		
		try {
			FabricaConexao fabrica = new FabricaConexao();
			Connection conexao = fabrica.fazerConexao();

			ComentarioDAO dao = new ComentarioDAO(conexao);
			
			this.listaComentarios = dao.TodasComentariosDaResposta(this.respostaSelecionada);
			
			fabrica.fecharConexao();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void PrepararNovoComentario() {
		
		this.comentario = new Comentario();
		
		try {
			
			FabricaConexao fabrica = new FabricaConexao();
			Connection conexao = fabrica.fazerConexao();

			ComentarioDAO dao = new ComentarioDAO(conexao);
			
			this.comentario.setPesquisador(new Pesquisador(SessaoUtil.pegarUsuarioSessao()));
			this.comentario.setId(dao.PegarID());
			
		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	public void CadastrarComentario() {
		try {
			
			this.comentario.setDataCadastro(new Date());
			this.comentario.setResposta(this.respostaSelecionada);
			
			FabricaConexao fabrica = new FabricaConexao();
			Connection conexao = fabrica.fazerConexao();

			ComentarioDAO dao = new ComentarioDAO(conexao);
			dao.Inserir(this.comentario);

			CarregarComentariosDaResposta();

			fabrica.fecharConexao();

			JSFUtil.adicionarMensagemSucesso("Comment successfully registered!");
			
		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
	}
	
	public void EditarComentario() {
		try {
			
			FabricaConexao fabrica = new FabricaConexao();
			Connection conexao = fabrica.fazerConexao();

			ComentarioDAO dao = new ComentarioDAO(conexao);
			dao.Editar(this.comentario);

			CarregarComentariosDaResposta();

			fabrica.fecharConexao();

			JSFUtil.adicionarMensagemSucesso("Comment edited successfully!");
			
		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
	}
	
	public void ExcluirComentario() {
		try {
			
			FabricaConexao fabrica = new FabricaConexao();
			Connection conexao = fabrica.fazerConexao();

			ComentarioDAO dao = new ComentarioDAO(conexao);
			dao.Excluir(this.comentario);
			
			CarregarComentariosDaResposta();

			fabrica.fecharConexao();

			JSFUtil.adicionarMensagemSucesso("Comment deleted successfully!");
			
		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
	}
	
	public String onProcessoFluxo(FlowEvent event) {
        
		if(skip) {
            skip = false;   //reset in case user goes back
            return "confirm";
        }
        else {
            return event.getNewStep();
        }
    }

	public void CopiarEquacaoExistenteParaNovaEquacao() {
		try {
			this.novaResposta.setNovaEquacao(this.desafio.getReacao().getEquation());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void EnviarEmailPesquisadoresDoProjeto(TipoOperacao _tipoOP, TipoObjeto _objeto) {

		try {
			
			String titulo = "";
			String msg = "";
			
			switch (_tipoOP) {
				case CREATE:
					titulo = _objeto + " successfully registered!";
					break;
				case UPDATE:
					titulo = _objeto + " edited successfully!";
					break;
				case DELETE:
					titulo = _objeto + " deleted successfully!";
					break;
				case READ:
					titulo = _objeto + " successfully read!";
					break;
				case VALIDACAO:
					break;
				case CANCELAR:
					break;
			}
			
			switch (_objeto) {
				case DESAFIO:
					break;
				case RESPOSTA:
					msg = "Answer Details: \n"
							+"\nAnswer Title: " + this.novaResposta.getTitulo()
							+ "\nDescription of the answer: " + this.novaResposta.getDesc()
							+ "\nOperation User: " + SessaoUtil.pegarUsuarioSessao().getNome();
					break;

			}
			
			List<String> emailsDestino = new ArrayList<String>();
			
			FabricaConexao fabrica = new FabricaConexao();
			Connection conexao = fabrica.fazerConexao();

			PesquisadorDAO dao = new PesquisadorDAO(conexao);

			List<Pesquisador> l = dao.listarTodosPorEspecialidade(this.desafio.getEspecialidade().getId());
			
			for (Pesquisador p : l) {
				emailsDestino.add(p.getEmail());
			}
			
			EmailUtil.enviaEmail(titulo, msg, emailsDestino);
			
		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
	}
	
	@PostConstruct
	public void PesquisarDesafios() {
		
		try {
			
			FabricaConexao fabrica = new FabricaConexao();
			Connection conexao = fabrica.fazerConexao();

			DesafioDAO dao = new DesafioDAO(conexao);
			PesquisadorDAO daoPes = new PesquisadorDAO(conexao);
			
			Usuario user = SessaoUtil.pegarUsuarioSessao();
			
			Pesquisador p = daoPes.PegarPeloID(user.getId());
			
			if (user.getTipoUsuario().getId().equals(new Long(1))) {
				this.listaDesafios = dao.listarTodos();
			}
			else if (p.getEspecialidade().getId().equals(new Long(3))){
				this.listaDesafios = dao.listarTodos();
			}
			else {
				this.listaDesafios = dao.listarTodosPorEspecialidade(p.getEspecialidade().getId());
			}
			
			fabrica.fecharConexao();
			
			//desafios = new ListDataModel<Desafio>(listaDesafios);
			
		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
	}
}
