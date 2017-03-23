package br.com.tcc.terraplenagem.util;

import org.hibernate.Session;
import org.junit.Test;

public class HibernateUtilTest {

	@Test
	public void conectar() {
		// abrindo uma sessao
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		// encerrando a sessao
		sessao.close();
		// destrói a fábrica de sessão
		HibernateUtil.getFabricaDeSessoes().close();
	}

}
