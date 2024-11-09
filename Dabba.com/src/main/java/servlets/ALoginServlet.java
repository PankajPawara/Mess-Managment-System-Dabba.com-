package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/ALoginServlet")
public class ALoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            // Load the MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to the database
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dabba.com", "root", "Pankaj@123");

            // Retrieve parameters from the request
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            // SQL query to check user credentials
            PreparedStatement ps = con.prepareStatement("SELECT name FROM admin WHERE mobile = ? AND password = ?");
            ps.setString(1, username);
            ps.setString(2, password);

            // Execute the query
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Login successful, redirect to dashboard with success message
                response.sendRedirect("Admin/Dashboard.html");
            } else {
                // Login failed, show error message and stay on login page
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Login failed. Please try again.');");
                out.println("window.location.href = 'Admin/ALogin.html';");
                out.println("</script>");
            }

            // Close resources
            rs.close();
            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<script type=\"text/javascript\">");
            out.println("alert('An error occurred. Please try again later.');");
            out.println("window.location.href = 'Admin/ALogin.html';");
            out.println("</script>");
        }
    }
}
