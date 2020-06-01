package controle;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.model.ListDataModel;

import org.primefaces.event.TabChangeEvent;
//import org.primefaces.event.TabCloseEvent;

import javax.faces.context.FacesContext;

import dao.PesquisadorDAO;
import dao.TipoUsuarioDAO;
import dao.UsuarioDAO;
import modelo.Pesquisador;
import modelo.TipoUsuario;
import modelo.Usuario;
import util.BeanBase;
import util.FabricaConexao;
import util.JSFUtil;
import util.SessaoUtil;

@ManagedBean
public class PesquisadorBean extends BeanBase implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long idProjetoEspecialidade;
	private Pesquisador pesquisador;
	private Usuario usuario;
	private String msgOps;
	private List<Pesquisador> listaPesquisadores;
	private List<Usuario> listaUsuariosParaPesquisadores;
	private Boolean ehNovoPesquisador = true;
	private ListDataModel<Pesquisador> pesquisadores;
	private ListDataModel<Pesquisador> pesquisadoresPendentes;
	private List<TipoUsuario> listaTiposUsuarios;
	
	private UIComponent componenteEmail;
	private UIComponent componenteTelefone;
	private UIComponent componenteSenha;
	private UIComponent componenteSenhaConfirmar;

	public UIComponent getComponenteEmail() {
		return componenteEmail;
	}

	public void setComponenteEmail(UIComponent componenteEmail) {
		this.componenteEmail = componenteEmail;
	}

	public UIComponent getComponenteTelefone() {
		return componenteTelefone;
	}

	public void setComponenteTelefone(UIComponent componenteTelefone) {
		this.componenteTelefone = componenteTelefone;
	}

	public UIComponent getComponenteSenha() {
		return componenteSenha;
	}

	public void setComponenteSenha(UIComponent componenteSenha) {
		this.componenteSenha = componenteSenha;
	}

	public UIComponent getComponenteSenhaConfirmar() {
		return componenteSenhaConfirmar;
	}

	public void setComponenteSenhaConfirmar(UIComponent componenteSenhaConfirmar) {
		this.componenteSenhaConfirmar = componenteSenhaConfirmar;
	}	
	
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
	
	public ListDataModel<Pesquisador> getPesquisadoresPendentes() {
		return pesquisadoresPendentes;
	}

	public void setPesquisadoresPendentes(ListDataModel<Pesquisador> pesquisadoresPendentes) {
		this.pesquisadoresPendentes = pesquisadoresPendentes;
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

			JSFUtil.adicionarMensagemSucesso(getMensagem("usuario.excluido"));

		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(getMensagem("usuario.excluir.erro"));
		}
	}

	public void PrepararExcluir() {
		this.pesquisador = pesquisadores.getRowData();
	}
	
	public void AprovarPesquisador() {
		try {

			FabricaConexao fabrica = new FabricaConexao();
			Connection conexao = fabrica.fazerConexao();

			PesquisadorDAO dao = new PesquisadorDAO(conexao);
			dao.Aprovar(this.pesquisador);

			AtualizarListaPesquisadoresPendentes();

			fabrica.fecharConexao();

			JSFUtil.adicionarMensagemSucesso(getMensagem("usuario.aprovado"));

		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
	}
	
	public void PrepararPendente() {
		this.pesquisador = pesquisadoresPendentes.getRowData();
	}
	
	@Deprecated
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

	public String EditarPesquisador(Pesquisador pesquisador) {
		try {
			String outcome = "";
			String msgSucesso = "";
			
			this.pesquisador = pesquisador;
			
			if (existeUsuarioEmail()) {
				JSFUtil.adicionarMensagemErroComponente(componenteEmail.getClientId(),
						this.getMensagem("usuario.email.ja.cadastrado"));
				return outcome;
			}

			if (!telefoneValido()) {
				JSFUtil.adicionarMensagemErroComponente(componenteTelefone.getClientId(),
						this.getMensagem("usuario.telefone.invalido"));
				return outcome;
			}

			if (!senhaValido()) {
				JSFUtil.adicionarMensagemErroComponente(componenteSenha.getClientId(),
						this.getMensagem("usuario.senha.invalida"));
				return outcome;
			}

			if (!senhasIguais()) {
				JSFUtil.adicionarMensagemErroComponente(componenteSenhaConfirmar.getClientId(),
						this.getMensagem("senha.confirmar.iguais"));
				return outcome;
			}

			FabricaConexao fabrica = new FabricaConexao();
			Connection conexao = fabrica.fazerConexao();

			PesquisadorDAO dao = new PesquisadorDAO(conexao);
			dao.Editar(pesquisador);

			fabrica.fecharConexao();

			msgSucesso = getMensagem("usuario.editado");
			JSFUtil.adicionarMensagemSucesso(msgSucesso);
			
			return outcome;

		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
			return "";
		}
	}	
	
	public void PrepararEditar() {
		this.pesquisador = pesquisadores.getRowData();
	}
	
	public String PrepararEditarPesquisador(Pesquisador pesquisador) throws Exception {

		try {
			
			Map<String,Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
			
			sessionMapObj.put("editarPesquisador", pesquisador);

			return "/alteracao/alt_usuario.jsf?faces-redirect=true";

		} catch (Exception e) {
			return null;
		}

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
	
	private void AtualizarListaPesquisadoresPendentes() {

		try {

			FabricaConexao fabrica = new FabricaConexao();
			Connection conexao = fabrica.fazerConexao();

			PesquisadorDAO dao = new PesquisadorDAO(conexao);
			
			pesquisadoresPendentes = new ListDataModel<Pesquisador>(dao.listarPesquisadoresPendentes());

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
			
			AtualizarListaPesquisadoresPendentes();

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

	private boolean existeUsuarioEmail() throws SQLException {
		FabricaConexao fabrica = new FabricaConexao();
		Connection conexao = fabrica.fazerConexao();
		UsuarioDAO dao = new UsuarioDAO(conexao);
		Usuario user = dao.PegarPeloEmail(this.pesquisador.getEmail());
		
		if (user != null) {
			if (user.getId().equals(this.pesquisador.getId())) {
				return false;
			}else {
				return true;
			}
		}else {	
			return false;
		}
	}	

	private boolean telefoneValido() {
		String telefone = this.pesquisador.getTel();
		if (telefone.endsWith("_")) {
			telefone = telefone.substring(0, telefone.length() - 1);
		}
		if (telefone.endsWith("_")) {
			return false;
		}
		this.pesquisador.setTel(telefone);
		return true;
	}	
	
	private boolean senhaValido() {
		return pesquisador.getSenha().length() >= 5;
	}

	private boolean senhasIguais() {
		return pesquisador.getSenha().equals(pesquisador.getSenhaConfirmar());
	}


	
}
