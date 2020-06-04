package pma.ebook.security;

import static pma.ebook.security.SecurityConstants.HEADER_STRING;
import static pma.ebook.security.SecurityConstants.SECRET;
import static pma.ebook.security.SecurityConstants.TOKEN_PREFIX;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	public JWTAuthorizationFilter(final AuthenticationManager authManager) {
		super(authManager);
	}

	@Override
	protected void doFilterInternal(final HttpServletRequest req, final HttpServletResponse res, final FilterChain chain) throws IOException, ServletException {
		final String header = req.getHeader(HEADER_STRING);

		if (header == null || !header.startsWith(TOKEN_PREFIX)) {
			chain.doFilter(req, res);
			return;
		}

		final var authentication = getAuthentication(req);

		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(req, res);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(final HttpServletRequest request) {
		final var token = request.getHeader(HEADER_STRING);
		if (token != null) {
			// parse the token.
			final var user = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
				.build()
				.verify(token.replace(TOKEN_PREFIX, ""))
				.getSubject();

			if (user != null) {
				return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
			}
			return null;
		}
		return null;
	}
}
