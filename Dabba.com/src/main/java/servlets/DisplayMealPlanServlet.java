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
@WebServlet("/DisplayMealPlanServlet")
public class DisplayMealPlanServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Include CSS styling for the table and form
        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Meal Plans</title>");
        out.println("<style>");
        out.println(".h2 { padding-bottom: 30px; font-size: 25px; }");
        out.println(".main-frame { background-color: #ed999a; padding: 30px; border: 2px solid gray; border-radius: 8px; border-collapse: collapse; }");
        out.println("table { margin-top: 30px; background-color: azure; width: 100%; border: 2px solid black; border-radius: 8px; }");
        out.println("thead { background-color: rgb(108, 108, 108); color: white; border-top-right-radius: 8px; }");
        out.println("th, td { border: 2px solid black; padding: 10px; font-weight: bold; }");
        out.println(".btn-edit, .btn-delete { padding: 5px 10px; color: white; font-weight: bold; border-radius: 5px; text-decoration: none; }");
        out.println(".btn-edit { background-color: #007bff; }");
        out.println(".btn-delete { background-color: #dc3545; }");
        out.println(".btn-edit:hover, .btn-delete:hover { opacity: 0.8; }");
        out.println(".btn-add { background-color: #28a745; color: white; padding: 10px 20px; font-weight: bold; border-radius: 5px; text-decoration: none; margin-bottom: 20px; display: inline-block; }");
        out.println(".btn-add:hover { opacity: 0.8; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h2 class='h2' align='center'>Meal Plan Details</h2>");
        
        // Add New Meal Plan Button
        out.println("<div align='center'>");
        out.println("<a href='/Dabba.com/AddMealPlanServlet' class='btn-add'>Add New Meal Plan</a>");
        out.println("</div>");
        
        out.println("<div class='main-frame'>");

        // Start of table
        out.println("<table>");
        out.println("<thead>");
        out.println("<tr>");
        out.println("<th scope='col'>Plan ID</th>");
        out.println("<th scope='col'>Plan Name</th>");
        out.println("<th scope='col'>Description</th>");
        out.println("<th scope='col'>Days</th>");
        out.println("<th scope='col'>Price</th>");
        out.println("<th scope='col'>Action</th>");
        out.println("</tr>");
        out.println("</thead>");
        out.println("<tbody>");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dabba.com", "root", "Pankaj@123");

            String query = "SELECT * FROM mealplan";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            // Dynamically add rows to the table
            while (rs.next()) {
                int id = rs.getInt("id");
                out.println("<tr>");
                out.println("<td scope='row'>" + id + "</td>");
                out.println("<td>" + rs.getString("planname") + "</td>");
                out.println("<td>" + rs.getString("description") + "</td>");
                out.println("<td>" + rs.getInt("days") + "</td>");
                out.println("<td>" + rs.getInt("price") + "</td>");
                out.println("<td>");
                out.println("<a href='/Dabba.com/EditMealPlanServlet?id=" + id + "' class='btn-edit'>Edit</a> ");
                out.println("<a href='/Dabba.com/DeleteMealPlanServlet?id=" + id + "' class='btn-delete' onclick='return confirm(\"Are you sure you want to delete this meal plan?\");'>Delete</a>");
                out.println("</td>");
                out.println("</tr>");
            }

            // Close resources
            rs.close();
            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // End of table and other HTML elements
        out.println("</tbody>");
        out.println("</table>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
}
