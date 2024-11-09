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

@WebServlet("/ViewMyWeeklyPlanServlet")
public class ViewMyWeeklyPlanServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        // Include CSS styling for the table
        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Weekly Menu</title>");
        out.println("<style>");
        out.println(".h2 { padding-bottom: 30px; font-size: 25px; }");
        out.println(".main-frame { background-color: #ed999a; padding: 30px; border: 2px solid gray; border-radius: 8px; border-collapse: collapse; }");
        out.println("table { margin-top: 30px; background-color: azure; width: 100%; border: 2px solid black; border-radius: 8px; }");
        out.println("thead { background-color: rgb(108, 108, 108); color: white; border-top-right-radius: 8px; }");
        out.println("th, td { border: 2px solid black; padding: 10px; font-weight: bold; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h2 class='h2' align='center'>Weekly Meal Plan</h2>");
        out.println("<div class='main-frame'>");

        // Start of table
        out.println("<table>");
        out.println("<thead>");
        out.println("<tr>");
        out.println("<th>ID</th>");
        out.println("<th>Day</th>");
        out.println("<th>Breakfast</th>");
        out.println("<th>Lunch</th>");
        out.println("<th>Dinner</th>");
        out.println("</tr>");
        out.println("</thead>");
        out.println("<tbody>");

        // Connect to the database and fetch weekly plan data
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dabba.com", "root", "Pankaj@123");

            String query = "SELECT * FROM weeklyplan";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            // Dynamically add rows to the table
            while (rs.next()) {
                int id = rs.getInt("id");
                out.println("<tr>");
                out.println("<td>" + id + "</td>");
                out.println("<td>" + rs.getString("day") + "</td>");
                out.println("<td>" + rs.getString("breakfast") + "</td>");
                out.println("<td>" + rs.getString("lunch") + "</td>");
                out.println("<td>" + rs.getString("dinner") + "</td>");
                out.println("</tr>");
            }

            // Close resources
            rs.close();
            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<p>Error fetching data from the database.</p>");
        }

        // End of table and other HTML elements
        out.println("</tbody>");
        out.println("</table>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
}
