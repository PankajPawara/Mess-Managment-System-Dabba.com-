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

@WebServlet("/UpdateMealPlanServlet")
public class UpdateMealPlanServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String planname = request.getParameter("planname");
        String description = request.getParameter("description");
        int days = Integer.parseInt(request.getParameter("days"));
        int price = Integer.parseInt(request.getParameter("price"));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dabba.com", "root", "Pankaj@123");

            String query = "UPDATE mealplan SET planname=?, description=?, days=?, price=? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, planname);
            ps.setString(2, description);
            ps.setInt(3, days);
            ps.setInt(4, price);
            ps.setInt(5, id);

            int rowUpdated = ps.executeUpdate();
            if (rowUpdated > 0) {
                response.sendRedirect("DisplayMealPlanServlet?message=update_success");
            }

            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
