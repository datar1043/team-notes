package com.wanted.a_statements;

import com.wanted.common.EmployeeDTO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.wanted.common.JDBCTemplate.close;
import static com.wanted.common.JDBCTemplate.getConnection;

public class Application04 {

    /*
    * comment. 하부르타 시 해야할 일
    *  SELECT * ALL FROM  EMPLOYEE;
    *  Application03 에서 EmployeeDTO 에 1명을 담았었다.
    *  그렇다면 모든 회원은 어떻게 담을까?
    *  */

    public static void main(String[] args) {

        Connection con = getConnection();
        Statement stmt = null;
        ResultSet rset = null;

        // 1명의 모든 정보를 담을 수 있는 EmployeeDTO 객체 생성
        // EmployeeDTO들만 담을 수 있는 리스트 주머니를 만든다!
        EmployeeDTO emp = null;
        List<EmployeeDTO> empList = new ArrayList<>();

        try {
            // statement 는 Connection 을 통해 객체 생성
            stmt = con.createStatement();

//            Scanner sc = new Scanner(System.in);
//            System.out.print("조회하실 사번을 입력해주세요 : ");
//            String empId = sc.nextLine();

            rset = stmt.executeQuery("SELECT * FROM EMPLOYEE ");

            while (rset.next()) {
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

                empList.add(emp);

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(stmt);
            close(con);
            close(rset);
        }

        System.out.println(empList);

    }

}
