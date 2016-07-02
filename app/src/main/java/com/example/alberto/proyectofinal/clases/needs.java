package com.example.alberto.proyectofinal.clases;

/**
 * Created by Alberto on 6/11/2016.
 */
public class needs {

    public needs(){

    }
    public String name;
    public int quantity;
    public String photo;

    @Override
    public String toString() {
        return this.name + this.quantity;
    }
}
