package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/User/ULoginServlet")
public class ULoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        try {
            // Load the MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to the database
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dabba.com", "root", "Pankaj@123");

            // Retrieve parameters from the request
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            // Determine if input is email or mobile based on regex
            boolean isEmail = username.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

            PreparedStatement ps;
            if (isEmail) {
                // Query if username is an email
                String emailQuery = "SELECT name, mobile FROM users WHERE email = ? AND password = ?";
                ps = con.prepareStatement(emailQuery);
                ps.setString(1, username);
                ps.setString(2, password);
            } else {
                // Query if username is a mobile number
                String mobileQuery = "SELECT name, mobile FROM users WHERE mobile = ? AND password = ?";
                ps = con.prepareStatement(mobileQuery);
                ps.setString(1, username);
                ps.setString(2, password);
            }

            // Execute the query
            ResultSet rs = ps.executeQuery();

            // Check if user exists
            if (rs.next()) {
             
                // In your login servlet, add these lines on successful login
                HttpSession session = request.getSession();
                session.setAttribute("username", username); // Store username (either email or mobile)
                session.setAttribute("name", rs.getString("name"));
                session.setAttribute("mobile", rs.getString("mobile"));
                // Redirect to the dashboard
                RequestDispatcher rd = request.getRequestDispatcher("Dashboard.html");
                rd.forward(request, response);
                
            } else {
                // Login failed, show error message
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Login failed. Please try again.');");
                out.println("location='ULogin.html';");
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
            out.println("location='User/URegister.html';");
            out.println("</script>");
        }
    }
}
