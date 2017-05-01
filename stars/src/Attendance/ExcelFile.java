package Attendance;


import java.io.*; 
import java.sql.*;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFCell;

public class ExcelFile {
        public static void main(String[] args) {
                try {
                        Class.forName("com.mysql.jdbc.Driver").newInstance();
                        Connection connection = DriverManager.getConnection(
                                        "jdbc:mysql://cs3.calstatela.edu/cs3220stu98", "cs3220stu98", "!SagHy*C");
                        PreparedStatement psmnt = null;
                        Statement st = connection.createStatement();
                        ResultSet rs = st.executeQuery("Select * from student");
//                        XSSFWorkbook workbook = new XSSFWorkbook(); 
//                      XSSFSheet spreadsheet = workbook
//                      .createSheet("student_db");
//                      XSSFRow row=spreadsheet.createRow(1);
//                      XSSFCell cell;
//                      cell=row.createCell(1);
//                      cell.setCellValue("FName");
//                      cell=row.createCell(2);
//                      cell.setCellValue("LName");
//                      cell=row.createCell(3);
//                      cell.setCellValue("CIN");
//                      cell=row.createCell(4);
//                      cell.setCellValue("user_id");

//                      int i=2;
//                      while(resultSet.next())
//                      {
//                         row=spreadsheet.createRow(i);

//                         cell=row.createCell(1);
//                         cell.setCellValue(resultSet.getString("firstName"));
//                         cell=row.createCell(2);
//                         cell.setCellValue(resultSet.getString("lastName"));
//                         cell=row.createCell(3);
//                         cell.setCellValue(resultSet.getInt("cin"));
//                         cell=row.createCell(4);
//                         cell.setCellValue(resultSet.getInt("user_id"));
//                         i++;
//                      }
//                      FileOutputStream out = new FileOutputStream(
//                      new File("exceldatabase.xlsx"));
//                      workbook.write(out);
//                      out.close();
//                      System.out.println(
//                      "exceldatabase.xlsx written successfully");

                        HSSFWorkbook wb = new HSSFWorkbook();
                        HSSFSheet sheet = wb.createSheet("Excel Sheet");
                        HSSFRow rowhead = sheet.createRow((short) 0);
                       
                        rowhead.createCell((short) 0).setCellValue("FName");
                        rowhead.createCell((short) 1).setCellValue("LName");
                        rowhead.createCell((short) 2).setCellValue("CIN");
                        rowhead.createCell((short) 3).setCellValue("user_id");
                        	/*
                        	 * String url = "jdbc:mysql://cs3.calstatela.edu/cs3220stu98";
					String username = "cs3220stu98";
					String password = "!SagHy*C";
                        	 */
                        int index = 1;
                        while (rs.next()) {

                                HSSFRow row = sheet.createRow((short) index);
                               
                                row.createCell((short) 0).setCellValue(rs.getString(10));
                                row.createCell((short) 1).setCellValue(rs.getString(10));
                                row.createCell((short) 2).setCellValue(rs.getInt(7));
                                row.createCell((short) 3).setCellValue(rs.getInt(5));
                                
                                index++;
                        }
                        FileOutputStream fileOut = new FileOutputStream("c:\\superexcelFile.xls");
                        wb.write(fileOut);
                        fileOut.close();
                        System.out.println("Data is saved in excel file.");
                        rs.close();
                        connection.close();
                } catch (Exception e) {
                }
        }
}