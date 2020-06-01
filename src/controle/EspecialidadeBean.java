package controle;

import java.sql.Connection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

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
