/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.henrique.model.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.henrique.model.negocios.Pet;
import org.henrique.model.negocios.Tutor;

/**
 *
 * @author henrique
 */
public class ManagerDao {
    
    private static ManagerDao myself = null;
    
    public static ManagerDao getCurrentInstance(){
        if(myself == null)
            myself = new ManagerDao();
        
        return myself;
    }
    
    private EntityManagerFactory emf = null;
            
    private ManagerDao(){
        this.emf = Persistence.createEntityManagerFactory("TikDoguinhoPU");
    } 
    
    public void insert(Object o){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(o);
        em.flush();
        em.getTransaction().commit();
        em.close();
    }
    
    public void update(Object o){
        EntityManager em = emf.createEntityManager();
        
        em.getTransaction().begin();
                
        em.merge(o);
        em.getTransaction().commit();
        em.close();
    }
    
    public List read(String query,Class c){
        
        EntityManager em = emf.createEntityManager();
        
        List returnedList = em.createQuery(query,c).getResultList();
        
        em.close();
        
        return returnedList;
    }
    
    public void delete(Object o){
        EntityManager em = emf.createEntityManager();
        
        Object oDelete = o;
        
        if(!em.contains(o)){
            oDelete = em.merge(o);
        }
        em.getTransaction().begin();
        
        em.remove(oDelete);
        em.getTransaction().commit();
        em.close();
    }
    
    public List<Pet> findPetByLikeNome(String nome) {
        
        EntityManager em = emf.createEntityManager();
        String jpql = "SELECT p FROM Pet p WHERE p.nome like :nome";

        return em.createQuery(jpql, Pet.class)
                .setParameter("nome", "%" + nome + "%")
                .getResultList();
    }
    
    public static void main(String[] args) {
        
         Tutor tutor = new Tutor();
         tutor.setEmail("Joao@teste.com");
         tutor.setLogin("Joao");
         tutor.setSenha("123");
        
        getCurrentInstance().insert(tutor);
        
    }
    
}
