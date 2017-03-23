package br.com.tcc.terraplenagem.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;

import br.com.tcc.terraplenagem.domain.ItemOrdemServico;
import br.com.tcc.terraplenagem.domain.OrdemServico;
import br.com.tcc.terraplenagem.util.HibernateUtil;

public class OrdemServicoDAO extends GenericDAO<OrdemServico> {

	public void salvar(OrdemServico ordemServico, List<ItemOrdemServico> itensOrdemServico) {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;

		try {
			transacao = sessao.beginTransaction();

			sessao.merge(ordemServico);
			transacao.commit();

			for (int posicao = 0; posicao < itensOrdemServico.size(); posicao++) {
				ItemOrdemServico itemOrdemServico = itensOrdemServico.get(posicao);
				itemOrdemServico.setOrdemServico(ordemServico);

				sessao.merge(itemOrdemServico);

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
	public List<OrdemServico> listarOrdenado() {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(OrdemServico.class);
			consulta.addOrder(Order.asc("codigo"));
			List<OrdemServico> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}

}
