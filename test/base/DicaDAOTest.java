package base;

import static org.fest.assertions.Assertions.assertThat;

import java.util.List;

import models.Dica;
import models.DicaConselho;
import models.Tema;
import models.User;
import models.dao.GenericDAO;
import models.dao.GenericDAOImpl;

import org.junit.Test;

import play.mvc.Http;
import play.mvc.Result;
import play.test.FakeRequest;


public class DicaDAOTest extends AbstractTest{
	Result result;
	GenericDAO dao = new GenericDAOImpl();

	/**
	 * Testa se é possível persistir uma dica.
	 */
	@Test
	public void deveCadastrarDica() {
		Tema tema = new Tema("Análise fundamentalista");
		Dica dica = new DicaConselho("Invista na Bovespa");
		tema.addDica(dica);
		dica.setTema(tema);
		
		dao.persist(tema);
		
		DicaConselho dicaNoBD = (DicaConselho) dao.findByAttributeName("DicaConselho", "conselho", "Invista na Bovespa").get(0);
		
		assertThat(dicaNoBD.getTipo()).isEqualTo("DicaConselho");
		assertThat(dicaNoBD.getTexto()).isEqualTo("Invista na Bovespa");
	}
	
	/**
	 * Testa se é possível dar merge em mudanças em uma dica.
	 */
	@Test
	public void deveIncrementarConcordancias() {
		Tema tema = new Tema("Análise fundamentalista");
		Dica dica = new DicaConselho("Invista na Bovespa");
		tema.addDica(dica);
		dica.setTema(tema);
		
		dao.persist(tema);
		
		DicaConselho dicaNoBD = (DicaConselho) dao.findByAttributeName("DicaConselho", "conselho", "Invista na Bovespa").get(0);
		
		assertThat(dicaNoBD.getConcordancias()).isEqualTo(0);
		
		dicaNoBD.incrementaConcordancias();
		dao.merge(dicaNoBD);
		
		dicaNoBD = (DicaConselho) dao.findByAttributeName("DicaConselho", "conselho", "Invista na Bovespa").get(0);
		assertThat(dicaNoBD.getConcordancias()).isEqualTo(1);
	}
}