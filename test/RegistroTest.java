import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.callAction;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.status;
import static play.test.Helpers.flash;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.User;
import models.dao.GenericDAO;
import models.dao.GenericDAOImpl;

import org.junit.Test;

import play.mvc.Http;
import play.mvc.Result;
import play.test.FakeRequest;
import base.AbstractTest;


public class RegistroTest extends AbstractTest{

	Result result;
	
	GenericDAO dao = new GenericDAOImpl();
	
	/**
	 * Testa se é possível se acessar a url de registro de usuário.
	 */
	@Test
	public void callLogin() {
		result = callAction(controllers.routes.ref.Register.show(),
				fakeRequest());
		assertThat(status(result)).isEqualTo(Http.Status.OK);
	}
	
	/**
	 * Testa se é possível registrar um usuário.
	 */
	@Test
	public void deveRegistrarUser() {
		List<User> users = dao.findAllByClassName("User");
		
		assertThat(users).isEmpty();
		
		FakeRequest fakeRequest = new FakeRequest();
		Map<String, String> form = new HashMap<String, String>();
		form.put("nome", "joao");
		form.put("email", "a@b.c");
		form.put("login", "joooao");
		form.put("pass", "tchutchu");
		fakeRequest.withFormUrlEncodedBody(form);
		
		result = callAction(controllers.routes.ref.Register.register(), fakeRequest);
		
		assertThat(status(result)).isEqualTo(Http.Status.SEE_OTHER);
		
		users = dao.findAllByClassName("User");
		
		assertThat(users).isNotEmpty();
	}
	
	/**
	 * Testa se realmente não se pode registrar um usuário com login já usado.
	 */
	@Test
	public void deveNaoPermitirCadastroDeUsuariosComMesmoLogin() {
		FakeRequest fakeRequest1 = new FakeRequest();
		Map<String, String> form1 = new HashMap<String, String>();
		form1.put("nome", "joao");
		form1.put("email", "abc@bbc.com");
		form1.put("login", "mimimimi");
		form1.put("pass", "tchutchu");
		fakeRequest1.withFormUrlEncodedBody(form1);
		
		result = callAction(controllers.routes.ref.Register.register(), fakeRequest1);
		
		assertThat(status(result)).isEqualTo(Http.Status.SEE_OTHER);
		
		List<User> users = dao.findAllByClassName("User");
		
		assertThat(users.size()).isEqualTo(1);
		
		FakeRequest fakeRequest2 = new FakeRequest();
		Map<String, String> form2 = new HashMap<String, String>();
		form2.put("nome", "joao");
		form2.put("email", "a@b.com");
		form2.put("login", "mimimimi");
		form2.put("pass", "tchutchu");
		fakeRequest2.withFormUrlEncodedBody(form2);
		
		result = callAction(controllers.routes.ref.Register.register(), fakeRequest2);
		
		assertThat(status(result)).isEqualTo(Http.Status.BAD_REQUEST);
		
		users = dao.findAllByClassName("User");
		
		assertThat(users.size()).isEqualTo(1);
		Map<String, String> flash = new HashMap<String, String>();
		flash.put("fail", "Login em uso");
		assertThat(flash(result)).isEqualTo(flash);
	}
	
	/**
	 * Testa se realmente não se pode registrar um usuário com e-mail já usado.
	 */
	@Test
	public void deveNaoPermitirCadastroDeUsuariosComMesmoEmail() {
		FakeRequest fakeRequest1 = new FakeRequest();
		Map<String, String> form1 = new HashMap<String, String>();
		form1.put("nome", "joao");
		form1.put("email", "abc@bbc.com");
		form1.put("login", "mimimimi");
		form1.put("pass", "tchutchu");
		fakeRequest1.withFormUrlEncodedBody(form1);
		
		result = callAction(controllers.routes.ref.Register.register(), fakeRequest1);
		
		assertThat(status(result)).isEqualTo(Http.Status.SEE_OTHER);
		
		List<User> users = dao.findAllByClassName("User");
		
		assertThat(users.size()).isEqualTo(1);
		
		FakeRequest fakeRequest2 = new FakeRequest();
		Map<String, String> form2 = new HashMap<String, String>();
		form2.put("nome", "joao");
		form2.put("email", "abc@bbc.com");
		form2.put("login", "herbert");
		form2.put("pass", "tchutchu");
		fakeRequest2.withFormUrlEncodedBody(form2);
		
		result = callAction(controllers.routes.ref.Register.register(), fakeRequest2);
		
		assertThat(status(result)).isEqualTo(Http.Status.BAD_REQUEST);
		
		users = dao.findAllByClassName("User");
		
		assertThat(users.size()).isEqualTo(1);
		Map<String, String> flash = new HashMap<String, String>();
		flash.put("fail", "E-mail em uso");
		assertThat(flash(result)).isEqualTo(flash);
	}
}