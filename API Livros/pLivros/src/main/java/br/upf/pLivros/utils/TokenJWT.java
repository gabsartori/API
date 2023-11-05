package br.upf.pLivros.utils;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;

public class TokenJWT {
	
	private static Key chave;

	public TokenJWT(Key chave) {
		TokenJWT.chave = chave;
	}

	private static Key gerarChave() {
		String keyString = "YzBhZTgwZWM2ZTI5Njk1YzQ3YWIxYzg0ZTk5NjkxZjQ4YzIwZGRkMGVlZWU4NTFiMjhjZDg5NzU5NTFjODQ3ZQ==";
		Key key = new SecretKeySpec(keyString.getBytes(), 0, keyString.getBytes().length, "HmacSHA256");
		return key;
	}

	public static Date definirDataDeExpiracao(long tempoEmMinutos) {
		LocalDateTime localDateTime = LocalDateTime.now().plusMinutes(tempoEmMinutos);
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	public static String processarTokenJWT(String usuario) {
		Key chave = gerarChave();
		TokenJWT token = new TokenJWT(chave);
		Date dataExpiracao = definirDataDeExpiracao(42000L);
		return token.gerarToken(usuario, dataExpiracao);
	}

	public static boolean validarToken(String token) {
		chave = gerarChave();
		boolean tokenValido = false;
		if (token != null) {
			try {
				Jwts.parserBuilder().setSigningKey(chave).build().parseClaimsJws(token);
				tokenValido = true;
			} catch (ExpiredJwtException e) {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Token expirado!");
			} catch (MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token inválido!");
			}
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token inválido!!");
		}
		return tokenValido;
	}

	public String gerarToken(String nomeUsuario, Date dataExpiracao) {
		return Jwts.builder().setHeaderParam("typ", "JWT") 
				.setSubject(nomeUsuario) 
				.setIssuer("upf") 
				.setIssuedAt(new Date()) 
				.claim("password", "sdlkjsdoijonpvf65v4e6fv5e6ver")
				.setExpiration(dataExpiracao) 
				.signWith(chave, SignatureAlgorithm.HS256) 
				.compact();
	}

	public String recuperarSubjectDoToken(String token) {
		Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(chave).build().parseClaimsJws(token);
		return claimsJws.getBody().getSubject();
	}

	public String recuperarIssuerDoToken(String token) {
		Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(chave).build().parseClaimsJws(token);
		return claimsJws.getBody().getIssuer();
	}

	public String resuperarClaim(String token, String claim) {
		String subject = "";
		if (token != null && !token.equals("")) {
			Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(chave).build().parseClaimsJws(token);
			subject = claimsJws.getBody().get(claim).toString();
		}
		return subject;
	}
}
