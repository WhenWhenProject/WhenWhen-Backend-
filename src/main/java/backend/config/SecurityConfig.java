package backend.config;

import backend.config.properties.CorsProperties;
import backend.filter.TokenAuthenticationFilter;
import backend.oauth.entity.RoleType;
import backend.oauth.exception.RestAuthenticationEntryPoint;
import backend.oauth.handler.OAuth2AuthenticationFailureHandler;
import backend.oauth.handler.OAuth2AuthenticationSuccessHandler;
import backend.oauth.handler.TokenAccessDeniedHandler;
import backend.oauth.repository.OAuth2AuthorizationRequestRepositoryBasedOnCookie;
import backend.oauth.service.PrincipalOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final OAuth2AuthorizationRequestRepositoryBasedOnCookie oAuth2AuthorizationRequestRepositoryBasedOnCookie;
    private final PrincipalOAuth2UserService principalOAuth2UserService;
    private final TokenAuthenticationFilter tokenAuthenticationFilter;
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    private final TokenAccessDeniedHandler tokenAccessDeniedHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final CorsProperties corsProperties;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // 기본 시큐리티 설정
        httpSecurity
                .cors()

                .and()

                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()

                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable();

        // OAuth2 시큐리티 설정
        httpSecurity.oauth2Login()
                .authorizationEndpoint()
                .baseUri("/oauth2/authorization") // 사용자가 소셜 로그인을 클릭하였을 때 리액트에서 이 URL 로 GET 요청을 하면, 자동으로 소셜 로그인 화면으로 리다이렉트해준다.
                .authorizationRequestRepository(oAuth2AuthorizationRequestRepositoryBasedOnCookie)

                .and()

                // 사용자가 소셜 로그인 화면에서 로그인을 해서 성공을 하면 구글 서버에서 우리 서버의 이 URL 로 인증 코드를 보내준다.
                .redirectionEndpoint()
                .baseUri("/*/oauth2/code/*")

                .and()

                .userInfoEndpoint()
                .userService(principalOAuth2UserService)

                .and()

                .successHandler(oAuth2AuthenticationSuccessHandler)
                .failureHandler(oAuth2AuthenticationFailureHandler);

        // 리액트에서 요청이 오면 제일 먼저 JWT 토큰 꺼내서 검증
        httpSecurity
                .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        // API 사용 권한 설정
        httpSecurity
                .authorizeRequests()
                .requestMatchers(request -> CorsUtils.isPreFlightRequest(request)).permitAll()
                .antMatchers("/api/admin/**").hasAnyAuthority(RoleType.ADMIN.getCode())
                .antMatchers("/api/users/**").hasAnyAuthority(RoleType.USER.getCode())
                .anyRequest().authenticated();

        // 예외 처리 설정
        httpSecurity
                .exceptionHandling()
                .accessDeniedHandler(tokenAccessDeniedHandler)
                .authenticationEntryPoint(restAuthenticationEntryPoint);
    }

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource corsConfigSource = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedHeaders(Arrays.asList(corsProperties.getAllowedHeaders().split(",")));
        corsConfig.setAllowedMethods(Arrays.asList(corsProperties.getAllowedMethods().split(",")));
        corsConfig.setAllowedOrigins(Arrays.asList(corsProperties.getAllowedOrigins().split(",")));
        corsConfig.setAllowCredentials(true);
        corsConfig.setMaxAge(corsConfig.getMaxAge());

        corsConfigSource.registerCorsConfiguration("/**", corsConfig);
        return corsConfigSource;
    }

    @Override
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

}