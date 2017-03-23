package br.com.tcc.terraplenagem.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.tcc.terraplenagem.domain.ItemOrdemServico;
import br.com.tcc.terraplenagem.util.HibernateUtil;

public class ItemOrdemServicoDAO extends GenericDAO<ItemOrdemServico> {

	@SuppressWarnings("unchecked")
	public List<ItemOrdemServico> buscarPorCodigoOrdemServico(Long codigoOrdemServico) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(ItemOrdemServico.class);
			consulta.add(Restrictions.eq("ordemServico.codigo", codigoOrdemServico));
			List<ItemOrdemServico> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}

	}
}
