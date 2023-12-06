package org.henrique.model.negocios;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.henrique.model.negocios.Arquivo;
import org.henrique.model.negocios.Tutor;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2023-12-06T00:02:17")
@StaticMetamodel(Pet.class)
public class Pet_ { 

    public static volatile SingularAttribute<Pet, Integer> codigo;
    public static volatile SingularAttribute<Pet, String> hashPet;
    public static volatile ListAttribute<Pet, Tutor> tutores;
    public static volatile SingularAttribute<Pet, Arquivo> arquivo;
    public static volatile SingularAttribute<Pet, String> nome;
    public static volatile SingularAttribute<Pet, String> porte;
    public static volatile SingularAttribute<Pet, String> mesAnoNascimento;

}