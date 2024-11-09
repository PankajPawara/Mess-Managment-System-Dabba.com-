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

@WebServlet("/ResetPasswordServlet")
public class ResetPasswordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String username = request.getParameter("email");
        String newPassword = request.getParameter("newpassword");
        String confirmPassword = request.getParameter("confirmpassword");

        if (!newPassword.equals(confirmPassword)) {
            out.println("<script>alert('Passwords do not match. Please try again.');window.location='forgot-password.html';</script>");
            return;
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dabba.com", "root", "Pankaj@123");

            String query;
            PreparedStatement ps;

            // Determine if input is an email or mobile number
            if (username.matches("\\d+")) {  // If username is numeric, treat it as a mobile number
                query = "SELECT * FROM users WHERE mobile = ?";
                ps = con.prepareStatement(query);
                ps.setLong(1, Long.parseLong(username));
            } else {  // Otherwise, treat it as an email
                query = "SELECT * FROM users WHERE email = ?";
                ps = con.prepareStatement(query);
                ps.setString(1, username);
            }

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String updateQuery;
                PreparedStatement updatePs;

                if (username.matches("\\d+")) {
                    updateQuery = "UPDATE users SET password = ? WHERE mobile = ?";
                    updatePs = con.prepareStatement(updateQuery);
                    updatePs.setString(1, newPassword);
                    updatePs.setLong(2, Long.parseLong(username));
                } else {
                    updateQuery = "UPDATE users SET password = ? WHERE email = ?";
                    updatePs = con.prepareStatement(updateQuery);
                    updatePs.setString(1, newPassword);
                    updatePs.setString(2, username);
                }

                int result = updatePs.executeUpdate();
                if (result > 0) {
                    out.println("<script>alert('Password reset successfully.');window.location='User/ULogin.html';</script>");
                } else {
                    out.println("<script>alert('Failed to reset password. Please try again.');window.location='User/ForgetPass.html';</script>");
                }

                updatePs.close();
            } else {
                out.println("<script>alert('Username not found. Please try again.');window.location='User/ForgetPass.html';</script>");
            }

            rs.close();
            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<script>alert('An error occurred. Please try again later.');window.location='User/ForgetPass.html';</script>");
        }
    }
}

