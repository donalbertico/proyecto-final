package com.example.alberto.proyectofinal.clases;

import java.util.List;

/**
 * Created by Alberto on 6/11/2016.
 */
public class User {

   public User(String e , String p){

       this.email =e ;
       this.password =  p;
   }
    public User(){

    }
    public String email;
    public String name;
    public String password;
    public String succes;
    public List<com.example.alberto.proyectofinal.clases.needs> needs;
    public String photo;
    public String id;
}
