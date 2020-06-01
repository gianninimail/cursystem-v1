package controle;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.model.ListDataModel;

import modelo.Reacao;

@ManagedBean
public class ReacaoBean {
	
	//------ Atributos -----------------------------
	Reacao reacao;
	private String msgOps;
	private List<Reacao> listaReactions;
	private ListDataModel<Reacao> reacoes;
	private int idFuncaoSubsistema;
	//------ fim atributos --------------------------

	public ReacaoBean() {
		this.reacao = new Reacao();
		this.idFuncaoSubsistema = 0;
	}
	
	public Reacao getReacao() {
		return reacao;
	}

	public void setReacao(Reacao reacao) {
		this.reacao = reacao;
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

	public int getIdFuncaoSubsistema() {
		return idFuncaoSubsistema;
	}

	public void setIdFuncaoSubsistema(int idFuncaoSubsistema) {
		this.idFuncaoSubsistema = idFuncaoSubsistema;
	}
	
	@PostConstruct
	public void PreparaPesquisa() {
//		try {
//			
//			FabricaConexao fabrica = new FabricaConexao();
//			Connection conexao = fabrica.fazerConexao();
//			
//			ReacaoDAO dao = new ReacaoDAO(conexao);
//			
//			//String nomeSubSistema = dao.BuscarNomeSubSistema(this.idFuncaoSubsistema);
//			
//			this.listaReactions = dao.listarTodosPorSistemaAndSubSistema(null, nomeSubSistema);
//	
//			fabrica.fecharConexao();
//			
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
	}
	
	public void PesquisarReacoes() {
//		try {
//			
//			FabricaConexao fabrica = new FabricaConexao();
//			Connection conexao = fabrica.fazerConexao();
//			
//			ReacaoDAO dao = new ReacaoDAO(conexao);
//			
//			String nomeSubSistema = dao.BuscarNomeSubSistema(this.idFuncaoSubsistema);
//			
//			this.listaReactions = dao.listarTodosPorSistemaAndSubSistema(null, nomeSubSistema);
//	
//			fabrica.fecharConexao();
//			
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
	}
}
