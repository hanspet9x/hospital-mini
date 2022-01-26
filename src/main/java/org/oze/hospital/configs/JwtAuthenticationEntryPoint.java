package org.oze.hospital.configs;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.oze.hospital.models.Response;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

	private static final long serialVersionUID = -7858869558953243875L;

	@Override
	public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			AuthenticationException authException) throws IOException, ServletException {
		httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
		log.info("::::::::: JWT Exception message ===> " + httpServletRequest.getAttribute("exception"));

		Response map = new Response(true, "Access denied", "Invalid or expired bearer token");

		byte[] body = new ObjectMapper().writeValueAsBytes(map);
		httpServletResponse.getOutputStream().write(body);
	}
}