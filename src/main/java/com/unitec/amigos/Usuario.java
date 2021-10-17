package com.unitec.amigos;


import org.springframework.data.annotation.Id;

public class Usuario {

    private String nombre;
    @Id
    private String email;
    private int edad;

    public Usuario() {
    }

    @Override
    public String toString() { // SIRVE POR SI HAY UN OBJETO NOS DE SUS ATRIBUTOS
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", edad=" + edad +
                '}';
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }
}
