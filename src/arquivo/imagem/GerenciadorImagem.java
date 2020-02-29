/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arquivo.imagem;

import java.net.URL;

/**
 *
 * @author anderson
 */
public class GerenciadorImagem
{
    public static final String ATUALIZAR = "atualizar.png";
    public static final String DELETAR = "deletar.png";
    public static final String EDITAR = "editar.png";
    public static final String NOVO = "novo.png";
    public static final String SAIR = "sair.png";
    public static final String TIMER = "timer.png";
    public static final String TIMER48X = "timer48x.png";
    
    public static URL getImagemUrl(String imagem)
    {
        URL url = GerenciadorImagem.class.getResource(imagem);
        return url;
    }
}
