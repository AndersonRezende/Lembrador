/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objeto;

import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author anderson
 */
public class Lembrete 
{
    private int id;
    private String nome;
    private Calendar data = Calendar.getInstance();
    private String descricao;
    private int dias;

    public Lembrete(int id, String nome, Calendar data, String descricao) 
    {
        this.id = id;
        this.nome = nome;
        this.data = data;
        this.descricao = descricao;
        this.dias = Data.diferencaDiasDataAtual(data);
    }
    
    public Lembrete(int id, String nome, int dia, int mes, int ano, String descricao) 
    {
        this.id = id;
        this.nome = nome;
        this.data.set(ano, mes - 1, dia);
        this.descricao = descricao;
        this.dias = Data.diferencaDiasDataAtual(data);
    }

    public int getId() 
    {   return id;  }

    public String getNome() 
    {   return nome;    }

    public Calendar getData() 
    {   return data;    }
    
    public String getDataFormatada()
    {   return Data.formatarData(this.data); }
    
    public int getDia()
    {   return data.get(Calendar.DAY_OF_MONTH); }
    
    public int getMes()
    {   return data.get(Calendar.MONTH) + 1; }
    
    public int getAno()
    {   return data.get(Calendar.YEAR); }

    public String getDescricao() 
    {   return descricao;   }
    
    public int getDias()
    {   return dias;    }

    public void setId(int id) 
    {   this.id = id;   }

    public void setNome(String nome) 
    {   this.nome = nome;   }

    public void setData(Calendar data) 
    {
        this.data = data;   
        this.setDias(Data.diferencaDiasDataAtual(data));
    }
    
    public void setData(int dia, int mes, int ano)
    {   
        this.data.set(ano, mes - 1, dia);   
        this.setDias(Data.diferencaDiasDataAtual(data));
    }

    public void setDescricao(String descricao) 
    {   this.descricao = descricao; }
    
    public void setDias(int dias)
    {   this.dias = dias;   }
    
    public static String[] getListaNome(ArrayList<Lembrete> lembretes)
    {
        String nomes[] = new String[lembretes.size()];
        for(int index = 0; index < lembretes.size(); index++)
            nomes[index] = lembretes.get(index).getNome();
        return nomes;
    }
}
