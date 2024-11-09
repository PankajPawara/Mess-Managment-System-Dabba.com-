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

@WebServlet("/BuyMealPlanServlet")
public class BuyMealPlanServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int mealPlanId = Integer.parseInt(request.getParameter("id"));
        
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        // Fetching user session
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login.html");  // Redirect to login if not logged in
            return;
        }

        String username = (String) session.getAttribute("username");
        String name = (String) session.getAttribute("name");
        String mobile = (String) session.getAttribute("mobile");

        try {
            // Database connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dabba.com", "root", "Pankaj@123");

            // Get meal plan details
            String query = "SELECT * FROM mealplan WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, mealPlanId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String planName = rs.getString("planname");
                int price = rs.getInt("price");
                int days = rs.getInt("days");  // Now we fetch the 'days' column

                // Insert into mypurchasedplan table
                String insertQuery = "INSERT INTO mypurchasedplan (username, planname, price, days, mobile, purchase_date) VALUES (?, ?, ?, ?, ?, NOW())";
                PreparedStatement insertPs = con.prepareStatement(insertQuery);
                insertPs.setString(1, name);
                insertPs.setString(2, planName);
                insertPs.setInt(3, price);
                insertPs.setInt(4, days);  // Insert the 'days' value
                insertPs.setString(5, mobile);

                int result = insertPs.executeUpdate();

                if (result > 0) {
                	out.println("<script type=\"text/javascript\">");
                    out.println("alert('Plan Purchesed successful!');");
                    out.println("location='User/Dashboard.html';");
                    out.println("</script>");
                } else {
                    response.getWriter().println("Error purchasing meal plan.");
                }

                insertPs.close();
            }

            rs.close();
            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error processing your purchase.");
        }
    }
}
