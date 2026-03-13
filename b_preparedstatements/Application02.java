package com.wanted.b_preparedstatements;

import java.sql.*;
import java.util.Scanner;

import static com.wanted.common.JDBCTemplate.close;
import static com.wanted.common.JDBCTemplate.getConnection;

public class Application02 {

    public static void main(String[] args) {

        Connection con = getConnection();

        /*
         * comment.
         *  Jdbc 의 핵심적인 인터페이스 2가지
         *  1. Statement
         *  - SQL 문을 저장하고 실행할 수 있는 기능을 가진 인터페이스
         *  2. ResultSet
         *  - SQL 문 결과 집합을 받아올 수 있는 인테페이스
         * */

        PreparedStatement pstmt = null;
        ResultSet rset = null;

        try {
            // statement 는 Connection 을 통해 객체 생성


            Scanner sc = new Scanner(System.in);
            System.out.println("조회하실 사번을 입력해주세요 : ");
            String empId = sc.nextLine();
            // statement 는 Connection 을 통해 개객체 생성
            pstmt = con.prepareStatement("SELECT EMP_ID, EMP_NAME FROM EMPLOYEE WHERE EMP_ID = ?");
            /*
            * ? : placeholder
            * ? 의 갯수에 따라 parameterIndex 가 결정된다
            * 항상 set 메소드로 값을 채워줘야 한다.
            * */

            pstmt.setString(1, empId);
            rset = pstmt.executeQuery();
            if (rset.next()){
                /* comment. next() : ResultSet 을 목록화 시켜
                 *   형이 존재하면 True,  존재하지 않으면 False 라고 반환한다.*/
                System.out.println(rset.getString("EMP_ID") + "번 " + rset.getString("EMP_NAME") + "사원");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(pstmt);
            close(con);
            close(rset);

        }

    }

}
