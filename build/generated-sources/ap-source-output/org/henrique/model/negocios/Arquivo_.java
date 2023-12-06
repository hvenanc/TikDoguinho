package org.henrique.model.negocios;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2023-12-06T00:02:17")
@StaticMetamodel(Arquivo.class)
public class Arquivo_ { 

    public static volatile SingularAttribute<Arquivo, String> extensao;
    public static volatile SingularAttribute<Arquivo, Integer> tamanhoArquivo;
    public static volatile SingularAttribute<Arquivo, String> nomeArquivo;
    public static volatile SingularAttribute<Arquivo, byte[]> arquivo;

}