package controle;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import javax.faces.context.FacesContext;

import dao.EspecialidadeDAO;
import modelo.Especialidade;
import util.BeanBase;
import util.FabricaConexao;
import util.JSFUtil;

@SuppressWarnings("serial")
@ManagedBean
public class EspecialidadeBean extends BeanBase {

	private Especialidade especialidade;
	private List<Especialidade> especialidades;

	public EspecialidadeBean() {
		super();
		this.especialidade = new Especialidade();
	}

	public Especialidade getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(Especialidade especialidade) {
		this.especialidade = especialidade;
	}

	public List<Especialidade> getEspecialidades() {
		return especialidades;
	}

	public void setEspecialidades(List<Especialidade> especialidades) {
		this.especialidades = especialidades;
	}

	public void CadastrarEspecialidade() {
		try {
			String msgSucesso = "";

			FabricaConexao fabrica = new FabricaConexao();
			Connection conexao = fabrica.fazerConexao();

			EspecialidadeDAO dao = new EspecialidadeDAO(conexao);
			dao.Inserir(this.especialidade);

			fabrica.fecharConexao();

			msgSucesso = getMensagem("especialidade.sucesso");

			JSFUtil.adicionarMensagemSucesso(msgSucesso);

			especialidade = new Especialidade();

		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
	}



	public String PrepararEditarEspecialidade(Especialidade especialidade) throws Exception {

		try {
			
			Map<String,Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
			
			sessionMapObj.put("editarEspecialidade", especialidade);

			return "/alteracao/alt_especiadade.jsf?faces-redirect=true";

		} catch (Exception e) {
			return null;
		}

	}

	public void EditarEspecialidade(Especialidade especialidade) {
		try {
			String msgSucesso = "";

			FabricaConexao fabrica = new FabricaConexao();
			Connection conexao = fabrica.fazerConexao();

			EspecialidadeDAO dao = new EspecialidadeDAO(conexao);
			dao.Editar(especialidade);

			fabrica.fecharConexao();

			msgSucesso = getMensagem("especialidade.editada");
			JSFUtil.adicionarMensagemSucesso(msgSucesso);

		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
	}
	
	public void PrepararExcluirEspecialidade(Especialidade especialidade) {
		try {

			this.especialidade = especialidade;

		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
	}
	
	public void ExcluirEspecialidade(Especialidade especialidade) {
		try {
			String msgSucesso = "";

			FabricaConexao fabrica = new FabricaConexao();
			Connection conexao = fabrica.fazerConexao();

			EspecialidadeDAO dao = new EspecialidadeDAO(conexao);
			dao.Excluir(especialidade);

			this.especialidades = dao.listarTodos();

			fabrica.fecharConexao();

			msgSucesso = getMensagem("especialidade.excluida");
			JSFUtil.adicionarMensagemSucesso(msgSucesso);

		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	@PostConstruct
	public void PreparaPesquisa() {
		try {
			FabricaConexao fabrica = new FabricaConexao();
			Connection conexao = fabrica.fazerConexao();

			EspecialidadeDAO dao = new EspecialidadeDAO(conexao);
			this.especialidades = dao.listarTodos();

			fabrica.fecharConexao();

		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
	}
}
