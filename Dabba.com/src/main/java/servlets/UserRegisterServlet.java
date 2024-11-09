package servlets;

import jakarta.servlet.RequestDispatcher;
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

@WebServlet("/UserRegisterServlet")
public class UserRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
        PrintWriter out = response.getWriter();
		try {
            // Load JDBC driver and connect to the database
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dabba.com", "root", "Pankaj@123");

            // Get form data
            String name = request.getParameter("fullname");
            String mobile = request.getParameter("mobileno");
            String email = request.getParameter("emailaddress");
            String password = request.getParameter("pass");
            String confirmPassword = request.getParameter("cpass");
            String city = request.getParameter("city");
            String district = request.getParameter("district");
            String state = request.getParameter("state");
            String pincode = request.getParameter("pincode");

            // Check if password and confirm password match
            if (!password.equals(confirmPassword)) {
                out.println("<font color='red'>Passwords do not match. Please try again.</font>");
                RequestDispatcher rd = request.getRequestDispatcher("User/URegister.html");
                rd.include(request, response);
                return;
            }

            // Insert data into database
            String query = "INSERT INTO users (name, mobile, email, password, city, district, state, pincode) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, mobile);
            ps.setString(3, email);
            ps.setString(4, password);
            ps.setString(5, city);
            ps.setString(6, district);
            ps.setString(7, state);
            ps.setString(8, pincode);

            int rowCount = ps.executeUpdate();

            if (rowCount > 0) {
                // Registration successful - show success alert and redirect to login page
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Registration successful!');");
                out.println("location='User/ULogin.html';");
                out.println("</script>");
            } else {
                // Registration failed - show failure alert and stay on registration page
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Registration failed. Please try again.');");
                out.println("location='User/URegister.html';");
                out.println("</script>");
            }

            // Close resources
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
