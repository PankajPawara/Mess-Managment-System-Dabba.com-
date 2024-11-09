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

@WebServlet("/UserListServlet")
public class UserListServlet extends HttpServlet {
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
        out.println("<title>User List</title>");
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
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h2 class='h2' align='center'>All Users</h2>");
        out.println("<div class='main-frame'>");

        // Start of table
        out.println("<table>");
        out.println("<thead>");
        out.println("<tr>");
        out.println("<th scope='col'>ID</th>");
        out.println("<th scope='col'>Name</th>");
        out.println("<th scope='col'>Mobile No</th>");
        out.println("<th scope='col'>Email</th>");
        out.println("<th scope='col'>Password</th>");
        out.println("<th scope='col'>City</th>");
        out.println("<th scope='col'>District</th>");
        out.println("<th scope='col'>State</th>");
        out.println("<th scope='col'>Pincode</th>");
        out.println("<th scope='col'>Action</th>");
        out.println("</tr>");
        out.println("</thead>");
        out.println("<tbody>");

        // Connect to the database and fetch user data
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dabba.com", "root", "Pankaj@123");

            String query = "SELECT * FROM users";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            // Dynamically add rows to the table
            while (rs.next()) {
                int id = rs.getInt("id");
                out.println("<tr>");
                out.println("<td scope='row'>" + id + "</td>");
                out.println("<td>" + rs.getString("name") + "</td>");
                out.println("<td>" + rs.getString("mobile") + "</td>");
                out.println("<td>" + rs.getString("email") + "</td>");
                out.println("<td>" + rs.getString("password") + "</td>");
                out.println("<td>" + rs.getString("city") + "</td>");
                out.println("<td>" + rs.getString("district") + "</td>");
                out.println("<td>" + rs.getString("state") + "</td>");
                out.println("<td>" + rs.getString("pincode") + "</td>");
                out.println("<td>");
                out.println("<a href='/Dabba.com/DeleteUserServlet?id=" + id + "' class='btn-delete' onclick='return confirm(\"Are you sure you want to delete this user?\");'>Delete</a>");
                out.println("</td>");
                out.println("</tr>");
            }

            // Close resources
            rs.close();
            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<p>Error fetching user data.</p>");
        }

        // End of table and other HTML elements
        out.println("</tbody>");
        out.println("</table>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
}
