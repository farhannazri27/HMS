package com.onsemi.hms.model;

/**
 * @author fg79cj
 */
public class IonicFtpTemp {
    
    private String refId;
    private String requestType;
    private String hardwareType;
    private String hardwareID;
    private String type;
    private String qty;
    private String requestedBy;
    private String requestedDate;
    private String remarks;
    
    public IonicFtpTemp() {
    }

    public IonicFtpTemp(String refId, String requestType, String hardwareType, String hardwareID, String type, String qty, String requestedBy, String requestedDate, String remarks) {
        super();
        this.refId = refId;
        this.requestType = requestType;
        this.hardwareType = hardwareType;
        this.hardwareID = hardwareID;
        this.type = type;
        this.qty = qty;
        this.requestedBy = requestedBy;
        this.requestedDate = requestedDate;
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "IonicFtpTemp{" + "refId=" + refId + ", requestType=" + requestType + ", hardwareType=" + hardwareType + ", hardwareID=" + hardwareID + ", type=" + type + ", qty=" + qty + ", requestedBy=" + requestedBy + ", requestedDate=" + requestedDate + ", remarks=" + remarks + '}';
    }
    
    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getHardwareType() {
        return hardwareType;
    }

    public void setHardwareType(String hardwareType) {
        this.hardwareType = hardwareType;
    }

    public String getHardwareID() {
        return hardwareID;
    }

    public void setHardwareID(String hardwareID) {
        this.hardwareID = hardwareID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
    }

    public String getRequestedDate() {
        return requestedDate;
    }

    public void setRequestedDate(String requestedDate) {
        this.requestedDate = requestedDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }    
    
//    private String eventCode;
//    private String rms;
//    private String intervals;
//    private String currentStatus;
//    private String dateOff;
//    private String equipId;
//    private String lcode;
//    private String hardwareFinal;
//    private String supportItem;
//    private String createdDate;
//
//    public IonicFtpTemp() {
//    }
//
//    public IonicFtpTemp(String eventCode, String rms,
//            String intervals, String currentStatus,
//            String dateOff, String equipId,
//            String lcode, String hardwareFinal, String supportItem) {
//        super();
//        this.eventCode = eventCode;
//        this.rms = rms;
//        this.intervals = intervals;
//        this.currentStatus = currentStatus;
//        this.dateOff = dateOff;
//        this.equipId = equipId;
//        this.lcode = lcode;
//        this.hardwareFinal = hardwareFinal;
//        this.supportItem = supportItem;
//    }
//
//    @Override
//    public String toString() {
//        return "ionicFtp [eventCode=" + eventCode + ", rms=" + rms
//                + ", intervals=" + intervals + ", currentStatus=" + currentStatus + ","
//                + "dateOff=" + dateOff + ", equipId=" + equipId
//                + ", lcode=" + lcode + ", hardwareFinal=" + hardwareFinal + ", supportItem=" + supportItem + "]";
//    }
//
//    public String getEventCode() {
//        return eventCode;
//    }
//
//    public void setEventCode(String eventCode) {
//        this.eventCode = eventCode;
//    }
//
//    public String getRms() {
//        return rms;
//    }
//
//    public void setRms(String rms) {
//        this.rms = rms;
//    }
//
//    public String getIntervals() {
//        return intervals;
//    }
//
//    public void setIntervals(String intervals) {
//        this.intervals = intervals;
//    }
//
//    public String getCurrentStatus() {
//        return currentStatus;
//    }
//
//    public void setCurrentStatus(String currentStatus) {
//        this.currentStatus = currentStatus;
//    }
//
//    public String getDateOff() {
//        return dateOff;
//    }
//
//    public void setDateOff(String dateOff) {
//        this.dateOff = dateOff;
//    }
//
//    public String getEquipId() {
//        return equipId;
//    }
//
//    public void setEquipId(String equipId) {
//        this.equipId = equipId;
//    }
//
//    public String getLcode() {
//        return lcode;
//    }
//
//    public void setLcode(String lcode) {
//        this.lcode = lcode;
//    }
//
//    public String getHardwareFinal() {
//        return hardwareFinal;
//    }
//
//    public void setHardwareFinal(String hardwareFinal) {
//        this.hardwareFinal = hardwareFinal;
//    }
//
//    public String getSupportItem() {
//        return supportItem;
//    }
//
//    public void setSupportItem(String supportItem) {
//        this.supportItem = supportItem;
//    }
//
//    public String getCreatedDate() {
//        return createdDate;
//    }
//
//    public void setCreatedDate(String createdDate) {
//        this.createdDate = createdDate;
//    }    
}
