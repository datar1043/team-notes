package com.wanted.b_preparedstatements;

import java.sql.*;

import static com.wanted.common.JDBCTemplate.close;
import static com.wanted.common.JDBCTemplate.getConnection;

public class Application01 {

    public static void main(String[] args) {

        /*
        * comment.
        *  Statement 는 SQL 쿼리문을 실행할 때마다 SQL 문이 DBMS 에 전송 되어
        *  DBMS 에서 SQL 구문을 파싱(번역)하고 컴파일을 하는 과정을 거치게 된다.
        *  PreparedStatement 는 최초에 한 번 실행 시, SQL 구문을 파싱하고 컴ㅍ파일 하는 것은
        *  동일하지만 동일한 구문을 여러번 실행하면, 최초에 컴파을 한 SQL 을 재사용하게 된다.
        *  따라서 파싱하고 컴파일 하는 과정을 생략하여 성능이 향상된다.
        * */

        Connection con = getConnection();
        PreparedStatement pstmt = null;
        ResultSet rset = null;

        try {
            // statement 는 Connection 을 통해 객체 생성
            pstmt = con.prepareStatement("SELECT EMP_ID, EMP_NAME FROM EMPLOYEE");

            rset = pstmt.executeQuery();

            while (rset.next()) {
                /* comment. next() : ResultSet 을 목록화 시켜
                 *   행이 존재하면 True , 존재하지 않으면 False 를 반환한다.
                 *  */
                System.out.println(rset.getString("EMP_ID") + "번 " +
                        rset.getString("EMP_NAME") + "사원"
                );
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
