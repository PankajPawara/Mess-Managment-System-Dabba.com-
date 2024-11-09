package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/ViewMealPlanServlet")
public class ViewMealPlanServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        // Check if user is logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login.html");  // Redirect to login if not logged in
            return;
        }

        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Available Meal Plans</title>");
        
        // CSS styling
        out.println("<style>");
        out.println(".h2 { padding-bottom: 30px; font-size: 25px; }");
        out.println(".main-frame { background-color: #ed999a; padding: 30px; border: 2px solid gray; border-radius: 8px; border-collapse: collapse; }");
        out.println("table { margin-top: 30px; background-color: azure; width: 100%; border: 2px solid black; border-radius: 8px; }");
        out.println("thead { background-color: rgb(108, 108, 108); color: white; border-top-right-radius: 8px; }");
        out.println("th, td { border: 2px solid black; padding: 10px; font-weight: bold; }");
        out.println(".btn-buy { padding: 5px 10px; background-color: #28a745; color: white; font-weight: bold; border-radius: 5px; text-decoration: none; }");
        out.println(".btn-buy:hover { opacity: 0.8; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h2 class='h2' align='center'>Available Meal Plans</h2>");
        out.println("<div class='main-frame'>");

        // Start of table
        out.println("<table>");
        out.println("<thead>");
        out.println("<tr>");
        out.println("<th scope='col'>Plan Name</th>");
        out.println("<th scope='col'>Description</th>");
        out.println("<th scope='col'>Days</th>");
        out.println("<th scope='col'>Price</th>");
        out.println("<th scope='col'>Action</th>");
        out.println("</tr>");
        out.println("</thead>");
        out.println("<tbody>");

        try {
            // Database connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dabba.com", "root", "Pankaj@123");

            // Query to fetch all meal plans
            String query = "SELECT * FROM mealplan";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            // Dynamically add rows to the table
            while (rs.next()) {
                int id = rs.getInt("id");
                String planName = rs.getString("planname");
                String description = rs.getString("description");
                int days = rs.getInt("days");
                int price = rs.getInt("price");

                out.println("<tr>");
                out.println("<td>" + planName + "</td>");
                out.println("<td>" + description + "</td>");
                out.println("<td>" + days + "</td>");
                out.println("<td>" + price + "</td>");
                out.println("<td><a href='/Dabba.com/BuyMealPlanServlet?id=" + id + "' class='btn-buy'>Buy Plan</a></td>");
                out.println("</tr>");
            }

            rs.close();
            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<p>Error fetching meal plans.</p>");
        }

        // End of table and other HTML elements
        out.println("</tbody>");
        out.println("</table>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
}
