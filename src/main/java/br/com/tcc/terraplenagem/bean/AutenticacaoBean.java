package br.com.tcc.terraplenagem.bean;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import br.com.tcc.terraplenagem.dao.UsuarioDAO;
import br.com.tcc.terraplenagem.domain.Funcionario;
import br.com.tcc.terraplenagem.domain.Usuario;

@ManagedBean
@SessionScoped
public class AutenticacaoBean {

	private Usuario usuario;
	private Usuario usuarioLogado;

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@PostConstruct
	public void iniciar() {
		usuario = new Usuario();
		usuario.setFuncionario(new Funcionario());
	}

	public void autenticar() {
		try {
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			usuarioLogado = usuarioDAO.autenticar(usuario.getLogin(), usuario.getSenha());

			if (usuarioLogado == null) {
				Messages.addGlobalError("Login e/ou senha incorretos!");
				return;
			}
			
			if(usuarioLogado.getAtivo().equals("Não")){
				Messages.addGlobalError("Usuário Inativo. Entre com outro usuário!");
				return;
			}

			Faces.redirect("./paginas/home.xhtml");
		} catch (IOException erro) {
			erro.printStackTrace();
			Messages.addGlobalError(erro.getMessage());
		}
	}
	
	public boolean temPermissoes(List<String> permissoes){
				
		for(String permissao : permissoes){
			if(usuarioLogado.getTipo().equals(permissao)){
				return true;
			}
		}
		return false;
	}

}
