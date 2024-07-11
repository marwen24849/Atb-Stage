package fsb.ucar.Gateway.security;

import com.nimbusds.jose.shaded.gson.JsonArray;
import com.nimbusds.jose.shaded.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;





@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class ConfigurationSecurity {



    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
    private String jwkSetUri;



    @Bean
    public ReactiveJwtDecoder jwtDecoder() {
        return NimbusReactiveJwtDecoder.withJwkSetUri(jwkSetUri).build();
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        // @formatter:off
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .authorizeExchange(exchange -> exchange
                        .pathMatchers(HttpMethod.OPTIONS).permitAll()
                       // .pathMatchers("/MS-Creadit/**").hasAnyAuthority("user","admin")
                        //.pathMatchers("/actuator/**").hasAnyAuthority("user", "admin")
                        .anyExchange().permitAll()

                       // .anyExchange().denyAll()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(jwtConverter())
                        )
                );

        // @formatter:on
        return http.build();
    }





    private Converter<Jwt, Mono<AbstractAuthenticationToken>> jwtConverter() {
        return new Converter<Jwt, Mono<AbstractAuthenticationToken>>() {
            private GrantedAuthoritiesExtractor extractor = new GrantedAuthoritiesExtractor();

            @Override
            public Mono<AbstractAuthenticationToken> convert(Jwt jwt) {
                return Mono.just(new JwtAuthenticationToken(jwt, extractor.extractAuthorities(jwt)) );
            }
        };
    }

    static class GrantedAuthoritiesExtractor extends JwtAuthenticationConverter {

        protected Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
            Map<String, Object> realmAcces;
            Collection<String> roles;
            if(jwt.getClaim("realm_access")== null){
                return Set.of();
            }
            realmAcces = jwt.getClaim("realm_access");
            roles = (Collection<String>) realmAcces.get("roles");


            return roles.stream()
                    .map(Object::toString)
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet());
        }
    }
}
