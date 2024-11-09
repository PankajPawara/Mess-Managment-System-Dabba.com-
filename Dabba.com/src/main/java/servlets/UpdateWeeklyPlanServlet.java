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

@WebServlet("/UpdateWeeklyPlanServlet")
public class UpdateWeeklyPlanServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
    	int id = Integer.parseInt(request.getParameter("id"));
        String day = request.getParameter("day");
        String breakfast = request.getParameter("breakfast");
        String lunch = request.getParameter("lunch");
        String dinner = request.getParameter("dinner");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dabba.com", "root", "Pankaj@123");

            String query = "UPDATE weeklyplan SET day=?, breakfast=?, lunch=?, dinner=? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, day);
            ps.setString(2, breakfast);
            ps.setString(3, lunch);
            ps.setString(4, dinner);
            ps.setInt(5, id);
            ps.executeUpdate();

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                out.println("<script>alert('Update successful!'); window.location.href = 'Admin/Dashboard.html';;</script>");
                
                out.println("<script>");
                out.println("document.getElementById('btn').addEventListener('click', function() {");
                out.println("window.location.href = 'Admin/Dashboard.html'; });");
                out.println("</script>");
                
            } else {
                out.println("<p>Update failed. User not found.</p>");
            }

            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
