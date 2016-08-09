package com.onsemi.hms.model;

public class IonicFtp {

    private String id;
    private String refId;
    private String requestType;
    private String hardwareType;
    private String hardwareId;
    private String type;
    private String qty;
    private String requestedBy;
    private String requestedDate;
    private String materialPass;
    private String rack;
    private String shelf;
    private String remarks;
    private String status;
    private String flag;
    
    public IonicFtp() {
    }
    
    public IonicFtp(String id, String refId, String requestType, String hardwareType, String hardwareId, String type, String requestedBy, String requestedDate, String materialPass, String rack, String shelf, String remarks, String status, String flag) {
        super();
        this.id = id;
        this.refId = refId;
        this.requestType = requestType;
        this.hardwareType = hardwareType;
        this.hardwareId = hardwareId;
        this.type = type;
        this.requestedBy = requestedBy;
        this.requestedDate = requestedDate;
        this.materialPass = materialPass;
        this.rack = rack;
        this.shelf = shelf;
        this.remarks = remarks;
        this.status = status;
        this.flag = flag;
    }
    
    @Override
    public String toString() {
        return "IonicFtp{" + "id=" + id + ", refId=" + refId + ", requestType=" + requestType + ", hardwareType=" + hardwareType + ", hardwareId=" + hardwareId + ", type=" + type + ", qty=" + qty + ", requestedBy=" + requestedBy + ", requestedDate=" + requestedDate + ", materialPass=" + materialPass + ", rack=" + rack + ", shelf=" + shelf + ", remarks=" + remarks + ", status=" + status + ", flag=" + flag + '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getHardwareId() {
        return hardwareId;
    }

    public void setHardwareId(String hardwareId) {
        this.hardwareId = hardwareId;
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

    public String getMaterialPass() {
        return materialPass;
    }

    public void setMaterialPass(String materialPass) {
        this.materialPass = materialPass;
    }

    public String getRack() {
        return rack;
    }

    public void setRack(String rack) {
        this.rack = rack;
    }

    public String getShelf() {
        return shelf;
    }

    public void setShelf(String shelf) {
        this.shelf = shelf;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    
//    private String id;
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
//    public IonicFtp() {
//    }
//
//    public IonicFtp(String eventCode, String rms,
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
//        return "ionicFtp [id=" + id + ",eventCode=" + eventCode + ", rms=" + rms
//                + ", intervals=" + intervals + ", currentStatus=" + currentStatus + ","
//                + "dateOff=" + dateOff + ", equipId=" + equipId
//                + ", lcode=" + lcode + ", hardwareFinal=" + hardwareFinal + ", supportItem=" + supportItem + "]";
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
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
