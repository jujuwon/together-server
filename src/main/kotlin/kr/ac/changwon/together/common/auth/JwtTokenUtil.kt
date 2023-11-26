package kr.ac.changwon.together.common.auth

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtTokenUtil {
    @Value("\${jwt.secret}")
    private lateinit var secret: String

    fun generateToken(id: String): String {
        val claims: Map<String, Any> = HashMap()
        return createToken(claims, id)
    }

    private fun createToken(claims: Map<String, Any>, subject: String): String {
        return Jwts.builder().setClaims(claims).setSubject(subject)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 60 * 100))
            .signWith(SignatureAlgorithm.HS256, secret).compact()
    }

    fun validateToken(token: String, userDetails: UserDetails): Boolean {
        val username = extractUsername(token)
        return (username == userDetails.username && !isTokenExpired(token))
    }

    fun extractUsername(token: String): String {
        return extractClaim(token, Claims::getSubject)
    }

    private fun isTokenExpired(token: String): Boolean {
        return extractExpiration(token).before(Date())
    }

    private fun extractExpiration(token: String): Date {
        return extractClaim(token, Claims::getExpiration)
    }

    private fun <T> extractClaim(token: String, claimsResolver: (Claims) -> T): T {
        val claims = extractAllClaims(token)
        return claimsResolver(claims)
    }

    private fun extractAllClaims(token: String): Claims {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).body
    }
}
