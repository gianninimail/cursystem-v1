package controle;

import java.sql.Connection;
import java.util.Map;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.model.ListDataModel;

import javax.faces.context.FacesContext;

import dao.DesafioDAO;
import dao.EspecialidadeDAO;
import dao.PesquisadorDAO;
import dao.RespostaDAO;
import modelo.Desafio;
import modelo.Especialidade;
import modelo.Pesquisador;
import modelo.Resposta;
import modelo.Status;
import modelo.Usuario;
import util.EmailUtil;
import util.BeanBase;
import util.FabricaConexao;
import util.JSFUtil;
import util.SessaoUtil;
import util.TipoObjeto;
import util.TipoOperacao;

@SuppressWarnings("serial")
@ManagedBean
public class DesafioBean extends BeanBase {

	private String msgOps;
	
	Desafio desafio;
	private List<Desafio> listaDesafios;
	private List<Desafio> listaDesafiosMeus;
	private List<Desafio> listaDesafiosFiltrados;
	private ListDataModel<Desafio> desafios;
	private ListDataModel<Desafio> desafiosMeus;
	private ListDataModel<Desafio> desafiosFiltrados;
	
	private ListDataModel<Resposta> respostas;
	
	private List<Pesquisador> listaPesquisadores;
	
	private List<Especialidade> especialidades;
	
	private Pesquisador pesquisador;
	
	private Resposta resposta;
	
	public DesafioBean() {
		this.desafio = new Desafio();
		//this.desafio.setTitulo("No challenges selected"); Khalil Comentado
	}
	
	public Desafio getDesafio() {
		return desafio;
	}
	
	public void setDesafio(Desafio desafio) {
		this.desafio = desafio;
	}
	
	public String getMsgOps() {
		return msgOps;
	}
	
	public void setMsgOps(String msgOps) {
		this.msgOps = msgOps;
	}
	
	public ListDataModel<Desafio> getDesafios() {
		return desafios;
	}
	
	public ListDataModel<Desafio> getDesafiosMeus() {
		return desafiosMeus;
	}
	
	public void setDesafios(ListDataModel<Desafio> desafios) {
		this.desafios = desafios;
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

	public List<Pesquisador> getListaPesquisadores() {
		return listaPesquisadores;
	}

	public void setListaPesquisadores(List<Pesquisador> listaPesquisadores) {
		this.listaPesquisadores = listaPesquisadores;
	}

	public List<Especialidade> getEspecialidades() {
		return especialidades;
	}

	public void setEspecialidades(List<Especialidade> especialidades) {
		this.especialidades = especialidades;
	}

	public ListDataModel<Desafio> getDesafiosFiltrados() {
		return desafiosFiltrados;
	}

	public void setDesafiosFiltrados(ListDataModel<Desafio> desafiosFiltrados) {
		this.desafiosFiltrados = desafiosFiltrados;
	}

	//ACESSO AO BANCO DE DADOS
	public void CadastrarDesafio() {
		try {
			
			this.desafio.setDataCadastro(new Date());
			
			modelo.Status status = new Status(2, "OPEN");
			this.desafio.setStatus(status);

			FabricaConexao fabrica = new FabricaConexao();
			Connection conexao = fabrica.fazerConexao();

			DesafioDAO dao = new DesafioDAO(conexao);
			dao.Inserir(this.desafio);
			
			PesquisarDesafios();

			fabrica.fecharConexao();
			
			EnviarEmailPesquisadoresDoProjeto(TipoOperacao.CREATE, TipoObjeto.DESAFIO); // Khalil - Comentado em Teste local - No servidor descomentar
			
			JSFUtil.adicionarMensagemSucesso(getMensagem("desafio.sucesso"));
			
			desafio = new Desafio();
			
		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
	}
	
	public void PrepararNovo() {
		this.desafio = new Desafio();
		
		try {
			
			FabricaConexao fabrica = new FabricaConexao();
			Connection conexao = fabrica.fazerConexao();

			DesafioDAO dao = new DesafioDAO(conexao);
			
			this.desafio.setId(dao.PegarID());
			
			//this.desafio.setUsuario(SessaoUtil.pegarUsuarioSessao());
			
			modelo.Status status = new Status(2, "OPEN");
			
			this.desafio.setStatus(status);
			
			//CarregarReacoes();
			
		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	public void ExcluirDesafio() {
		try {
			
			FabricaConexao fabrica = new FabricaConexao();
			Connection conexao = fabrica.fazerConexao();

			DesafioDAO dao = new DesafioDAO(conexao);
			dao.Excluir(this.desafio);

			PesquisarDesafios();

			fabrica.fecharConexao();

			EnviarEmailPesquisadoresDoProjeto(TipoOperacao.DELETE, TipoObjeto.DESAFIO); // Khalil - Comentado em Teste local - No servidor descomentar
			
			JSFUtil.adicionarMensagemSucesso(getMensagem("desafio.excluido"));
			
		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
	}
	
	public void PrepararExcluir() 
	{
		this.desafio = desafios.getRowData();
	}

	public void PrepararExcluirDesafio(Desafio desafio) 
	{
		try {

			this.desafio = desafio;

		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
	}	
	
	@Deprecated
	public void EditarDesafio() {
		try {
			
			FabricaConexao fabrica = new FabricaConexao();
			Connection conexao = fabrica.fazerConexao();

			DesafioDAO dao = new DesafioDAO(conexao);
			dao.Atualizar(this.desafio);

			PesquisarDesafios();

			fabrica.fecharConexao();

			EnviarEmailPesquisadoresDoProjeto(TipoOperacao.UPDATE, TipoObjeto.DESAFIO);
			
			JSFUtil.adicionarMensagemSucesso("Challenge edited successfully!");
			
		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
	}
	
	public void EditarDesafio(Desafio desafio) {
		try {
			String msgSucesso = "";

			FabricaConexao fabrica = new FabricaConexao();
			Connection conexao = fabrica.fazerConexao();

			DesafioDAO dao = new DesafioDAO(conexao);
			dao.Atualizar(desafio);

			fabrica.fecharConexao();
			
			EnviarEmailPesquisadoresDoProjeto(TipoOperacao.UPDATE, TipoObjeto.DESAFIO); // Khalil - Comentado em Teste local - No servidor descomentar

			msgSucesso = getMensagem("desafio.editado");
			JSFUtil.adicionarMensagemSucesso(msgSucesso);

		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
	}
	
	public void PrepararEditar() 
	{
		this.desafio = desafios.getRowData();
		
		FiltrarPesquisadores();
	}

	public String PrepararEditarDesafio(Desafio desafio) 
	{

		try {
			
			Map<String,Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
			
			sessionMapObj.put("editarDesafio", desafio);
			
			FiltrarPesquisadoresEditar(desafio);
			
			return "/alteracao/alt_desafio.jsf?faces-redirect=true";

		} catch (Exception e) {
			return null;
		}
		
	}

	public String PrepararRespostas(Desafio desafio) 
	{

		try {
			
			Map<String,Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
			
			sessionMapObj.put("editarDesafio", desafio);
			
			FiltrarPesquisadoresEditar(desafio);
			
			this.resposta = new Resposta();
			
			this.resposta.setDesafio(desafio.getId());
			this.resposta.setStatus(false);
			this.resposta.setValidacoes(0);
			
			Pesquisador pesquisador = new Pesquisador(SessaoUtil.pegarUsuarioSessao());
			
			this.resposta.setPesquisador(pesquisador);
			
			sessionMapObj.put("respostaDesafio", this.resposta);

			return "/analise/resposta.jsf?faces-redirect=true";

		} catch (Exception e) {
			return null;
		}
		
	}
	
	public void PrepararVerInformcoes() 
	{
		this.desafio = desafios.getRowData();
	}
	
	/*
	public void PesquisarReacoes() {
		try {
			
			FabricaConexao fabrica = new FabricaConexao();
			Connection conexao = fabrica.fazerConexao();
			
			ReacaoDAO dao = new ReacaoDAO(conexao);
			
			String nomeSubSistema = dao.BuscarNomeSubSistema(this.subsistema.getId());
			
			this.listaReacoes = dao.listarTodosPorSistemaAndSubSistema(null, nomeSubSistema);
			
			//this.reacoes = new ListDataModel<>(this.listaReacoes);
	
			fabrica.fecharConexao();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	*/
	
	/*
	private void CarregarReacoes() {
		try {
			
			FabricaConexao fabrica = new FabricaConexao();
			Connection conexao = fabrica.fazerConexao();
			
			FuncaoSubsistemaDAO daoFuncSubsistema = new FuncaoSubsistemaDAO(conexao);
			this.listaSubsistemas = daoFuncSubsistema.listarTodos();
			//this.setSubsistemas(new ListDataModel<>(listaSubsistemas));
			
			ReacaoDAO daoReacao = new ReacaoDAO(conexao);
			this.listaReacoes = daoReacao.listarTodos();
			//this.reacoes = new ListDataModel<>(this.listaReacoes);
	
			fabrica.fecharConexao();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	*/
  	
	public void FiltrarPesquisadores() {
		try {

			FabricaConexao fabrica = new FabricaConexao();
			Connection conexao = fabrica.fazerConexao();

			PesquisadorDAO dao = new PesquisadorDAO(conexao);

			this.listaPesquisadores = dao.listarTodosPorEspecialidade(this.desafio.getEspecialidade().getId());

			fabrica.fecharConexao();

		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
	}
	
	public void FiltrarPesquisadoresEditar(Desafio desafio) {
		try {

			FabricaConexao fabrica = new FabricaConexao();
			Connection conexao = fabrica.fazerConexao();

			PesquisadorDAO dao = new PesquisadorDAO(conexao);
			
			Map<String,Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
			
			sessionMapObj.put("editarListaPesquisadores", dao.listarTodosPorEspecialidade(desafio.getEspecialidade().getId()));

			fabrica.fecharConexao();

		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
	}	
	
	//FIM DO ACESSO AO BANCO DE DADOS
	
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
					msg = "Challenge Details: \n"
							+"\nChallenge Title: " + this.desafio.getTitulo()
							+ "\nDescription of the challenge: " + this.desafio.getDesc()
							+ "\nResponsible: " + this.desafio.getUsuario().getNome()
							+ "\nOperation User: " + SessaoUtil.pegarUsuarioSessao().getNome();
					break;
				case RESPOSTA:
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
			
			Usuario user = SessaoUtil.pegarUsuarioSessao();
			
			if (user.getTipoUsuario().getId() == 1) {
				this.listaDesafios = dao.listarTodos();
			}
			else {
				this.listaDesafios = dao.listarTodosPorPesquisador(user.getId());
			}
			
			this.listaDesafiosMeus = dao.listarTodosPorPesquisador(user.getId());
			
			
			pesquisador = new Pesquisador();
			PesquisadorDAO pesquisadorDao = new PesquisadorDAO(conexao);
			pesquisador = pesquisadorDao.PegarPeloID(user.getId());
			
			if (pesquisador==null) {
				pesquisador = new Pesquisador();
			}
			
			if (pesquisador.getEspecialidade().getId().equals(new Long(3))){
				this.listaDesafiosFiltrados = dao.listarTodos();
			}
			else {
				this.listaDesafiosFiltrados = dao.listarTodosPorEspecialidade(pesquisador.getEspecialidade().getId());
			}
				
			//this.respostas = new ListDataModel<Resposta>(this.desafio.getRespostas());
			EspecialidadeDAO daoEsp = new EspecialidadeDAO(conexao);
			this.especialidades = daoEsp.listarTodosAtivos();

			fabrica.fecharConexao();
			
			desafios = new ListDataModel<Desafio>(listaDesafios);
			desafiosMeus = new ListDataModel<Desafio>(listaDesafiosMeus);
			desafiosFiltrados = new ListDataModel<Desafio>(listaDesafiosFiltrados);
			
		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
	}

}
