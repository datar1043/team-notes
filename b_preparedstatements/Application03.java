package com.wanted.b_preparedstatements;

import com.wanted.common.EmployeeDTO;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;
import java.util.Scanner;

import static com.wanted.common.JDBCTemplate.close;
import static com.wanted.common.JDBCTemplate.getConnection;

public class Application03 {

    public static void main(String[] args) {

        /*
        * comment.
        *  SQL 구문은 1문장으로 끝나면 문자열로 작성해도 큰 무리가 없다.
        *  다만, Join 을 하거나 조건이 복잡해지면 문자열 합치기로 SQL 구문을 작성하기 굉장히 어려워진다.
        * */


        Connection con = getConnection();
        PreparedStatement stmt = null;
        ResultSet rset = null;

        // 1명의 모든 정보를 담을 수 있는 EmployeeDTO 객체 생성
        EmployeeDTO emp = null;

        Scanner sc = new Scanner(System.in);
        System.out.print("조회하실 사번을 입력해주세요. : ");
        String empId = sc.nextLine();

        Properties prop = new Properties();

        try {

            prop.loadFromXML(
                    new FileInputStream("src/main/java/com/wanted/b_preparedstatements/employee-query.xml")
            );

            String query = prop.getProperty("selectEmpById");

            // statement 는 Connection 을 통해 객체 생성
            stmt = con.prepareStatement(query);
            stmt.setString(1, empId);
            rset = stmt.executeQuery();

            if (rset.next()) {
                emp = new EmployeeDTO();
                emp.setEmpId(rset.getString("EMP_ID"));
                emp.setEmpName(rset.getString("EMP_NAME"));
                emp.setEmpNo(rset.getString("EMP_NO"));
                emp.setEmail(rset.getString("EMAIL"));
                emp.setPhone(rset.getString("PHONE"));
                emp.setDeptCode(rset.getString("DEPT_CODE"));
                emp.setJobCode(rset.getString("JOB_CODE"));
                emp.setSalLevel(rset.getString("SAL_LEVEL"));
                emp.setSalary(rset.getInt("SALARY"));
                emp.setBonus(rset.getDouble("BONUS"));
                emp.setManagerId(rset.getString("MANAGER_ID"));
                emp.setHireDate(rset.getDate("HIRE_DATE"));
                emp.setEntDate(rset.getDate("ENT_DATE"));
                emp.setEntYn(rset.getString("ENT_YN"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InvalidPropertiesFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            close(stmt);
            close(con);
            close(rset);
        }

        System.out.println("조회 한 사원의 정보 : " + emp);

    }

}
