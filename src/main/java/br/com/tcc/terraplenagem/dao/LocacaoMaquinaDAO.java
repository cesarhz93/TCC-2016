package br.com.tcc.terraplenagem.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;

import br.com.tcc.terraplenagem.domain.ItemLocacaoMaquina;
import br.com.tcc.terraplenagem.domain.LocacaoMaquina;
import br.com.tcc.terraplenagem.util.HibernateUtil;

public class LocacaoMaquinaDAO extends GenericDAO<LocacaoMaquina> {

	public void salvar(LocacaoMaquina locacaoMaquina, List<ItemLocacaoMaquina> itensLocacao) {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;

		try {
			transacao = sessao.beginTransaction();
			
			sessao.merge(locacaoMaquina);
			transacao.commit();

			for (int posicao = 0; posicao < itensLocacao.size(); posicao++) {
				ItemLocacaoMaquina itemLocacao = itensLocacao.get(posicao);
				itemLocacao.setLocacaoMaquina(locacaoMaquina);

				sessao.merge(itemLocacao);
			}

			transacao.commit();
		} catch (RuntimeException erro) {
			if (transacao != null) {
				transacao.rollback();
			}
			throw erro;
		} finally {
			sessao.close();
		}

	}
	
	@SuppressWarnings("unchecked")
	public List<LocacaoMaquina> listarOrdenado() {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(LocacaoMaquina.class);
			consulta.addOrder(Order.asc("codigo"));
			List<LocacaoMaquina> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}

}
