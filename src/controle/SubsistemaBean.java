package controle;

import java.sql.Connection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.model.ListDataModel;

import dao.FuncaoSubsistemaDAO;
import dao.ReacaoDAO;
import modelo.Subsistema;
import modelo.Reacao;
import util.FabricaConexao;
import util.JSFUtil;

@ManagedBean
public class SubsistemaBean {

	private Subsistema funcaoSubsistema;
	private List<Subsistema> funcaoSubsistemas;
	private String msgOps;
	private List<Reacao> listaReactions;
	private ListDataModel<Reacao> reacoes;
	
	public SubsistemaBean() {
		super();
		this.funcaoSubsistema = new Subsistema();
	}

	public Subsistema getFuncaoSubsistema() {
		return funcaoSubsistema;
	}

	public void setFuncaoSubsistema(Subsistema funcaoSubsistema) {
		this.funcaoSubsistema = funcaoSubsistema;
	}

	public List<Subsistema> getFuncaoSubsistemas() {
		return funcaoSubsistemas;
	}

	public void setFuncaoSubsistemas(List<Subsistema> funcaoSubsistemas) {
		this.funcaoSubsistemas = funcaoSubsistemas;
	}
	
	public String getMsgOps() {
		return msgOps;
	}

	public void setMsgOps(String msgOps) {
		this.msgOps = msgOps;
	}
	
	public List<Reacao> getListaReactions() {
		return listaReactions;
	}

	public void setListaReactions(List<Reacao> listaReactions) {
		this.listaReactions = listaReactions;
	}
	
	public ListDataModel<Reacao> getReacoes() {
		return reacoes;
	}

	public void setReacoes(ListDataModel<Reacao> reacoes) {
		this.reacoes = reacoes;
	}

	@PostConstruct
	public void PreparaPesquisa() {
		try {
			FabricaConexao fabrica = new FabricaConexao();
			Connection conexao = fabrica.fazerConexao();

			FuncaoSubsistemaDAO dao = new FuncaoSubsistemaDAO(conexao);
			this.funcaoSubsistemas = dao.listarTodos();
			
			ReacaoDAO daoReacao = new ReacaoDAO(conexao);
			this.listaReactions = daoReacao.listarTodos();
			
			this.reacoes = new ListDataModel<>(this.listaReactions);

			fabrica.fecharConexao();
						
		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
	}
	
	public void PesquisarReacoes() {
		try {
			
			FabricaConexao fabrica = new FabricaConexao();
			Connection conexao = fabrica.fazerConexao();
			
			ReacaoDAO dao = new ReacaoDAO(conexao);
			
			String nomeSubSistema = dao.BuscarNomeSubSistema(this.funcaoSubsistema.getId());
			
			this.listaReactions = dao.listarTodosPorSistemaAndSubSistema(null, nomeSubSistema);
	
			fabrica.fecharConexao();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
