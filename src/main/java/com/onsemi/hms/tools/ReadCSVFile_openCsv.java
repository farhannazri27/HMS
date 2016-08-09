package com.onsemi.hms.tools;

import com.onsemi.hms.dao.IonicFtpDAO;
import com.onsemi.hms.dao.WhRequestDAO;
import com.onsemi.hms.model.IonicFtp;
import com.onsemi.hms.model.IonicFtpTemp;
import com.onsemi.hms.model.WhRequest;
import com.opencsv.CSVReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author fg79cj
 */
//contoh kalau pki buffered reader
//ad disadvantage klu ad comma dkt dlm column
public class ReadCSVFile_openCsv {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReadCSVFile_openCsv.class);
    //Delimiters used in the CSV file
    private static final String COMMA_DELIMITER = ",";

    public static void main(String args[]) {
//        CSVReader csvReader = null;
////        BufferedReader br = null;
//        try {
//            /**
//             * Reading the CSV File Delimiter is comma Start reading from line 1
//             */
////            Date initDate1 = new Date();
////            SimpleDateFormat formatter1 = new SimpleDateFormat("yyyyMMdd");
////            String parsedDate1 = formatter1.format(initDate1);
////            System.out.println(parsedDate1 + " tarikh csv!!!!...");
////            csvReader = new CSVReader(new FileReader("C:\\Hardware_From_Humidity_Stress_FTP-" + parsedDate1 + ".csv"), ',', '"', 1);
//
//            String username = System.getProperty("user.name");
//            String targetLocation = "C:\\Users\\" + username + "\\Documents\\CDARS\\cdars.csv";
//
//            LOGGER.info("test...." + targetLocation);
//
//            //csvReader = new CSVReader(new FileReader("C:\\Users\\zbczmg\\Desktop\\test - original cdars.csv"), ',', '"', 1);
//            csvReader = new CSVReader(new FileReader(targetLocation), ',', '"', 1);
//            //employeeDetails stores the values current line
//
//            String[] ionicFtp = null;
//            //Create List for holding Employee objects
//            List<IonicFtpTemp> empList = new ArrayList<IonicFtpTemp>();
//
//            while ((ionicFtp = csvReader.readNext()) != null) {
//                //Save the employee details in Employee object
//                IonicFtpTemp emp = new IonicFtpTemp(ionicFtp[0],
//                        ionicFtp[1], ionicFtp[2],
//                        ionicFtp[3], ionicFtp[4],
//                        ionicFtp[5], ionicFtp[6],
//                        ionicFtp[7], ionicFtp[8]);
//                empList.add(emp);
//
//                LOGGER.info("testssss1...." + ionicFtp[0]);
//                LOGGER.info("testssss2...." + ionicFtp[1]);
//                LOGGER.info("testssss3...." + ionicFtp[2]);
//                LOGGER.info("testssss4...." + ionicFtp[3]);
//                LOGGER.info("testssss5...." + ionicFtp[4]);
//                LOGGER.info("testssss6...." + ionicFtp[5]);
//                LOGGER.info("testssss7...." + ionicFtp[6]);
//                LOGGER.info("testssss8...." + ionicFtp[7]);
//                LOGGER.info("testssss9...." + ionicFtp[8]);
//            }
//
////            LOGGER.info("test...." + ionicFtp[0]);
//            //Lets print the Employee List
//            for (IonicFtpTemp e : empList) {
//
//                LOGGER.info("masuk!!");
//
//
//                /*
//                Date initDate = new SimpleDateFormat("MM/dd/yy H:mm:ss").parse(e.getDateOff());
//                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd H:mm:ss");
//                String parsedDate = formatter.format(initDate);
//                 */
////                IonicFtp ftp = new IonicFtp();
////                ftp.setEventCode(e.getEventCode());
////                ftp.setRms(e.getRms());
////                ftp.setIntervals(e.getIntervals());
////                ftp.setCurrentStatus(e.getCurrentStatus());
////                ftp.setDateOff(e.getDateOff());
////                ftp.setEquipId(e.getEquipId());
////                ftp.setLcode(e.getLcode());
////                ftp.setHardwareFinal(e.getHardwareFinal());
////                ftp.setSupportItem(e.getSupportItem());
//                WhRequest wh = new WhRequest();
//                wh.setEquipmentId(e.getEventCode());
//                wh.setEquipmentType(e.getRms());
//                wh.setFirstApprovedStatus(e.getEquipId());
//                wh.setRemarks(e.getLcode());
//                wh.setType(e.getHardwareFinal());
//                wh.setStatus(e.getSupportItem());
//
//                LOGGER.info("e.getEventCode()bbbbbbb...." + e.getEventCode());
//                LOGGER.info("e.e.getSupportItem()()bbbbbbb...." + e.getSupportItem());
//                WhRequestDAO whd = new WhRequestDAO();
////                IonicFtpDAO ionicFtpDAO = new IonicFtpDAO();
//                QueryResult queryResult1 = whd.insertWhRequest(wh);
//                LOGGER.info("++++++++++++++");
//
////                int count = ionicFtpDAO.getCountExistingData(ftp);
////                LOGGER.info("countiiii...." + count);
////                
////                if (count == 0) {                
////                    ionicFtpDAO = new IonicFtpDAO();
////                    QueryResult queryResult1 = ionicFtpDAO.insertIonicFtp(ftp);
////                }
//            }
//        } catch (Exception ee) {
//            ee.printStackTrace();
//        }

    }

}
