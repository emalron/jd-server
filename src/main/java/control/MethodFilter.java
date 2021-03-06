package control;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MethodFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HashMap<String, String> whitelist = Ignite.getWhitelist();

        String test = req.getHeader("origin");

        if(whitelist.containsKey(test)) {
            ((HttpServletResponse) response).addHeader("Access-Control-Allow-Origin", test);
        }
        if(test != null && test.contains("localhost")) {
            ((HttpServletResponse) response).addHeader("Access-Control-Allow-Origin", "*");
        }

        // set CORS off globally
        ((HttpServletResponse) response).setContentType("application/json; charset=utf-8");
        ((HttpServletResponse) response).addHeader("Access-Control-Allow-Methods", "*");
        ((HttpServletResponse) response).addHeader("Access-Control-Max-Age", "3600");
        ((HttpServletResponse) response).addHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, Content-Length, X-Requested-With");

        // AXIOS POST handshake
        if(req.getMethod().equals("OPTIONS")) {
            resp.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        chain.doFilter(req, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}
}