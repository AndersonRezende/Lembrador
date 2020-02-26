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
    {
        Calendar calendario = Calendar.getInstance();
        String data = calendario.get(Calendar.DAY_OF_MONTH) + "/";
        data += calendario.get(Calendar.MONTH) + "/";
        data += calendario.get(Calendar.YEAR) + "";
        return data;
    }
    
    public static String formatarData(Calendar calendario)
    {
        String dataFormatada = "";
        dataFormatada = calendario.get(Calendar.DAY_OF_MONTH) + "/";
        dataFormatada += calendario.get(Calendar.MONTH) + "/";
        dataFormatada += calendario.get(Calendar.YEAR) + "";
        return dataFormatada;
    }
    
    public static int diferencaDiasDataAtual(Calendar calendario)
    {
        Calendar calendarioAtual = Calendar.getInstance();
        int diferenca = calendario.get(Calendar.DAY_OF_YEAR) - calendarioAtual.get(Calendar.DAY_OF_YEAR);
        return diferenca;
    }
}
