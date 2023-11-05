package com.zero.studentdashboard.filter;

import com.zero.studentdashboard.util.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    /**
     * This method defines the paths that should not have to be filtered.
     *
     * @param request current HTTP request.
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        List<AntPathRequestMatcher> matchers = Arrays.asList(
                new AntPathRequestMatcher("/admin/**"),
                new AntPathRequestMatcher("/login")
        );
        return matchers.stream().anyMatch(m -> m.matches(request));
    }

    /**
     * This method defines the paths that should be filtered.
     *
     * @param request current HTTP request.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        if (request.getAttribute(WebUtils.FORWARD_REQUEST_URI_ATTRIBUTE) != null) {
            chain.doFilter(request, response);
            return;
        }
        final String requestTokenHeader = request.getHeader("Authorization");

        String username = null;
        String jwtToken = null;
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                logger.warn("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                logger.warn("JWT Token has expired");
            }
        } else {
            logger.warn("JWT Token does not begin with Bearer String");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
                // Checks whether user's role is student.
                if (userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("STUDENT"))) {
                    // Student is allowed to send request to specific path it checks whether it is valid
                    if (request.getRequestURI().equals("/applications/create") && request.getMethod().equals("POST")) {
                        // Allows access to Student to use the endpoint.
                        chain.doFilter(request, response);
                    } else {
                        // Returns forbidden if the user with role Student tries to access other endpoints.
                        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Forbidden");
                    }
                } else if (userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
                    // Allows any path for admin.
                    chain.doFilter(request, response);
                } else {
                    // Handle access restrictions for other roles or scenarios here.
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Forbidden");
                }
            }
        }
        chain.doFilter(request, response);
    }

}