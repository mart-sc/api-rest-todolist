package br.com.martsc.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.martsc.todolist.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        /**
         * Obtém o caminho da requisição
         */
        var servletPath = request.getServletPath();

        /**
         * Verifica se o caminho começa com /tasks/
         */
        if (servletPath.startsWith("/tasks/")) {

            /**
             * Obtém o cabeçalho de Autorização da requisição
             */
            var authorization = request.getHeader("Authorization");
            
            /**
             * Verifica se inexiste uma autorização no cabeçalho
             */
            if (authorization == null) {
                response.sendError(401, "Cabeçalho de autorização é obrigatório");
                return;
            }

            /**
             * Separa a string, mantendo somente o código de base64, removendo quaisquer
             * espaços
             */
            var authEncoded = authorization.split(" ")[1].trim();

            /**
             * Decodifica o código de base64
             */
            var authDecode = Base64.getDecoder().decode(authEncoded);

            /**
             * Transforma o resultado em uma string
             */
            var authString = new String(authDecode);

            /**
             * Tenta recuperar as credenciais, caso sejam existentes
             */
            String[] credentials = null;
            var username = "";
            var password = "";
            try {
                credentials = authString.split(":");
                username = credentials[0];
                password = credentials[1];
            } catch (Exception e) {
                response.sendError(401, "Credenciais inexistentes.");
                return;
            }
                   
            /**
             * Valida usuário e senha, comparando com as credenciais obtidas
             */
            var user = this.userRepository.findByUsername(username);
            if (user == null) {
                response.sendError(401, "Usuário sem autorização");
            } else {
                // Utilização do BCrypt para verificar se as senhas batem
                var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
                
                if (passwordVerify.verified) {
                    // Seta um atributo para essa requisição, passando o id do usuário 
                    request.setAttribute("idUser", user.getId());
                    filterChain.doFilter(request, response);
                    
                } else {
                    response.sendError(401);
                }
            }
        } else {
            filterChain.doFilter(request, response);
        }

    }

}
