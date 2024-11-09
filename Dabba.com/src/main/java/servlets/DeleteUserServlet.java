package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@WebServlet("/DeleteUserServlet")
public class DeleteUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("id"));

        try {
            // Connect to the database
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dabba.com", "root", "Pankaj@123");

            // Delete the user with the specified ID
            String query = "DELETE FROM users WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, userId);

            int result = ps.executeUpdate();

            ps.close();
            con.close();

            // Redirect back to the User List page with a success or failure message
            if (result > 0) {
                response.sendRedirect("/Dabba.com/Admin/Dashboard.html?message=User deleted successfully");
            } else {
                response.sendRedirect("/Dabba.com/Admin/Dashboard.html?message=Failed to delete user");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("UserListServlet?message=An error occurred while deleting the user");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response); // Handle both GET and POST requests
    }
}
