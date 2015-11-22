import java.util.ArrayList;
import java.util.List;


import models.DicaAssunto;
import models.DicaConselho;
import models.DicaDisciplina;
import models.DicaMaterial;
import models.Disciplina;
import models.Tema;
import models.User;
import models.dao.GenericDAOImpl;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.db.jpa.JPA;


public class Global extends GlobalSettings {

	private static GenericDAOImpl dao = new GenericDAOImpl();
	private List<Disciplina> disciplinas = new ArrayList<>();
	
	private User usuario0;
	private User usuario1;
	private User usuario2;
	private User usuario3;
	private User usuario4;
	private User usuario5;
	private User usuario6;
	private User usuario7;
	private User usuario8;
	private User usuario9;
	private User usuario10;
	
	@Override
	public void onStart(Application app) {
		Logger.info("Aplicação inicializada...");

		JPA.withTransaction(new play.libs.F.Callback0() {
			@Override
			public void invoke() throws Throwable {
				if(dao.findAllByClassName(Disciplina.class.getName()).size() == 0){
					criaUsuarios();
					criaDisciplinaTemas();
					
				}
			}
		});
	}
	
	@Override
	public void onStop(Application app){
	    JPA.withTransaction(new play.libs.F.Callback0() {
	    @Override
	    public void invoke() throws Throwable {
	        Logger.info("Aplicação finalizando...");
	        disciplinas = dao.findAllByClassName("Disciplina");

	        for (Disciplina disciplina: disciplinas) {
	        dao.removeById(Disciplina.class, disciplina.getId());
	       } 
	    }}); 
	}
	// para este, é obrigatorio a criacao de usuarios antes
	private void criaDisciplinaTemas(){
		
		Disciplina si1 = new Disciplina("Sistemas de Informação 1");
		
		Tema analise = new Tema("Análise x Design");
		si1.addTema(analise);
		this.criaDicas(analise, usuario1);
		criaDicas(analise, usuario10);
		
		Tema oo = new Tema("Orientação a objetos");
		si1.addTema(oo);
		this.criaDicas(oo, usuario2);
		
		Tema grasp = new Tema("GRASP");
		si1.addTema(grasp);
		criaDicas(grasp, usuario3);
		
		Tema gof = new Tema("GoF");
		si1.addTema(gof);
		criaDicas(gof, usuario4);
		
		Tema arquitetura = new Tema("Arquitetura"); 
		si1.addTema(arquitetura);
		criaDicas(arquitetura, usuario5);
		
		si1.addTema(new Tema("Play"));
		si1.addTema(new Tema("JavaScript"));
		si1.addTema(new Tema("HTML / CSS / Bootstrap"));
		si1.addTema(new Tema("Heroku"));
		si1.addTema(new Tema("Labs"));
		si1.addTema(new Tema("Minitestes"));
		si1.addTema(new Tema("Projeto"));
		dao.persist(si1);
		
		Disciplina p1 = new Disciplina( "Programação 1");
		
		Tema logica = new Tema( "Lógica Procedural"); 
		p1.addTema(logica);
		criaDicas(logica, usuario6);
		
		Tema operadores = new Tema( "Operadores Lógicos"); 
		p1.addTema(operadores);
		criaDicas(operadores, usuario7);
		
		p1.addTema(new Tema( "Operadores Condicionais"));
		p1.addTema(new Tema( "Listas"));
		p1.addTema(new Tema( "Laço For"));
		p1.addTema(new Tema( "Laço While"));
		p1.addTema(new Tema( "Dicionários"));
		p1.addTema(new Tema( "Matrizes"));
		dao.persist(p1);
		
		Disciplina tc = new Disciplina("Teoria da Computação");
		
		Tema automatos = new Tema("Autômatos Determinísticos"); 
		tc.addTema(automatos);
		criaDicas(automatos, usuario8);
		
		Tema lingr = new Tema("Linguagens Regulares"); 
		tc.addTema(lingr);
		criaDicas(lingr, usuario9);
		
		tc.addTema(new Tema("Autômatos Não Determinísticos"));
		tc.addTema(new Tema("Autômatos Com Pilha"));
		tc.addTema(new Tema("Linguagens Livre de Contexto"));
		tc.addTema(new Tema("Gramáticas"));
		tc.addTema(new Tema("Linguagens Sensíveis ao Contexto"));
		tc.addTema(new Tema("Máquina de Turing"));
		dao.persist(tc);
		
		dao.flush();
	}
	
	public void criaDicas( Tema tema, User usuario ){
		// Para assunto
		DicaAssunto assunto = new DicaAssunto("Assunto da dica");
		assunto.setTema(tema);
		assunto.setUser(usuario.getNome());
		tema.addDica(assunto);
		dao.persist(assunto);
		
		//Para conselho
		DicaConselho conselho = new DicaConselho("Conselho dado pelo usuario.");
		tema.addDica(conselho);
		conselho.setTema(tema);
		conselho.setUser(usuario.getNome());
		dao.persist(conselho);
		
		//Para disciplina
		DicaDisciplina dcDisciplina = new DicaDisciplina("Disciplina citada", "Motivo pela indicação da disciplina");
		tema.addDica(dcDisciplina);
		dcDisciplina.setTema(tema);
		dcDisciplina.setUser(usuario.getNome());
		dao.persist(dcDisciplina);
		
		//Para material
		DicaMaterial material = new DicaMaterial("www.sitedematerial.com");
		tema.addDica(material);
		material.setTema(tema);
		material.setUser(usuario.getNome());
		dao.persist(material);
		
		
	}
	
	public void criaUsuarios(){
		usuario0 = new User("jeferson@gmail.com","123456", "jeferson"); usuario0.setNome("Jeferson Holanda");
		usuario1 = new User("damon@gmail.com","segredo", "damon"); usuario1.setNome("Damon Albarn");
		usuario2 = new User("graham@gmail.com","segredo", "graham"); usuario2.setNome("Graham Coxon");
		usuario3 = new User("alex@gmail.com","segredo", "alex"); usuario3.setNome("Alex James");
		usuario4 = new User("david@gmail.com","segredo", "david"); usuario4.setNome("David Rowntree");
		usuario5 = new User("kurt@gmail.com","segredo", "kurt"); usuario5.setNome("Kurt Cobain");
		usuario6 = new User("chris@gmail.com","segredo", "chris"); usuario6.setNome("Chris Novoselic");
		usuario7 = new User("dave@gmail.com","segredo", "dave"); usuario7.setNome("Dave Growl");
		usuario8 = new User("dolores@gmail.com","segredo", "dolores"); usuario8.setNome("Dolores O'Riddan");
		usuario9 = new User("nina@gmail.com","segredo", "nina"); usuario9.setNome("Nina Perssom");
		usuario10 = new User("alanis@gmail.com","segredo", "alanis"); usuario10.setNome("Alanis Morissette");
		
		dao.persist(usuario1);
		dao.persist(usuario2);
		dao.persist(usuario3);
		dao.persist(usuario4);
		dao.persist(usuario5);
		dao.persist(usuario6);
		dao.persist(usuario7);
		dao.persist(usuario8);
		dao.persist(usuario9);
		dao.persist(usuario10);
		
		dao.flush();
	}
}
