package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/DashboardServlet")
public class DashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        int userCount = 0;
        int mealPlanCount = 0;
        int purchasedPlanCount = 0;
        int weeklyPlanCount = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dabba.com", "root", "Pankaj@123");

            // Fetch user count
            PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM users");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                userCount = rs.getInt(1);
            }
            rs.close();
            ps.close();

            // Fetch meal plan count
            ps = con.prepareStatement("SELECT COUNT(*) FROM mealplan");
            rs = ps.executeQuery();
            if (rs.next()) {
                mealPlanCount = rs.getInt(1);
            }
            rs.close();
            ps.close();

            // Fetch purchased plans count
            ps = con.prepareStatement("SELECT COUNT(*) FROM mypurchasedplan");
            rs = ps.executeQuery();
            if (rs.next()) {
                purchasedPlanCount = rs.getInt(1);
            }
            rs.close();
            ps.close();

            // Fetch weekly plans count
            ps = con.prepareStatement("SELECT COUNT(*) FROM weeklyplan");
            rs = ps.executeQuery();
            if (rs.next()) {
                weeklyPlanCount = rs.getInt(1);
            }
            rs.close();
            ps.close();

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<p>Error retrieving data from the database.</p>");
        }

        // HTML structure and styling
        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Dashboard</title>");
        out.println("<link rel='shortcut icon' type='image/icon' href='images/p1.png'/>");
        out.println("<style>");
        out.println("h2 { margin-bottom: 20px; margin-right: 10px; }");
        out.println(".main-frame { margin-left: 10%; width: 80%; background-color: #ed999a; border: 2px solid rgb(57, 57, 57); border-radius: 6px; }");
        out.println(".blue-frame { padding: 5px; font-weight: bold; font-size: larger; background-color: rgb(57, 57, 57); color: white; max-height: fit-content; border-top: 8px; border-top-left-radius: 4px; border-top-right-radius: 4px; }");
        out.println(".frames { display: flex; flex-wrap: wrap; padding-left: 50px; padding-bottom: 30px; }");
        out.println(".child-frame { margin: 20px; padding: 20px; align-content: center; text-align: center; height: 100px; max-width: fit-content; background-color: azure; border: 1px solid black; border-radius: 8px; box-shadow: grey 2; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        
        // Dashboard content
        out.println("<h2 align='center'>Dashboard</h2>");
        out.println("<div class='main-frame'>");
        out.println("<legend class='blue-frame'>Analytics</legend>");
        out.println("<div class='frames' align='center'>");

        // Display each count in its section
        out.println("<div class='child-frame'>");
        out.println("<h3>Total Users</h3>");
        out.println("<hr>");
        out.println("<h1>" + userCount + "</h1>");
        out.println("</div>");

        out.println("<div class='child-frame'>");
        out.println("<h3>All Plans</h3>");
        out.println("<hr>");
        out.println("<h1>" + mealPlanCount + "</h1>");
        out.println("</div>");

        out.println("<div class='child-frame'>");
        out.println("<h3>Sold Plans</h3>");
        out.println("<hr>");
        out.println("<h1>" + purchasedPlanCount + "</h1>");
        out.println("</div>");

        out.println("<div class='child-frame'>");
        out.println("<h3>Weekly Plans</h3>");
        out.println("<hr>");
        out.println("<h1>" + weeklyPlanCount + "</h1>");
        out.println("</div>");

        out.println("</div>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
}
