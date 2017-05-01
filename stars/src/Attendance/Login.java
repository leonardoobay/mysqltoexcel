package Attendance;

import java.io.File; 
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@WebServlet("/Attendance/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void init( ServletConfig config ) throws ServletException{
        super.init( config );

        try
        {
            Class.forName( "com.mysql.jdbc.Driver" );
        }
        catch( ClassNotFoundException e )
        {
            throw new ServletException( e );
        }
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher( "/WEB-INF/Login.jsp" ).forward(request, response );
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		String user = request.getParameter("username")==null?"":request.getParameter("username");
		String pw = request.getParameter("password")==null?"":request.getParameter("password");
		
		Connection c = null;
		int instructorID = -1;
		boolean pass = false;
		
		ArrayList<CourseModel> courses = new ArrayList<>();
		
		try{
			
			String url = "jdbc:mysql://cs3.calstatela.edu/cs3220stu98";
			String username = "cs3220stu98";
			String password = "!SagHy*C";
            
			c = DriverManager.getConnection(url,username,password);
			Statement stmt = c.createStatement();
			//***************
			 Statement stmt2 = c.createStatement();
	            ResultSet rs2 = stmt2.executeQuery( "select * from student" );
	  	     
	  	     
	          XSSFWorkbook workbook = new XSSFWorkbook(); 
	  	      XSSFSheet spreadsheet = workbook.createSheet("student db");
	  	      XSSFRow row=spreadsheet.createRow(1);
	  	      XSSFCell cell;
	  	      cell=row.createCell(1);
	  	      cell.setCellValue("firstName");
	  	      cell=row.createCell(2);
	  	      cell.setCellValue("lastName");
	  	      cell=row.createCell(3);
	  	      cell.setCellValue("cin");
	  	      cell=row.createCell(4);
	  	      cell.setCellValue("user_id");
	  	      
	  	    
	  	      int i=2;
	  	      while(rs2.next())
	  	      {

	  	         row=spreadsheet.createRow(i);
	  	         cell=row.createCell(1);
	  	         cell.setCellValue(rs2.getString("firstName"));
	  	         cell=row.createCell(2);
	  	         cell.setCellValue(rs2.getString("lastName"));
	  	         cell=row.createCell(3);
	  	         cell.setCellValue(rs2.getInt("cin"));
	  	         cell=row.createCell(4);
	  	         cell.setCellValue(rs2.getInt("user_id"));
	  	         i++;
	  	      }
	  	      FileOutputStream fout = new FileOutputStream(
	  	      new File("exceldatabase.xlsx"));
	  	      workbook.write(fout);
	  	      fout.close();
			
			//**********
			
			//Queries all instructors that are in DB
			ResultSet rs = stmt.executeQuery("select * from instructors");
			while(rs.next()){
				if(rs.getString("username").equals(user) && rs.getString("password").equals(pw)){
					request.getSession().setAttribute("user", rs.getString("instructor_lastname"));
					instructorID = rs.getInt("instructor_id");
					pass=true;
				}
			}
			
			//Queries for all courses that the user has under his ID
			rs = stmt.executeQuery("select * from class where instructor_id = '"+instructorID+"'");
			while(rs.next()){
				courses.add(new CourseModel(rs.getString("course_name"),null, rs.getTime("deadline").getHours(),rs.getTime("deadline").getMinutes()));
			}
			
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
		if(pass){
			request.getSession().setAttribute("loginError", null);
			request.getSession().setAttribute("instructorID", instructorID);
			request.getSession().setAttribute("courses", courses);
			//*******
			
		
		
//******
			response.sendRedirect("Courses");		
		}else{
			request.getSession().setAttribute("loginError", "Invalid username/password!");
			request.getRequestDispatcher( "/WEB-INF/Login.jsp" ).forward(request, response );
		}
	}
  //*********************************
//	 public static  void makex() throws Exception 
//	   {
//		   
//	      Class.forName("com.mysql.jdbc.Driver");
//	      Connection connect = DriverManager.getConnection( 
//	      "jdbc:mysql://cs3.calstatela.edu/cs3220stu98" , 
//	      "cs3220stu98" , 
//	      "!SagHy*C"
//	      );
//	      Statement statement = connect.createStatement();
//	      ResultSet resultSet = statement
//	      .executeQuery("select * from student");
//	      XSSFWorkbook workbook = new XSSFWorkbook(); 
//	      XSSFSheet spreadsheet = workbook
//	    		  //"C:\\Users\\clinica digital\\Desktop\\starsss\\
//	      .createSheet("student db");
//	      XSSFRow row=spreadsheet.createRow(1);
//	      XSSFCell cell;
//	      cell=row.createCell(1);
//	      cell.setCellValue("FName");
//	      cell=row.createCell(2);
//	      cell.setCellValue("LName");
//	      cell=row.createCell(3);
//	      cell.setCellValue("CIN");
//	      cell=row.createCell(4);
//	      cell.setCellValue("user_id");
//	      
//	    
//	      int i=2;
//	      while(resultSet.next())
//	      {
//	         
//	 
//	         row=spreadsheet.createRow(i);
//
//	         cell=row.createCell(1);
//	         cell.setCellValue(resultSet.getString("firstName"));
//	         cell=row.createCell(2);
//	         cell.setCellValue(resultSet.getString("lastName"));
//	         cell=row.createCell(3);
//	         cell.setCellValue(resultSet.getInt("cin"));
//	         cell=row.createCell(4);
//	         cell.setCellValue(resultSet.getInt("user_id"));
//	         i++;
//	      }
//	      FileOutputStream out = new FileOutputStream(
//	      new File("exceldatabase.xlsx"));
//	      workbook.write(out);
//	      out.close();
//  
//	      }
	
	
	//********************************
}
