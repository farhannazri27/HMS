/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.onsemi.hms.tools;

import com.onsemi.hms.dao.IonicFtpDAO;
import com.onsemi.hms.model.IonicFtp;
import com.onsemi.hms.model.IonicFtpTemp;
import com.opencsv.CSVReader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author fg79cj
 */

//contoh kalau pki buffered reader
//ad disadvantage klu ad comma dkt dlm column
public class ReadCSVFile_openCsv {
//
//   //Delimiters used in the CSV file
    private static final String COMMA_DELIMITER = ",";

    public static void main(String args[]) {
        CSVReader csvReader = null;
//        BufferedReader br = null;
        try {
  /**
             * Reading the CSV File Delimiter is comma Start reading from line 1
             */
//            Date initDate1 = new Date();
//            SimpleDateFormat formatter1 = new SimpleDateFormat("yyyyMMdd");
//            String parsedDate1 = formatter1.format(initDate1);
//            System.out.println(parsedDate1 + " tarikh csv!!!!...");
//            csvReader = new CSVReader(new FileReader("C:\\Hardware_From_Humidity_Stress_FTP-" + parsedDate1 + ".csv"), ',', '"', 1);

            csvReader = new CSVReader(new FileReader("C:\\Hardware_From_Humidity_Stress_FTP-20160505.csv"), ',', '"', 1);
            //employeeDetails stores the values current line
            String[] ionicFtp = null;
            //Create List for holding Employee objects
            List<IonicFtpTemp> empList = new ArrayList<IonicFtpTemp>();

            while ((ionicFtp = csvReader.readNext()) != null) {
                //Save the employee details in Employee object
                IonicFtpTemp emp = new IonicFtpTemp(ionicFtp[0],
                        ionicFtp[1], ionicFtp[2],
                        ionicFtp[3], ionicFtp[4],
                        ionicFtp[5], ionicFtp[6],
                        ionicFtp[7], ionicFtp[8]);
                empList.add(emp);
            }

            //Lets print the Employee List
            for (IonicFtpTemp e : empList) {

                Date initDate = new SimpleDateFormat("MM/dd/yy H:mm").parse(e.getDateOff());
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd H:mm");
                String parsedDate = formatter.format(initDate);

                IonicFtp ftp = new IonicFtp();
                ftp.setEventCode(e.getEventCode());
                ftp.setRms(e.getRms());
                ftp.setIntervals(e.getIntervals());
                ftp.setCurrentStatus(e.getCurrentStatus());
                ftp.setDateOff(parsedDate);
                ftp.setEquipId(e.getEquipId());
                ftp.setLcode(e.getLcode());
                ftp.setHardwareFinal(e.getHardwareFinal());
                ftp.setSupportItem(e.getSupportItem());

                IonicFtpDAO ionicFtpDAO = new IonicFtpDAO();
                int count = ionicFtpDAO.getCountExistingData(ftp);

                if (count == 0) {
                    ionicFtpDAO = new IonicFtpDAO();
//                    IonicFtpDAO ionicFtpDAO1 = new IonicFtpDAO();
                    QueryResult queryResult1 = ionicFtpDAO.insertIonicFtp(ftp);
                }

            }
        } catch (Exception ee) {
            ee.printStackTrace();
        }

    }

}