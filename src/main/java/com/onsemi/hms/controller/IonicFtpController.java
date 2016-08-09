package com.onsemi.hms.controller;

import com.onsemi.hms.dao.IonicFtpDAO;
import java.util.List;
import com.onsemi.hms.model.IonicFtp;
import com.onsemi.hms.model.IonicFtpTemp;
import com.onsemi.hms.tools.QueryResult;
import com.opencsv.CSVReader;
import java.io.FileReader;
import java.util.ArrayList;
import javax.servlet.ServletContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping(value = "/wh/IonicFtp")
@SessionAttributes({"userSession"})
public class IonicFtpController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IonicFtpController.class);
    String[] args = {};

    //Delimiters which has to be in the CSV file
//    private static final String COMMA_DELIMITER = ",";
//    private static final String LINE_SEPARATOR = "\n";

    //File header
    private static final String HEADER = "id,request_type,hardware_type,hardware_id,type,quantity,requested_by,requested_date,remarks";

    @Autowired
    private MessageSource messageSource;

    @Autowired
    ServletContext servletContext;

    @RequestMapping(value = "/testOpenCsv", method = RequestMethod.GET)
    public String addtestOpenCsv(Model model) {

        CSVReader csvReader = null;

        try {
            /**
             * Reading the CSV File Delimiter is comma Start reading from line 1
             */
            String username = System.getProperty("user.name");
            String targetLocation = "C:\\Users\\" + username + "\\Documents\\CDARS\\test.csv";
            csvReader = new CSVReader(new FileReader(targetLocation), ',', '"', 1);
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
                IonicFtp  ftp = new IonicFtp();
                ftp.setRefId(e.getRefId());
                ftp.setRequestType(e.getRequestType());
                ftp.setHardwareType(e.getHardwareType());
                ftp.setHardwareId(e.getHardwareID());
                ftp.setType(e.getType());
                ftp.setQty(e.getQty());
                ftp.setRequestedBy(e.getRequestedBy());
                ftp.setRequestedDate(e.getRequestedDate());
                ftp.setRemarks(e.getRemarks());
                ftp.setMaterialPass("-");
                ftp.setRack("-");
                ftp.setShelf("-");
                ftp.setStatus("-");
                ftp.setFlag("-");
                
                IonicFtpDAO ionicFtpDAO = new IonicFtpDAO();
                int count = ionicFtpDAO.getCountExistingData(ftp);
                if (count == 0) {
                    LOGGER.info("data xdeeeeee");
                    ionicFtpDAO = new IonicFtpDAO();
                    IonicFtpDAO ionicFtpDAO1 = new IonicFtpDAO();
                    QueryResult queryResult1 = ionicFtpDAO.insertIonicFtp(ftp);
                }

/*
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
            LOGGER.info("testmaaaaaaa" + count);
            if (count == 0) {
                LOGGER.info("data xdeeeeee");
                ionicFtpDAO = new IonicFtpDAO();
                IonicFtpDAO ionicFtpDAO1 = new IonicFtpDAO();
                QueryResult queryResult1 = ionicFtpDAO.insertIonicFtp(ftp);
            }
*/
            }
        } catch (Exception ee) {
            ee.printStackTrace();
        }
        return "whRequest/add";
    }
}