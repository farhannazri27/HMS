package com.onsemi.hms.dao;

import com.onsemi.hms.db.DB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import com.onsemi.hms.model.IonicFtp;
import com.onsemi.hms.tools.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IonicFtpDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(IonicFtpDAO.class);
    private final Connection conn;
    private final DataSource dataSource;

    public IonicFtpDAO() {
        DB db = new DB();
        this.conn = db.getConnection();
        this.dataSource = db.getDataSource();
        
    }

    public QueryResult insertIonicFtp(IonicFtp ionicFtp) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO hms_wh_retrieval_list (ref_id, request_type, hardware_type, hardware_id, type, quantity, requested_by, requested_date, material_pass, rack, shelf, remarks, status, flag) "
              + "VALUES (?,?,?,?,?,?,?,NOW(),?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, ionicFtp.getRefId());
            ps.setString(2, ionicFtp.getRequestType());
            ps.setString(3, ionicFtp.getHardwareType());
            ps.setString(4, ionicFtp.getHardwareId());
            ps.setString(5, ionicFtp.getType());
            ps.setString(5, ionicFtp.getQty());
            ps.setString(6, ionicFtp.getRequestedBy());
            ps.setString(7, ionicFtp.getRequestedDate());
            ps.setString(8, ionicFtp.getMaterialPass());
            ps.setString(9, ionicFtp.getRack());
            ps.setString(10, ionicFtp.getShelf());
            ps.setString(11, ionicFtp.getRemarks());
            ps.setString(12, ionicFtp.getStatus());
            ps.setString(13, ionicFtp.getFlag());
            
            queryResult.setResult(ps.executeUpdate());
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                queryResult.setGeneratedKey(Integer.toString(rs.getInt(1)));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return queryResult;
    }

//    public QueryResult createStoreProcedure(String event, String rms,
//            String intervals, String status, String date, String equip,
//            String lcode, String hardware, String support_item) {
//        QueryResult queryResult = new QueryResult();
//        try {
//            PreparedStatement ps = conn.prepareStatement(
//                      "DROP PROCEDURE IF EXISTS select_or_insert_ionic_ftp $$\n"
//                    + "delimiter $$\n"
//                    + "CREATE PROCEDURE select_or_insert_ionic_ftp(\n"
//                    + "IN '" + event + "' VARCHAR(50),\n"
//                    + "IN '" + rms + "' VARCHAR(50),\n"
//                    + "IN '" + intervals + "' VARCHAR(50),\n"
//                    + "IN '" + status + "' VARCHAR(100),\n"
//                    + "IN '" + equip + "' VARCHAR(100),\n"
//                    + "IN '" + lcode + "' VARCHAR(50),\n"
//                    + "IN '" + hardware + "' VARCHAR(100),\n"
//                    + "IN '" + support_item + "' VARCHAR(100)) \n"
//                    + "BEGIN \n"
//                    + "IF NOT EXISTS (\n"
//                    + "SELECT * FROM cdars_ionic_ftp \n"
//                    + "WHERE event_code = '" + event + "' and rms = '" + rms + "' and intervals = '" + intervals + "' and current_status = '" + status + "' and \n"
//                    + "equip_id = '" + equip + "' and lcode = '" + lcode + "' and hardware_final = '" + hardware + "' \n"
//                    + "support_item = '" + support_item + "') THEN \n"
//                    + "INSERT INTO cdars_ionic_ftp (event_code, rms, intervals, current_status, date_off, equip_id,\n"
//                    + "lcode, hardware_final, support_item, created_date) VALUES ('" + event + "','" + rms + "','" + intervals + "','" + status + "',"
//                    + "'" + date + "','" + equip + "','" + lcode + "','" + hardware + "','" + support_item + "', NOW());\n"
//                    + "END IF;\n"
//                    + "END $$\n"
//                    + "delimiter ;"
//            );
//            queryResult.setResult(ps.executeUpdate());
//            ps.close();
//            LOGGER.info("masuk procedure");
//        } catch (SQLException e) {
//            queryResult.setErrorMessage(e.getMessage());
//            LOGGER.error(e.getMessage());
//        } finally {
//            if (conn != null) {
//                try {
//                    conn.close();
//                } catch (SQLException e) {
//                    LOGGER.error(e.getMessage());
//                }
//            }
//        }
//        return queryResult;
//    }
//    public QueryResult InsertAndcallStoreProcedure(IonicFtp ionicFtp) {
//        QueryResult queryResult = new QueryResult();
//        try {
//
//            String callProcedure = "{call select_or_insert_ionic_ftp(?,?,?,?,?,?,?,?,?)}";
//
//            PreparedStatement ps = conn.prepareStatement(callProcedure, Statement.RETURN_GENERATED_KEYS);
//
//            ps.setString(1, ionicFtp.getEventCode());
//            ps.setString(2, ionicFtp.getRms());
//            ps.setString(3, ionicFtp.getIntervals());
//            ps.setString(4, ionicFtp.getCurrentStatus());
//            ps.setString(5, ionicFtp.getDateOff());
//            ps.setString(6, ionicFtp.getEquipId());
//            ps.setString(7, ionicFtp.getLcode());
//            ps.setString(8, ionicFtp.getHardwareFinal());
//            ps.setString(9, ionicFtp.getSupportItem());
////			ps.setString(10, ionicFtp.getCreatedDate());
//            queryResult.setResult(ps.executeUpdate());
//            ResultSet rs = ps.getGeneratedKeys();
//            if (rs.next()) {
//                queryResult.setGeneratedKey(Integer.toString(rs.getInt(1)));
//            }
//            rs.close();
//            ps.close();
//            LOGGER.info("berjaya save");
//        } catch (SQLException e) {
//            queryResult.setErrorMessage(e.getMessage());
//            LOGGER.error(e.getMessage());
//        } finally {
//            if (conn != null) {
//                try {
//                    conn.close();
//                } catch (SQLException e) {
//                    LOGGER.error(e.getMessage());
//                }
//            }
//        }
//        return queryResult;
//    }

    public QueryResult updateIonicFtp(IonicFtp ionicFtp) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE hms_wh_retrieval_list "
                  + "SET ref_id = ?, request_type = ?, hardware_type = ?, hardware_id = ?, type = ?, quantity = ?, requested_by = ?, requested_date = ?, material_pass = ?, "
                      + "rack = ?, shelf = ?, remarks = ?, status = ?, flag = ? "
                  + "WHERE id = ?"
            );
            ps.setString(1, ionicFtp.getRefId());
            ps.setString(2, ionicFtp.getRequestType());
            ps.setString(3, ionicFtp.getHardwareType());
            ps.setString(4, ionicFtp.getHardwareId());
            ps.setString(5, ionicFtp.getType());
            ps.setString(6, ionicFtp.getQty());
            ps.setString(7, ionicFtp.getRequestedBy());
            ps.setString(8, ionicFtp.getRequestedDate());
            ps.setString(9, ionicFtp.getMaterialPass());
            ps.setString(10, ionicFtp.getRack());
            ps.setString(11, ionicFtp.getShelf());
            ps.setString(12, ionicFtp.getStatus());
            ps.setString(13, ionicFtp.getFlag());
            queryResult.setResult(ps.executeUpdate());
            ps.close();
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return queryResult;
    }

    public Integer getCountExistingData(IonicFtp ionicFtp) {
//        QueryResult queryResult = new QueryResult();
        Integer count = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) AS count FROM hms_ws_retrieval_list WHERE id = ?"
            );
            ps.setString(1, ionicFtp.getRefId());
            ps.setString(2, ionicFtp.getRequestType());
            ps.setString(3, ionicFtp.getHardwareType());
            ps.setString(4, ionicFtp.getHardwareId());
            ps.setString(5, ionicFtp.getType());
            ps.setString(6, ionicFtp.getQty());
            ps.setString(7, ionicFtp.getRequestedBy());
            ps.setString(8, ionicFtp.getRequestedDate());
            ps.setString(9, ionicFtp.getMaterialPass());
            ps.setString(10, ionicFtp.getRack());
            ps.setString(11, ionicFtp.getShelf());
            ps.setString(12, ionicFtp.getStatus());
            ps.setString(13, ionicFtp.getFlag());
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt("count");
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return count;
    }

    public QueryResult deleteIonicFtp(String ionicFtpId) {
        QueryResult queryResult = new QueryResult();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM hms_wh_retrieval_list WHERE ref_id = '" + ionicFtpId + "'"
            );
            queryResult.setResult(ps.executeUpdate());
            ps.close();
        } catch (SQLException e) {
            queryResult.setErrorMessage(e.getMessage());
            LOGGER.error(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return queryResult;
    }

    public IonicFtp getIonicFtp(String ionicFtpId) {
        String sql = "SELECT * FROM hms_wh_retrieval_list WHERE ref_id = '" + ionicFtpId + "'";
        IonicFtp ionicFtp = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ionicFtp = new IonicFtp();
                ionicFtp.setRefId(rs.getString("ref_id"));
                ionicFtp.setRequestedBy(rs.getString("request_type"));
                ionicFtp.setHardwareType(rs.getString("hardware_type"));
                ionicFtp.setHardwareId(rs.getString("hardware_id"));
                ionicFtp.setType(rs.getString("type"));
                ionicFtp.setQty(rs.getString("quantity"));
                ionicFtp.setRequestedBy(rs.getString("requested_by"));
                ionicFtp.setRequestedDate(rs.getString("requested_date"));
                ionicFtp.setMaterialPass(rs.getString("material_pass"));
                ionicFtp.setRack(rs.getString("rack"));
                ionicFtp.setShelf(rs.getString("shelf"));
                ionicFtp.setRemarks(rs.getString("remarks"));
                ionicFtp.setStatus(rs.getString("status"));
                ionicFtp.setFlag(rs.getString("flag"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return ionicFtp;
    }

    public List<IonicFtp> getIonicFtpList() {
        String sql = "SELECT * FROM hms_wh_retrieval_list ORDER BY id ASC";
        List<IonicFtp> ionicFtpList = new ArrayList<IonicFtp>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            IonicFtp ionicFtp;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ionicFtp = new IonicFtp();
                ionicFtp.setRefId(rs.getString("ref_id"));
                ionicFtp.setRequestedBy(rs.getString("request_type"));
                ionicFtp.setHardwareType(rs.getString("hardware_type"));
                ionicFtp.setHardwareId(rs.getString("hardware_id"));
                ionicFtp.setType(rs.getString("type"));
                ionicFtp.setQty(rs.getString("quantity"));
                ionicFtp.setRequestedBy(rs.getString("requested_by"));
                ionicFtp.setRequestedDate(rs.getString("requested_date"));
                ionicFtp.setMaterialPass(rs.getString("material_pass"));
                ionicFtp.setRack(rs.getString("rack"));
                ionicFtp.setShelf(rs.getString("shelf"));
                ionicFtp.setRemarks(rs.getString("remarks"));
                ionicFtp.setStatus(rs.getString("status"));
                ionicFtp.setFlag(rs.getString("flag"));
                ionicFtpList.add(ionicFtp);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return ionicFtpList;
    }
}