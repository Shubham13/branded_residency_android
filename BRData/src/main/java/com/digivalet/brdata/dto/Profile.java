package com.digivalet.brdata.dto;


import java.io.Serializable;
import java.util.List;

import com.digivalet.utils.stickyheader.StickyMainData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Profile implements Serializable {

    @SerializedName("headerProfile")
    @Expose
    private HeaderProfile headerProfile;
    @SerializedName("headerTitles")
    @Expose
    private List<HeaderTitle> headerTitles = null;
    private final static long serialVersionUID = -5514727547242498089L;

    public HeaderProfile getHeaderProfile() {
        return headerProfile;
    }

    public void setHeaderProfile(HeaderProfile headerProfile) {
        this.headerProfile = headerProfile;
    }

    public List<HeaderTitle> getHeaderTitles() {
        return headerTitles;
    }

    public void setHeaderTitles(List<HeaderTitle> headerTitles) {
        this.headerTitles = headerTitles;
    }

    public class HeaderProfile implements Serializable {

        @SerializedName("name")
        @Expose
        private Name name;
        @SerializedName("housedetails")
        @Expose
        private Housedetails housedetails;
        @SerializedName("userType")
        @Expose
        private String userType;
        @SerializedName("gender")
        @Expose
        private String gender;
        @SerializedName("date")
        @Expose
        private Date date;
        @SerializedName("mobilenumber")
        @Expose
        private String mobilenumber;
        @SerializedName("emailaddress")
        @Expose
        private String emailaddress;
        @SerializedName("otheraddress")
        @Expose
        private String otheraddress;
        @SerializedName("occupation")
        @Expose
        private String occupation;
        @SerializedName("maritalstatus")
        @Expose
        private String maritalstatus;
        @SerializedName("iddetails")
        @Expose
        private Iddetails iddetails;
        @SerializedName("Profile Notes")
        @Expose
        private String profileNotes;
        private final static long serialVersionUID = 4975801539181274551L;

        public Name getName() {
            return name;
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Housedetails getHousedetails() {
            return housedetails;
        }

        public void setHousedetails(Housedetails housedetails) {
            this.housedetails = housedetails;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public String getMobilenumber() {
            return mobilenumber;
        }

        public void setMobilenumber(String mobilenumber) {
            this.mobilenumber = mobilenumber;
        }

        public String getEmailaddress() {
            return emailaddress;
        }

        public void setEmailaddress(String emailaddress) {
            this.emailaddress = emailaddress;
        }

        public String getOtheraddress() {
            return otheraddress;
        }

        public void setOtheraddress(String otheraddress) {
            this.otheraddress = otheraddress;
        }

        public String getOccupation() {
            return occupation;
        }

        public void setOccupation(String occupation) {
            this.occupation = occupation;
        }

        public String getMaritalstatus() {
            return maritalstatus;
        }

        public void setMaritalstatus(String maritalstatus) {
            this.maritalstatus = maritalstatus;
        }

        public Iddetails getIddetails() {
            return iddetails;
        }

        public void setIddetails(Iddetails iddetails) {
            this.iddetails = iddetails;
        }

        public String getProfileNotes() {
            return profileNotes;
        }

        public void setProfileNotes(String profileNotes) {
            this.profileNotes = profileNotes;
        }

        public class Housedetails implements Serializable {

            @SerializedName("blocknumber/apartment")
            @Expose
            private String blocknumberApartment;
            @SerializedName("number/wing")
            @Expose
            private String numberWing;
            private final static long serialVersionUID = 363803934559222902L;

            public String getBlocknumberApartment() {
                return blocknumberApartment;
            }

            public void setBlocknumberApartment(String blocknumberApartment) {
                this.blocknumberApartment = blocknumberApartment;
            }

            public String getNumberWing() {
                return numberWing;
            }

            public void setNumberWing(String numberWing) {
                this.numberWing = numberWing;
            }

        }

        public class Iddetails implements Serializable {

            @SerializedName("idtype")
            @Expose
            private String idtype;
            @SerializedName("idnumber")
            @Expose
            private String idnumber;
            private final static long serialVersionUID = 7711640439404053084L;

            public String getIdtype() {
                return idtype;
            }

            public void setIdtype(String idtype) {
                this.idtype = idtype;
            }

            public String getIdnumber() {
                return idnumber;
            }

            public void setIdnumber(String idnumber) {
                this.idnumber = idnumber;
            }

        }

        public class Name implements Serializable {

            @SerializedName("firstname")
            @Expose
            private String firstname;
            @SerializedName("middlename")
            @Expose
            private String middlename;
            @SerializedName("lastname")
            @Expose
            private String lastname;
            @SerializedName("preferredname")
            @Expose
            private String preferredname;
            @SerializedName("profileimage")
            @Expose
            private String profileimage;
            private final static long serialVersionUID = -3078977286839819589L;

            public String getFirstname() {
                return firstname;
            }

            public void setFirstname(String firstname) {
                this.firstname = firstname;
            }

            public String getMiddlename() {
                return middlename;
            }

            public void setMiddlename(String middlename) {
                this.middlename = middlename;
            }

            public String getLastname() {
                return lastname;
            }

            public void setLastname(String lastname) {
                this.lastname = lastname;
            }

            public String getPreferredname() {
                return preferredname;
            }

            public void setPreferredname(String preferredname) {
                this.preferredname = preferredname;
            }

            public String getProfileimage() {
                return profileimage;
            }

            public void setProfileimage(String profileimage) {
                this.profileimage = profileimage;
            }

        }

        public class Date implements Serializable {

            @SerializedName("dateofpurchase")
            @Expose
            private String dateofpurchase;
            @SerializedName("dateofmove")
            @Expose
            private String dateofmove;
            @SerializedName("dateoflease")
            @Expose
            private String dateoflease;
            private final static long serialVersionUID = 6217396817716889987L;

            public String getDateofpurchase() {
                return dateofpurchase;
            }

            public void setDateofpurchase(String dateofpurchase) {
                this.dateofpurchase = dateofpurchase;
            }

            public String getDateofmove() {
                return dateofmove;
            }

            public void setDateofmove(String dateofmove) {
                this.dateofmove = dateofmove;
            }

            public String getDateoflease() {
                return dateoflease;
            }

            public void setDateoflease(String dateoflease) {
                this.dateoflease = dateoflease;
            }

        }

    }


    public class HeaderTitle implements Serializable {

        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("subTitles")
        @Expose
        private List<SubTitle> subTitles = null;
        private final static long serialVersionUID = -4826456939153739635L;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<SubTitle> getSubTitles() {
            return subTitles;
        }

        public void setSubTitles(List<SubTitle> subTitles) {
            this.subTitles = subTitles;
        }

        public class SubTitle implements Serializable, StickyMainData {

            @SerializedName("title")
            @Expose
            private String title;
            @SerializedName("type")
            @Expose
            private String type;
            @SerializedName("id")
            @Expose
            private String id;
            @SerializedName("count")
            @Expose
            private String count;
            @SerializedName("stepCount")
            @Expose
            private String stepCount;
            @SerializedName("calCount")
            @Expose
            private String calCount;
            @SerializedName("bpmCount")
            @Expose
            private String bpmCount;
            private final static long serialVersionUID = -1097581224645327078L;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getCount() {
                return count;
            }

            public void setCount(String count) {
                this.count = count;
            }

            public String getStepCount() {
                return stepCount;
            }

            public void setStepCount(String stepCount) {
                this.stepCount = stepCount;
            }

            public String getCalCount() {
                return calCount;
            }

            public void setCalCount(String calCount) {
                this.calCount = calCount;
            }

            public String getBpmCount() {
                return bpmCount;
            }

            public void setBpmCount(String bpmCount) {
                this.bpmCount = bpmCount;
            }

        }

    }
}















