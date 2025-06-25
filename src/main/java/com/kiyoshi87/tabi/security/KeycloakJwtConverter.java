package com.kiyoshi87.tabi.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class KeycloakJwtConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    @SuppressWarnings("ConstantConditions")
    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt jwt) {
        Stream<GrantedAuthority> defaultAuthorities =
                Optional.ofNullable(new JwtGrantedAuthoritiesConverter().convert(jwt))
                        .stream()
                        .flatMap(Collection::stream);

        return new JwtAuthenticationToken(
                jwt,
                Stream.concat(
                                defaultAuthorities,
                                extractAuthorities(jwt).stream())
                        .collect(Collectors.toSet())
        );
    }

    /**
     * Extract authorities from the JWT token.
     *
     * @param jwt token
     * @return The actual authorities/roles converted from keycloak roles to spring roles.
     */
    private Collection<? extends GrantedAuthority> extractAuthorities(Jwt jwt) {
        return Optional.ofNullable(jwt.getClaim("resource_access"))
                .filter(Map.class::isInstance)
                .map(Map.class::cast)
                .map(resourceAccess -> resourceAccess.get("account"))
                .filter(Map.class::isInstance)
                .map(Map.class::cast)
                .map(account -> account.get("roles"))
                .filter(List.class::isInstance)
                .map(List.class::cast)
                .map(this::mapRolesToAuthorities)
                .orElse(Collections.emptySet());
    }

    private Collection<GrantedAuthority> mapRolesToAuthorities(List<?> roles) {
        return roles.stream()
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.replace("-", "_")))
                .collect(Collectors.toSet());
    }
}
