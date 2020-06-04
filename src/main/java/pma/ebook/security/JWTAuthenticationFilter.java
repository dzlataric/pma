package pma.ebook.security;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static pma.ebook.security.SecurityConstants.EXPIRATION_TIME;
import static pma.ebook.security.SecurityConstants.HEADER_STRING;
import static pma.ebook.security.SecurityConstants.SECRET;
import static pma.ebook.security.SecurityConstants.TOKEN_PREFIX;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.AllArgsConstructor;
import pma.ebook.users.ApplicationUser;

@AllArgsConstructor
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;

	@Override
	public Authentication attemptAuthentication(final HttpServletRequest req,
		final HttpServletResponse res) throws AuthenticationException {
		try {
			final var creds = new ObjectMapper()
				.readValue(req.getInputStream(), ApplicationUser.class);

			return authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
					creds.getUsername(),
					creds.getPassword(),
					new ArrayList<>())
			);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(final HttpServletRequest req,final HttpServletResponse res, final FilterChain chain, final Authentication auth) {
		final var token = JWT.create()
			.withSubject(((User) auth.getPrincipal()).getUsername())
			.withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
			.sign(HMAC512(SECRET.getBytes()));
		res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
	}
}
