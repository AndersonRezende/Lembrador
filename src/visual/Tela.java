/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visual;

import arquivo.imagem.GerenciadorImagem;
import gerenciador.Arquivo;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import objeto.Data;
import objeto.Lembrete;

/**
 *
 * @author anderson
 */
public class Tela extends javax.swing.JFrame 
{
    private final Arquivo arquivo;
    private ArrayList<Lembrete> lembretes;
    private final String separador = System.getProperty("file.separator");
    private final String caminhoSistema = System.getProperty("user.dir");
    private final String caminhoLembretes = caminhoSistema + separador + "lembretes.xml";
    
    /**
     * Creates new form Tela
     * @throws java.io.UnsupportedEncodingException
     */
    public Tela() throws UnsupportedEncodingException, IOException 
    {
        super("Lembrador");
        arquivo = new Arquivo(caminhoLembretes);
        lembretes = arquivo.lerArquivo();
        initComponents(); 
        configurarImagens();
        configurarSystemTray();
        this.setSize(1000, 800);
        exibirData();
        atualizarLista();
    }
        
    private void configurarImagens()
    {
        iconeJanela = new ImageIcon(GerenciadorImagem.getImagemUrl(GerenciadorImagem.TIMER));
        this.setIconImage(iconeJanela.getImage());
        jButtonAtualizar.setIcon(new ImageIcon(GerenciadorImagem.getImagemUrl(GerenciadorImagem.ATUALIZAR)));
        jButtonNovoLembrete.setIcon(new ImageIcon(GerenciadorImagem.getImagemUrl(GerenciadorImagem.NOVO)));
        jButtonEditar.setIcon(new ImageIcon(GerenciadorImagem.getImagemUrl(GerenciadorImagem.EDITAR)));
        jButtonExcluir.setIcon(new ImageIcon(GerenciadorImagem.getImagemUrl(GerenciadorImagem.DELETAR)));
        jButtonSobre.setIcon(new ImageIcon(GerenciadorImagem.getImagemUrl(GerenciadorImagem.INFO)));
    }
    
    private void configurarSystemTray()
    {
        String texto = "Lembrador.";
        
        tray = SystemTray.getSystemTray();
        Image image = iconeJanela.getImage();
        popupTray = new PopupMenu();
        
        menuItemExibirOcultarTray = new MenuItem("Exibir/Ocultar");
        menuItemExibirOcultarTray.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {   menuItemExibirOcultarTrayActionPerformed(e);  }
        });
        menuItemSairTray = new MenuItem("Sair");
        menuItemSairTray.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {   menuItemSairTrayActionPerformed(e);  }
        });
        
        popupTray.add(menuItemExibirOcultarTray);
        popupTray.add(menuItemSairTray);
        
        trayIcon = new TrayIcon(image, texto, popupTray);
        trayIcon.setImageAutoSize(true);
        try 
        {   
            tray.add(trayIcon);   
            this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        } 
        catch (AWTException e) 
        {   System.err.println("Não pode adicionar a tray");    }
    }
    
    private void criarLembrete()
    {
        String nome = jTextFieldNomeLembrete.getText();
        String descricao = jTextAreaDescricaoLembrete.getText();
        String []data = jFormattedTextFieldDataLembrete.getText().split("/");
        Lembrete lembrete = new Lembrete((lembretes.size() + 1), nome, Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2]), descricao);
        if(Data.diferencaDiasDataAtual(lembrete.getData()) > 0)
        {
            lembretes.add(lembrete);
            try 
            {   
                gravar();
                limparNovoLembrete();
                JOptionPane.showMessageDialog(this.getContentPane(), "Lembrete criado com sucesso.", "Sucesso ao criar lembrete", JOptionPane.INFORMATION_MESSAGE);
            } 
            catch (IOException ex) 
            {   JOptionPane.showMessageDialog(this.getContentPane(), "Falha ao salvar arquivo.", "Erro", JOptionPane.ERROR_MESSAGE);    }
        }
        else
            JOptionPane.showMessageDialog(this.getContentPane(), "A data final do lembrete deve ser superior a data atual.", "Falha ao criar lembrete", JOptionPane.ERROR_MESSAGE);
    }
    
    private void editarLembrete()
    {
        String []data = jFormattedTextFieldEditarDataLembrete.getText().split("/");
        if(Data.diferencaDiasDataAtual(Integer.parseInt(data[0]), (Integer.parseInt(data[1]) - 1), Integer.parseInt(data[2])) > 0)
        {
            Lembrete lembrete = lembretes.get(Integer.parseInt(jTextFieldEditarIndex.getText()));
            lembrete.setNome(jTextFieldEditarNomeLembrete.getText());
            lembrete.setData(Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2]));
            
            try
            {
                gravar();
                limparEditarLembrete();
                JOptionPane.showMessageDialog(this.getContentPane(), "Lembrete editado com sucesso.", "Sucesso ao criar lembrete", JOptionPane.INFORMATION_MESSAGE);
            } 
            catch (IOException ex) 
            {   JOptionPane.showMessageDialog(this.getContentPane(), "Falha ao salvar arquivo.", "Erro", JOptionPane.ERROR_MESSAGE);    }
        }
        else
            JOptionPane.showMessageDialog(this.getContentPane(), "A data final do lembrete deve ser superior a data atual.", "Falha ao criar lembrete", JOptionPane.ERROR_MESSAGE);
    }
    
    private void excluirLembrete()
    {
        int index = jListLembretes.getSelectedIndex();
        if(index >= 0)
        {
            String texto = "Deseja excluir o item selecionado?\n"+lembretes.get(index).getNome();
            int resultado = JOptionPane.showConfirmDialog(this.getContentPane(), texto, "Confirmação de exclusão", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if(resultado == 0)
            {
                lembretes.remove(index);
                try 
                {   
                    arquivo.gravarArquivo(lembretes);
                    atualizarLista();
                    JOptionPane.showMessageDialog(this.getContentPane(), "Lembrete excluido com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } 
                catch (IOException ex) 
                {   JOptionPane.showMessageDialog(this.getContentPane(), "Falha ao tentar excluir o lembrete.", "Erro", JOptionPane.ERROR); }
            }
        }
        else
            JOptionPane.showMessageDialog(this.getContentPane(), "Nenhum item selecionado.", "Selecione um item", JOptionPane.ERROR);
    }
    
    private void recarregar() throws UnsupportedEncodingException, IOException
    {
        lembretes = arquivo.lerArquivo();
        atualizarLista();
        limparDadosExibicao();
        limparEditarLembrete();
        limparNovoLembrete();
        jTabbedPaneLembretes.setSelectedIndex(0);
    }
    
    private void limparNovoLembrete()
    {
        jTextFieldNomeLembrete.setText("");
        jFormattedTextFieldDataLembrete.setText("");
        jTextAreaDescricaoLembrete.setText("");
    }
    
    private void limparEditarLembrete()
    {
        jTextFieldEditarNomeLembrete.setText("");
        jFormattedTextFieldEditarDataLembrete.setText("");
        jTextAreaEditarDescricaoLembrete.setText("");
        jTextFieldEditarIndex.setText("");
    }
    
    private void limparDadosExibicao()
    {
        String semSelecao = "Nenhum item selecionado";
        jLabelNome.setText(semSelecao);
        jLabelData.setText(semSelecao);
        jLabelDescricao.setText(semSelecao);
        jLabelDias.setText(semSelecao);
        jLabelDias.setForeground(Color.black);
        
        jButtonExcluir.setEnabled(false);
        jButtonEditar.setEnabled(false);
        jMenuItemExcluir.setEnabled(false);
    }
    
    private void atualizarLista()
    {
        jListLembretes.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = Lembrete.getListaNome(lembretes);
            @Override
            public int getSize() { return strings.length; }
            @Override
            public String getElementAt(int i) { return strings[i]; }
        });
        jListLembretes.clearSelection();
        limparDadosExibicao();
    }
    
    private void exibirData()
    {   jLabelDataAtual.setText("Hoje: " + Data.dataAtual());   }
    
    private void checarCriarLembrete()
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
                JOptionPane.showMessageDialog(this.getContentPane(), "Os campos nome e data não podem ficar em branco.", "Erro ao criar lembrete", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private boolean configurarExibicaoEditarLembrete()
    {
        boolean configurado = false;
        int index = jListLembretes.getSelectedIndex();
        if(index >= 0)
        {
            Lembrete lembrete = lembretes.get(index);
            jTextFieldEditarIndex.setText(""+index);
            jTextFieldEditarNomeLembrete.setText(lembrete.getNome());
            jFormattedTextFieldEditarDataLembrete.setText(lembrete.getDataFormatada());
            jTextAreaEditarDescricaoLembrete.setText(lembrete.getDescricao());
            jTabbedPaneLembretes.setSelectedIndex(2);
            configurado = true;
        }
        else
        {
            jTabbedPaneLembretes.setSelectedIndex(0);
            configurado = false;
        }
        return configurado;
    }
    
    private void gravar() throws IOException
    {   
        arquivo.gravarArquivo(lembretes);
        atualizarLista();
        jTabbedPaneLembretes.setSelectedIndex(0);
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
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabelNome = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabelData = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabelDescricao = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabelDias = new javax.swing.JLabel();
        jPanelLembreteNovo = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jTextFieldNomeLembrete = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jFormattedTextFieldDataLembrete = new javax.swing.JFormattedTextField();
        jButtonCriar = new javax.swing.JButton();
        jButtonCancelar = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTextAreaDescricaoLembrete = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jTextFieldEditarNomeLembrete = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jFormattedTextFieldEditarDataLembrete = new javax.swing.JFormattedTextField();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTextAreaEditarDescricaoLembrete = new javax.swing.JTextArea();
        jLabel22 = new javax.swing.JLabel();
        jButtonEditarSalvar = new javax.swing.JButton();
        jButtonEditarCancelar = new javax.swing.JButton();
        jTextFieldEditarIndex = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabelDataAtual = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jButtonAtualizar = new javax.swing.JButton();
        jButtonEditar = new javax.swing.JButton();
        jButtonNovoLembrete = new javax.swing.JButton();
        jButtonExcluir = new javax.swing.JButton();
        jButtonSobre = new javax.swing.JButton();
        jMenuBar = new javax.swing.JMenuBar();
        jMenuArquivo = new javax.swing.JMenu();
        jMenuItemNovo = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItemSalvar = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuItemSair = new javax.swing.JMenuItem();
        jMenuEditar = new javax.swing.JMenu();
        jMenuItemAtualizar = new javax.swing.JMenuItem();
        jMenuItemExcluir = new javax.swing.JMenuItem();
        jMenuAjuda = new javax.swing.JMenu();
        jMenuItemSobre = new javax.swing.JMenuItem();

        jMenuItem2.setText("jMenuItem2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Lembrador");
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
        jListLembretes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jListLembretesKeyReleased(evt);
            }
        });
        jListLembretes.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jListLembretesValueChanged(evt);
            }
        });
        jScrollPane6.setViewportView(jListLembretes);

        jLabel5.setText("Nome:");

        jLabelNome.setText("Nenhum item selecionado");

        jLabel18.setText("Data:");

        jLabelData.setText("Nenhum item selecionado");

        jLabel19.setText("Descrição");

        jLabelDescricao.setText("Nenhum item selecionado");

        jLabel1.setText("Dias até finalizar:");

        jLabelDias.setText("Nenhum item selecionado");

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
                        .addComponent(jLabelNome))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelDias)))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabelDias))
                .addGap(0, 118, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 552, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(54, 54, 54))
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

        jButtonCriar.setText("Criar");
        jButtonCriar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCriarActionPerformed(evt);
            }
        });

        jButtonCancelar.setText("Cancelar");
        jButtonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelarActionPerformed(evt);
            }
        });

        jLabel17.setText("Descrição:");

        jTextAreaDescricaoLembrete.setColumns(20);
        jTextAreaDescricaoLembrete.setRows(5);
        jTextAreaDescricaoLembrete.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jScrollPane5.setViewportView(jTextAreaDescricaoLembrete);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, 714, Short.MAX_VALUE)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
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
                        .addComponent(jButtonCriar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonCancelar)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                    .addComponent(jButtonCriar)
                    .addComponent(jButtonCancelar))
                .addContainerGap(348, Short.MAX_VALUE))
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

        jLabel4.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        jLabel4.setText("EDITAR LEMBRETE");
        jPanel7.add(jLabel4);

        jLabel20.setText("Nome:");

        jTextFieldEditarNomeLembrete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldEditarNomeLembreteActionPerformed(evt);
            }
        });

        jLabel21.setText("Data final:");

        jFormattedTextFieldEditarDataLembrete.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter()));

        jTextAreaEditarDescricaoLembrete.setColumns(20);
        jTextAreaEditarDescricaoLembrete.setRows(5);
        jTextAreaEditarDescricaoLembrete.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jScrollPane7.setViewportView(jTextAreaEditarDescricaoLembrete);

        jLabel22.setText("Descrição:");

        jButtonEditarSalvar.setText("Salvar");
        jButtonEditarSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEditarSalvarActionPerformed(evt);
            }
        });

        jButtonEditarCancelar.setText("Cancelar");
        jButtonEditarCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEditarCancelarActionPerformed(evt);
            }
        });

        jTextFieldEditarIndex.setEditable(false);
        jTextFieldEditarIndex.setEnabled(false);
        jTextFieldEditarIndex.setVisible(false);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jTextFieldEditarIndex, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonEditarSalvar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonEditarCancelar))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                        .addComponent(jTextFieldEditarNomeLembrete, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFormattedTextFieldEditarDataLembrete))
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 447, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(180, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldEditarNomeLembrete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20)
                    .addComponent(jLabel21)
                    .addComponent(jFormattedTextFieldEditarDataLembrete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonEditarSalvar)
                            .addComponent(jButtonEditarCancelar)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldEditarIndex, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 348, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPaneLembretes.addTab("Editar Lembrete", jPanel2);

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

        jButtonAtualizar.setText("Atualizar");
        jButtonAtualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAtualizarActionPerformed(evt);
            }
        });

        jButtonEditar.setText("Editar");
        jButtonEditar.setEnabled(false);
        jButtonEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEditarActionPerformed(evt);
            }
        });

        jButtonNovoLembrete.setText("Novo");
        jButtonNovoLembrete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNovoLembreteActionPerformed(evt);
            }
        });

        jButtonExcluir.setText("Excluir");
        jButtonExcluir.setEnabled(false);
        jButtonExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExcluirActionPerformed(evt);
            }
        });

        jButtonSobre.setText("Sobre");
        jButtonSobre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSobreActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonNovoLembrete)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonEditar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonExcluir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonAtualizar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonSobre)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 14, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonAtualizar)
                    .addComponent(jButtonEditar)
                    .addComponent(jButtonNovoLembrete)
                    .addComponent(jButtonExcluir)
                    .addComponent(jButtonSobre)))
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

        jMenuItemAtualizar.setMnemonic('A');
        jMenuItemAtualizar.setText("Atualizar");
        jMenuItemAtualizar.setToolTipText("");
        jMenuItemAtualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemAtualizarActionPerformed(evt);
            }
        });
        jMenuEditar.add(jMenuItemAtualizar);

        jMenuItemExcluir.setMnemonic('E');
        jMenuItemExcluir.setText("Excluir");
        jMenuItemExcluir.setToolTipText("");
        jMenuItemExcluir.setEnabled(false);
        jMenuItemExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemExcluirActionPerformed(evt);
            }
        });
        jMenuEditar.add(jMenuItemExcluir);

        jMenuBar.add(jMenuEditar);

        jMenuAjuda.setMnemonic('j');
        jMenuAjuda.setText("Ajuda");

        jMenuItemSobre.setMnemonic('S');
        jMenuItemSobre.setText("Sobre");
        jMenuItemSobre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSobreActionPerformed(evt);
            }
        });
        jMenuAjuda.add(jMenuItemSobre);

        jMenuBar.add(jMenuAjuda);

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

    private void jButtonCriarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCriarActionPerformed
        checarCriarLembrete();
    }//GEN-LAST:event_jButtonCriarActionPerformed

    private void jTextFieldNomeLembreteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldNomeLembreteActionPerformed

    }//GEN-LAST:event_jTextFieldNomeLembreteActionPerformed

    private void jListLembretesValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jListLembretesValueChanged
        if(jListLembretes.getSelectedIndex() >= 0)
        {
            jButtonExcluir.setEnabled(true);
            jButtonEditar.setEnabled(true);
            jMenuItemExcluir.setEnabled(true);
            
            Lembrete lembrete = lembretes.get(jListLembretes.getSelectedIndex());
            jLabelNome.setText(lembrete.getNome());
            jLabelData.setText(lembrete.getDataFormatada());
            jLabelDescricao.setText(lembrete.getDescricao());
            if(lembrete.getDias() > 0)
            {   
                jLabelDias.setText(""+lembrete.getDias());  
                jLabelDias.setForeground(Color.blue);
            }
            else
            {
                if(lembrete.getDias() == 0)
                {
                    jLabelDias.setText("O lembrete termina hoje.");
                    jLabelDias.setForeground(Color.magenta);
                }
                else
                {
                    jLabelDias.setText("Lembrete finalizado há " + (lembrete.getDias() * (-1)) + " dia(s).");
                    jLabelDias.setForeground(Color.red);
                }
            }
        }
    }//GEN-LAST:event_jListLembretesValueChanged

    private void jMenuItemNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemNovoActionPerformed
        jTabbedPaneLembretes.setSelectedIndex(1);
    }//GEN-LAST:event_jMenuItemNovoActionPerformed

    private void jMenuItemSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemSairActionPerformed
        int resultado = JOptionPane.showConfirmDialog(this.getContentPane(), "Deseja sair do programa?", "Confirmação", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
        if(resultado == 0)
            System.exit(0);
    }//GEN-LAST:event_jMenuItemSairActionPerformed

    private void jMenuItemSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemSalvarActionPerformed
        checarCriarLembrete();
    }//GEN-LAST:event_jMenuItemSalvarActionPerformed

    private void jTabbedPaneLembretesStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPaneLembretesStateChanged
        switch(jTabbedPaneLembretes.getSelectedIndex())
        {
            case 0:
                jMenuItemSalvar.setEnabled(false);
                break;
            case 1:
                jMenuItemSalvar.setEnabled(true);
                break;
            case 2:
                if(!configurarExibicaoEditarLembrete())
                    JOptionPane.showMessageDialog(this.getContentPane(), "Nenhum lembrete selecionado.\nSelecione um lembrete e tente novamente.", "Erro ao tentar editar", JOptionPane.ERROR_MESSAGE);
                break;
        }
    }//GEN-LAST:event_jTabbedPaneLembretesStateChanged

    private void jMenuItemSobreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemSobreActionPerformed
        Sobre sobre = new Sobre(this, true);
    }//GEN-LAST:event_jMenuItemSobreActionPerformed

    private void jButtonExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExcluirActionPerformed
        excluirLembrete();
    }//GEN-LAST:event_jButtonExcluirActionPerformed

    private void jMenuItemExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemExcluirActionPerformed
        excluirLembrete();
    }//GEN-LAST:event_jMenuItemExcluirActionPerformed

    private void jListLembretesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jListLembretesKeyReleased
        if(evt.getKeyCode() == KeyEvent.VK_DELETE)
            excluirLembrete();
        if(evt.getKeyCode() == KeyEvent.VK_F5)
        {
            try 
            {   recarregar();   } 
            catch (IOException ex) 
            {   JOptionPane.showMessageDialog(this.getContentPane(), "Falha ao ler arquivo.\nTente novamente.", "Erro ao tentar atualizar", JOptionPane.ERROR_MESSAGE); }
        }
    }//GEN-LAST:event_jListLembretesKeyReleased

    private void jButtonEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditarActionPerformed
        if(!configurarExibicaoEditarLembrete())
            JOptionPane.showMessageDialog(this.getContentPane(), "Nenhum lembrete selecionado.\nSelecione um lembrete e tente novamente.", "Erro ao tentar editar", JOptionPane.ERROR_MESSAGE);
    }//GEN-LAST:event_jButtonEditarActionPerformed

    private void jTextFieldEditarNomeLembreteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldEditarNomeLembreteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldEditarNomeLembreteActionPerformed

    private void jButtonEditarSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditarSalvarActionPerformed
        editarLembrete();
    }//GEN-LAST:event_jButtonEditarSalvarActionPerformed

    private void jButtonEditarCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditarCancelarActionPerformed
        jListLembretes.clearSelection();
        limparEditarLembrete();
        limparDadosExibicao();
        jTabbedPaneLembretes.setSelectedIndex(0);
    }//GEN-LAST:event_jButtonEditarCancelarActionPerformed

    private void jButtonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelarActionPerformed
        limparNovoLembrete();
        limparDadosExibicao();
        jTabbedPaneLembretes.setSelectedIndex(0);
    }//GEN-LAST:event_jButtonCancelarActionPerformed

    private void jButtonNovoLembreteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNovoLembreteActionPerformed
        jTabbedPaneLembretes.setSelectedIndex(1);
    }//GEN-LAST:event_jButtonNovoLembreteActionPerformed

    private void jMenuItemAtualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemAtualizarActionPerformed
        try 
        {   recarregar();   } 
        catch (IOException ex) 
        {   JOptionPane.showMessageDialog(this.getContentPane(), "Falha ao ler arquivo.\nTente novamente.", "Erro ao tentar atualizar", JOptionPane.ERROR_MESSAGE); }
    }//GEN-LAST:event_jMenuItemAtualizarActionPerformed

    private void jButtonAtualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAtualizarActionPerformed
        try 
        {   recarregar();   } 
        catch (IOException ex) 
        {   JOptionPane.showMessageDialog(this.getContentPane(), "Falha ao ler arquivo.\nTente novamente.", "Erro ao tentar atualizar", JOptionPane.ERROR_MESSAGE); }
    }//GEN-LAST:event_jButtonAtualizarActionPerformed

    private void jButtonSobreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSobreActionPerformed
        Sobre sobre = new Sobre(this, true);
    }//GEN-LAST:event_jButtonSobreActionPerformed

    private void menuItemExibirOcultarTrayActionPerformed(java.awt.event.ActionEvent evt) {   
        this.setVisible(!this.isVisible()); 
    }
    
    private void menuItemSairTrayActionPerformed(java.awt.event.ActionEvent evt) {   
        System.exit(0);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAtualizar;
    private javax.swing.JButton jButtonCancelar;
    private javax.swing.JButton jButtonCriar;
    private javax.swing.JButton jButtonEditar;
    private javax.swing.JButton jButtonEditarCancelar;
    private javax.swing.JButton jButtonEditarSalvar;
    private javax.swing.JButton jButtonExcluir;
    private javax.swing.JButton jButtonNovoLembrete;
    private javax.swing.JButton jButtonSobre;
    private javax.swing.JFormattedTextField jFormattedTextFieldDataLembrete;
    private javax.swing.JFormattedTextField jFormattedTextFieldEditarDataLembrete;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabelData;
    private javax.swing.JLabel jLabelDataAtual;
    private javax.swing.JLabel jLabelDescricao;
    private javax.swing.JLabel jLabelDias;
    private javax.swing.JLabel jLabelNome;
    private javax.swing.JList<String> jListLembretes;
    private javax.swing.JMenu jMenuAjuda;
    private javax.swing.JMenu jMenuArquivo;
    private javax.swing.JMenuBar jMenuBar;
    private javax.swing.JMenu jMenuEditar;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItemAtualizar;
    private javax.swing.JMenuItem jMenuItemExcluir;
    private javax.swing.JMenuItem jMenuItemNovo;
    private javax.swing.JMenuItem jMenuItemSair;
    private javax.swing.JMenuItem jMenuItemSalvar;
    private javax.swing.JMenuItem jMenuItemSobre;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanelLembreteAtual;
    private javax.swing.JPanel jPanelLembreteNovo;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JTabbedPane jTabbedPaneLembretes;
    private javax.swing.JTextArea jTextAreaDescricaoLembrete;
    private javax.swing.JTextArea jTextAreaEditarDescricaoLembrete;
    private javax.swing.JTextField jTextFieldEditarIndex;
    private javax.swing.JTextField jTextFieldEditarNomeLembrete;
    private javax.swing.JTextField jTextFieldNomeLembrete;
    // End of variables declaration//GEN-END:variables
    private SystemTray tray;
    private TrayIcon trayIcon;
    private PopupMenu popupTray;
    private MenuItem menuItemExibirOcultarTray;
    private MenuItem menuItemSairTray;
    private ImageIcon iconeJanela;
}
