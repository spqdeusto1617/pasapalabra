
package com.pasapalabra.game.controllers;

import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;

import com.pasapalabra.game.model.DTO.UserDTO;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**Class that manages events from Registro.fxml
 * @author asier.gutierrez
 *
 */
public class SignInController extends Control implements Initializable {
	//Recuerda usar JavaDoc para cada método
	//Recuerda que tienes que añadir los listener y todo eso
	//Recuerda que tienes que enlazar esta clase con el código fxml
	//(Haz esta clase la clase controladora del código fxml)
	//Si tienes dudas mira en la clase main y eventoslogin

	//Otro stage para cargar la ventada de términos y condiciones de servivio de la app (EULA)
	//(Mirar eventosregistro.java IrATerminos para más info)


	@FXML public Pane panel;

	@FXML
	private  TextField txtNombreUsuario;
	
	@FXML
	private TextField txtCorreoUsuario;

	@FXML
	private PasswordField pflContrasenya;

	@FXML
	private PasswordField pfdRepetirContrasenya;

	@FXML
	private DatePicker dpFechaNacimiento;

	@FXML
	private ImageView ImgImagenUsuario;
	
	
	private String userImg;

	@FXML
	private CheckBox chkTerms;

	@FXML
	private Button btnCreate;

	private boolean datosCorrectos=false;

	@FXML
	private DatePicker fcNacimiento;

	private File file;

	private boolean Terminos=false;


	ContextMenu userNameValidator = new ContextMenu();


	ContextMenu userMailValidator = new ContextMenu();

	ContextMenu userPasswordValidator = new ContextMenu();

	ContextMenu userPasswordValidator2 = new ContextMenu();

	ContextMenu userDateValidator = new ContextMenu();

	ContextMenu userNameElegido = new ContextMenu();

	ContextMenu userMailElegido = new ContextMenu();

	private Date userDate;

	SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");


	@Override
	public void initialize(URL location, ResourceBundle resources) {


		//panel.getStylesheets().add("/css/application.css");
		userNameValidator.getItems().add(
				new MenuItem("The user name must be between 8 and 16 characters"));

		userMailValidator.getItems().add(
				new MenuItem("Email not acceptable"));

		userPasswordValidator.getItems().add(
				new MenuItem("Short password"));

		userPasswordValidator2.getItems().add(
				new MenuItem("Passwords don't match"));

		userDateValidator.getItems().add(
				new MenuItem("You must be 13 years or older to play"));

		userNameElegido.getItems().add(
				new MenuItem("The user name already exists"));

		userMailElegido.getItems().add(
				new MenuItem("The email already exists"));

		pfdRepetirContrasenya.focusedProperty().addListener(new ChangeListener<Boolean>() {


			//Focuslost del texto de: repetir contraseña, que comprueba que ambas contraseñas sean iguales
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				if(pflContrasenya.getText().compareTo(pfdRepetirContrasenya.getText())!=0){
					datosCorrectos=false;

					userPasswordValidator2.show(pfdRepetirContrasenya, Side.BOTTOM, 0, 0);

				}
				else{
					userPasswordValidator2.hide();
					datosCorrectos=true;
					chkTerms.setSelected(false);
				}


			}
		} );
		chkTerms.setDisable(true);


	}

	/**Method that check that the name introduced by the user has between 8 and 16 caracters
	 * @param event of the mouse
	 */
	public void checkUserName(Event event){
		if(userNameElegido.isShowing()){
			userNameElegido.hide();
		}
		if(txtNombreUsuario.getLength()<7||txtNombreUsuario.getLength()>15){
			datosCorrectos=false;
			userNameValidator.show(txtNombreUsuario, Side.BOTTOM, 0, 0);

		}
		else{
			userNameValidator.hide();
			datosCorrectos=true;
			chkTerms.setSelected(false);

		}

	}


	/**Method that check that the email introduced is correct or no. 
	 * @param event of the mouse
	 */
	public void checkUserMail(Event event){
		if(userMailElegido.isShowing()){
			userMailElegido.hide();
		}
		Pattern VALID_EMAIL_ADDRESS_REGEX = 
				Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{1,6}$", Pattern.CASE_INSENSITIVE);


		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(txtCorreoUsuario.getText());

		if(matcher.matches()){
			datosCorrectos=true;
			userMailValidator.hide();
			chkTerms.setSelected(false);


		}
		else{
			userMailValidator.show(txtCorreoUsuario, Side.BOTTOM, 0, 0);
			datosCorrectos=false;
		}
	}

	/**Method that check that the password length is longer than 8 characters. To the contrary, the
	 * user can not be created
	 * @param event of the mouse
	 */
	public void checkPass1(Event event){

		if(pflContrasenya.getText().length()<7){
			datosCorrectos=false;
			userPasswordValidator.show(pflContrasenya, Side.BOTTOM, 0, 0);
		}
		else{
			datosCorrectos=true;
			userPasswordValidator.hide();
			chkTerms.setSelected(false);

		}
	}
	
	public void checkPass2(Event event){

			}

	/**Button in order to create a user. Before the creation, needs to check that the information is complete.
	 * To the contrary, the button can not be pressed
	 * @param event of the mouse
	 */
	public void createUser(MouseEvent event){
		if(txtNombreUsuario.getText().length()<8||txtCorreoUsuario.getText().length()==0||pflContrasenya.getText().length()<8||pfdRepetirContrasenya.getText().length()<8||!pflContrasenya.getText().equals(pfdRepetirContrasenya.getText()))
		{
			datosCorrectos=false;
			btnCreate.setDisable(true);
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Something fails");

			alert.setContentText("There is a problem with the information introduced. Please, revise it before you sign in.");
			alert.initModality(Modality.APPLICATION_MODAL);


			alert.initOwner((Stage) ((Node) event.getSource()).getScene().getWindow());
			alert.showAndWait();
			chkTerms.setSelected(false);

		}
		else{
			try{
				UserDTO udto= new UserDTO(txtNombreUsuario.getText(), txtCorreoUsuario.getText(), userImg, userDate, 0, 0);

				Alert alert = new Alert(AlertType.CONFIRMATION);

				alert.setTitle("Create new user");

				alert.setHeaderText("Are you sure?");

				alert.setContentText("Are you sure you want to create the previous user?");


				alert.initModality(Modality.APPLICATION_MODAL);
				//Añade 'dueño'. (=La ventana sobre la cual se va a posicionar y la cual bloqueará)
				alert.initOwner((Stage) ((Node) event.getSource()).getScene().getWindow());

				Optional<ButtonType> result = alert.showAndWait();

				if (result.get() == ButtonType.OK){

					boolean finalResult = com.pasapalabra.game.service.ClientConnection.createUser(udto, pflContrasenya.getText());
					if(finalResult){
						Alert alert2 = new Alert(AlertType.INFORMATION);

						alert2.setTitle("User created succesfuly");

						alert2.setHeaderText("Operation succes");

						alert2.setContentText("The user has been created succesfuly");


						alert2.initModality(Modality.APPLICATION_MODAL);
						//Añade 'dueño'. (=La ventana sobre la cual se va a posicionar y la cual bloqueará)
						alert2.initOwner((Stage) ((Node) event.getSource()).getScene().getWindow());

						alert2.showAndWait();


						com.pasapalabra.game.utilities.WindowUtilities.windowTransition("LogIn", event);
					}
					else{
						
						Alert alert2 = new Alert(AlertType.ERROR);

						alert2.setTitle("Existing data");

						alert2.setHeaderText("The user already exists");

						alert2.setContentText("The user introduced already exists. Please, introduce another Username.");


						alert2.initModality(Modality.APPLICATION_MODAL);
						//Añade 'dueño'. (=La ventana sobre la cual se va a posicionar y la cual bloqueará)
						alert2.initOwner((Stage) ((Node) event.getSource()).getScene().getWindow());

						alert2.showAndWait();

						//userMailElegido.show(txtCorreoUsuario, Side.BOTTOM, 0, 0);

						chkTerms.setSelected(false);

						btnCreate.setDisable(true);

						userNameElegido.show(txtNombreUsuario, Side.BOTTOM, 0, 0);
					}
				}
			}catch(Exception a){
				Alert alert2 = new Alert(AlertType.ERROR);

				alert2.setTitle("Error while proccesing the creation of the user");

				alert2.setHeaderText("Error trying to create the user");

				alert2.setContentText("An error has occurred when the user was trying to create, please, revise the information and change it if it's neccesary.");


				alert2.initModality(Modality.APPLICATION_MODAL);
				//Añade 'dueño'. (=La ventana sobre la cual se va a posicionar y la cual bloqueará)
				alert2.initOwner((Stage) ((Node) event.getSource()).getScene().getWindow());

				alert2.showAndWait();
				
				a.printStackTrace();
			}

		}

	}


	/**Method that cancel the creation of the user
	 * @param event
	 */
	public void cancelUser(MouseEvent event){
		//Crea alerta de tipo confirmación
		Alert alert = new Alert(AlertType.CONFIRMATION);
		//Pone título
		alert.setTitle("Cancel the new user");
		//Pone cabecera
		alert.setHeaderText("Are you sure?");
		//Pone contenido
		alert.setContentText("Are you sure you want to discard the creation of the user?");

		//Añade modalidad
		alert.initModality(Modality.APPLICATION_MODAL);
		//Añade 'dueño'. (=La ventana sobre la cual se va a posicionar y la cual bloqueará)
		alert.initOwner((Stage) ((Node) event.getSource()).getScene().getWindow());
		//Mostrar y bloquear ventana padre hasta aceptar o rechazar.
		Optional<ButtonType> result = alert.showAndWait();

		//Ha pulsado ok?
		if (result.get() == ButtonType.OK){

			com.pasapalabra.game.utilities.WindowUtilities.windowTransition("LogIn", event);
		}

	}

	/**FileChooser for the user image of type jpg, gif, bmp o png
	 * @param event of the mouse
	 */
	public void changeImage(MouseEvent event){
		
//		Alert alert = new Alert(AlertType.INFORMATION);
//
//		alert.setTitle("Function not yet implemented.");
//		
//		alert.setHeaderText("Do not use this function");
//		 
//		alert.setContentText("This Function is not implemented, please, do not use it");
//
//		alert.showAndWait();
		
//		//Otro stage para cargar el filechooser
		Stage stageFilechooser = new Stage();
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(
				new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.jpeg"));

		try{
			//AnotherStage2: para que lance el filechooser
			file = fileChooser.showOpenDialog(stageFilechooser);
			if(file.length()>5242880){
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Tamaño excesivo");
				alert.initModality(Modality.APPLICATION_MODAL);

				//Elijo el dueño de la alerta (o la base) de la misma.
				alert.initOwner((Stage) ((Node) event.getSource()).getScene().getWindow());
				alert.setContentText("Parece que la imagen es muy grande, por favor, introduzca una imagen más pequeña (tamaño máximo: 5Mb).");

				alert.showAndWait();

			}
			else{
				String path="file:///"+file.getAbsolutePath();
				BufferedImage originalImage = ImageIO.read(file);
				
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ImageIO.write( originalImage, "jpg", baos );
				baos.flush();
				byte[] imageInByte = baos.toByteArray();
				baos.close();
				Image img = new Image(path);
				ImgImagenUsuario.setImage(img);
				//TODO: check this image
				userImg = Base64.encodeBase64URLSafeString(imageInByte);
			}
		}catch(NullPointerException a){
			
		}
		catch(Exception a){

			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error al leer la imagen");

			alert.setContentText("Se ha producido un error a la hora de leer la imagen. Por favor intenteló otra vez.");

			alert.showAndWait();

		}


	}

	/**Method that check that the birth date is correct. If the birth day is higher that the actual date
	 * it can no be introduced. 
	 * @param event of the mouse
	 */
	@SuppressWarnings("deprecation")
	public void checkDay(Event event){
		@SuppressWarnings("unused")
		String st = null;
		if(dpFechaNacimiento!=null){
			try {
				st = ft.format(ft.parse(this.dpFechaNacimiento.getValue().toString()));
			} catch (ParseException e) {

			}
		}
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		//get current date time with Date()
		Date date = new Date();
		Date date2 = Date.from(dpFechaNacimiento.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
		dateFormat.format(date);

		if(date.getYear()-date2.getYear()>13){
			datosCorrectos=true; 
			userDateValidator.hide();
			chkTerms.setSelected(false);

			//UserDTO dto=new UserDto(); No se si sería esto

			//Datos_usuario[3]=dateFormat.format(date2);

			userDate = date;

		}
		else{
			datosCorrectos=false;  
			userDateValidator.show(dpFechaNacimiento, Side.BOTTOM, -35, 0);
		}
	}


	/**Method in order to the user to read the conditions of the game
	 * @param event of the mouse
	 */

	public void goToTerms(MouseEvent event){
		//TODO: ¿path en el jar?

		String path="./resources/pdfEULA/EULA.pdf";
		
		//Usarlo si lo acabamos exportando a .jar
		//		try {
		//			path = ExportResource("src/pdfEULA/EULA.pdf");
		//		} catch (Exception e1) {
		//			// TODO Auto-generated catch block
		//			
		//		}
		File ficheroPDF=new File((path));


		try{	 
			new Thread(new Runnable() {  
				@Override  
				public void run() {  
					try {  
						Desktop.getDesktop().open(ficheroPDF);  
					} catch (IOException e) {  


					}  
				}  
			}).start(); 
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
			chkTerms.setDisable(false);
		}catch(Exception a){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Parece que hubo un error");
			alert.setContentText("No se pudo abrir el archivo con un visor de PDF. Por favor, comprueba si tiene algún visor de pdf instalado e intenteló de nuevo.");
			alert.initModality(Modality.APPLICATION_MODAL);

			//Elijo el dueño de la alerta (o la base) de la misma.
			alert.initOwner((Stage) ((Node) event.getSource()).getScene().getWindow());


			alert.showAndWait();	

			//				Stage anotherStage = new Stage();
			//				Pane page ;
			//				try {
			//					page = FXMLLoader.load(Main.class.getResource("/fxml/EULA.fxml"));
			//					 Scene anotherScene = new Scene(page);
			//			            anotherStage.setScene(anotherScene);
			//			            anotherStage.show();
			//				} catch (IOException e) {
			//					
			//					e.printStackTrace();
			//				}// FXML for second stage
			//	           // Parent anotherRoot = page.load();
			//	           

		}

	}


	/**
	 * Export a resource embedded into a Jar file to the local file path.
	 *
	 * @param resourceName ie.: "/SmartLibrary.dll"
	 * @return The path to the exported resource
	 * @throws Exception
	 */
	@SuppressWarnings("resource")
	static public String ExportResource(String resourceName) throws Exception {
		InputStream stream = null;
		OutputStream resStreamOut = null;
		String jarFolder;
		try {
			stream = SignInController.class.getResourceAsStream(resourceName);//note that each / is a directory down in the "jar tree" been the jar the root of the tree
			if(stream == null) {
				throw new Exception("Cannot get resource \"" + resourceName + "\" from Jar file.");
			}

			int readBytes;
			byte[] buffer = new byte[4096];
			jarFolder = new File(SignInController.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile().getPath().replace('\\', '/');
			resStreamOut = new FileOutputStream(jarFolder + resourceName);
			while ((readBytes = stream.read(buffer)) > 0) {
				resStreamOut.write(buffer, 0, readBytes);
			}
		} catch (Exception ex) {
			throw ex;
		} finally {
			//            stream.close();
			//            resStreamOut.close();
		}

		return jarFolder + resourceName;
	}



	/**Methood that check if the user has accepted the conditions. If he/she doesn't accept, the button
	 * to create the user will be disable. 
	 * @param event: el evento del pulsador
	 */
	public void acceptTerms(Event event){
		if(txtNombreUsuario.getText().length()==0||txtCorreoUsuario.getText().length()==0||pflContrasenya.getText().length()==0||pfdRepetirContrasenya.getText().length()==0||dpFechaNacimiento==null)
		{
			datosCorrectos=false;
			chkTerms.setSelected(false);
		}

		if(chkTerms.isSelected()&&datosCorrectos==true){
			btnCreate.setDisable(false);

		}
		else{
			btnCreate.setDisable(true);
		}


	}
}