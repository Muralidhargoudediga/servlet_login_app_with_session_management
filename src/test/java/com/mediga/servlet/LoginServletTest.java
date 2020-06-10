package com.mediga.servlet;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LoginServletTest extends TestCase {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    ServletConfig servletConfig;

    @Mock
    ServletContext servletContext;

    @Mock
    RequestDispatcher requestDispatcher;

   @Override
    public void setUp(){
        System.out.println("Before...");
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testLoginServlet() throws IOException, ServletException {
        when(request.getParameter("user")).thenReturn("Murali");
        when(request.getParameter("pwd")).thenReturn("secure");

        when(servletConfig.getInitParameter("user")).thenReturn("Murali");
        when(servletConfig.getInitParameter("password")).thenReturn("secure1");
        when(servletConfig.getInitParameter("dbURL")).thenReturn("jdbc:mysql://localhost/mysql_db");
        when(servletConfig.getInitParameter("dbUser")).thenReturn("mysql_user");
        when(servletConfig.getInitParameter("dbUserPwd")).thenReturn("mysql_pwd");

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        when(response.getWriter()).thenReturn(pw);
        LoginServlet loginServlet = new LoginServlet();
        loginServlet.init(servletConfig);
        when(loginServlet.getServletContext()).thenReturn(servletContext);
        when(servletContext.getRequestDispatcher("/login.html")).thenReturn(requestDispatcher);
        loginServlet.doPost(request, response);

        verify(response).sendRedirect("LoginSuccess.jsp");
        //String result = sw.getBuffer().toString().trim();

        assertEquals(0, response.getStatus());
    }
}