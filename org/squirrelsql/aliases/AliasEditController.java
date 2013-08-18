package org.squirrelsql.aliases;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.squirrelsql.AppState;
import org.squirrelsql.drivers.DriverCell;
import org.squirrelsql.drivers.DriversFilteredPref;
import org.squirrelsql.drivers.DriversManager;
import org.squirrelsql.drivers.SQLDriver;
import org.squirrelsql.services.*;

public class AliasEditController
{
   private final TreePositionCtrl _treePositionCtrl;
   private Pref _pref = new Pref(getClass());

   private I18n _i18n = new I18n(this.getClass());
   private final AliasEditView _aliasEditView;
   private final Stage _dialog;
   private Alias _alias = new Alias();
   private boolean _ok = false;


   public AliasEditController(boolean parentNodeSelected, boolean parentAllowsChildren)
   {
      FxmlHelper<AliasEditView> fxmlHelper = new FxmlHelper<>(AliasEditView.class);

      _aliasEditView = fxmlHelper.getView();

      _treePositionCtrl = new TreePositionCtrl(_aliasEditView.treePositionViewController, parentNodeSelected, parentAllowsChildren);

      String title = _i18n.t("title.new.alias");
      _aliasEditView.lblChangeDriver.setText(title);

      initListeners();

      ObservableList<SQLDriver> drivers = new DriversManager().getDrivers(DriversFilteredPref.isFiltered());

      _aliasEditView.cboDriver.getItems().addAll(drivers);

      _aliasEditView.cboDriver.setCellFactory(cf -> new DriverCell());

      if(0 < drivers.size())
      {
         _aliasEditView.cboDriver.getSelectionModel().select(0);
      }


      initListener();


      _dialog = new Stage();
      _dialog.setTitle(title);
      _dialog.initModality(Modality.WINDOW_MODAL);
      _dialog.initOwner(AppState.get().getPrimaryStage());
      Region region = fxmlHelper.getRegion();
      _dialog.setScene(new Scene(region));

      GuiUtils.makeEscapeClosable(region);

      new StageDimensionSaver("aliasedit", _dialog, _pref, region.getPrefWidth(), region.getPrefHeight(), _dialog.getOwner());

      _dialog.showAndWait();
   }

   private void initListener()
   {
      _aliasEditView.lnkSetToSampleUrl.setOnAction(e -> onSetSampleURL());

      _aliasEditView.btnClose.setOnAction(e -> onClose());
      _aliasEditView.btnOk.setOnAction(e -> onOk());

      _aliasEditView.chkUserEmpty.setOnAction(this::onAjustUserNullEmpty);
      _aliasEditView.chkUserNull.setOnAction(this::onAjustUserNullEmpty);

      _aliasEditView.chkPasswordEmpty.setOnAction(this::onAjustPwdNullEmpty);
      _aliasEditView.chkPasswordNull.setOnAction(this::onAjustPwdNullEmpty);


   }

   private void onAjustUserNullEmpty(ActionEvent e)
   {
      _adjustEmptyNull(e, _aliasEditView.chkUserNull, _aliasEditView.chkUserEmpty, _aliasEditView.txtUserName);
   }

   private void onAjustPwdNullEmpty(ActionEvent e)
   {
      _adjustEmptyNull(e, _aliasEditView.chkPasswordNull, _aliasEditView.chkPasswordEmpty, _aliasEditView.txtPassword);
   }

   private void _adjustEmptyNull(ActionEvent e, CheckBox chkNull, CheckBox chkEmpty, TextField txt)
   {
      if(e.getSource() == chkEmpty)
      {
         if (chkEmpty.isSelected())
         {
            chkNull.setSelected(false);
         }
      }
      else
      {
         if (chkNull.isSelected())
         {
            chkEmpty.setSelected(false);
         }
      }

      if(chkNull.isSelected() || chkEmpty.isSelected())
      {
         txt.setDisable(true);
      }
      else
      {
         txt.setDisable(false);
      }
   }

   private void onOk()
   {
      if(Utils.isEmptyString(_aliasEditView.txtName.getText()))
      {
         FXMessageBox.showInfoOk(_dialog, _i18n.t("alias.edit.no.name"));
         return;
      }


      if(Utils.isEmptyString(_aliasEditView.txtUrl.getText()))
      {
         FXMessageBox.showInfoOk(_dialog, _i18n.t("alias.edit.no.url"));
         return;
      }


      if(   false == _aliasEditView.chkUserEmpty.isSelected()
         && false == _aliasEditView.chkUserNull.isSelected()
         && Utils.isEmptyString(_aliasEditView.txtUserName.getText()))
      {
         FXMessageBox.showInfoOk(_dialog, _i18n.t("alias.edit.no.user"));
         return;
      }

      if(_aliasEditView.chkSavePassword.isSelected()
         && false == _aliasEditView.chkPasswordEmpty.isSelected()
         && false == _aliasEditView.chkPasswordNull.isSelected()
         && Utils.isEmptyString(_aliasEditView.txtPassword.getText()))
      {
         FXMessageBox.showInfoOk(_dialog, _i18n.t("alias.edit.no.password"));
         return;
      }

      SQLDriver sqlDriver = _aliasEditView.cboDriver.getSelectionModel().getSelectedItem();
      if(false == sqlDriver.isLoaded() && FXMessageBox.NO.equals(FXMessageBox.showYesNo(_dialog, _i18n.t("alias.edit.driver.not.loaded.continue"))))
      {
         return;
      }

      _alias.setName(_aliasEditView.txtName.getText().trim());
      _alias.setDriverId(sqlDriver.getId());
      _alias.setUrl(_aliasEditView.txtUrl.getText().trim());

      _alias.setUserName(_aliasEditView.txtUserName.getText().trim());
      _alias.setUserNull(_aliasEditView.chkUserNull.isSelected());
      _alias.setUserEmptyString(_aliasEditView.chkUserEmpty.isSelected());

      _alias.setSavePassword(_aliasEditView.chkSavePassword.isSelected());

      _alias.setPassword(_aliasEditView.txtPassword.getText().trim());
      _alias.setPasswordNull(_aliasEditView.chkPasswordNull.isSelected());
      _alias.setPasswordEmptyString(_aliasEditView.chkPasswordEmpty.isSelected());

      _alias.setAutoLogon(_aliasEditView.chkAutoLogon.isSelected());
      _alias.setConnectAtStartUp(_aliasEditView.chkConnectAtStartUp.isSelected());

      _ok = true;

      _dialog.close();
   }

   private void onClose()
   {
      _dialog.close();
   }

   private void onSetSampleURL()
   {
      SQLDriver selectedItem = _aliasEditView.cboDriver.getSelectionModel().getSelectedItem();

      if(null != selectedItem)
      {
         _aliasEditView.txtUrl.setText(selectedItem.getUrl());
      }
   }

   private void initListeners()
   {
      _aliasEditView.chkSavePassword.setOnAction(e -> onSavePassword());
      onSavePassword();

   }

   private void onSavePassword()
   {
      _aliasEditView.lblPassword.setDisable(true);
      _aliasEditView.txtPassword.setDisable(true);
      _aliasEditView.chkAutoLogon.setDisable(true);
      _aliasEditView.chkConnectAtStartUp.setDisable(true);
      _aliasEditView.chkPasswordNull.setDisable(true);
      _aliasEditView.chkPasswordEmpty.setDisable(true);

      if(_aliasEditView.chkSavePassword.isSelected())
      {
         _aliasEditView.lblPassword.setDisable(false);
         _aliasEditView.txtPassword.setDisable(false);
         _aliasEditView.chkAutoLogon.setDisable(false);
         _aliasEditView.chkConnectAtStartUp.setDisable(false);
         _aliasEditView.chkPasswordNull.setDisable(false);
         _aliasEditView.chkPasswordEmpty.setDisable(false);
      }
   }

   public Alias getAlias()
   {
      return _alias;
   }

   public boolean isOk()
   {
      return _ok;
   }

   public TreePositionCtrl getTreePositionCtrl()
   {
      return _treePositionCtrl;
   }
}
