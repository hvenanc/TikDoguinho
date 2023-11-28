package org.henrique.model.negocios;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.henrique.model.negocios.Arquivo;
import org.henrique.model.negocios.Pet;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2023-11-27T20:06:11")
@StaticMetamodel(Tutor.class)
public class Tutor_ { 

    public static volatile ListAttribute<Tutor, Pet> pets;
    public static volatile SingularAttribute<Tutor, String> senha;
    public static volatile SingularAttribute<Tutor, Integer> codigo;
    public static volatile SingularAttribute<Tutor, Arquivo> arquivo;
    public static volatile SingularAttribute<Tutor, String> login;
    public static volatile SingularAttribute<Tutor, String> email;

}