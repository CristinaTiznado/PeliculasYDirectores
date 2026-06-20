package com.cristina_tiznado.demo.controladores;

import java.text.Normalizer;
import java.util.HashMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControladorPeliculas {
    private static HashMap<String, String> listaPeliculas = new HashMap<String, String>();

    public ControladorPeliculas() {
        listaPeliculas.put("Winnie the Pooh", "Don Hall");
        listaPeliculas.put("El zorro y el sabueso", "Ted Berman");
        listaPeliculas.put("Tarzán", "Kevin Lima");
        listaPeliculas.put("Mulán", "Barry Cook");
        listaPeliculas.put("Oliver", "Kevin Lima");
        listaPeliculas.put("Big Hero 6", "Don Hall");

    }

    // Método para normalizar
    private String normalizar(String texto){
        String normalizado = Normalizer.normalize(texto, Normalizer.Form.NFD);
        return normalizado.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "").toLowerCase();

    }

    @GetMapping("/peliculas")
    public String obtenerTodasLasPeliculas(){
        String str = "";

        for (String pelicula : listaPeliculas.keySet()) {
            String director = listaPeliculas.get(pelicula);

            str += "Pelicula: " + pelicula + ", Director: " + director + "<br>";
        }

        return str;
    }

    @GetMapping("/peliculas/{nombre}")
    public String obtenerPeliculaPorNombre(@PathVariable("nombre") String nombre){
        String str = "";

        for (String pelicula : listaPeliculas.keySet()) {
            String director = listaPeliculas.get(pelicula);

            if(normalizar(nombre).equalsIgnoreCase(normalizar(pelicula))){
                str = "Pelicula: " + pelicula + ", Director: " + director;
                return str;
            }
        }

        return "La película no se encuentra en nuestra lista.";
    }

    @GetMapping("/peliculas/director/{nombre}")
    public String obtenerPeliculasPorDirector(@PathVariable("nombre") String nombre){
        String str1 = "";
        String str2 = "--------------<br>";
        String str3 = "";

        for (String pelicula : listaPeliculas.keySet()) {
            String director = listaPeliculas.get(pelicula);

            if(normalizar(nombre).equalsIgnoreCase(normalizar(director))){
                str1 = "Director: " + director + "<br>";
                str3 += "Pelicula: " + pelicula + "<br>";
            }
        }

        if(str3.isEmpty()){
            return "No contamos con películas con ese director en nuestra lista.";
        } else {
            return str1 + str2 + str3;
        }
    }
}
