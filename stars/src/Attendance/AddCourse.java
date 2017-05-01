package Attendance;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Attendance/AddCourse")
public class AddCourse extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher( "/WEB-INF/Courses.jsp" ).forward(request, response );
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ArrayList<CourseModel> courses = (ArrayList<CourseModel>) request.getSession().getAttribute("courses");

		String courseName = request.getParameter("courseName");
		
		int hour = 0;
		int min = 0;
		int instructorID = -1;
		
		try{
			hour = Integer.parseInt(request.getParameter("hour"));
			min = Integer.parseInt(request.getParameter("min"));
			instructorID = (int) request.getSession().getAttribute("instructorID");
		}catch(Exception e){
			System.out.print("Add Course Error");
		}
		
		Connection c = null;
		
		if(courseName!=null && courseName!=""){
			try{
				String url = "jdbc:mysql://cs3.calstatela.edu/cs3220stu98";
				String username = "cs3220stu98";
				String password = "!SagHy*C";
	            
	            String sql = "INSERT INTO class (course_name,instructor_id,deadline) VALUES(?,?,?)";
	            c = DriverManager.getConnection( url, username, password );
	            
	            Time deadline = new Time(hour,min,00);
	            
	            PreparedStatement pstmt = c.prepareStatement( sql );
	            pstmt.setString(1, courseName);
	            pstmt.setInt(2,instructorID);
	            pstmt.setTime(3, deadline);

	            pstmt.executeUpdate();
	            
	            courses.add(new CourseModel(courseName,deadline,hour,min));
	            request.getSession().setAttribute("courses", courses);
				
			 }catch( SQLException e ){
				 throw new ServletException( e );
		     }
		     finally{
	            try{
	                if( c != null ) c.close();
	            }
	            catch( SQLException e ){
	                throw new ServletException( e );
	            }
		     }
			response.sendRedirect("Courses");
		}
	}

}
