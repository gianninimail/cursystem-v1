package controle;

import java.sql.Connection;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import dao.DesafioDAO;
import dao.RespostaDAO;
import modelo.Desafio;
import modelo.Resposta;
import modelo.Usuario;
import util.FabricaConexao;
import util.JSFUtil;
import util.SessaoUtil;

@ManagedBean(name = "servicoRespostas")
@ViewScoped
public class ServicoRespostas {

	@SuppressWarnings("unused")
	public TreeNode criarArvoreRespostas() {
		
        TreeNode raiz = new DefaultTreeNode();
        
        try {
			
        	FabricaConexao fabrica = new FabricaConexao();
			Connection conexao = fabrica.fazerConexao();
			
			Usuario user = SessaoUtil.pegarUsuarioSessao();
				
			DesafioDAO daoD = new DesafioDAO(conexao);
			List<Desafio> desafiosDoEspecialista = daoD.listarTodosPorPesquisador(user.getId());
			
			for (Desafio d : desafiosDoEspecialista) {
				
				TreeNode noDesafio = new DefaultTreeNode("desafio", d, raiz);
				
				RespostaDAO daoR = new RespostaDAO(conexao);
				List<Resposta> respostas = daoR.TodasRespostasDoDesafio(d.getId());
				
				for (Resposta r : respostas) {
					
					
					if (r.getValidacoes() > 0) {
						//r.setTitulo(r.getTitulo() + "  " + "<<");
						TreeNode noResposta = new DefaultTreeNode("resposta", r, noDesafio);
					}
					else {
						TreeNode noResposta = new DefaultTreeNode("resposta", r, noDesafio);
					}
				}
			}

			fabrica.fecharConexao();
			
			
		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
         
        return raiz;
    }
}
