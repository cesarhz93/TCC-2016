package br.com.tcc.terraplenagem.main;

import org.hibernate.Session;
import br.com.tcc.terraplenagem.util.HibernateUtil;

public class HibernateUtilTest {
	public static void main(String[] args) {
		// abrindo uma sessao
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		// encerrando a sessao
		sessao.close();
		// destrói a fábrica de sessão
		HibernateUtil.getFabricaDeSessoes().close();
	}
}
