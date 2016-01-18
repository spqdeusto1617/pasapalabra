package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Control;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class EventosEstadisticas extends ClaseExtensora implements Initializable {

    @FXML
    private Text textoESPanel;

    @FXML
    private Text textoPlus;

    @FXML
    private Text textoMiPerfil;

    @FXML
    private ImageView logopsp;

    @FXML
    private Text textoLogeadoComo;

    @FXML
    private Text textoCerrarSesion;

    @FXML
    private Rectangle rectanguloPanel;

    @FXML
    private Rectangle rectanguloCerrarSesion;

    @FXML
    private Rectangle rectanguloAmigos;

    @FXML
    private Text textoJugar;

    @FXML
    private Rectangle rectanguloMiPerfil;

    @FXML
    private Rectangle rectanguloJugar;

    @FXML
    private Text textoAmigos;

    @FXML
    private Circle circuloPanel;

    @FXML
    private Text textoNombreDeUsuario;

    @FXML
    private Text textoEstadisticas;

    @FXML
    private ImageView imagenAvatar;

    @FXML
    private Rectangle rectanguloEstadisticas;

    @FXML
    private Pane panel;

    @FXML
    private Circle circuloPlus;
    
    public static ArrayList<String> Datos_Usuario_Estadisticas=new ArrayList<String>();;
    /*Posiciones:
     * 0: partidas ganadas
     * 1: partidas perdidas
     * 2:partidas empatadas
     * 3:partidas totales jugadas
     * 4: posición en el ranking
     */
    @FXML
    void btnJugar(ActionEvent event) {

    }

    @FXML
    void btnMiPerfil(ActionEvent event) {

    }

    @FXML
    void entrado(ActionEvent event) {

    }

    @FXML
    void salido(ActionEvent event) {

    }

    @FXML
    void btnEstadisticas(ActionEvent event) {

    }


    @FXML
    void btnAmigos(ActionEvent event) {

    }

    @FXML
    void btnCerrarSesion(ActionEvent event) {

    }

    @FXML
    void esPanel(ActionEvent event) {

    }


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		for (String string : Datos_Usuario_Estadisticas) {
			System.out.println(string);
		}
	}

}

