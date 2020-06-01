package controle;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.ListDataModel;

//import org.apache.commons.mail.EmailException;
import org.primefaces.event.FlowEvent;

import dao.DesafioDAO;
import dao.PesquisadorDAO;
import dao.RespostaDAO;
import dao.ComentarioDAO;
import modelo.Pesquisador;
import modelo.Resposta;
import modelo.Status;
import modelo.Desafio;
import modelo.Comentario;
import util.FabricaConexao;
import util.JSFUtil;
import util.SessaoUtil;
import util.TipoObjeto;
import util.TipoOperacao;
import util.BeanBase;
import util.EmailUtil;

@SuppressWarnings("serial")
@ManagedBean
public class RespostaBean extends BeanBase {

	@ManagedProperty(value = "#{desafioBean}")
	private DesafioBean desafioBean;

	Resposta resposta;
	Comentario comentario;
	
	private boolean skip;

	private List<Resposta> listaRespostas;
	private ListDataModel<Resposta> respostas;
	
	private UIComponent componenteDesc;

	private UIComponent componenteTexto;	
	
	public RespostaBean() {
		super();
		this.resposta = new Resposta();
	}

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
	
	public UIComponent getComponenteDesc() {
		return componenteDesc;
	}

	public void setComponenteDesc(UIComponent componenteDesc) {
		this.componenteDesc = componenteDesc;
	}
	
	public UIComponent getComponenteTexto() {
		return componenteTexto;
	}

	public void setComponenteTexto(UIComponent componenteTexto) {
		this.componenteTexto = componenteTexto;
	}

	public boolean isSkip() {
		return skip;
	}

	public void setSkip(boolean skip) {
		this.skip = skip;
	}

	public void PrepararNovo() {

		this.resposta = new Resposta();

		try {

			FabricaConexao fabrica = new FabricaConexao();
			Connection conexao = fabrica.fazerConexao();

			RespostaDAO dao = new RespostaDAO(conexao);

			this.resposta.setId(dao.PegarID());

		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	@Deprecated
	public void CadastrarResposta() {
		try {
			this.resposta.setDataCadastro(new Date());

			FabricaConexao fabrica = new FabricaConexao();
			Connection conexao = fabrica.fazerConexao();

			this.resposta.setDesafio(desafioBean.desafio.getId());
			this.resposta.setPesquisador((Pesquisador) desafioBean.desafio.getUsuario());

			RespostaDAO dao = new RespostaDAO(conexao);
			dao.Inserir(this.resposta);

			// EnviarEmail();

			this.listaRespostas = dao.listarTodos();

			this.respostas = new ListDataModel<Resposta>(listaRespostas);

			fabrica.fecharConexao();

			JSFUtil.adicionarMensagemSucesso("Resposta cadastrado com sucesso!");

		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	public String CadastrarResposta(Resposta resposta) {
		try {
			
			String outcome = "";
			
			if (resposta.getDesc().isEmpty()) {
				JSFUtil.adicionarMensagemErroComponente(componenteDesc.getClientId(),
						this.getMensagem("resposta.descricao.obrigatorio"));
				return outcome;
			}

			Desafio desafio = new Desafio();

			Map<String, Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

			resposta.setDataCadastro(new Date());

			FabricaConexao fabrica = new FabricaConexao();
			Connection conexao = fabrica.fazerConexao();

			RespostaDAO dao = new RespostaDAO(conexao);
			DesafioDAO daoDesafio = new DesafioDAO(conexao);
			dao.Inserir(resposta);

			// EnviarEmail();

			desafio = daoDesafio.PegarPeloID(resposta.getDesafio());

			fabrica.fecharConexao();

			resposta.setTitulo("");
			resposta.setDesc("");

			sessionMapObj.put("respostaDesafio", resposta);
			sessionMapObj.put("editarDesafio", desafio);

			JSFUtil.adicionarMensagemSucesso(getMensagem("resposta.sucesso"));
			
			return outcome;

		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
			return "";
		}
	}

	public void PrepararValidarResposta(Resposta resposta) throws Exception {

		try {

			this.resposta = resposta;

		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());

		}

	}

	public void ValidarResposta() {
		try {
			Desafio desafio = new Desafio();
			Map<String, Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
			String msgSucesso = "";

			// AnaliseRespostaBean.ValidarResposta

			FabricaConexao fabrica = new FabricaConexao();
			Connection conexao = fabrica.fazerConexao();

			RespostaDAO daoResposta = new RespostaDAO(conexao);
			this.resposta.setStatus(true);
			daoResposta.MudarStatusRespostas(this.resposta);

			DesafioDAO daoDesafio = new DesafioDAO(conexao);
			daoDesafio.MudarStatusDesafio(this.resposta.getDesafio(), util.Status.COMPLETED);
			
			desafio = daoDesafio.PegarPeloID(this.resposta.getDesafio());

			fabrica.fecharConexao();
			
			//EnviarEmailPesquisadoresDoProjeto(TipoOperacao.VALIDACAO, TipoObjeto.RESPOSTA, desafio); // Khalil - Comentado em Teste local - No servidor descomentar
			
			sessionMapObj.put("editarDesafio", desafio);

			msgSucesso = getMensagem("resposta.validada.desafio.fechado");// Response validated and Challenge successfully closed!
			JSFUtil.adicionarMensagemSucesso(msgSucesso);

		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	public void PrepararCancelarResposta(Resposta resposta) throws Exception {

		try {

			this.resposta = resposta;

		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());

		}

	}
	
	public void CancelarResposta() {
		try {
			Desafio desafio = new Desafio();
			Map<String, Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
			String msgSucesso = "";

			// AnaliseRespostaBean.ValidarResposta

			FabricaConexao fabrica = new FabricaConexao();
			Connection conexao = fabrica.fazerConexao();

			RespostaDAO daoResposta = new RespostaDAO(conexao);
			this.resposta.setStatus(false);
			this.resposta.setJustificativa("");
			daoResposta.MudarStatusRespostas(this.resposta);

			DesafioDAO daoDesafio = new DesafioDAO(conexao);
			daoDesafio.MudarStatusDesafio(this.resposta.getDesafio(), util.Status.REOPENED);
			
			desafio = daoDesafio.PegarPeloID(this.resposta.getDesafio());

			fabrica.fecharConexao();
			
			//EnviarEmailPesquisadoresDoProjeto(TipoOperacao.CANCELAR, TipoObjeto.RESPOSTA, desafio); // Khalil - Comentado em Teste local - No servidor descomentar
			
			sessionMapObj.put("editarDesafio", desafio);

			msgSucesso = getMensagem("resposta.cancelada.desafio.reaberto");// Response validated and Challenge successfully closed!
			JSFUtil.adicionarMensagemSucesso(msgSucesso);

		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
	}
	
	public void PrepararEditarResposta(Resposta resposta) throws Exception {

		try {

			Map<String, Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

			sessionMapObj.put("respostaDesafio", resposta);

		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());

		}

	}

	public void EditarResposta(Resposta resposta) {
		try {
			String msgSucesso = "";

			FabricaConexao fabrica = new FabricaConexao();
			Connection conexao = fabrica.fazerConexao();

			RespostaDAO dao = new RespostaDAO(conexao);
			dao.Atualizar(resposta);

			fabrica.fecharConexao();

			msgSucesso = getMensagem("resposta.editada");
			JSFUtil.adicionarMensagemSucesso(msgSucesso);

		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	public void CancelarEditarResposta(Resposta resposta) throws Exception {

		try {

			Resposta novaResposta = new Resposta();

			Map<String, Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

			novaResposta.setDesafio(resposta.getDesafio());
			novaResposta.setStatus(false);
			novaResposta.setValidacoes(0);

			Pesquisador pesquisador = new Pesquisador(SessaoUtil.pegarUsuarioSessao());

			novaResposta.setPesquisador(pesquisador);

			sessionMapObj.put("respostaDesafio", novaResposta);

		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());

		}

	}

	public void PrepararExcluirResposta(Resposta resposta) {
		try {

			this.resposta = resposta;

		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	public void ExcluirResposta() {
		try {

			Desafio desafio = new Desafio();

			Map<String, Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

			String msgSucesso = "";

			FabricaConexao fabrica = new FabricaConexao();
			Connection conexao = fabrica.fazerConexao();

			RespostaDAO dao = new RespostaDAO(conexao);

			dao.Excluir(this.resposta);

			DesafioDAO daoDesafio = new DesafioDAO(conexao);
			
			if (this.resposta.getStatus()) {
				daoDesafio.MudarStatusDesafio(this.resposta.getDesafio(), util.Status.REOPENED);
				//EnviarEmailPesquisadoresDoProjeto(TipoOperacao.CANCELAR, TipoObjeto.RESPOSTA, desafio); // Khalil - Comentado em Teste local - No servidor descomentar
				msgSucesso = getMensagem("resposta.excluida.desafio.reaberto");
			}else {
				msgSucesso = getMensagem("resposta.excluida");
			}
			
			desafio = daoDesafio.PegarPeloID(this.resposta.getDesafio());

			fabrica.fecharConexao();

			sessionMapObj.put("editarDesafio", desafio);

			
			JSFUtil.adicionarMensagemSucesso(msgSucesso);

		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
	}
	
	public void PrepararComentarios(Resposta resposta) {
		try {

			this.resposta = resposta;
			
			Map<String,Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
			
			this.comentario = new Comentario();
			
			this.comentario.setResposta(resposta.getId());

			Pesquisador pesquisador = new Pesquisador(SessaoUtil.pegarUsuarioSessao());
			this.comentario.setPesquisador(pesquisador);
			
			sessionMapObj.put("comentarioResposta", this.comentario);
			
		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
	}
	
	public String CadastrarComentario(Comentario comentario) {
		try {
			
			String outcome = "";
			if (comentario.getTexto().isEmpty()) {
				JSFUtil.adicionarMensagemErroComponente(componenteTexto.getClientId(),
						this.getMensagem("comentario.texto.obrigatorio"));
				return outcome;
			}
			
			Desafio desafio = new Desafio();
			
			Map<String, Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
			
			comentario.setDataCadastro(new Date());
			
			FabricaConexao fabrica = new FabricaConexao();
			Connection conexao = fabrica.fazerConexao();
			
			DesafioDAO daoDesafio = new DesafioDAO(conexao);
			RespostaDAO daoResposta = new RespostaDAO(conexao);
			ComentarioDAO daoComentario = new ComentarioDAO(conexao);
			
			daoComentario.Inserir(comentario);
			
			desafio = daoDesafio.PegarPeloID(this.resposta.getDesafio());
			this.resposta = daoResposta.PegarPeloID(comentario.getResposta());
			
			fabrica.fecharConexao();
			
			comentario.setTexto("");
			
			sessionMapObj.put("editarDesafio", desafio);
			sessionMapObj.put("comentarioResposta", comentario);
			
			JSFUtil.adicionarMensagemSucesso(getMensagem("comentario.sucesso"));
			
			return outcome;
			
		}catch (Exception e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
			return "";
		}
	}
	
	public void PrepararEditarComentario(Comentario comentario) throws Exception {

		try {

			Map<String, Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
			
			this.comentario = comentario;
			
			sessionMapObj.put("comentarioResposta", comentario);

		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());

		}

	}
	
	public void EditarComentario(Comentario comentario) {
		try {
			String msgSucesso = "";

			FabricaConexao fabrica = new FabricaConexao();
			Connection conexao = fabrica.fazerConexao();

			ComentarioDAO dao = new ComentarioDAO(conexao);
			dao.Editar(comentario);

			fabrica.fecharConexao();

			msgSucesso = getMensagem("comentario.editado");
			JSFUtil.adicionarMensagemSucesso(msgSucesso);

		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
	}
	
	public void CancelarEditarComentario(Comentario _comentario) throws Exception {

		try {
		
			Map<String, Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
			
			this.comentario = new Comentario();
			
			this.comentario.setResposta(_comentario.getResposta());
			
			Pesquisador pesquisador = new Pesquisador(SessaoUtil.pegarUsuarioSessao());
			this.comentario.setPesquisador(pesquisador);
			
			sessionMapObj.put("comentarioResposta", this.comentario);

		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());

		}

	}
	
	public void ExcluirComentario(Comentario comentario) {
		try {
			
			Desafio desafio = new Desafio();
			
			Map<String, Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
			
			FabricaConexao fabrica = new FabricaConexao();
			Connection conexao = fabrica.fazerConexao();
			
			DesafioDAO daoDesafio = new DesafioDAO(conexao);
			RespostaDAO daoResposta = new RespostaDAO(conexao);
			ComentarioDAO daoComentario = new ComentarioDAO(conexao);
			
			daoComentario.Excluir(comentario);
			
			desafio = daoDesafio.PegarPeloID(this.resposta.getDesafio());
			this.resposta = daoResposta.PegarPeloID(comentario.getResposta());
			
			fabrica.fecharConexao();
			
			sessionMapObj.put("editarDesafio", desafio);
			
			JSFUtil.adicionarMensagemSucesso(getMensagem("comentario.excluido"));
			
			
		}catch (Exception e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	public String onProcessoFluxo(FlowEvent event) {

		if (skip) {
			skip = false; // reset in case user goes back
			return "confirm";
		} else {
			return event.getNewStep();
		}
	}

	// FIM DO ACESSO AO BANCO DE DADOS

//		public void EnviarEmail() throws EmailException {
//			
//			util.EmailUtil email = new EmailUtil();
//			
//			String titulo = "Nova resposta Cadastrada";
//			String msg = "Dados da Resposta: \n"
//				+"\nT�tulo da resposta: " + this.resposta.getTitulo()
//				+ "\nDescri��o da resposta: " + this.resposta.getDesc()
//				+ "\nRea��o envolvida no resposta: " + this.resposta.getReacao().getAbbreviation() + "  |--|  Eq:" + this.resposta.getReacao().getEquation()
//				+ "";
//		}
	
	

	@PostConstruct
	public void PreparaPesquisa() {
		this.resposta = new Resposta(); 
		this.resposta.setStatus(false);

	}

	public void EnviarEmailPesquisadoresDoProjeto(TipoOperacao _tipoOP, TipoObjeto _objeto, Desafio desafioFechado) {

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
				msg = "Answer and Challenge Details: \n" + "\nAnswer Title: " + this.resposta.getTitulo()
						+ "\nDescription of the answer: " + this.resposta.getDesc() + "\nOperation User: "
						+ SessaoUtil.pegarUsuarioSessao().getNome() + "\n\n\n" + "\nChallenge closed: "
						+ desafioFechado.getTitulo();
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
