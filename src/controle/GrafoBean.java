package controle;

import java.io.Serializable;
import java.sql.Connection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.diagram.ConnectEvent;
import org.primefaces.event.diagram.ConnectionChangeEvent;
import org.primefaces.event.diagram.DisconnectEvent;
import org.primefaces.model.diagram.DefaultDiagramModel;
import org.primefaces.model.diagram.Element;
import org.primefaces.model.diagram.connector.StraightConnector;
import org.primefaces.model.diagram.endpoint.EndPoint;
import org.primefaces.model.diagram.endpoint.EndPointAnchor;
import org.primefaces.model.diagram.endpoint.RectangleEndPoint;
import org.primefaces.model.diagram.overlay.ArrowOverlay;

import dao.ReacaoDAO;
import modelo.Metabolito;
import modelo.Reacao;
import util.FabricaConexao;

//@Scope(value = WebApplicationContext.SCOPE_SESSION)
@ManagedBean
public class GrafoBean implements Serializable {

	//------ Atributos -----------------------------
	private static final long serialVersionUID = 1L;
	private DefaultDiagramModel modelo;
	private List<Reacao> listaReactions;
    private boolean suspenderEvento;
    private int idFuncaoSubsistema;
    //------ fim atributos --------------------------
    
    //------ Metodos de acesso (gets e sets) ---------
//    public void CarregarModelo(int _idFunc) {
//    	
//    	modelo = new DefaultDiagramModel();
//        modelo.setMaxConnections(-1);
//         
//        modelo.getDefaultConnectionOverlays().add(new ArrowOverlay(20, 20, 1, 1));
//        StraightConnector connector = new StraightConnector();
//        connector.setPaintStyle("{strokeStyle:'#98AFC7', lineWidth:2}");
//        connector.setHoverPaintStyle("{strokeStyle:'#5C738B'}");
//        
//        modelo.setDefaultConnector(connector);
//        
//        CarregarListaReacoes(_idFunc);
//        
//        CarregarListaElementos();
//    }
    
    public DefaultDiagramModel getModelo() {
    	return this.modelo;
    }
    
    public int getIdFuncaoSubsistema() {
		return idFuncaoSubsistema;
	}

	public void setIdFuncaoSubsistema(int idFuncaoSubsistema) {
		this.idFuncaoSubsistema = idFuncaoSubsistema;
	}
    
    //------ fim metodos de acesso -------------------
        

	@PostConstruct
    public void init() {
		
		int id = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"));
		
        modelo = new DefaultDiagramModel();
        modelo.setMaxConnections(-1);
         
        modelo.getDefaultConnectionOverlays().add(new ArrowOverlay(20, 20, 1, 1));
        StraightConnector connector = new StraightConnector();
        connector.setPaintStyle("{strokeStyle:'#98AFC7', lineWidth:2}");
        connector.setHoverPaintStyle("{strokeStyle:'#5C738B'}");
        
        modelo.setDefaultConnector(connector);
        
        CarregarListaReacoes(id);
        
        CarregarListaElementos();
        
    }
    
    public void Conectar(ConnectEvent event) {
        if(!suspenderEvento) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Conectado", 
                    "De " + event.getSourceElement().getData()+ " Para " + event.getTargetElement().getData());
         
            FacesContext.getCurrentInstance().addMessage(null, msg);
         
            RequestContext.getCurrentInstance().update("form:msgs");
        }
        else {
        	suspenderEvento = false;
        }
    }
    
    public void MudarConexao(ConnectionChangeEvent event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Conexão Trocada", 
                    "Saída Original:" + event.getOriginalSourceElement().getData() + 
                    ", Nova Saída: " + event.getNewSourceElement().getData() + 
                    ", Entrada Original: " + event.getOriginalTargetElement().getData() + 
                    ", Nova Entrada: " + event.getNewTargetElement().getData());
         
        FacesContext.getCurrentInstance().addMessage(null, msg);
         
        RequestContext.getCurrentInstance().update("form:msgs");
        suspenderEvento = true;
    }
    
    public void Desconectar(DisconnectEvent event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Disconectado", 
                    "De " + event.getSourceElement().getData()+ " Para " + event.getTargetElement().getData());
         
        FacesContext.getCurrentInstance().addMessage(null, msg);
         
        RequestContext.getCurrentInstance().update("form:msgs");
    }
    
//    private void CarregarListaReacoes(String _sistema, String _subSistema) {
//		try {
//			
//			FabricaConexao fabrica = new FabricaConexao();
//			Connection conexao = fabrica.fazerConexao();
//			
//			ReactionDAO dao = new ReactionDAO(conexao);
//			this.listaReactions = dao.listarTodosPorSistemaAndSubSistema(null, "Teste");
//
//			fabrica.fecharConexao();
//			
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//	}
    
    private void CarregarListaReacoes(int _idFunc) {
		try {
			
			FabricaConexao fabrica = new FabricaConexao();
			Connection conexao = fabrica.fazerConexao();
			
			ReacaoDAO dao = new ReacaoDAO(conexao);
			
			String nomeSubSistema = dao.BuscarNomeSubSistema(Integer.toUnsignedLong(_idFunc));
			
			this.listaReactions = dao.listarTodosPorSistemaAndSubSistema(null, nomeSubSistema);

			fabrica.fecharConexao();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
    
    private void CarregarListaElementos() {
    	try {
    		int numR = 0;
    		int numM = 0;
    		
    		int posE = 13;
    		int posT = 8;
    		int elementoPorLinha = 0;
    		
			for (Reacao reac : this.listaReactions) {
				int posEdutoEsq = 3;
				int posEstudoTop = 3;
				numR++;
				
				Element el = new Element(new Reacao(reac.getName(), "reacao.png"), posE + "em", posT + "em");
				
				EndPoint in = CriarPontoFixador(EndPointAnchor.LEFT);
				in.setTarget(true);
				
				EndPoint out = CriarPontoFixador(EndPointAnchor.RIGHT);
				out.setSource(true);
		        
				el.addEndPoint(in);
				el.addEndPoint(out);
		        
				el.setStyleClass("ui-diagram-reacao");
				
		        this.modelo.addElement(el);
		        
		        posE+=10;
		        elementoPorLinha++;
		        if (elementoPorLinha > 7) {
		        	posE = 3;
					posT = posT + 7;
					elementoPorLinha = 0;
				}
		        
		        String regex = "\\(\\d?.\\d*\\)|\\[\\D\\]|\\:|\\s";
		        String equacaoSemEspacos = reac.getEquation().replaceAll(" ", "");
		        
		        String reacao[];
		        if (equacaoSemEspacos.contains("-->")) {
		        	reacao = equacaoSemEspacos.split("-->");
		        	System.out.println("reação com: -->");
				} 
		        else {
		        	reacao = equacaoSemEspacos.split("<==>");
		        	System.out.println("reação com: <==>");
				}
		        
		        System.out.println(equacaoSemEspacos);
		        
		        String edutos = reacao[0].replaceAll(regex, "");
		        String compsEduto[] = edutos.split("\\+");
		        for (String comp : compsEduto) {
			        
		        	System.out.println(comp);
		        	Element compExistente = BuscarMetabolitoNoGrafo(comp);
		        	if (compExistente != null) {
						System.out.println("Já está incluído: " + comp);
						org.primefaces.model.diagram.Connection cn = new org.primefaces.model.diagram.Connection(compExistente.getEndPoints().get(1), el.getEndPoints().get(0));
				        this.modelo.connect(cn);
					}
			        else {
			        	numM++;
			        	Element eduto = new Element(new Metabolito(comp, "metabolito.png"), posEdutoEsq + "em", posEstudoTop + "em");
						
						EndPoint inE = CriarPontoFixador(EndPointAnchor.LEFT);
						inE.setTarget(true);
						
						EndPoint outE = CriarPontoFixador(EndPointAnchor.RIGHT);
						outE.setSource(true);
				        
						eduto.addEndPoint(inE);
						eduto.addEndPoint(outE);
				        
				        this.modelo.addElement(eduto);
				        
				        org.primefaces.model.diagram.Connection cn = new org.primefaces.model.diagram.Connection(eduto.getEndPoints().get(1), el.getEndPoints().get(0));
				        this.modelo.connect(cn);
				        
				        posEstudoTop = posEstudoTop + 7;
				        /*posE+=10;
				        elementoPorLinha++;
				        if (elementoPorLinha > 7) {
				        	posE = 3;
							posT = posT + 7;
							elementoPorLinha = 0;
						}*/
					}
		        }
		        
		        String produtos = reacao[1].replaceAll(regex, "");
		        String compsProduto[] = produtos.split("\\+");
		        for (String comp : compsProduto) {
			        
		        	System.out.println(comp);
		        	Element compExistente = BuscarMetabolitoNoGrafo(comp);
			        if (compExistente != null) {
						System.out.println("Já está incluído: " + comp);
						org.primefaces.model.diagram.Connection cn = new org.primefaces.model.diagram.Connection(el.getEndPoints().get(1), compExistente.getEndPoints().get(0));
				        this.modelo.connect(cn);
				        //org.primefaces.model.diagram.overlay.LabelOverlay
					}
			        else {
				        numM++;
			        	Element produto = new Element(new Metabolito(comp, "metabolito.png"), posE + "em", posT + "em");
						
						EndPoint inP = CriarPontoFixador(EndPointAnchor.LEFT);
						inP.setTarget(true);
						
						EndPoint outP = CriarPontoFixador(EndPointAnchor.RIGHT);
						outP.setSource(true);
				        
						produto.addEndPoint(inP);
						produto.addEndPoint(outP);
				        
				        this.modelo.addElement(produto);
				        
				        org.primefaces.model.diagram.Connection cn = new org.primefaces.model.diagram.Connection(el.getEndPoints().get(1), produto.getEndPoints().get(0));
				        this.modelo.connect(cn);
				        
				        posE+=10;
				        elementoPorLinha++;
				        if (elementoPorLinha > 7) {
				        	posE = 3;
							posT = posT + 7;
							elementoPorLinha = 0;
						}
					}
		        }
		        System.out.println("mapeamento");
		        
			}
			System.out.println("Número de reações: " + numR);
	        System.out.println("Número de metabólitos: " + numM);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
    }
    
    private EndPoint CriarPontoFixador(EndPointAnchor _anchor) {
        RectangleEndPoint endPoint = new RectangleEndPoint(_anchor, 10, 10);
        endPoint.setScope("network");
        endPoint.setStyle("{fillStyle:'#98AFC7'}");
        endPoint.setHoverStyle("{fillStyle:'#5C738B'}");
         
        return endPoint;
    }
    
    private Element BuscarMetabolitoNoGrafo(String _name) {
    	Element elemento = null;
    	try {
    		List<Element> lista = modelo.getElements();
    		for (Element el : lista) {
				if (el.getData().getClass().getSimpleName().equals("Metabolito")) {
					Metabolito com = (Metabolito)el.getData();
					if (com.getName().equals(_name)) {
						elemento = el;
						return elemento;
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
    	
    	return null;
    }
}
