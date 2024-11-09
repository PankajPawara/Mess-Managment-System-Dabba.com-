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

@WebServlet("/EditUserServlet")
public class EditUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        int id = Integer.parseInt(request.getParameter("id")); // ID from the URL

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dabba.com", "root", "Pankaj@123");

            String query = "SELECT * FROM users WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Start HTML output with style applied
                out.println("<!DOCTYPE html>");
                out.println("<html lang='en'>");
                out.println("<head>");
                out.println("<meta charset='UTF-8'>");
                out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
                out.println("<title>Edit User</title>");
                out.println("<link rel='shortcut icon' type='image/icon' href='p1.png'/>");
                out.println("<style>");
                out.println("*, body { margin: 0; padding: 0; box-sizing: border-box; }");
                out.println("body { margin-top: 150px; margin-bottom: 150px; display: flex; align-items: center; justify-content: center; height: 100vh; background-color: #e1585a; font-family: Arial, sans-serif; }");
                out.println(".reg-frame { background-color: #ffffff; width: 700px; padding: 20px; border-radius: 12px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2); text-align: center; }");
                out.println("legend { font-size: 18px; font-weight: bold; color: #e1585a; padding: 0 5px; text-align: left; }");
                out.println("h2 { font-weight: bold; margin-bottom: 20px; color: #e1585a; }");
                out.println(".input-group { display: grid; grid-template-columns: 1fr 1fr; gap: 10px; margin-top: 15px; }");
                out.println(".input-group label, .input-group input { width: 100%; padding: 8px; border: 1px solid #ccc; border-radius: 5px; }");
                out.println(".input-group label { text-align: left; font-weight: bold; background-color: #f9f9f9; border: none; padding: 0; }");
                out.println(".section-box { background-color: #f9f9f9; padding: 15px; border: 1px solid #ddd; border-radius: 8px; margin-bottom: 20px; }");
                out.println(".reg-btn { font-weight: bold; background-color: #e1585a; color: white; padding: 15px 0; border: none; border-radius: 5px; margin-top: 15px; cursor: pointer; width: 100%; }");
                out.println(".reg-btn:hover { background-color: #d04c4d; }");
                out.println(".cancel-btn { font-weight: bold; background-color: gray; color: white; padding: 15px 0; border: none; border-radius: 5px; cursor: pointer; width: 100%; margin-top: 15px; }");
                out.println(".cancel-btn:hover { background-color: #757575; }");
                out.println("</style>");
                out.println("</head>");
                out.println("<body>");
                out.println("<div class='reg-frame'>");
                out.println("<h2>Edit User Information</h2>");
                out.println("<form action='UpdateUserServlet' method='post'>");
                
                // Include the hidden ID field
                out.println("<input type='hidden' name='id' value='" + id + "'/>");

                // Personal Information
                out.println("<fieldset class='section-box'>");
                out.println("<legend>Personal Information</legend>");
                out.println("<div class='input-group'>");
                out.println("<label for='name'>*Name:</label>");
                out.println("<input type='text' name='name' id='name' value='" + rs.getString("name") + "' required>");
                out.println("</div>");
                out.println("</fieldset>");

                // User Information
                out.println("<fieldset class='section-box'>");
                out.println("<legend>Contact Details</legend>");
                out.println("<div class='input-group'>");
                out.println("<label for='mobile'>*Mobile No:</label>");
                out.println("<label for='email'>Email Address:</label>");
                out.println("<input type='text' name='mobile' id='mobile' value='" + rs.getString("mobile") + "' required>");
                out.println("<input type='email' name='email' id='email' value='" + rs.getString("email") + "' required>");
                out.println("</div>");
                out.println("</fieldset>");

                // Address Details
                out.println("<fieldset class='section-box'>");
                out.println("<legend>Address Details</legend>");
                out.println("<div class='input-group'>");
                out.println("<label for='city'>*City/Village:</label>");
                out.println("<label for='district'>*District:</label>");
                out.println("<input type='text' name='city' id='city' value='" + rs.getString("city") + "' required>");
                out.println("<input type='text' name='district' id='district' value='" + rs.getString("district") + "' required>");
                out.println("</div>");
                out.println("<div class='input-group'>");
                out.println("<label for='state'>*State:</label>");
                out.println("<label for='pincode'>*Pincode:</label>");
                out.println("<input type='text' name='state' id='state' value='" + rs.getString("state") + "' required>");
                out.println("<input type='text' name='pincode' id='pincode' value='" + rs.getString("pincode") + "' required>");
                out.println("</div>");
                out.println("</fieldset>");

                // Submit and Cancel buttons
                out.println("<button type='submit' class='reg-btn' onclick='window.history.back();'>Save Changes</button>");
                out.println("<button type='button' class='cancel-btn' onclick='window.history.back();'>Cancel</button>");
                
                out.println("</form>");
                out.println("</div>");
                out.println("</body></html>");
            }

            rs.close();
            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<p>Error retrieving user data.</p>");
        }
    }
}
