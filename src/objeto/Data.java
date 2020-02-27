/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objeto;

import java.util.Calendar;

/**
 *
 * @author anderson
 */
public class Data 
{
    public static String dataAtual()
    {   return formatarData(Calendar.getInstance());    }
    
    public static String formatarData(Calendar calendario)
    {
        String dataFormatada = "";
        dataFormatada = calendario.get(Calendar.DAY_OF_MONTH) + "/";
        dataFormatada += (calendario.get(Calendar.MONTH) + 1) + "/";
        dataFormatada += calendario.get(Calendar.YEAR) + "";
        return dataFormatada;
    }
    
    public static int diferencaDiasDataAtual(int dia, int mes, int ano)
    {
        Calendar calendario = Calendar.getInstance();
        calendario.set(ano, mes, dia);
        return diferencaDiasDataAtual(calendario);
    }
    
    public static int diferencaDiasDataAtual(Calendar calendario)
    {   return diferencaDias(Calendar.getInstance(), calendario);   }
    
    public static int diferencaDias(Calendar dataInicial, Calendar dataFinal)
    {
        int diferenca = dataFinal.get(Calendar.DAY_OF_YEAR) - dataInicial.get(Calendar.DAY_OF_YEAR);
        return diferenca;
    }
}
