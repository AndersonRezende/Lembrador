/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gerenciador;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import objeto.Lembrete;


/**
 *
 * @author anderson
 */
public class Arquivo 
{
    public static final String ABRE_LEMBRETES = "<lembretes>"; 
    public static final String FECHA_LEMBRETES = "</lembretes>"; 
    public static final String ABRE_LEMBRETE = "<lembrete>"; 
    public static final String FECHA_LEMBRETE = "</lembrete>";
    public static final String ABRE_ID = "<id>";
    public static final String FECHA_ID = "</id>";
    public static final String ABRE_NOME = "<nome>";
    public static final String FECHA_NOME = "</nome>";
    public static final String ABRE_DIA = "<dia>";
    public static final String FECHA_DIA = "</dia>";
    public static final String ABRE_MES = "<mes>";
    public static final String FECHA_MES = "</mes>";
    public static final String ABRE_ANO = "<ano>";
    public static final String FECHA_ANO = "</ano>";
    public static final String ABRE_DESCRICAO = "<descricao>";
    public static final String FECHA_DESCRICAO = "</descricao>";
    
    private File arquivo;
    
    
    public Arquivo(File arquivo)
    {   this.arquivo = arquivo; }
    
    public Arquivo(String caminho)
    {   this.arquivo = new File(caminho);   }
            
    private String pegarElemento(String linha, String abreElemento, String fechaElemento)
    {
        int inicio = linha.indexOf(abreElemento) + abreElemento.length();
        int fim = linha.indexOf(fechaElemento);
        return linha.substring(inicio, fim);
    }
    
    public ArrayList<Lembrete> lerArquivo() throws FileNotFoundException, UnsupportedEncodingException, IOException
    {
        ArrayList<Lembrete> lembretes = new ArrayList();
        String linha, id = "", nome = "", dia = "", mes = "", ano = "", descricao = "";
        int inicio, fim;
        
        if(arquivo.exists())
        {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(arquivo.getAbsolutePath()), "UTF-8"));
            
            while(br.ready())
            {
                linha = br.readLine();
                if(linha.contains(ABRE_LEMBRETE))       //MARCA O INICIO DA LEITURA DE UM LEMBRETE
                {
                    id = "";
                    nome = "";
                    dia = "";
                    mes = "";
                    ano = "";
                    descricao = "";
                }
                
                if(linha.contains(ABRE_ID))
                {   id = pegarElemento(linha, ABRE_ID, FECHA_ID);   }
                
                if(linha.contains(ABRE_NOME))
                {   nome = pegarElemento(linha, ABRE_NOME, FECHA_NOME); }
                
                if(linha.contains(ABRE_DIA))
                {   dia = pegarElemento(linha, ABRE_DIA, FECHA_DIA); }
                
                if(linha.contains(ABRE_MES))
                {   mes = pegarElemento(linha, ABRE_MES, FECHA_MES); }
                
                if(linha.contains(ABRE_ANO))
                {   ano = pegarElemento(linha, ABRE_ANO, FECHA_ANO); }
                
                if(linha.contains(ABRE_DESCRICAO))
                {   descricao = pegarElemento(linha, ABRE_DESCRICAO, FECHA_DESCRICAO); }
                
                if(linha.contains(FECHA_LEMBRETE))
                {   
                    Lembrete lembrete = new Lembrete(Integer.parseInt(id), nome,  Integer.parseInt(dia), Integer.parseInt(mes), Integer.parseInt(ano), descricao);
                    //lembretes.add(lembrete);
                    int index = 0;
                    boolean inseriu = false;
                    while(index < lembretes.size() && !inseriu)
                    {
                        if(lembrete.getDias() < lembretes.get(index).getDias())
                        {
                            lembretes.add(index, lembrete);
                            inseriu = true;
                        }
                        index++;
                    }
                    if(!inseriu)
                        lembretes.add(lembrete);
                }
            }
        }
        
        return lembretes;
    }
    
    public void gravarArquivo(ArrayList<Lembrete> lembretes) throws IOException
    {
        int index = 0;
        
        if(arquivo.exists())
        {   arquivo.createNewFile();    }
        
        
        FileWriter fw = new FileWriter(arquivo, false);
        BufferedWriter bw = new BufferedWriter(fw);
        
        bw.write(ABRE_LEMBRETES);
        bw.newLine();
        while(index < lembretes.size())
        {
            bw.write(ABRE_LEMBRETE);
            bw.newLine();
            
            bw.write(ABRE_ID);
            bw.write(""+lembretes.get(index).getId());
            bw.write(FECHA_ID);
            bw.newLine();
            
            bw.write(ABRE_NOME);
            bw.write(""+lembretes.get(index).getNome());
            bw.write(FECHA_NOME);
            bw.newLine();
            
            bw.write(ABRE_DIA);
            bw.write(""+lembretes.get(index).getDia());
            bw.write(FECHA_DIA);
            bw.newLine();
            
            bw.write(ABRE_MES);
            bw.write(""+lembretes.get(index).getMes());
            bw.write(FECHA_MES);
            bw.newLine();
            
            bw.write(ABRE_ANO);
            bw.write(""+lembretes.get(index).getAno());
            bw.write(FECHA_ANO);
            bw.newLine();
            
            bw.write(ABRE_DESCRICAO);
            bw.write(""+lembretes.get(index).getDescricao());
            bw.write(FECHA_DESCRICAO);
            bw.newLine();
            
            bw.write(FECHA_LEMBRETE);
            bw.newLine();
            index++;
        }
        bw.write(FECHA_LEMBRETES);
        
        bw.close();
        fw.close();
    }
}
