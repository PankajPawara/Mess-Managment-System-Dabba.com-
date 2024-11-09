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

@WebServlet("/AddMealPlanServlet")
public class AddMealPlanServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title>Add Meal Plan</title>");
        out.println("<style>");
        out.println("body { background-color: #f1f1f1; font-family: Arial, sans-serif; }");
        out.println(".container { display: flex; justify-content: center; align-items: center; height: 100vh; }");
        out.println(".form-frame { background-color: #ffffff; width: 500px; padding: 30px; border-radius: 8px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); }");
        out.println("h2 { text-align: center; color: #e1585a; }");
        out.println(".input-group { margin-bottom: 15px; }");
        out.println(".input-group label { display: block; margin-bottom: 5px; color: #555; font-weight: bold; }");
        out.println(".input-group input { width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 5px; }");
        out.println(".btn-submit { width: 100%; padding: 10px; background-color: #e1585a; color: white; border: none; border-radius: 5px; font-weight: bold; cursor: pointer; }");
        out.println(".btn-submit:hover { background-color: #d04c4d; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='container'>");
        out.println("<div class='form-frame'>");
        out.println("<h2>Add New Meal Plan</h2>");
        out.println("<form action='AddMealPlanServlet' method='post'>");
        out.println("<div class='input-group'><label for='planname'>Plan Name:</label><input type='text' name='planname' required></div>");
        out.println("<div class='input-group'><label for='description'>Description:</label><input type='text' name='description' required></div>");
        out.println("<div class='input-group'><label for='days'>Days:</label><input type='number' name='days' required></div>");
        out.println("<div class='input-group'><label for='price'>Price:</label><input type='number' name='price' required></div>");
        out.println("<button type='submit' class='btn-submit' onclick='window.history.back();'>Add Meal Plan</button>");
        out.println("</form>");
        out.println("</div>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String planname = request.getParameter("planname");
        String description = request.getParameter("description");
        int days = Integer.parseInt(request.getParameter("days"));
        int price = Integer.parseInt(request.getParameter("price"));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dabba.com", "root", "Pankaj@123");
            String query = "INSERT INTO mealplan (planname, description, days, price) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, planname);
            ps.setString(2, description);
            ps.setInt(3, days);
            ps.setInt(4, price);
            ps.executeUpdate();

            ps.close();
            con.close();

            response.sendRedirect("Admin/Dashboard.html");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
