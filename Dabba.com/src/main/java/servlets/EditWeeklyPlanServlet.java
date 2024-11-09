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

@WebServlet("/EditWeeklyPlanServlet")
public class EditWeeklyPlanServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        int id = Integer.parseInt(request.getParameter("id"));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dabba.com", "root", "Pankaj@123");
            String query = "SELECT * FROM weeklyplan WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                out.println("<!DOCTYPE html>");
                out.println("<html lang='en'>");
                out.println("<head><meta charset='UTF-8'><meta name='viewport' content='width=device-width, initial-scale=1.0'>");
                out.println("<title>Edit Weekly Plan</title>");
                out.println("<style>");
                out.println("body { margin: 0; font-family: Arial, sans-serif; background-color: #f1f1f1; }");
                out.println(".container { display: flex; justify-content: center; align-items: center; height: 100vh; padding: 20px; }");
                out.println(".form-frame { background-color: #ffffff; width: 700px; padding: 50px; border-radius: 12px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); }");
                out.println("h2 { text-align: center; color: #e1585a; margin-bottom: 20px; }");
                out.println(".form-group { margin-bottom: 15px; }");
                out.println(".form-group label { font-weight: bold; color: #555; display: block; margin-bottom: 5px; }");
                out.println(".form-group input { width: 100%; padding: 10px; font-size: 14px; border: 1px solid #ddd; border-radius: 5px; }");
                out.println(".form-group input:focus { border-color: #e1585a; outline: none; }");
                out.println(".button-group { display: flex; justify-content: space-between; margin-top: 20px; }");
                out.println(".btn-primary { background-color: #e1585a; color: white; font-weight: bold; padding: 15px 0; border: none; border-radius: 5px; width: 48%; cursor: pointer; }");
                out.println(".btn-primary:hover { background-color: #d04c4d; }");
                out.println(".cancel-btn { background-color: gray; color: white; font-weight: bold; padding: 15px 0; border: none; border-radius: 5px; width: 48%; cursor: pointer; }");
                out.println(".cancel-btn:hover { background-color: #757575; }");
                out.println("</style></head>");
                out.println("<body>");
                
                out.println("<div class='container'>");
                out.println("<div class='form-frame'>");
                out.println("<h2>Edit Weekly Plan</h2>");
                
                out.println("<form action='/Dabba.com/EditWeeklyPlanServlet' method='post'>");
                out.println("<input type='hidden' name='id' value='" + id + "'/>");
                
                out.println("<div class='form-group'><label>Day:</label>");
                out.println("<input type='text' name='day' value='" + rs.getString("day") + "' required></div>");

                out.println("<div class='form-group'><label>Breakfast:</label>");
                out.println("<input type='text' name='breakfast' value='" + rs.getString("breakfast") + "' required></div>");

                out.println("<div class='form-group'><label>Lunch:</label>");
                out.println("<input type='text' name='lunch' value='" + rs.getString("lunch") + "' required></div>");

                out.println("<div class='form-group'><label>Dinner:</label>");
                out.println("<input type='text' name='dinner' value='" + rs.getString("dinner") + "' required></div>");
                
                out.println("<div class='button-group'>");
                out.println("<button type='submit' class='btn-primary' >Update</button>");
                out.println("<button type='button' class='cancel-btn' onclick='window.history.back();'>Cancel</button>");
                out.println("</div></form>");
                
                out.println("</div>");
                out.println("</div>");
                out.println("</body>");
                out.println("</html>");
            }
            rs.close();
            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<p>Error retrieving weekly plan data.</p>");
        }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String day = request.getParameter("day");
        String breakfast = request.getParameter("breakfast");
        String lunch = request.getParameter("lunch");
        String dinner = request.getParameter("dinner");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dabba.com", "root", "Pankaj@123");
            String updateQuery = "UPDATE weeklyplan SET day = ?, breakfast = ?, lunch = ?, dinner = ? WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(updateQuery);
            ps.setString(1, day);
            ps.setString(2, breakfast);
            ps.setString(3, lunch);
            ps.setString(4, dinner);
            ps.setInt(5, id);

            int result = ps.executeUpdate();
            ps.close();
            con.close();

            if (result > 0) {
                response.sendRedirect("Admin/Dashboard.html");
            } else {
                PrintWriter out = response.getWriter();
                out.println("<p>Error updating weekly plan.</p>");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
