import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

import models.User;
import models.dao.GenericDAO;
import models.dao.GenericDAOImpl;
import base.AbstractTest;


public class UsuarioTest extends AbstractTest{

	GenericDAO dao = new GenericDAOImpl();
	
	@Test
	public void deveSalvarUsuarioNoBD() {
		User u = new User("admin@gmail.com","1234","Admin");
		dao.persist(u);
		
		assertThat(dao.findAllByClassName("User").size()).isEqualTo(1);
		
		User usuarioNoBD = dao.findByEntityId(User.class, u.getId());
		
		assertThat(usuarioNoBD.getLogin()).isEqualTo("Admin");
		
		User u2 = new User("abc@gmail.com", "123", "Leo");
		dao.persist(u2);
		
		assertThat(dao.findAllByClassName("User").size()).isEqualTo(2);
		
		User usuario2NoBD = dao.findByEntityId(User.class, u2.getId());
		
		assertThat(usuario2NoBD.getLogin()).isEqualTo("Leo");
	}
	
	@Test
	public void senhaDoUsuarioDeveEstarCriptografada() {
		User u = new User("leo@gmail.com", "789456", "Leo");
		dao.persist(u);
		
		assertThat(u.checkPass("789456")).isTrue();
		assertThat(u.checkPass("789455")).isFalse();
	}
}