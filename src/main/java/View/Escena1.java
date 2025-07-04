package View;

import Modelo.Pelicula;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Escena1 {
    private ScrollPane scrollPadre;
    private String[] lista = {"Video 1", "Video 2","Video 3","Video 4","Video 5"};
    private VBox scrollPane;
    private Label titulo;
    private VBox contenedorPrincipal;
    private Stage stage;
    private HBox contenedorTitulo;
    private double xOffset = 0.0;
    private double scrollSpeed = 1.5;
    private HBox labelsContainer;
    private Pelicula peliculaSeleccionada;
    private Scene scene;

    public void setPeliculaSeleccionada(Pelicula peliculaSeleccionada) {
        this.peliculaSeleccionada = peliculaSeleccionada;
    }

    public Escena1(Stage stage, List<Pelicula> peliculas){
        this.stage = stage;
        contenedorPrincipal = getContenedorPrincipal();

        scrollPadre = new ScrollPane();
        scrollPadre.setId("scrollPadre");
        scrollPadre.setFitToWidth(true);
        contenedorPrincipal.getChildren().add(contenedorTitulo);
        scrollPadre.setContent(contenedorPrincipal);
        scrollPadre.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);


        List<String> generos = new ArrayList<>();
        //Obtengo los generos de todas las peliculas
        for(Pelicula pelicula: peliculas){
            if(!generos.contains(pelicula.getGenero())){
                generos.add(pelicula.getGenero());
            }
        }

        //Creo un ciclo para enviar las canciones de acuerdo a su genero
        for (String genero : generos) {
            // Lista de películas que coinciden con el género
            List<Pelicula> peliculasEncontradas = new ArrayList<>();

            // Recorre la lista de películas
            for (Pelicula pelicula : peliculas) {
                // Comprueba si el género de la película coincide con la palabra actual
                if (pelicula.getGenero().equals(genero)) {
                    peliculasEncontradas.add(pelicula);
                }
            }
            contenedorPrincipal.getChildren().add(createScrollPane(peliculasEncontradas));
        }


        scene = new Scene(scrollPadre, 800, 600);
        stage.setScene(scene);

        String cssPath = "/Estilos.css"; // Reemplaza con la ruta correcta
        scene.getStylesheets().add(cssPath);
    }

    public VBox createScrollPane(List<Pelicula> peliculas) {
        VBox container = new VBox();
        container.setPrefWidth(950);
        container.setPadding(new Insets(10, 40, 0, 40));

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true); // Establecer en false para que no se expanda más allá del ancho del padre
        scrollPane.setId("scrollPane");
        scrollPane.setPrefHeight(189);

        labelsContainer = new HBox(12);
        labelsContainer.setId("ColorFondoNegro");
        labelsContainer.setAlignment(Pos.TOP_LEFT); // Alinea el contenido al principio

        //obtengo el genero que recibi xd y pues lo obtengo de uno de los objetos que recibí vea, de onde mas pues.
        String genero = peliculas.getFirst().getGenero();

        Label tituloV = new Label(genero);
        tituloV.setId("EstiloGeneroEscena1");

        for (Pelicula pelicula : peliculas) {
            ImageView imageView = new ImageView(new Image(pelicula.getImagen(), 275, 174, false, true));
            labelsContainer.getChildren().add(imageView);
            labelsContainer.setStyle("-fx-border-color: black; ");
            imageView.setOnMouseClicked(e->{
                peliculaSeleccionada = pelicula;
            });
        }
        labelsContainer.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            xOffset = event.getSceneX();
        });

        labelsContainer.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
            double deltaX = event.getSceneX() - xOffset;
            xOffset = event.getSceneX();
            scrollPane.setHvalue(scrollPane.getHvalue() - deltaX * scrollSpeed / labelsContainer.getWidth());
        });

        labelsContainer.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
            labelsContainer.setStyle("-fx-cursor: hand;");
        });

        labelsContainer.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
            labelsContainer.setStyle("-fx-cursor: default;");
        });

        // Establece un ChangeListener para ajustar el maxWidth del VBox al ancho del ScrollPane
        scrollPadre.widthProperty().addListener((observable, oldValue, newValue) -> {
            container.setPrefWidth(newValue.doubleValue());
        });

        scrollPane.setContent(labelsContainer);
        container.getChildren().addAll(tituloV, scrollPane);

        return container;
    }

    public VBox createScrollPaneMiLista(List<Pelicula> peliculas) {
        VBox container = new VBox();
        container.setPrefWidth(950);
        container.setPadding(new Insets(10, 40, 0, 40));

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true); // Establecer en false para que no se expanda más allá del ancho del padre
        scrollPane.setId("scrollPane");
        scrollPane.setPrefHeight(189);

        labelsContainer = new HBox(12);
        labelsContainer.setId("ColorFondoNegro");
        labelsContainer.setAlignment(Pos.TOP_LEFT); // Alinea el contenido al principio

        //obtengo el genero que recibi xd y pues lo obtengo de uno de los objetos que recibí vea, de onde mas pues.
        Label tituloV = new Label("Mi lista");
        tituloV.setId("EstiloGeneroEscena1");


        if(peliculas.size() > 1){
            contenedorPrincipal.getChildren().remove(1);
            for (Pelicula pelicula : peliculas) {
                ImageView imageView = new ImageView(new Image(pelicula.getImagen(), 275, 174, false, true));
                labelsContainer.getChildren().add(imageView);
                labelsContainer.setStyle("-fx-border-color: black; ");
                imageView.setOnMouseClicked(e->{
                    peliculaSeleccionada = pelicula;
                });
            }
            scrollPane.setContent(labelsContainer);
        }else {
            for (Pelicula pelicula : peliculas) {
                ImageView imageView = new ImageView(new Image(pelicula.getImagen(), 275, 174, false, true));
                labelsContainer.getChildren().add(imageView);
                labelsContainer.setStyle("-fx-border-color: black; ");
                imageView.setOnMouseClicked(e->{
                    peliculaSeleccionada = pelicula;
                });
            }
            scrollPane.setContent(labelsContainer);
        }


        labelsContainer.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            xOffset = event.getSceneX();
        });

        labelsContainer.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
            double deltaX = event.getSceneX() - xOffset;
            xOffset = event.getSceneX();
            scrollPane.setHvalue(scrollPane.getHvalue() - deltaX * scrollSpeed / labelsContainer.getWidth());
        });

        labelsContainer.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
            labelsContainer.setStyle("-fx-cursor: hand;");
        });

        labelsContainer.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
            labelsContainer.setStyle("-fx-cursor: default;");
        });

        // Establece un ChangeListener para ajustar el maxWidth del VBox al ancho del ScrollPane
        scrollPadre.widthProperty().addListener((observable, oldValue, newValue) -> {
            container.setPrefWidth(newValue.doubleValue());
        });


        container.getChildren().addAll(tituloV, scrollPane);

        contenedorPrincipal.getChildren().add(1, container);
        return container;
    }

    public VBox getContenedorPrincipal() {
        contenedorPrincipal = new VBox();
        contenedorPrincipal.setId("ColorFondoNegro");
        contenedorTitulo = new HBox();
        titulo = new Label("OnyxFlix");
        titulo.setId("EstiloTituloEscena1");
        contenedorTitulo.getChildren().add(titulo);
        contenedorTitulo.setPadding(new Insets(15,0,0,15));
        return contenedorPrincipal;
    }

    public Scene getScene(){
        return scene;
    }

    public ScrollPane getScrollPadre() {
        return scrollPadre;
    }

    public Pelicula getPeliculaSeleccionada() {
        return peliculaSeleccionada;
    }

    public VBox createScrollPaneRecientesReproducidas(List<Pelicula> peliculas) {
        VBox container = new VBox();
        container.setPrefWidth(950);
        container.setPadding(new Insets(10, 40, 0, 40));

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true); // Establecer en false para que no se expanda más allá del ancho del padre
        scrollPane.setId("scrollPane");
        scrollPane.setPrefHeight(189);

        labelsContainer = new HBox(12);
        labelsContainer.setId("ColorFondoNegro");
        labelsContainer.setAlignment(Pos.TOP_LEFT); // Alinea el contenido al principio

        //obtengo el genero que recibi xd y pues lo obtengo de uno de los objetos que recibí vea, de onde mas pues.
        Label tituloV = new Label("Recientemente vistas");
        tituloV.setId("EstiloGeneroEscena1");


        if(peliculas.size() > 1){
            contenedorPrincipal.getChildren().removeLast();
            for (Pelicula pelicula : peliculas) {
                ImageView imageView = new ImageView(new Image(pelicula.getImagen(), 275, 174, false, true));
                labelsContainer.getChildren().add(imageView);
                labelsContainer.setStyle("-fx-border-color: black; ");
                imageView.setOnMouseClicked(e->{
                    peliculaSeleccionada = pelicula;
                });
            }
            scrollPane.setContent(labelsContainer);
        }else {
            for (Pelicula pelicula : peliculas) {
                ImageView imageView = new ImageView(new Image(pelicula.getImagen(), 275, 174, false, true));
                labelsContainer.getChildren().add(imageView);
                labelsContainer.setStyle("-fx-border-color: black; ");
                imageView.setOnMouseClicked(e->{
                    peliculaSeleccionada = pelicula;
                });
            }
            scrollPane.setContent(labelsContainer);
        }


        labelsContainer.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            xOffset = event.getSceneX();
        });

        labelsContainer.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
            double deltaX = event.getSceneX() - xOffset;
            xOffset = event.getSceneX();
            scrollPane.setHvalue(scrollPane.getHvalue() - deltaX * scrollSpeed / labelsContainer.getWidth());
        });

        labelsContainer.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
            labelsContainer.setStyle("-fx-cursor: hand;");
        });

        labelsContainer.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
            labelsContainer.setStyle("-fx-cursor: default;");
        });

        // Establece un ChangeListener para ajustar el maxWidth del VBox al ancho del ScrollPane
        scrollPadre.widthProperty().addListener((observable, oldValue, newValue) -> {
            container.setPrefWidth(newValue.doubleValue());
        });


        container.getChildren().addAll(tituloV, scrollPane);

        contenedorPrincipal.getChildren().add(container);
        return container;
    }
}
