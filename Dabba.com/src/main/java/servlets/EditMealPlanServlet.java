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

@WebServlet("/EditMealPlanServlet")
public class EditMealPlanServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        int id = Integer.parseInt(request.getParameter("id"));
        
        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Edit Meal Plan</title>");
        out.println("<link rel='shortcut icon' type='image/icon' href='p1.png'/>");
        out.println("<style>");
        out.println("body { margin: 0; font-family: Arial, sans-serif; background-color: #f1f1f1; }");
        out.println(".container { display: flex; justify-content: center; align-items: center; height: 100vh; padding: 20px; }");
        out.println(".form-frame { background-color: #fff; width: 700px; padding: 50px; border-radius: 12px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); }"); // Increased padding
        out.println("h2 { text-align: center; color: #e1585a; margin-bottom: 20px; }");
        out.println(".section-box { padding: 20px; margin-bottom: 20px; background-color: #f9f9f9; border-radius: 8px; border: 1px solid #ddd; }");
        out.println(".section-box legend { font-size: 18px; font-weight: bold; color: #e1585a; }");
        out.println(".input-group { margin-bottom: 15px; padding: 0 30px; }"); // Added padding
        out.println(".input-group label { font-weight: bold; color: #555; margin-bottom: 5px; display: block; }");
        out.println(".input-group input { width: 100%; padding: 10px; font-size: 14px; border: 1px solid #ddd; border-radius: 5px; }");
        out.println(".input-group input:focus { border-color: #e1585a; outline: none; }");
        out.println(".btn { padding: 15px 0; border: none; border-radius: 5px; font-weight: bold; cursor: pointer; width: 100%; }");
        out.println(".btn-primary { background-color: #e1585a; color: white; }");
        out.println(".btn-primary:hover { background-color: #d04c4d; }");
        out.println(".cancel-btn { font-weight: bold; background-color: gray; color: white; padding: 15px 0; border: none; border-radius: 5px; cursor: pointer; width: 100%; margin-top: 15px; }");
        out.println(".cancel-btn:hover { background-color: #757575; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='container'>");
        out.println("<div class='form-frame'>");
        
        out.println("<h2>Edit Meal Plan</h2>");
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dabba.com", "root", "Pankaj@123");
            
            String query = "SELECT * FROM mealplan WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                out.println("<form action='UpdateMealPlanServlet' method='post'>");
                out.println("<input type='hidden' name='id' value='" + id + "'/>");

                out.println("<div class='section-box'>");
                out.println("<legend>Meal Plan Information</legend>");
                
                out.println("<div class='input-group'>");
                out.println("<label for='planname'>Plan Name:</label>");
                out.println("<input type='text' name='planname' id='planname' value='" + rs.getString("planname") + "' required>");
                out.println("</div>");

                out.println("<div class='input-group'>");
                out.println("<label for='description'>Description:</label>");
                out.println("<input type='text' name='description' id='description' value='" + rs.getString("description") + "' required>");
                out.println("</div>");

                out.println("<div class='input-group'>");
                out.println("<label for='days'>Days:</label>");
                out.println("<input type='number' name='days' id='days' value='" + rs.getInt("days") + "' required>");
                out.println("</div>");

                out.println("<div class='input-group'>");
                out.println("<label for='price'>Price:</label>");
                out.println("<input type='number' name='price' id='price' value='" + rs.getInt("price") + "' required>");
                out.println("</div>");
                out.println("</div>");

                out.println("<div class='input-group'>");
                out.println("<button type='submit' class='btn btn-primary' onclick='window.history.back();'>Save Changes</button>");
                out.println("<button type='button' class='cancel-btn' onclick='window.history.back();'>Cancel</button>");
                out.println("</div>");
                out.println("</form>");
            }

            rs.close();
            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        out.println("</div>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
}
