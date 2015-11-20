import java.util.ArrayList;
import java.util.List;

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
	
	@Override
	public void onStart(Application app) {
		Logger.info("Aplicação inicializada...");

		JPA.withTransaction(new play.libs.F.Callback0() {
			@Override
			public void invoke() throws Throwable {
				if(dao.findAllByClassName(Disciplina.class.getName()).size() == 0){
					criaDisciplinaTemas();
					criaUsuarios();
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
	
	private void criaDisciplinaTemas(){
		Disciplina si1 = new Disciplina("Sistemas de Informação 1");
		si1.addTema(new Tema("Análise x Design"));
		si1.addTema(new Tema("Orientação a objetos"));
		si1.addTema(new Tema("GRASP"));
		si1.addTema(new Tema("GoF"));
		si1.addTema(new Tema("Arquitetura"));
		si1.addTema(new Tema("Play"));
		si1.addTema(new Tema("JavaScript"));
		si1.addTema(new Tema("HTML / CSS / Bootstrap"));
		si1.addTema(new Tema("Heroku"));
		si1.addTema(new Tema("Labs"));
		si1.addTema(new Tema("Minitestes"));
		si1.addTema(new Tema("Projeto"));
		dao.persist(si1);
		
		Disciplina p1 = new Disciplina( "Programação 1");
		p1.addTema(new Tema( "Lógica Procedural"));
		p1.addTema(new Tema( "Operadores Lógicos"));
		p1.addTema(new Tema( "Operadores Condicionais"));
		p1.addTema(new Tema( "Listas"));
		p1.addTema(new Tema( "Laço For"));
		p1.addTema(new Tema( "Laço While"));
		p1.addTema(new Tema( "Dicionários"));
		p1.addTema(new Tema( "Matrizes"));
		dao.persist(p1);
		
		Disciplina tc = new Disciplina("Teoria da Computação");
		tc.addTema(new Tema("Autômatos Determinísticos"));
		tc.addTema(new Tema("Linguagens Regulares"));
		tc.addTema(new Tema("Autômatos Não Determinísticos"));
		tc.addTema(new Tema("Autômatos Com Pilha"));
		tc.addTema(new Tema("Linguagens Livre de Contexto"));
		tc.addTema(new Tema("Gramáticas"));
		tc.addTema(new Tema("Linguagens Sensíveis ao Contexto"));
		tc.addTema(new Tema("Máquina de Turing"));
		dao.persist(tc);
		
		dao.flush();
	}
	
	public void criaUsuarios(){
		User usuario0 = new User("jeferson@gmail.com","123456", "jeferson"); usuario0.setNome("Jeferson Holanda");
		User usuario1 = new User("damon@gmail.com","segredo", "damon"); usuario1.setNome("Damon Albarn");
		User usuario2 = new User("graham@gmail.com","segredo", "graham"); usuario2.setNome("Graham Coxon");
		User usuario3 = new User("alex@gmail.com","segredo", "alex"); usuario3.setNome("Alex James");
		User usuario4 = new User("david@gmail.com","segredo", "david"); usuario4.setNome("David Rowntree");
		User usuario5 = new User("kurt@gmail.com","segredo", "kurt"); usuario5.setNome("Kurt Cobain");
		User usuario6 = new User("chris@gmail.com","segredo", "chris"); usuario6.setNome("Chris Novoselic");
		User usuario7 = new User("daven@gmail.com","segredo", "dave"); usuario7.setNome("Dave Growl");
		User usuario8 = new User("dolores@gmail.com","segredo", "dolores"); usuario8.setNome("Dolores O'Riddan");
		User usuario9 = new User("nina@gmail.com","segredo", "nina"); usuario9.setNome("Nina Perssom");
		User usuario10 = new User("alanis@gmail.com","segredo", "alanis"); usuario10.setNome("Alanis Morissette");
		
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
