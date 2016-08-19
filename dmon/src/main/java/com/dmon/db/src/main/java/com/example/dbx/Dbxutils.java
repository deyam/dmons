package com.dmon.db.src.main.java.com.example.dbx;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Dbxutils {
public static void viewTable(Connection con, String dbName)
    throws SQLException {

    Statement stmt = null;
//    String query = "select COF_NAME, SUP_ID, PRICE, " +
//                   "SALES, TOTAL " +
//                   "from " + dbName + ".COFFEES";


    String query = "SELECT 'Pending - Email Notifications'description, to_char(count(*)),NULL " +
    "FROM apps.wf_notifications wn,  " +
    "     apps.wf_roles wr " +
    "WHERE wr.NAME=wn.recipient_role  " +
    "AND wn.status IN ('OPEN') " +
    "AND ( (wn.mail_status NOT IN ('SENT', 'FAILED')) OR (wn.mail_status IS NULL) ) " +
    "AND wr.notification_preference NOT IN('QUERY', 'DISABLED', 'SUMMARY','SUMHTML') " +
    "AND trunc(wn.begin_date) BETWEEN trunc(sysdate) AND trunc(sysdate) " +
"UNION ALL " +
"    SELECT  initcap(decode(wn.mail_status, 'MAIL', 'PENDING',wn.mail_status))||' - Purchase Orders to Suppliers' \"Email Send Status\", to_char(count(*)), NULL  " +
"    FROM apps.wf_notifications wn,  " +
"         apps.wf_roles wr, " +
"         apps.wf_item_types_vl wiv, " +
"         apps.wf_items wi " +
"   WHERE wr.NAME = wn.recipient_role " +
"     AND wr.notification_preference NOT IN " +
"                                  ('QUERY', 'DISABLED', 'SUMMARY', 'SUMHTML') " +
"    AND (wn.mail_status NOT IN ('SENT'))  " +
"     AND (   wn.begin_date >= trunc(sysdate)-1  " +
"         ) " +
"     AND wiv.NAME = wn.MESSAGE_TYPE " +
"     AND wn.message_type=wi.item_type " +
"     AND wn.item_key=wi.item_key " +
"     AND wiv.display_name = 'PO Approval' " +
"     AND wr.display_name LIKE '%-%' " +
"     GROUP BY wn.mail_status  " +
"UNION ALL " +
"    SELECT 'Failed Email Notifications  ('||trunc(sysdate)||' and '||trunc(sysdate)||')', to_char(count(*)) total,NULL " +
"    FROM apps.wf_notifications wn,  " +
"         apps.wf_roles wr " +
"    WHERE wr.NAME=wn.recipient_role  " +
"    AND wn.status IN ('OPEN') " +
"    AND (wn.mail_status IN ('FAILED') OR wn.mail_status IS NULL) " +
"    --AND (wn.mail_status  IN ('FAILED') OR wn.mail_status IS NULL) " +
"    AND message_type <> 'WFERROR' AND message_name <> 'DEFAULT_EVENT_WARNING' " +
"    AND wr.notification_preference NOT IN('QUERY', 'DISABLED', 'SUMMARY','SUMHTML') " +
"    AND trunc(wn.begin_date) BETWEEN trunc(sysdate) AND trunc(sysdate) " +
"    UNION ALL  " +
"    SELECT 'Errored Notifications  ('||trunc(sysdate)||' and '||trunc(sysdate)||')', to_char(count(*)) total,NULL " +
"    FROM apps.wf_notifications wn,  " +
"         apps.wf_roles wr " +
"    WHERE wr.NAME=wn.recipient_role  " +
"    AND wn.status IN ('OPEN') " +
"    AND (wn.mail_status IN ('ERROR') OR wn.mail_status IS NULL) " +
"    --AND (wn.mail_status  IN ('FAILED') OR wn.mail_status IS NULL) " +
"    AND message_type <> 'WFERROR' AND message_name <> 'DEFAULT_EVENT_WARNING' " +
"    AND wr.notification_preference NOT IN('QUERY', 'DISABLED', 'SUMMARY','SUMHTML') " +
"    AND trunc(wn.begin_date) BETWEEN trunc(sysdate) AND trunc(sysdate) " +
"UNION ALL " +
"   SELECT 'Last successful email from mailer' Description , NULL count, max(wsn.END_date) last_successful_email " +
"    FROM apps.wf_notifications wsn " +
"UNION ALL " +
"    SELECT 'Notifications sent out in the last 15 mins',  to_char(count(*)), NULL " +
"    FROM apps.wf_notifications wn,  " +
"         apps.wf_roles wr " +
"    WHERE wr.NAME=wn.recipient_role  " +
"    AND wr.notification_preference NOT IN('QUERY', 'DISABLED', 'SUMMARY','SUMHTML') " +
"    AND wn.end_date BETWEEN sysdate-0.018  AND sysdate " +
"    AND end_date IS NOT NULL   " +
" UNION ALL " +
"    SELECT 'Notifications sent out in the last 30 mins',  to_char(count(*)), NULL " +
"    FROM apps.wf_notifications wn,  " +
"         apps.wf_roles wr " +
"    WHERE wr.NAME=wn.recipient_role  " +
"    AND wr.notification_preference NOT IN('QUERY', 'DISABLED', 'SUMMARY','SUMHTML') " +
"    AND wn.end_date BETWEEN sysdate-0.036  AND sysdate " +
"    AND end_date IS NOT NULL   " +
"UNION ALL " +
"    SELECT 'Notifications sent out in the last 1 hr',  to_char(count(*)), NULL " +
"    FROM apps.wf_notifications wn,  " +
"         apps.wf_roles wr " +
"    WHERE wr.NAME=wn.recipient_role  " +
"    AND wr.notification_preference NOT IN('QUERY', 'DISABLED', 'SUMMARY','SUMHTML') " +
"    AND wn.end_date BETWEEN sysdate-0.072  AND sysdate " +
"    AND end_date IS NOT NULL   " +
" UNION ALL " +
"    SELECT 'Notifications sent out in the last 2 hrs',  to_char(count(*)), NULL " +
"    FROM apps.wf_notifications wn,  " +
"         apps.wf_roles wr " +
"    WHERE wr.NAME=wn.recipient_role  " +
"    AND wr.notification_preference NOT IN('QUERY', 'DISABLED', 'SUMMARY','SUMHTML') " +
"    AND wn.end_date BETWEEN sysdate-0.144  AND sysdate " +
"    AND end_date IS NOT NULL " +
"UNION ALL " +
"    SELECT 'Notifications sent out today',  to_char(count(*)), NULL " +
"    FROM apps.wf_notifications wn,  " +
"         apps.wf_roles wr " +
"    WHERE wr.NAME=wn.recipient_role  " +
"    AND wr.notification_preference NOT IN('QUERY', 'DISABLED', 'SUMMARY','SUMHTML') " +
"    AND trunc(wn.end_date) > = trunc(sysdate) " +
"    AND end_date IS NOT NULL " ;


    try {
        stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            String coffeeName = rs.getString("COF_NAME");
            int supplierID = rs.getInt("SUP_ID");
            float price = rs.getFloat("PRICE");
            int sales = rs.getInt("SALES");
            int total = rs.getInt("TOTAL");
            System.out.println(coffeeName + "\t" + supplierID +
                               "\t" + price + "\t" + sales +
                               "\t" + total);
        }
    } catch (SQLException e ) {
        //JDBCTutorialUtilities.printSQLException(e);
        System.out.println("Exception:"+e.toString());
    } finally {
        if (stmt != null) { stmt.close(); }
    }
}

}