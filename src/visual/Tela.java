/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visual;

import gerenciador.Arquivo;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import objeto.Lembrete;

/**
 *
 * @author anderson
 */
public class Tela extends javax.swing.JFrame 
{
    private Arquivo arquivo;
    private ArrayList<Lembrete> lembretes;
    /**
     * Creates new form Tela
     */
    public Tela() throws UnsupportedEncodingException, IOException 
    {
        arquivo = new Arquivo("/home/anderson/Documentos/Documentos/Projetos/Java/NetBeansProjects/Lembrador/assets/lembretes.xml");
        lembretes = arquivo.lerArquivo();
        initComponents();
        exibirData();
        
        atualizarLista();
    }
    
    private void criarLembrete()
    {
        String nome = jTextFieldNomeLembrete.getText();
        String descricao = jTextAreaDescricaoLembrete.getText();
        String []data = jFormattedTextFieldDataLembrete.getText().split("/");
        Lembrete lembrete = new Lembrete(1, nome, Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2]), descricao);
        lembretes.add(lembrete);
        try 
        {   
            arquivo.gravarArquivo(lembretes);   
            jTabbedPaneLembretes.setSelectedIndex(0);
            JOptionPane.showMessageDialog(this.getContentPane(), "Lembrete criado com sucesso.", "Sucesso ao criar lembrete", JOptionPane.INFORMATION_MESSAGE);
        } 
        catch (IOException ex) 
        {   
            Logger.getLogger(Tela.class.getName()).log(Level.SEVERE, null, ex); 
            JOptionPane.showMessageDialog(this.getContentPane(), "Falha ao criar o lembrete.", "Falha ao criar lembrete", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void atualizarLista()
    {
        jListLembretes.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = Lembrete.getListaNome(lembretes);
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
    }
    
    private void exibirData()
    {
        Calendar calendario = Calendar.getInstance();
        String data = calendario.get(Calendar.DAY_OF_MONTH) + "/";
        data += calendario.get(Calendar.MONTH) + "/";
        data += calendario.get(Calendar.YEAR) + "";
        jLabelDataAtual.setText("Hoje: " + data);
    }
    
    private void confirmarCriarLembrete()
    {
        if(jTabbedPaneLembretes.getSelectedIndex() == 1)
        {
            if(jTextFieldNomeLembrete.getText().length() > 0 && jFormattedTextFieldDataLembrete.getText().length() > 0)
            {
                int resultado = JOptionPane.showConfirmDialog(this.getContentPane(), "Deseja criar o lembrete?", "Confirmação", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if(resultado == 0)
                    criarLembrete();
            }
            else
            {
                JOptionPane.showMessageDialog(this.getContentPane(), "Os campos nome e data não podem ficar em branco.", "Erro ao criar lembrete", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem2 = new javax.swing.JMenuItem();
        jTabbedPaneLembretes = new javax.swing.JTabbedPane();
        jPanelLembreteAtual = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jListLembretes = new javax.swing.JList<>();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabelNome = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabelData = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabelDescricao = new javax.swing.JLabel();
        jPanelLembreteNovo = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jTextFieldNomeLembrete = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jFormattedTextFieldDataLembrete = new javax.swing.JFormattedTextField();
        jButtonCriar3 = new javax.swing.JButton();
        jButtonCancelar3 = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTextAreaDescricaoLembrete = new javax.swing.JTextArea();
        jPanel4 = new javax.swing.JPanel();
        jLabelDataAtual = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jMenuBar = new javax.swing.JMenuBar();
        jMenuArquivo = new javax.swing.JMenu();
        jMenuItemNovo = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItemSalvar = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuItemSair = new javax.swing.JMenuItem();
        jMenuEditar = new javax.swing.JMenu();

        jMenuItem2.setText("jMenuItem2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(800, 800));

        jTabbedPaneLembretes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTabbedPaneLembretes.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        jTabbedPaneLembretes.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        jTabbedPaneLembretes.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTabbedPaneLembretes.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPaneLembretesStateChanged(evt);
            }
        });

        jPanel6.setBorder(null);

        jLabel8.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        jLabel8.setText("LEMBRETES");
        jPanel8.add(jLabel8);

        jLabel3.setText("Lembretes:");

        jListLembretes.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = Lembrete.getListaNome(lembretes);
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jListLembretes.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jListLembretes.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jListLembretesValueChanged(evt);
            }
        });
        jScrollPane6.setViewportView(jListLembretes);

        jButton1.setText("Excluir");

        jButton2.setText("Editar");

        jButton3.setText("Ver");

        jLabel5.setText("Nome:");

        jLabelNome.setText("Nenhum item selecionado");

        jLabel18.setText("Data:");

        jLabelData.setText("Nenhum item selecionado");

        jLabel19.setText("Descrição");

        jLabelDescricao.setText("Nenhum item selecionado");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelData))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelDescricao))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelNome)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabelNome))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jLabelData))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(jLabelDescricao))
                .addGap(0, 140, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addGap(215, 215, 215))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanelLembreteAtualLayout = new javax.swing.GroupLayout(jPanelLembreteAtual);
        jPanelLembreteAtual.setLayout(jPanelLembreteAtualLayout);
        jPanelLembreteAtualLayout.setHorizontalGroup(
            jPanelLembreteAtualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanelLembreteAtualLayout.setVerticalGroup(
            jPanelLembreteAtualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPaneLembretes.addTab("Lembretes", jPanelLembreteAtual);

        jLabel2.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        jLabel2.setText("NOVO LEMBRETE");
        jPanel12.add(jLabel2);

        jLabel15.setText("Nome:");

        jTextFieldNomeLembrete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldNomeLembreteActionPerformed(evt);
            }
        });

        jLabel16.setText("Data final:");

        jFormattedTextFieldDataLembrete.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter()));

        jButtonCriar3.setText("Criar");
        jButtonCriar3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCriar3ActionPerformed(evt);
            }
        });

        jButtonCancelar3.setText("Cancelar");

        jLabel17.setText("Descrição:");

        jTextAreaDescricaoLembrete.setColumns(20);
        jTextAreaDescricaoLembrete.setRows(5);
        jTextAreaDescricaoLembrete.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jScrollPane5.setViewportView(jTextAreaDescricaoLembrete);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17)
                            .addComponent(jLabel15))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(jTextFieldNomeLembrete, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jFormattedTextFieldDataLembrete))
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 447, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jButtonCriar3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonCancelar3)))
                .addContainerGap(125, Short.MAX_VALUE))
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldNomeLembrete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16)
                    .addComponent(jFormattedTextFieldDataLembrete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonCriar3)
                    .addComponent(jButtonCancelar3))
                .addContainerGap(190, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelLembreteNovoLayout = new javax.swing.GroupLayout(jPanelLembreteNovo);
        jPanelLembreteNovo.setLayout(jPanelLembreteNovoLayout);
        jPanelLembreteNovoLayout.setHorizontalGroup(
            jPanelLembreteNovoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanelLembreteNovoLayout.setVerticalGroup(
            jPanelLembreteNovoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPaneLembretes.addTab("Novo Lembrete", jPanelLembreteNovo);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Status"));

        jLabelDataAtual.setText("");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelDataAtual)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelDataAtual)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        jMenuArquivo.setMnemonic('A');
        jMenuArquivo.setText("Arquivo");

        jMenuItemNovo.setMnemonic('N');
        jMenuItemNovo.setText("Novo Lembrete");
        jMenuItemNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemNovoActionPerformed(evt);
            }
        });
        jMenuArquivo.add(jMenuItemNovo);
        jMenuArquivo.add(jSeparator1);

        jMenuItemSalvar.setMnemonic('S');
        jMenuItemSalvar.setText("Salvar");
        jMenuItemSalvar.setToolTipText("");
        jMenuItemSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSalvarActionPerformed(evt);
            }
        });
        jMenuArquivo.add(jMenuItemSalvar);
        jMenuArquivo.add(jSeparator2);

        jMenuItemSair.setMnemonic('r');
        jMenuItemSair.setText("Sair");
        jMenuItemSair.setToolTipText("");
        jMenuItemSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSairActionPerformed(evt);
            }
        });
        jMenuArquivo.add(jMenuItemSair);

        jMenuBar.add(jMenuArquivo);

        jMenuEditar.setMnemonic('E');
        jMenuEditar.setText("Editar");
        jMenuBar.add(jMenuEditar);

        setJMenuBar(jMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jTabbedPaneLembretes)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPaneLembretes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPaneLembretes.getAccessibleContext().setAccessibleName("Lembretes");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonCriar3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCriar3ActionPerformed
        confirmarCriarLembrete();
    }//GEN-LAST:event_jButtonCriar3ActionPerformed

    private void jTextFieldNomeLembreteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldNomeLembreteActionPerformed

    }//GEN-LAST:event_jTextFieldNomeLembreteActionPerformed

    private void jListLembretesValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jListLembretesValueChanged
        Lembrete lembrete = lembretes.get(jListLembretes.getSelectedIndex());
        jLabelNome.setText(lembrete.getNome());
        jLabelData.setText(lembrete.getDataFormatada());
        jLabelDescricao.setText(lembrete.getDescricao());
    }//GEN-LAST:event_jListLembretesValueChanged

    private void jMenuItemNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemNovoActionPerformed
        jTabbedPaneLembretes.setSelectedIndex(1);
    }//GEN-LAST:event_jMenuItemNovoActionPerformed

    private void jMenuItemSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemSairActionPerformed
        int resultado = JOptionPane.showConfirmDialog(this.getContentPane(), "Deseja sair do programa?", "Confirmação", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
        if(resultado == 0)
            this.dispose();
    }//GEN-LAST:event_jMenuItemSairActionPerformed

    private void jMenuItemSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemSalvarActionPerformed
        confirmarCriarLembrete();
    }//GEN-LAST:event_jMenuItemSalvarActionPerformed

    private void jTabbedPaneLembretesStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPaneLembretesStateChanged
        if(jTabbedPaneLembretes.getSelectedIndex() == 0)
            jMenuItemSalvar.setEnabled(false);
        else
            jMenuItemSalvar.setEnabled(true);
    }//GEN-LAST:event_jTabbedPaneLembretesStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButtonCancelar3;
    private javax.swing.JButton jButtonCriar3;
    private javax.swing.JFormattedTextField jFormattedTextFieldDataLembrete;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabelData;
    private javax.swing.JLabel jLabelDataAtual;
    private javax.swing.JLabel jLabelDescricao;
    private javax.swing.JLabel jLabelNome;
    private javax.swing.JList<String> jListLembretes;
    private javax.swing.JMenu jMenuArquivo;
    private javax.swing.JMenuBar jMenuBar;
    private javax.swing.JMenu jMenuEditar;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItemNovo;
    private javax.swing.JMenuItem jMenuItemSair;
    private javax.swing.JMenuItem jMenuItemSalvar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanelLembreteAtual;
    private javax.swing.JPanel jPanelLembreteNovo;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JTabbedPane jTabbedPaneLembretes;
    private javax.swing.JTextArea jTextAreaDescricaoLembrete;
    private javax.swing.JTextField jTextFieldNomeLembrete;
    // End of variables declaration//GEN-END:variables
}
