package br.com.tcc.terraplenagem.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.tcc.terraplenagem.domain.ItemLocacaoMaquina;
import br.com.tcc.terraplenagem.util.HibernateUtil;

public class ItemLocacaoMaquinaDAO extends GenericDAO<ItemLocacaoMaquina> {
	
	@SuppressWarnings("unchecked")
	public List<ItemLocacaoMaquina> buscarPorCodigoLocacaoMaquina(Long codigoLocacaoMaquina) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(ItemLocacaoMaquina.class);
			consulta.add(Restrictions.eq("locacaoMaquina.codigo", codigoLocacaoMaquina));
			List<ItemLocacaoMaquina> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}

	}
}
