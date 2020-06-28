package pma.ebook.config;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.models.auth.In;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	private static final String TOKEN_ACCESS = "Token_Access";
	private static final String ACCESS_GLOBAL = "global";
	private static final String ACCESS_EVERYTHING = "accessEverything";

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
			.select()
			.apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
			.paths(PathSelectors.any())
			.build()
			.securitySchemes(List.of(new ApiKey(TOKEN_ACCESS, AUTHORIZATION, In.HEADER.name())))
			.securityContexts(List.of(securityContext()));
	}

	private static SecurityContext securityContext() {
		return SecurityContext.builder()
			.securityReferences(defaultAuth())
			.forPaths(PathSelectors.any())
			.build();
	}

	private static List<SecurityReference> defaultAuth() {
		return List.of(new SecurityReference(TOKEN_ACCESS, new AuthorizationScope[]{ new AuthorizationScope(ACCESS_GLOBAL, ACCESS_EVERYTHING) }));
	}
}
