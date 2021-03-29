package com.example.brainsoup.model;

public class AddPatientInfo {
        public  String email;
        public String tc;
        public String cinsiyet;
        public String date;
        public String name;
        public  String surname;
        public  String userKey;
        public AddPatientInfo(String tc,String cinsiyet,String date,String email,  String name, String surname,String userKey){
            this.email=email;
            this.tc = tc;
            this.cinsiyet = cinsiyet;
            this.date = date;
            this.name = name;
            this.surname = surname;
            this.userKey=userKey;
        }

}
