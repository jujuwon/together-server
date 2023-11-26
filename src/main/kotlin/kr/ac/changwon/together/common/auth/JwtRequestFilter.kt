package kr.ac.changwon.together.common.auth

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtRequestFilter : OncePerRequestFilter() {

    @Autowired
    private lateinit var userDetailsService: JwtUserDetailsService

    @Autowired
    private lateinit var jwtTokenUtil: JwtTokenUtil

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val requestTokenHeader = request.getHeader("Authorization")

        var username: String? = null
        var jwtToken: String? = null

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7)
            username = jwtTokenUtil.extractUsername(jwtToken)
        }

        if (username != null && SecurityContextHolder.getContext().authentication == null) {
            val userDetails = this.userDetailsService.loadUserByUsername(username)

            if (jwtTokenUtil.validateToken(jwtToken!!, userDetails)) {
                val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.authorities
                )
                usernamePasswordAuthenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
            }
        }
        chain.doFilter(request, response)
    }
}
