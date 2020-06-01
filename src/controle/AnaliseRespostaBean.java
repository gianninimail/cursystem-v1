package controle;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.model.ListDataModel;

import org.primefaces.model.TreeNode;

import dao.ComentarioDAO;
import dao.DesafioDAO;
import dao.PesquisadorDAO;
import dao.RespostaDAO;
import modelo.Comentario;
import modelo.Desafio;
import modelo.Pesquisador;
import modelo.Resposta;
import modelo.Status;
import util.EmailUtil;
import util.FabricaConexao;
import util.JSFUtil;
import util.SessaoUtil;
import util.TipoObjeto;
import util.TipoOperacao;

@ManagedBean
public class AnaliseRespostaBean {

	private Resposta resposta;
	private List<Resposta> listaRespostas;
	private ListDataModel<Resposta> respostas;
	private TreeNode raiz;
	private TreeNode noSelecionado;
	private boolean temRespostaSelecionada = false;
	
	private List<Comentario> listaComentarios;
	
	@ManagedProperty("#{servicoRespostas}")
	private ServicoRespostas servico;
	
	public AnaliseRespostaBean() {
		this.resposta = new Resposta();
		this.resposta.setStatus(true);
		this.listaRespostas = new ArrayList<Resposta>();
	}

	//INICIO METODOS GETS E SETS
	public Resposta getResposta() {
		return resposta;
	}

	public void setResposta(Resposta resposta) {
		this.resposta = resposta;
	}

	public List<Resposta> getListaRespostas() {
		return listaRespostas;
	}

	public void setListaRespostas(List<Resposta> listaRespostas) {
		this.listaRespostas = listaRespostas;
	}
	
	public ListDataModel<Resposta> getRespostas() {
		return respostas;
	}

	public void setRespostas(ListDataModel<Resposta> respostas) {
		this.respostas = respostas;
	}

	public ServicoRespostas getServico() {
		return servico;
	}

	public void setServico(ServicoRespostas servico) {
		this.servico = servico;
	}

	public TreeNode getRaiz() {
		return raiz;
	}

	public void setRaiz(TreeNode raiz) {
		this.raiz = raiz;
	}

	public TreeNode getNoSelecionado() {
		return noSelecionado;
	}

	public void setNoSelecionado(TreeNode noSelecionado) {
		this.noSelecionado = noSelecionado;
		try {
			
			if (this.noSelecionado != null) 
			{	
				if (this.noSelecionado.getData() instanceof Resposta) 
				{	
					this.temRespostaSelecionada = true;
					this.resposta = (Resposta)this.noSelecionado.getData();
				} 
				else 
				{
					this.temRespostaSelecionada = false;
					this.resposta = null;
				}
			}
		} 
		catch (Exception e) {
			this.temRespostaSelecionada = false;
			e.printStackTrace();
		}
	}

	/*
	public void setNoSelecionado(TreeNode noSelecionado) {
		this.noSelecionado = noSelecionado;
	}
	*/

	public boolean isTemRespostaSelecionada() {
		return temRespostaSelecionada;
	}

	public void setTemRespostaSelecionada(boolean temRespostaSelecionada) {
		this.temRespostaSelecionada = temRespostaSelecionada;
	}
	
	public List<Comentario> getListaComentarios() {
		return listaComentarios;
	}

	public void setListaComentarios(List<Comentario> listaComentarios) {
		this.listaComentarios = listaComentarios;
	}
	
	//FIM METODOS GETS E SETS
	
	//INCIO METODOS ACESSO AO BANCO DE DADOS

	public void ValidarResposta() {
		try {
			
			if(this.noSelecionado != null) {
				
				if (this.noSelecionado.getData() instanceof Resposta) {
					
					FabricaConexao fabrica = new FabricaConexao();
					Connection conexao = fabrica.fazerConexao();
					
					RespostaDAO daoR = new RespostaDAO(conexao);
					
					Resposta resposta = (Resposta)this.noSelecionado.getData();
					
					TreeNode noPai = this.noSelecionado.getParent();
					
					List<TreeNode> irmas = noPai.getChildren();
					
					for (TreeNode irma : irmas) {
						Resposta r = (Resposta)irma.getData();
						
						if (!r.equals(resposta)) {
							r.setStatus(false);
							r.setJustificativa("");
						}
						else {
							r.setStatus(true);
							Desafio desafio = (Desafio)noPai.getData();
							desafio.setStatus(new Status(util.Status.COMPLETED.valor(), util.Status.COMPLETED.toString()));
						}
					}
					
					if (daoR.MudarStatusRespostas(resposta)) {
						
						DesafioDAO daoDes = new DesafioDAO(conexao);
						
						daoDes.MudarStatusDesafio(resposta.getDesafio(), util.Status.COMPLETED);
					}
					
					EnviarEmailPesquisadoresDoProjeto(TipoOperacao.VALIDACAO, TipoObjeto.RESPOSTA);
					
					JSFUtil.adicionarMensagemSucesso("Response validated and Challenge successfully closed!");

				}
				else {
					JSFUtil.adicionarMensagemErro("The selection does not match with ANSWER!!!");
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void AtualizarJustificativa() {
		
		try {
			
			FabricaConexao fabrica = new FabricaConexao();
			Connection conexao = fabrica.fazerConexao();

			RespostaDAO dao = new RespostaDAO(conexao);
			dao.Atualizar(this.resposta);
			
			PesquisarRespostas();

			fabrica.fecharConexao();

			EnviarEmailPesquisadoresDoProjeto(TipoOperacao.UPDATE, TipoObjeto.RESPOSTA);
			
			JSFUtil.adicionarMensagemSucesso("Answer updated successfully!");
		}	
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void ExcluirResposta() {
		
		try {
			
			FabricaConexao fabrica = new FabricaConexao();
			Connection conexao = fabrica.fazerConexao();

			RespostaDAO dao = new RespostaDAO(conexao);
			dao.Excluir(this.resposta);
			
			if (this.resposta.getStatus() == true) {
				
				DesafioDAO daoDes = new DesafioDAO(conexao);
				
				daoDes.MudarStatusDesafio(resposta.getDesafio(), util.Status.REOPENED);
			}
			
			PesquisarRespostas();
			
			fabrica.fecharConexao();

			EnviarEmailPesquisadoresDoProjeto(TipoOperacao.DELETE, TipoObjeto.RESPOSTA);
			
			JSFUtil.adicionarMensagemSucesso("Answer deleted successfully and Challenge reopened!");
			
		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
	}
	
	public void CancelarValidacao() {
		try {
			
			FabricaConexao fabrica = new FabricaConexao();
			Connection conexao = fabrica.fazerConexao();

			RespostaDAO dao = new RespostaDAO(conexao);
			
			this.resposta.setStatus(false);
			this.resposta.setJustificativa(null);
			
			dao.Atualizar(this.resposta);
			
			DesafioDAO daoDes = new DesafioDAO(conexao);
			
			daoDes.MudarStatusDesafio(resposta.getDesafio(), util.Status.REOPENED);
			
			PesquisarRespostas();

			fabrica.fecharConexao();

			EnviarEmailPesquisadoresDoProjeto(TipoOperacao.CANCELAR, TipoObjeto.RESPOSTA);
			
			JSFUtil.adicionarMensagemSucesso("Answer with validation successfully canceled and Challenge reopened!");
		}	
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void CarregarComentariosDaResposta() {
		
		try {
			FabricaConexao fabrica = new FabricaConexao();
			Connection conexao = fabrica.fazerConexao();

			ComentarioDAO dao = new ComentarioDAO(conexao);
			
			this.listaComentarios = dao.TodasComentariosDaResposta(this.resposta);
			
			fabrica.fecharConexao();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//FIM METODOS ACESSO AO BANCO DE DADOS
	
	@PostConstruct
	public void PesquisarRespostas() {
		try {
			servico = new ServicoRespostas();
			this.raiz = servico.criarArvoreRespostas();
			
		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
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
					titulo = _objeto + " validated and Challenge successfully closed!";
					break;
				case CANCELAR:
					titulo = _objeto + " with validation successfully canceled!";
					break;
			}
			
			switch (_objeto) {
				case DESAFIO:
					break;
				case RESPOSTA:
					Desafio desafioFechado = (Desafio)this.noSelecionado.getParent().getData();
					msg = "Answer and Challenge Details: \n"
							+"\nAnswer Title: " + this.resposta.getTitulo()
							+ "\nDescription of the answer: " + this.resposta.getDesc()
							+ "\nOperation User: " + SessaoUtil.pegarUsuarioSessao().getNome()
							+ "\n\n\n"
							+ "\nChallenge closed: " + desafioFechado.getTitulo(); 
					break;

			}
			
			List<String> emailsDestino = new ArrayList<String>();
			
			FabricaConexao fabrica = new FabricaConexao();
			Connection conexao = fabrica.fazerConexao();

			PesquisadorDAO dao = new PesquisadorDAO(conexao);

			List<Pesquisador> l = dao.listarTodosPorEspecialidade(this.resposta.getPesquisador().getId());
			
			for (Pesquisador p : l) {
				emailsDestino.add(p.getEmail());
			}
			
			EmailUtil.enviaEmail(titulo, msg, emailsDestino);
			
		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
	}
}
 