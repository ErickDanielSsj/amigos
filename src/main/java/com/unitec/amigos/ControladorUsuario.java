package com.unitec.amigos;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //
@RequestMapping("/api")
public class ControladorUsuario {

    //AQUI VA UN METODO QUE REPRESENTA CADA UNO DE LOS ESTADOS QUE VAMOS A TRANSFERIR
    //ES DECIR, VA UN GET , POST, PUT Y DELTE. COMO MINIMO

    //AQUI YA VIENE EL USO DE LA INVERSION DE CONTROL

    @Autowired RepositorioUsuario repoUsuario;

    //Implementamos el codigo para guardar un usuario en MonfoDB
    //PARA GUARDAR SE OCUPA ESTE METODO
    @PostMapping("/usuario")
    public Estatus guardar(@RequestBody String json)throws Exception{
        //PRIMERO LEEMOS Y CONVERTIMOS EL OBJETO JSON A OBJETO JAVA
        ObjectMapper mapper = new ObjectMapper();
        Usuario u = mapper.readValue(json,Usuario.class);
        //ESTE USUARIO, YA EN FORMATO JSON LO GUARDAMOS EN MONGODB
        repoUsuario.save(u);
        //CREAMOS UN OBJETO DE TIPO ESTATUS Y ESTE OBJETO LO RETORNAMOS AL CLIENTE (ANDROID O POSTMAN)
        Estatus estatus = new Estatus();
        estatus.setSuccess(true);
        estatus.setMensaje("Tu usuario se guardo con exito!!");
        return estatus;

    }

    @GetMapping("/usuario/{id}")// ESTO ES UN CONTROLADOR DE ESTILO REST
    public Usuario obtenerPorId(@PathVariable String id){
        //LEEMOS UN USUARIO CON EL METODO finById pasandole como argumento el id(email) que queremos
        Usuario u =repoUsuario.findById(id).get();
        return u;
    }
    //ESTE METODO BUSCA A TODOS LOS QUE ESTAN GUARDADOS EN MONGODB
    @GetMapping("/usuario")
    public List<Usuario> buscarTodos(){ // List es un tipo de arreglo mutable, que crece en tiempo real

        return repoUsuario.findAll();
    }
    //Metodo para actualizar
    @PutMapping("/usuario")
    public Estatus actualizar(@RequestBody String json)throws Exception{

        //Primero debemos verificar que exista, por lo tanto primero buscamos
        ObjectMapper mapper = new ObjectMapper();
        Usuario u=mapper.readValue(json, Usuario.class);
        Estatus e=new Estatus();
        if(repoUsuario.findById(u.getEmail()).isPresent()) {
            //Lo volvemos a guardar
            repoUsuario.save(u);
            e.setMensaje("Usuario se actualizo con exito");
            e.setSuccess(true);
        }else{

            e.setMensaje("Ese usuario no existe, no se actualiza");
            e.setSuccess(false);
        }
        return e;

    }
    @DeleteMapping("/usuario/{id}")
    public Estatus borrar(@PathVariable String id){

        Estatus estatus = new Estatus();
        if(repoUsuario.findById(id).isPresent()){
            repoUsuario.deleteById(id);
            estatus.setSuccess(true);
            estatus.setMensaje("Usuario borrado con exito");

        }else{
            estatus.setSuccess(false);
            estatus.setMensaje("Ese usuario no existe");
        }
        return estatus;
    }


}