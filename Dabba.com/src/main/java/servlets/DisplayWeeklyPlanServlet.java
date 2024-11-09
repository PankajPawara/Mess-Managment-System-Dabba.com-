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

@WebServlet("/DisplayWeeklyPlanServlet")
public class DisplayWeeklyPlanServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title>Weekly Meal Plan</title>");
        out.println("<style>");
        out.println(".h2 { padding-bottom: 30px; font-size: 25px; }");
        out.println(".main-frame { background-color: #ed999a; padding: 30px; border: 2px solid gray; border-radius: 8px; }");
        out.println("table { margin-top: 30px; background-color: azure; width: 100%; border: 2px solid black; border-radius: 8px; }");
        out.println("thead { background-color: rgb(108, 108, 108); color: white; }");
        out.println("th, td { border: 2px solid black; padding: 10px; font-weight: bold; }");
        out.println(".btn-edit { padding: 5px 10px; color: white; font-weight: bold; background-color: #007bff; border-radius: 5px; text-decoration: none; }");
        out.println(".btn-edit:hover { opacity: 0.8; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h2 class='h2' align='center'>Weekly Meal Plan</h2>");
        out.println("<div class='main-frame'>");

        out.println("<table>");
        out.println("<thead>");
        out.println("<tr><th>ID</th><th>Day</th><th>Breakfast</th><th>Lunch</th><th>Dinner</th><th>Action</th></tr>");
        out.println("</thead>");
        out.println("<tbody>");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dabba.com", "root", "Pankaj@123");
            String query = "SELECT * FROM weeklyplan";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                out.println("<tr>");
                out.println("<td>" + id + "</td>");
                out.println("<td>" + rs.getString("day") + "</td>");
                out.println("<td>" + rs.getString("breakfast") + "</td>");
                out.println("<td>" + rs.getString("lunch") + "</td>");
                out.println("<td>" + rs.getString("dinner") + "</td>");
                out.println("<td><a href='/Dabba.com/EditWeeklyPlanServlet?id=" + id + "' class='btn-edit'>Edit</a></td>");
                out.println("</tr>");
            }
            rs.close();
            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<p>Error fetching data from the database.</p>");
        }
        out.println("</tbody>");
        out.println("</table>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
}
