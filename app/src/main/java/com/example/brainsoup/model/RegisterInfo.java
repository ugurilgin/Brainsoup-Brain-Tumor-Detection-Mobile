package com.example.brainsoup.model;

public class RegisterInfo {
        public String email;
        public String password;
        public String name;
        public  String surname;
        public RegisterInfo(String email, String password,String name,String surname){
          this.email = email;
          this.password = password;
          this.name = name;
          this.surname = surname;
        }

}
