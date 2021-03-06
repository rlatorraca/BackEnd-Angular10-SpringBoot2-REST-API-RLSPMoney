package com.rlsp.moneyapi.resource;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rlsp.moneyapi.config.property.RlspMoneyApiProperty;

/**
 * Usado para fazer o LOGOUT da aplicacao REST
 *  - apenas tirando o REFRESH TOKEN
 * @author rlatorraca
 *
 */

@RestController
@RequestMapping("/tokens")
public class TokenResource {
	
	@Autowired
	private RlspMoneyApiProperty rlspMoneyApiProperty;
	
	@DeleteMapping("/revoke")
	public void revoke(HttpServletRequest requisicao, HttpServletResponse resposta) {
		Cookie cookie = new Cookie("refreshToken", null); // coloca o valor do "refreshToken" como NULL
		cookie.setHttpOnly(true);
		//cookie.setSecure(false); // em producao sera TRUE
		cookie.setSecure(rlspMoneyApiProperty.getSeguranca().isEnableHttps()); // TODO em producao sera TRUE
		cookie.setPath(requisicao.getContextPath() + "/oauth/token");
		cookie.setMaxAge(0); // Faz expirar o TOKEN
		
		resposta.addCookie(cookie);
		resposta.setStatus(HttpStatus.NO_CONTENT.value()); // retorna um resposta sem CONTEUDO
		
	}

}
