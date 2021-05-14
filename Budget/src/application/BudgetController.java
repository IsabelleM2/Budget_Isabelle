package application;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class BudgetController implements Initializable{

	@FXML
	private TableColumn<Budget, Double> mpColumn;

	@FXML
	private TextField txtDate;

	@FXML
	private TextField txtMP;

	@FXML
	private Button btnClear;

	@FXML
	private TableView<Budget> budgetTable;

	@FXML
	private Button btnEffacer;

	@FXML
	private TableColumn<Budget, String> usageColumn;

	@FXML
	private TextField txtBmontant;

	@FXML
	private ComboBox<String> cboUsage;
	
	@FXML
    private TextArea BudgetR;

	@FXML
	private TableColumn<Budget, Double>bmontantColumn;

	@FXML
	private Button btnModifier;

	@FXML
	private TableColumn<Budget, Double> dateColumn;

	@FXML
	private Button btnAjouter;

	private ObservableList<String> list=(ObservableList<String>) FXCollections.observableArrayList("Général", "Ventes & Marketing", "Recherche & développement");


	public ObservableList<Budget> budgetData=FXCollections.observableArrayList();

	public ObservableList<Budget> getbudgetData()
	{
		return budgetData;
	}


	@Override public void initialize(URL location, ResourceBundle resources) {
		cboUsage.setItems(list); dateColumn.setCellValueFactory(new PropertyValueFactory<>("date")); 
		bmontantColumn.setCellValueFactory(new PropertyValueFactory<>("budget")); 
		usageColumn.setCellValueFactory(new PropertyValueFactory<>("usage")); 
		mpColumn.setCellValueFactory(new PropertyValueFactory<>("mp"));
		budgetTable.setItems(budgetData); 

		btnModifier.setDisable(true);
		btnEffacer.setDisable(true);
		btnClear.setDisable(true);

		showBudget(null);
		//mettre a jour
		budgetTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)-> showBudget(newValue));   
	}

	
	
	
	public void verfiNum()
	{
		txtDate.textProperty().addListener((observable,oldValue,newValue)->
		{
			if(!newValue.matches("^[0-9](\\.[0-9]+)?$"))
			{
				txtDate.setText(newValue.replaceAll("[^\\d*]", ""));

			}
		});
	}
	
	
	
	private boolean noEmptyInput()
	{
		String errorMessage="";
		if(txtDate.getText().trim().equals("")) {
			errorMessage+="Le champ Date ne doit pas etre vide \n";

		}
		if(txtBmontant.getText().trim().equals("")) {
			errorMessage+="Le champ Budget ne doit pas etre vide \n";

		}
		if(txtMP.getText().trim().equals("")) {
			errorMessage+="Le champ Montant Prise ne doit pas etre vide \n";
		}
		if(cboUsage.getValue()==null) {
			errorMessage+="Le champ Usage ne doit pas etre vide \n";

		}
		if(errorMessage.length()==0)
		{
			return true;
		}
		else
		{
			Alert alert=new Alert(AlertType.ERROR);
			alert.setTitle("Champs Manquants");
			alert.setHeaderText("Completer les champs manquants");
			alert.setContentText(errorMessage);
			alert.showAndWait();
			return false;
		}
	}







	//Ajouter

	@FXML
	public void ajouter()
	{
		
			if(noEmptyInput())
			{
		Budget tmp=new Budget();
		tmp= new Budget();
		tmp.setDate(Double.parseDouble(txtDate.getText()));
		tmp.setBudget(Double.parseDouble(txtBmontant.getText()));
		tmp.setMp(Double.parseDouble(txtMP.getText()));
		tmp.setUsage(cboUsage.getValue());
		budgetData.add(tmp);
		clearFields();
	}
	}

	@FXML
	public void clearFields()
	{
		cboUsage.setValue(null);
		txtBmontant.setText("");
		txtMP.setText("");
		txtDate.setText("");
	}

	
	public void showBudget(Budget budget)
	{
		if(budget !=null)
		{
			cboUsage.setValue(budget.getUsage());
			txtDate.setText(Double.toString(budget.getDate()));    	
			txtMP.setText(Double.toString(budget.getMp()));
			txtBmontant.setText(Double.toString(budget.getBudget()));
			btnModifier.setDisable(false);
			btnEffacer.setDisable(false);
			btnClear.setDisable(false);

		}
		else 
		{
			clearFields();
		}
	}
	@FXML
	public void updateBudget()
	{
	if(noEmptyInput())
			{
			Budget budget=budgetTable.getSelectionModel().getSelectedItem();
	
			budget.setBudget(Double.parseDouble(txtBmontant.getText()));
			budget.setMp(Double.parseDouble(txtMP.getText()));
			budget.setDate(Double.parseDouble(txtDate.getText()));
			budget.setUsage(cboUsage.getValue());
			budgetTable.refresh();
			}
	}
	@FXML 
	public void deleteBudget()
	{
		int selectedIndex = budgetTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >=0)
		{
			Alert alert=new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Effacer");
			alert.setContentText("confirmer la suppression");
			Optional<ButtonType> result=alert.showAndWait();
			if(result.get()==ButtonType.OK)						
				budgetTable.getItems().remove(selectedIndex);
		}
	}

@FXML
public void Substraction()
{
	double num1= Double.parseDouble(txtBmontant.getText());
	double num2= Double.parseDouble(txtMP.getText());
	double result= num1-num2;
	BudgetR.setText(Double.toString(result));
}






	//SAUVERGARDE DE DONNEES

	public File getBudgetFilePath()
	{
		Preferences prefs =Preferences.userNodeForPackage(Main.class);
		String filePath =prefs.get("filePath", null);

		if(filePath !=null)
		{
			return new File(filePath);
		}
		else
		{
			return null;
		}
	}

	//Attribuer un chemin de fichiers
	public void setBudgetFilePath(File file)
	{
		Preferences prefs =Preferences.userNodeForPackage(Main.class);
		if (file !=null)
		{
			prefs.put("filePath", file.getPath());
		}
		else
		{
			prefs.remove("filePath");
		}
	}

	public void loadBudgetDataFromFile (File file)
	{
		try {
			JAXBContext context= JAXBContext.newInstance(BudgetListWrapper.class);
			Unmarshaller um=context.createUnmarshaller();

			BudgetListWrapper wrapper = (BudgetListWrapper) um.unmarshal(file);
			budgetData.clear();
			budgetData.addAll(wrapper.getBudget());
			setBudgetFilePath(file);
			
			Stage pStage=(Stage) budgetTable.getScene().getWindow();
			pStage.setTitle(file.getName());
			
		}catch (Exception e) {
			Alert alert= new Alert(AlertType.ERROR);
			alert.setTitle("Erreur");
			alert.setHeaderText("les donnees n'ont pas ete trouvees");
			alert.setContentText("Les donnees ne pouvaient pas etre trouvees dans le fichier");
			alert.showAndWait();
		}
	}

	//Prendre les donnees de type JavaFx et les convertrir en type XML
	public void saveBudgetDataToFile (File file) {
		try {
			JAXBContext context = JAXBContext.newInstance(BudgetListWrapper.class);
			Marshaller m=context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			BudgetListWrapper wrapper = new BudgetListWrapper();
			wrapper.setBudget(budgetData);

			m.marshal(wrapper, file);

			//sauvegarde dans le registre
			setBudgetFilePath(file);

			Stage pStage=(Stage) budgetTable.getScene().getWindow();
			pStage.setTitle(file.getName());
			


		}catch (Exception e) {

			Alert alert= new Alert(AlertType.ERROR);
			alert.setTitle("Erreur");
			alert.setHeaderText("Donnees non sauvegardees");
			alert.setContentText("Les donnees ne pouvaient pas etre sauvegardees dans le fichier");
			alert.showAndWait();

		}
	}

	//Commencer un nouveau
	@FXML
	private void handleNew()
	{
		getbudgetData().clear();
		setBudgetFilePath(null);
	}


	//FileChooser

	@FXML
	private void handleOpen() {
		FileChooser fileChooser =new FileChooser();

		FileChooser.ExtensionFilter extFilter =new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);

		File file =fileChooser.showOpenDialog(null);

		if (file !=null) {
			loadBudgetDataFromFile(file);
		}
	}


	//Sauvegarde le fichier correspondant a l'etudiant actif

	@FXML 
	private void handleSave() {
		File budgetFile =getBudgetFilePath();
		if(budgetFile != null) {
			saveBudgetDataToFile(budgetFile);

		}else {
			handleSaveAs();
		}
	}
	
	
	
	



	//Ouvrir le FileChooser pour chemin

	@FXML
	private void handleSaveAs() {
		FileChooser fileChooser =new FileChooser();

		FileChooser.ExtensionFilter extFilter =new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);

		File file =fileChooser.showSaveDialog(null);

		if (file != null) {
			if(!file.getPath().endsWith(".xml")) {
				file=new File(file.getPath()+ ".xml");
			}
			saveBudgetDataToFile(file);
		}

	}
























}
