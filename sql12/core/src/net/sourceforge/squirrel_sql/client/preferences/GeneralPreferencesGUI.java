package net.sourceforge.squirrel_sql.client.preferences;

import com.jidesoft.swing.MultilineLabel;
import net.sourceforge.squirrel_sql.client.ApplicationArguments;
import net.sourceforge.squirrel_sql.client.util.ApplicationFiles;
import net.sourceforge.squirrel_sql.fw.util.LocaleUtils;
import net.sourceforge.squirrel_sql.fw.util.StringManager;
import net.sourceforge.squirrel_sql.fw.util.StringManagerFactory;
import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

final class GeneralPreferencesGUI extends JPanel
{

   /** Internationalized strings for this class. */
   private static final StringManager s_stringMgr =
         StringManagerFactory.getStringManager(GeneralPreferencesGUI.class);

   private JRadioButton _tabbedStyle = new JRadioButton(s_stringMgr.getString("GeneralPreferencesPanel.tabbedStyle"));
   private JRadioButton _internalFrameStyle = new JRadioButton(s_stringMgr.getString("GeneralPreferencesPanel.internalFrameStyle"));
   private JCheckBox _useScrollableTabbedPanesForSessionTabs = new JCheckBox(s_stringMgr.getString("GeneralPreferencesPanel.useScrollableTabbedPanesForSessionTabs"));
   private JCheckBox _showContents = new JCheckBox(s_stringMgr.getString("GeneralPreferencesPanel.showwindowcontents"));
   private JCheckBox _maximimizeSessionSheet = new JCheckBox(s_stringMgr.getString("GeneralPreferencesPanel.maxonopen"));
   private JCheckBox _showTabbedStyleHint = new JCheckBox(s_stringMgr.getString("GeneralPreferencesPanel.showTabbedStyleHint"));


   private JCheckBox _showAliasesToolBar = new JCheckBox(s_stringMgr.getString("GeneralPreferencesPanel.showaliasestoolbar"));
   private JCheckBox _showDriversToolBar = new JCheckBox(s_stringMgr.getString("GeneralPreferencesPanel.showdriverstoolbar"));
   private JCheckBox _showMainStatusBar = new JCheckBox(s_stringMgr.getString("GeneralPreferencesPanel.showmainwinstatusbar"));
   private JCheckBox _showMainToolBar = new JCheckBox(s_stringMgr.getString("GeneralPreferencesPanel.showmainwintoolbar"));
   private JCheckBox _showToolTips = new JCheckBox(s_stringMgr.getString("GeneralPreferencesPanel.showtooltips"));
   private JCheckBox _useScrollableTabbedPanes = new JCheckBox(s_stringMgr.getString("GeneralPreferencesPanel.usescrolltabs"));

   private JCheckBox _showColoriconsInToolbar = new JCheckBox(s_stringMgr.getString("GeneralPreferencesPanel.showcoloricons"));
   private JCheckBox _showPluginFilesInSplashScreen = new JCheckBox(s_stringMgr.getString("GeneralPreferencesPanel.showpluginfiles"));
   private JCheckBox _useShortSessionTitle = new JCheckBox(s_stringMgr.getString("GeneralPreferencesPanel.useShortSessionTitle"));
   private JCheckBox _rememberValueOfPopup = new JCheckBox(s_stringMgr.getString("GeneralPreferencesPanel.rememberValueOfPopup"));


   //      private JLabel _executionLogFileNameLbl = new OutputLabel(" ");
//      // Must have at least 1 blank otherwise width gets set to zero.
//      private JLabel _logConfigFileNameLbl = new OutputLabel(" ");
//      // Must have at least 1 blank otherwise width gets set to zero.
   private JCheckBox _confirmSessionCloseChk = new JCheckBox(s_stringMgr.getString("GeneralPreferencesPanel.confirmSessionClose"));
   private JCheckBox _warnJreJdbcMismatch = new JCheckBox(s_stringMgr.getString("GeneralPreferencesPanel.warnJreJdbcMismatch"));
   private JCheckBox _warnForUnsavedFileEdits = new JCheckBox(s_stringMgr.getString("GeneralPreferencesPanel.warnForUnsavedFileEdits"));
   private JCheckBox _warnForUnsavedBufferEdits = new JCheckBox(s_stringMgr.getString("GeneralPreferencesPanel.warnForUnsavedBufferEdits"));
   private JCheckBox _showSessionStartupTimeHint = new JCheckBox(s_stringMgr.getString("GeneralPreferencesPanel.showSessionStartupTimeHint"));
   private JCheckBox _savePreferencesImmediately = new JCheckBox(s_stringMgr.getString("GeneralPreferencesPanel.savePreferencesImmediately"));
   private JCheckBox _selectOnRightMouseClick = new JCheckBox(s_stringMgr.getString("GeneralPreferencesPanel.selectOnRightMouseClick"));
   private JCheckBox _showPleaseWaitDialog = new JCheckBox(s_stringMgr.getString("GeneralPreferencesPanel.showPleaseWaitDialog"));
   private JLabel _localeChooserLabel = new JLabel(s_stringMgr.getString("GeneralPreferencesPanel.localeChooserLabel"));

   ArrayList<String> localeDisplayStrings = new ArrayList<String>();

   private JComboBox _localeChooser = new JComboBox(getAvailableLocaleStringsIncludingDontChange());
   private MaxColumnAdjustLengthCtrl _maxColumnAdjustLengthCtrl = new MaxColumnAdjustLengthCtrl();


   GeneralPreferencesGUI()
   {
      super(new GridBagLayout());
      createUserInterface();
   }

   public static String[] getAvailableLocaleStringsIncludingDontChange()
   {
      String[] availableLocaleStrings = LocaleUtils.getAvailableLocaleStrings();

      ArrayList<String> ret = new ArrayList<String>();
      ret.add(SquirrelPreferences.getDontChangeLocaleConstant());
      ret.addAll(Arrays.asList(availableLocaleStrings));

      return ret.toArray(new String[ret.size()]);
   }

   void loadData(SquirrelPreferences prefs)
   {
      _tabbedStyle.setSelected(prefs.getTabbedStyle());
      _useScrollableTabbedPanesForSessionTabs.setSelected(prefs.getUseScrollableTabbedPanesForSessionTabs());
      _internalFrameStyle.setSelected(!prefs.getTabbedStyle());
      onStyleChanged();
      _showTabbedStyleHint.setSelected(prefs.getShowTabbedStyleHint());


      _showContents.setSelected(prefs.getShowContentsWhenDragging());
      _maximimizeSessionSheet.setSelected(prefs.getMaximizeSessionSheetOnOpen());

      _showToolTips.setSelected(prefs.getShowToolTips());
      _useScrollableTabbedPanes.setSelected(prefs.getUseScrollableTabbedPanes());
      _showMainStatusBar.setSelected(prefs.getShowMainStatusBar());
      _showMainToolBar.setSelected(prefs.getShowMainToolBar());
      _showAliasesToolBar.setSelected(prefs.getShowAliasesToolBar());
      _showDriversToolBar.setSelected(prefs.getShowDriversToolBar());
      _showColoriconsInToolbar.setSelected(prefs.getShowColoriconsInToolbar());
      _showPluginFilesInSplashScreen.setSelected(prefs.getShowPluginFilesInSplashScreen());
      _useShortSessionTitle.setSelected(prefs.getUseShortSessionTitle());
      _rememberValueOfPopup.setSelected(prefs.isRememberValueOfPopup());

      _confirmSessionCloseChk.setSelected(prefs.getConfirmSessionClose());
      _warnJreJdbcMismatch.setSelected(prefs.getWarnJreJdbcMismatch());
      _warnForUnsavedFileEdits.setSelected(prefs.getWarnForUnsavedFileEdits());
      _warnForUnsavedBufferEdits.setSelected(prefs.getWarnForUnsavedBufferEdits());
      _showSessionStartupTimeHint.setSelected(prefs.getShowSessionStartupTimeHint());
      _savePreferencesImmediately.setSelected(prefs.getSavePreferencesImmediately());
      _selectOnRightMouseClick.setSelected(prefs.getSelectOnRightMouseClick());
      _showPleaseWaitDialog.setSelected(prefs.getShowPleaseWaitDialog());
      _maxColumnAdjustLengthCtrl.init(prefs.getMaxColumnAdjustLengthDefined(), prefs.getMaxColumnAdjustLength());
      String preferredLocalString = prefs.getPreferredLocale();
      if (StringUtils.isEmpty(preferredLocalString))
      {
         preferredLocalString = "en_US";
      }
      _localeChooser.setSelectedItem(preferredLocalString);

      _tabbedStyle.addActionListener(new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent e)
         {
            onStyleChanged();
         }
      });

      _internalFrameStyle.addActionListener(new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent e)
         {
            onStyleChanged();
         }
      });

   }

   private void onStyleChanged()
   {
      _useScrollableTabbedPanesForSessionTabs.setEnabled(_tabbedStyle.isSelected());
      _showContents.setEnabled(_internalFrameStyle.isSelected());
      _maximimizeSessionSheet.setEnabled(_internalFrameStyle.isSelected());
      _showTabbedStyleHint.setEnabled(_internalFrameStyle.isSelected());
   }

   void applyChanges(SquirrelPreferences prefs)
   {
      prefs.setTabbedStyle(_tabbedStyle.isSelected());
      prefs.setUseScrollableTabbedPanesForSessionTabs(_useScrollableTabbedPanesForSessionTabs.isSelected());
      prefs.setShowContentsWhenDragging(_showContents.isSelected());
      prefs.setShowTabbedStyleHint(_showTabbedStyleHint.isSelected());
      prefs.setShowToolTips(_showToolTips.isSelected());
      prefs.setUseScrollableTabbedPanes(_useScrollableTabbedPanes.isSelected());
      prefs.setShowMainStatusBar(_showMainStatusBar.isSelected());
      prefs.setShowMainToolBar(_showMainToolBar.isSelected());
      prefs.setShowAliasesToolBar(_showAliasesToolBar.isSelected());
      prefs.setShowDriversToolBar(_showDriversToolBar.isSelected());
      prefs.setMaximizeSessionSheetOnOpen(_maximimizeSessionSheet.isSelected());
      prefs.setShowColoriconsInToolbar(_showColoriconsInToolbar.isSelected());
      prefs.setShowPluginFilesInSplashScreen(_showPluginFilesInSplashScreen.isSelected());
      prefs.setUseShortSessionTitle(_useShortSessionTitle.isSelected());
      prefs.setRememberValueOfPopup(_rememberValueOfPopup.isSelected());
      prefs.setConfirmSessionClose(_confirmSessionCloseChk.isSelected());
      prefs.setWarnJreJdbcMismatch(_warnJreJdbcMismatch.isSelected());
      prefs.setWarnForUnsavedFileEdits(_warnForUnsavedFileEdits.isSelected());
      prefs.setWarnForUnsavedBufferEdits(_warnForUnsavedBufferEdits.isSelected());
      prefs.setShowSessionStartupTimeHint(_showSessionStartupTimeHint.isSelected());
      prefs.setSavePreferencesImmediately(_savePreferencesImmediately.isSelected());
      prefs.setSelectOnRightMouseClick(_selectOnRightMouseClick.isSelected());
      prefs.setShowPleaseWaitDialog(_showPleaseWaitDialog.isSelected());
      prefs.setPreferredLocale(_localeChooser.getSelectedItem().toString());
      prefs.setMaxColumnAdjustLengthDefined(_maxColumnAdjustLengthCtrl.isMaxColumnAdjustLengthDefined());
      prefs.setMaxColumnAdjustLength(_maxColumnAdjustLengthCtrl.getMaxColumnAdjustLength());
   }

   private void createUserInterface()
   {
      final GridBagConstraints gbc = new GridBagConstraints();
      gbc.fill = GridBagConstraints.HORIZONTAL;
      gbc.anchor = GridBagConstraints.NORTHWEST;
      gbc.insets = new Insets(4, 4, 4, 4);
      gbc.gridx = 0;
      gbc.gridy = 0;
      gbc.weightx = 1;
      add(createAppearancePanel(), gbc);
      ++gbc.gridx;
      add(createGeneralPanel(), gbc);
      gbc.gridx = 0;
      ++gbc.gridy;
      gbc.gridwidth = 2;
      add(createLoggingPanel(), gbc);
      gbc.gridx = 0;
      ++gbc.gridy;
      gbc.gridwidth = 2;
      add(createPathsPanel(), gbc);
   }

   private JPanel createAppearancePanel()
   {
      final JPanel pnl = new JPanel(new GridBagLayout());
      pnl.setBorder(BorderFactory.createTitledBorder(s_stringMgr.getString("GeneralPreferencesPanel.appearance")));
      pnl.setLayout(new GridBagLayout());

      ButtonGroup g = new ButtonGroup();

      g.add(_tabbedStyle);
      g.add(_internalFrameStyle);
      final GridBagConstraints gbc = new GridBagConstraints();
      gbc.fill = GridBagConstraints.HORIZONTAL;
      gbc.insets = new Insets(2, 4, 2, 4);
      gbc.gridx = 0;
      gbc.weightx = 1;
      gbc.insets.top = 0;

      gbc.gridy = 0;
      _tabbedStyle.setName("tabbedStyleRadioButton");
      pnl.add(_tabbedStyle, gbc);
      ++gbc.gridy;
      _internalFrameStyle.setName("internalFrameStyleRadioButton");
      pnl.add(_internalFrameStyle, gbc);
      ++gbc.gridy;

      _useScrollableTabbedPanesForSessionTabs.setName("useScrollableTabbedPanes");
      pnl.add(_useScrollableTabbedPanesForSessionTabs, gbc);
      ++gbc.gridy;

      _showContents.setName("showContentsCheckBox");
      pnl.add(_showContents, gbc);
      ++gbc.gridy;
      _maximimizeSessionSheet.setName("maximizeSessionSheetCheckBox");
      pnl.add(_maximimizeSessionSheet, gbc);
      ++gbc.gridy;
      _showTabbedStyleHint.setName("showTabbedStyleHintCheckBox");
      pnl.add(_showTabbedStyleHint, gbc);


      ++gbc.gridy;
      pnl.add(_showToolTips, gbc);
      ++gbc.gridy;
      pnl.add(_useScrollableTabbedPanes, gbc);
      ++gbc.gridy;
      pnl.add(_showMainToolBar, gbc);
      ++gbc.gridy;
      pnl.add(_showMainStatusBar, gbc);
      ++gbc.gridy;
      pnl.add(_showDriversToolBar, gbc);
      ++gbc.gridy;
      pnl.add(_showAliasesToolBar, gbc);
      ++gbc.gridy;
      pnl.add(_showColoriconsInToolbar, gbc);
      ++gbc.gridy;
      pnl.add(_showPluginFilesInSplashScreen, gbc);
      ++gbc.gridy;
      pnl.add(_useShortSessionTitle, gbc);
      ++gbc.gridy;
      pnl.add(_rememberValueOfPopup, gbc);

      return pnl;
   }

   private JPanel createGeneralPanel()
   {
      final JPanel pnl = new JPanel();
      pnl.setBorder(BorderFactory.createTitledBorder(s_stringMgr.getString(
            "GeneralPreferencesPanel.general")));
      pnl.setLayout(new GridBagLayout());

      final GridBagConstraints gbc = new GridBagConstraints();
      gbc.fill = GridBagConstraints.HORIZONTAL;
      gbc.insets = new Insets(2, 4, 2, 4);
      gbc.gridx = 0;
      gbc.gridy = 0;
      gbc.weightx = 1;
      pnl.add(_confirmSessionCloseChk, gbc);

      gbc.gridx = 0;
      gbc.gridy = 1;
      pnl.add(_warnJreJdbcMismatch, gbc);

      gbc.gridx = 0;
      gbc.gridy = 2;
      pnl.add(_warnForUnsavedFileEdits, gbc);

      gbc.gridx = 0;
      gbc.gridy = 3;
      pnl.add(_warnForUnsavedBufferEdits, gbc);

      gbc.gridx = 0;
      gbc.gridy = 4;
      pnl.add(_showSessionStartupTimeHint, gbc);

      gbc.gridx = 0;
      gbc.gridy = 5;
      pnl.add(getSavePreferencesImmediatelyPanel(), gbc);

      gbc.gridx = 0;
      gbc.gridy = 6;
      pnl.add(_selectOnRightMouseClick, gbc);

      gbc.gridx = 0;
      gbc.gridy = 7;
      pnl.add(_showPleaseWaitDialog, gbc);

      gbc.gridx = 0;
      gbc.gridy = 8;
      pnl.add(createLocalePanel(), gbc);

      gbc.gridx = 0;
      gbc.gridy = 9;
      pnl.add(_maxColumnAdjustLengthCtrl.getPanel(), gbc);


      return pnl;
   }


   private JPanel createLocalePanel()
   {
      _localeChooser.setBorder(new EmptyBorder(5, 20, 5, 30));
      JPanel localePanel = new JPanel();
      BoxLayout layout = new BoxLayout(localePanel, BoxLayout.X_AXIS);
      localePanel.setLayout(layout);
      localePanel.add(_localeChooserLabel);
      localePanel.add(_localeChooser);
      return localePanel;
   }

   private JPanel getSavePreferencesImmediatelyPanel()
   {
      JPanel ret = new JPanel(new BorderLayout(3, 3));

      ret.add(new MultilineLabel(s_stringMgr.getString("GeneralPreferencesPanel.savePreferencesImmediatelyWarning")), BorderLayout.CENTER);
      ret.add(_savePreferencesImmediately, BorderLayout.SOUTH);

      ret.setBorder(BorderFactory.createEtchedBorder());
      return ret;
   }


   private JPanel createLoggingPanel()
   {
      final JPanel pnl = new JPanel();
      pnl.setBorder(BorderFactory.createTitledBorder(s_stringMgr.getString("GeneralPreferencesPanel.logging")));

      pnl.setLayout(new GridBagLayout());
      final GridBagConstraints gbc = new GridBagConstraints();
      gbc.anchor = GridBagConstraints.NORTHWEST;
      gbc.fill = GridBagConstraints.NONE;
      gbc.insets = new Insets(2, 4, 2, 4);

      ApplicationFiles appFiles = new ApplicationFiles();
      String execLogFile = appFiles.getExecutionLogFile().getPath();
      String configFile = ApplicationArguments.getInstance().getLoggingConfigFileName();
      configFile = null == configFile ? s_stringMgr.getString("GeneralPreferencesPanel.unspecified") : configFile;

      gbc.gridx = 0;
      gbc.gridy = 0;
      JTextField execLogFileField = new JTextField(s_stringMgr.getString("GeneralPreferencesPanel.execlogfileNew", execLogFile));
      execLogFileField.setEditable(false);
      execLogFileField.setBackground(pnl.getBackground());
      execLogFileField.setBorder(null);
      pnl.add(execLogFileField, gbc);

      ++gbc.gridy;
      JTextField configFileField = new JTextField(s_stringMgr.getString("GeneralPreferencesPanel.configfileNew", configFile));
      configFileField.setEditable(false);
      configFileField.setBackground(pnl.getBackground());
      configFileField.setBorder(null);
      pnl.add(configFileField, gbc);

      gbc.weightx = 1.0;

      gbc.gridy = 0;
      ++gbc.gridx;
      pnl.add(new JPanel(), gbc);

      ++gbc.gridy;
      pnl.add(new JPanel(), gbc);

      return pnl;
   }

   private JPanel createPathsPanel()
   {
      final JPanel pnl = new JPanel();
      // i18n[GeneralPreferencesPanel.paths=SQuirreL paths]
      pnl.setBorder(BorderFactory.createTitledBorder(s_stringMgr.getString("GeneralPreferencesPanel.paths")));

      pnl.setLayout(new GridBagLayout());
      final GridBagConstraints gbc = new GridBagConstraints();
      gbc.anchor = GridBagConstraints.NORTHWEST;
      gbc.fill = GridBagConstraints.NONE;
      gbc.insets = new Insets(2, 4, 2, 4);

      ApplicationFiles appFiles = new ApplicationFiles();
      String userDir = appFiles.getUserSettingsDirectory().getPath();
      String homeDir = appFiles.getSquirrelHomeDir().getPath();


      gbc.gridx = 0;
      gbc.gridy = 0;
      // i18n[GeneralPreferencesPanel.squirrelHomePath=Home directory: -home {0}]
      JTextField homePathField = new JTextField(s_stringMgr.getString("GeneralPreferencesPanel.squirrelHomePath", homeDir));
      homePathField.setEditable(false);
      homePathField.setBackground(pnl.getBackground());
      homePathField.setBorder(null);
      pnl.add(homePathField, gbc);

      ++gbc.gridy;
      // i18n[GeneralPreferencesPanel.squirrelUserPath=User directory: -userdir {0}]
      JTextField userPathField = new JTextField(s_stringMgr.getString("GeneralPreferencesPanel.squirrelUserPath", userDir));
      userPathField.setEditable(false);
      userPathField.setBackground(pnl.getBackground());
      userPathField.setBorder(null);
      pnl.add(userPathField, gbc);

      gbc.weightx = 1.0;

      gbc.gridy = 0;
      ++gbc.gridx;
      pnl.add(new JPanel(), gbc);

      ++gbc.gridy;
      pnl.add(new JPanel(), gbc);

      return pnl;
   }


}
