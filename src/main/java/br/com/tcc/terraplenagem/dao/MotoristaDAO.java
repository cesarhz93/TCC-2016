package br.com.tcc.terraplenagem.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import br.com.tcc.terraplenagem.domain.Motorista;
import br.com.tcc.terraplenagem.util.HibernateUtil;

public class MotoristaDAO extends GenericDAO<Motorista> {

	@SuppressWarnings("unchecked")
	public List<Motorista> listarOrdenado() {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Motorista.class);
			consulta.createAlias("funcionario", "funcionario");
			consulta.addOrder(Order.asc("funcionario.nome"));
			List<Motorista> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}

}
