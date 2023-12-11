/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.henrique.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Henri
 */
@ManagedBean
@ViewScoped
public class VideoBean {
    
    private static final long serialVersionUID = 1L;
    
    private String events = "";
 
    public String getEvents() {
        return events;
    }
    
    public void setEvents(String events) {
    this.events = events;
  }
 
  public void onPlay() {
    events = "play" + "\n" + events;
  }
  public void onPause() {
    events = "pause" + "\n" + events;
  }
 
  public void onSeeking() {
    events = "seeking" + "\n" + events;
  }
 
  public void onCanplaythrough() {
    events = "can play through" + "\n" + events;
  }
 
  public void onLoadeddata() {
    events = "loaded data" + "\n" + events;
  }
}
