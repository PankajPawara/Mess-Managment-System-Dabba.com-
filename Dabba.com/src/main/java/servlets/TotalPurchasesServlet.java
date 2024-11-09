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

@WebServlet("/TotalPurchasesServlet")
public class TotalPurchasesServlet extends HttpServlet {
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
        out.println("<title>My Purchased Plans</title>");
        out.println("<style>");
        out.println(".h2 { padding-bottom: 30px; font-size: 25px; }");
        out.println(".main-frame { background-color: #ed999a; padding: 30px; border: 2px solid gray; border-radius: 8px; border-collapse: collapse; }");
        out.println("table { margin-top: 30px; background-color: azure; width: 100%; border: 2px solid black; border-radius: 8px; }");
        out.println("thead { background-color: rgb(108, 108, 108); color: white; border-top-right-radius: 8px; }");
        out.println("th, td { border: 2px solid black; padding: 10px; font-weight: bold; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h2 class='h2' align='center'>Users and Purchased Plans</h2>");
        out.println("<div class='main-frame'>");

        // Start of table
        out.println("<table>");
        out.println("<thead>");
        out.println("<tr>");
        out.println("<th scope='col'>ID</th>"); 
        out.println("<th scope='col'>User Name</th>");
        out.println("<th scope='col'>Plan Name</th>");
        out.println("<th scope='col'>Price</th>");
        out.println("<th scope='col'>Mobile</th>");
        out.println("<th scope='col'>Days</th>");
        out.println("<th scope='col'>Purchase Date</th>");
        out.println("</tr>");
        out.println("</thead>");
        out.println("<tbody>");

        try {
            // Database connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dabba.com", "root", "Pankaj@123");

            // Query to fetch purchased meal plans by user
            String query = "SELECT * FROM mypurchasedplan";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            // Dynamically add rows to the table
            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>" + rs.getInt("id") + "</td>");
                out.println("<td>" + rs.getString("username") + "</td>");
                out.println("<td>" + rs.getString("planname") + "</td>");
                out.println("<td>" + rs.getInt("price") + "</td>");
                out.println("<td>" + rs.getString("mobile") + "</td>");
                out.println("<td>" + rs.getInt("days") + "</td>");  // Display the 'days' value
                out.println("<td>" + rs.getString("purchase_date") + "</td>");
                out.println("</tr>");
            }

            // Close resources
            rs.close();
            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<p>Error fetching total purchase count.</p>");
        }

        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
}
