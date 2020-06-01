package controle;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.model.ListDataModel;

//import org.apache.commons.mail.EmailException;
import org.primefaces.event.FlowEvent;

import dao.RespostaDAO;
import modelo.Pesquisador;
import modelo.Resposta;
import util.FabricaConexao;
import util.JSFUtil;

@ManagedBean
public class RespostaBean {

	@ManagedProperty(value = "#{desafioBean}")
	private DesafioBean desafioBean;
	
	Resposta resposta;
	private boolean skip;
	
	private List<Resposta> listaRespostas;
	private ListDataModel<Resposta> respostas;
	
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
	
	public boolean isSkip() {
		return skip;
	}

	public void setSkip(boolean skip) {
		this.skip = skip;
	}
	
	public void CadastrarResposta() {        
		try {
			this.resposta.setDataCadastro(new Date());

			FabricaConexao fabrica = new FabricaConexao();
			Connection conexao = fabrica.fazerConexao();
			
			this.resposta.setDesafio(desafioBean.desafio.getId());
			this.resposta.setPesquisador((Pesquisador)desafioBean.desafio.getUsuario());

			RespostaDAO dao = new RespostaDAO(conexao);
			dao.Inserir(this.resposta);
			
			//EnviarEmail();

			this.listaRespostas = dao.listarTodos();

			this.respostas = new ListDataModel<Resposta>(listaRespostas);

			fabrica.fecharConexao();

			JSFUtil.adicionarMensagemSucesso("Resposta cadastrado com sucesso!");
			
		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
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
	
	public String onProcessoFluxo(FlowEvent event) {
        
		if(skip) {
            skip = false;  // reset in case user goes back
            return "confirm";
        }
        else {
            return event.getNewStep();
        }
    }
	
	//FIM DO ACESSO AO BANCO DE DADOS
	
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
		
	}
	
}
