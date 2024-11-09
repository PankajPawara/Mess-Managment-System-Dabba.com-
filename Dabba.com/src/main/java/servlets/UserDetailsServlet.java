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

@WebServlet("/UserDetailsServlet")
public class UserDetailsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        HttpSession session = request.getSession(false);
        String username = (String) session.getAttribute("username");  // Assuming username is stored in session after login

        // Include CSS styling for the table and form
        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>User Details</title>");
        out.println("<style>");
        out.println(".h2 { padding-bottom: 30px; font-size: 25px; }");
        out.println(".main-frame { background-color: #ed999a; padding: 30px; border: 2px solid gray; border-radius: 8px; border-collapse: collapse; }");
        out.println("table { margin-top: 30px; background-color: azure; width: 100%; border: 2px solid black; border-radius: 8px; }");
        out.println("thead { background-color: rgb(108, 108, 108); color: white; }");
        out.println("th, td { border: 2px solid black; padding: 10px; font-weight: bold; }");
        out.println(".btn-edit { padding: 5px 10px; color: white; font-weight: bold; background-color: #007bff; border-radius: 5px; text-decoration: none; }");
        out.println(".btn-edit:hover { opacity: 0.8; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h2 class='h2' align='center'>User Details</h2>");
        out.println("<div class='main-frame'>");

        // Start of table
        out.println("<table>");
        out.println("<thead>");
        out.println("<tr>");
        out.println("<th scope='col'>ID</th>");
        out.println("<th scope='col'>Name</th>");
        out.println("<th scope='col'>Mobile No</th>");
        out.println("<th scope='col'>Email</th>");
        out.println("<th scope='col'>Address</th>");
        out.println("<th scope='col'>Action</th>");
        out.println("</tr>");
        out.println("</thead>");
        out.println("<tbody>");

        try {
            // Database connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dabba.com", "root", "Pankaj@123");

            // Query to get user details
            String query = "SELECT id, name, mobile, email, CONCAT(city, ', ', district, ', ', state, ', ', pincode) AS address FROM users WHERE email = ? OR mobile = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, username);

            ResultSet rs = ps.executeQuery();

            // Display user data if found
            if (rs.next()) {
                out.println("<tr>");
                out.println("<td scope='row'>" + rs.getInt("id") + "</td>");
                out.println("<td>" + rs.getString("name") + "</td>");
                out.println("<td>" + rs.getString("mobile") + "</td>");
                out.println("<td>" + rs.getString("email") + "</td>");
                out.println("<td>" + rs.getString("address") + "</td>");
                out.println("<td><a href='/Dabba.com/EditUserServlet?id=" + rs.getInt("id") + "' class='btn-edit'>Edit</a></td>");
                out.println("</tr>");
            } else {
                out.println("<tr><td colspan='6' align='center'>User data not found.</td></tr>");
            }

            // Close resources
            rs.close();
            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<tr><td colspan='6' align='center'>Error retrieving user details.</td></tr>");
        }

        // End of table and other HTML elements
        out.println("</tbody>");
        out.println("</table>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
}
