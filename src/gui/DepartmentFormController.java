package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentFormController implements Initializable {
	
	private Department entity;
	
	private DepartmentService service;
	
	private List<DataChangeListener> dataChangeListers = new ArrayList<>();
	
	@FXML
	private TextField txtID;
	
	@FXML
	private TextField txtName;
	
	@FXML
	private Label labErrorName;
	
	@FXML
	private Button butSave;
	
	@FXML
	private Button butCancel;
	
	public void setDepartment(Department entity) {
		this.entity = entity;
	}
	
	public void setDepartmentService(DepartmentService service) {
		this.service = service;
	}
	
	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListers.add(listener);
	}
	
	@FXML
	public void onButSaveAction(ActionEvent event) {
		if (entity == null) {
			throw new IllegalStateException("Entity was Null.");
		}
		if (service == null) {
			throw new IllegalStateException("Service was Null.");
		}
		try {
			entity = getFormData();
			service.saveOrUpdate(entity);
			notifyDataChangeListeners();
			Utils.currentStage(event).close();
		}
		catch (DbException e) {
			Alerts.showAlert("ERROR Saving Object.", null, e.getMessage(), AlertType.ERROR);
		}
	}
	
	private void notifyDataChangeListeners() {
		for (DataChangeListener listener : dataChangeListers) {
			listener.onDataChanged();
		}
	}

	private Department getFormData() {
		Department obj = new Department();
		
		obj.setId(Utils.tryParseToInt(txtID.getText()));
		obj.setName(txtName.getText());
		
		return obj;
	}

	@FXML
	public void onButCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}
	
	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtID);
		Constraints.setTextFieldMaxLength(txtName, 30);
	}
	
	public void updateFormData() {
		if (entity == null) {
			throw new IllegalStateException("Entity was Null.");
		}
		txtID.setText(String.valueOf(entity.getId()));
		txtName.setText(entity.getName());
	}

}
