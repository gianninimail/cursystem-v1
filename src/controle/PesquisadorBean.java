package controle;

import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.model.ListDataModel;

import org.primefaces.event.TabChangeEvent;
//import org.primefaces.event.TabCloseEvent;

import dao.PesquisadorDAO;
import dao.TipoUsuarioDAO;
import modelo.Pesquisador;
import modelo.TipoUsuario;
import modelo.Usuario;
import util.FabricaConexao;
import util.JSFUtil;
import util.SessaoUtil;

@ManagedBean
public class PesquisadorBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long idProjetoEspecialidade;
	private Pesquisador pesquisador;
	private Usuario usuario;
	private String msgOps;
	private List<Pesquisador> listaPesquisadores;
	private List<Usuario> listaUsuariosParaPesquisadores;
	private Boolean ehNovoPesquisador = true;
	private ListDataModel<Pesquisador> pesquisadores;
	private List<TipoUsuario> listaTiposUsuarios;

	public PesquisadorBean() {
		this.pesquisador = new Pesquisador();
		this.listaPesquisadores = new ArrayList<Pesquisador>();
	}

	public Pesquisador getPesquisador() {
		return this.pesquisador;
	}

	public void setPesquisador(Pesquisador _pesquisador) {
		this.pesquisador = _pesquisador;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getMsgOps() {
		return msgOps;
	}

	public void setMsgOps(String msgOps) {
		this.msgOps = msgOps;
	}

	public Boolean getEhNovoPesquisador() {
		return ehNovoPesquisador;
	}

	public void setEhNovoPesquisador(Boolean ehNovoPesquisador) {
		this.ehNovoPesquisador = ehNovoPesquisador;
	}

	public ListDataModel<Pesquisador> getPesquisadores() {
		return pesquisadores;
	}

	public void setPesquisadores(ListDataModel<Pesquisador> pesquisadores) {
		this.pesquisadores = pesquisadores;
	}

	public List<Pesquisador> getListaPesquisadores() {
		return listaPesquisadores;
	}

	public void setListaPesquisadores(List<Pesquisador> listaPesquisadores) {
		this.listaPesquisadores = listaPesquisadores;
	}

	public List<Usuario> getListaUsuariosParaPesquisadores() {
		return listaUsuariosParaPesquisadores;
	}

	public void setListaUsuariosParaPesquisadores(List<Usuario> listaUsuariosParaPesquisadores) {
		this.listaUsuariosParaPesquisadores = listaUsuariosParaPesquisadores;
	}

	public Long getIdProjetoEspecialidade() {
		return idProjetoEspecialidade;
	}

	public void setIdProjetoEspecialidade(Long idProjetoEspecialidade) {
		this.idProjetoEspecialidade = idProjetoEspecialidade;
	}

	public List<TipoUsuario> getListaTiposUsuarios() {
		return listaTiposUsuarios;
	}

	public void setListaTiposUsuarios(List<TipoUsuario> tiposUsuarios) {
		this.listaTiposUsuarios = tiposUsuarios;
	}

	// METODOS DAO - ACESSO AO BANCO DE DADOS
	public void CadastrarPesquisador() {

		try {

			FabricaConexao fabrica = new FabricaConexao();
			Connection conexao = fabrica.fazerConexao();

			PesquisadorDAO dao = new PesquisadorDAO(conexao);

			if (this.ehNovoPesquisador) {

				this.pesquisador.setDataCadastro(new Date());
				//this.pesquisador.setTipoUsuario(2);
				dao.InserirNovo(this.pesquisador);
			}
			if (!this.ehNovoPesquisador) {

				this.pesquisador.setUsuario(this.usuario);
				dao.InserirExiste(this.pesquisador);
			}

			PesquisarPesquisadores();

			fabrica.fecharConexao();

			JSFUtil.adicionarMensagemSucesso("Researcher registered successfully!");

		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	public void PrepararNovo() {
		this.pesquisador = new Pesquisador();

		try {

			FabricaConexao fabrica = new FabricaConexao();
			Connection conexao = fabrica.fazerConexao();

			PesquisadorDAO dao = new PesquisadorDAO(conexao);

			this.pesquisador.setId(dao.PegarProximoID());
			
			AtualizarListaUsuarioParaPesquisadores();

		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	public void ExcluirPesquisador() {
		try {

			FabricaConexao fabrica = new FabricaConexao();
			Connection conexao = fabrica.fazerConexao();

			PesquisadorDAO dao = new PesquisadorDAO(conexao);
			dao.Excluir(this.pesquisador);

			PesquisarPesquisadores();

			fabrica.fecharConexao();

			JSFUtil.adicionarMensagemSucesso("Successfully deleted searcher!");

		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	public void PrepararExcluir() {
		this.pesquisador = pesquisadores.getRowData();
	}

	public void EditarPesquisador() {
		try {

			FabricaConexao fabrica = new FabricaConexao();
			Connection conexao = fabrica.fazerConexao();

			PesquisadorDAO dao = new PesquisadorDAO(conexao);
			dao.Editar(this.pesquisador);

			PesquisarPesquisadores();

			fabrica.fecharConexao();

			JSFUtil.adicionarMensagemSucesso("Researcher edited successfully!");

		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	public void PrepararEditar() {
		this.pesquisador = pesquisadores.getRowData();
	}

	private void AtualizarListaUsuarioParaPesquisadores() {

		try {

			FabricaConexao fabrica = new FabricaConexao();
			Connection conexao = fabrica.fazerConexao();

			PesquisadorDAO dao = new PesquisadorDAO(conexao);

			this.listaUsuariosParaPesquisadores = dao.listarUsuariosParaPesquisador();

			fabrica.fecharConexao();

		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
	}
	
	public void FiltrarPesquisadores() {
		try {

			FabricaConexao fabrica = new FabricaConexao();
			Connection conexao = fabrica.fazerConexao();

			PesquisadorDAO dao = new PesquisadorDAO(conexao);
			dao.Editar(this.pesquisador);

			PesquisarPesquisadores();

			fabrica.fecharConexao();

		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
	}
	// FIM DOS METODOS DAO - ACESSO AO BANCO DE DADOS

	// METODOS PARA MANIPULACAO DA PAGINA - FRONT END
	@PostConstruct
	public void PesquisarPesquisadores() {
		try {
			
			this.listaPesquisadores.clear();
			
			FabricaConexao fabrica = new FabricaConexao();
			Connection conexao = fabrica.fazerConexao();

			PesquisadorDAO dao = new PesquisadorDAO(conexao);
			
			Usuario user = SessaoUtil.pegarUsuarioSessao();
			
			if (user.getTipoUsuario().getId() == 1) {
				this.listaPesquisadores = dao.listarTodos();
			}
			else {
				
				Pesquisador pesq = dao.PegarPeloID(user.getId());
				
				this.listaPesquisadores.add(pesq);
			}

			TipoUsuarioDAO daoTU = new TipoUsuarioDAO(conexao);
			listaTiposUsuarios = daoTU.listarTodosAtivos();
			
			fabrica.fecharConexao();

			pesquisadores = new ListDataModel<Pesquisador>(listaPesquisadores);
			
			AtualizarListaUsuarioParaPesquisadores();

		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	public void onTabChange(TabChangeEvent event) {
		String nomeAba = event.getTab().getId().toString();
		System.out.println(nomeAba);

		if (nomeAba.equals("abaNovo")) {
			this.ehNovoPesquisador = true;
		}
		if (nomeAba.equals("abaExistente")) {
			this.ehNovoPesquisador = false;
		}
	}

	// FIM DOS METODOS PARA MANIPULACAO DA PAGINA - FRONT END
}
