/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import java.net.*;
import java.io.*;


import Connect4.Config;
import engine.GameEngine;
import entity.Pawn;
import entity.Player;
import java.awt.event.MouseEvent;

/**
 *
 * @author c
 */
public class GameEngineOnline extends GameEngine{
        
        ServerSocket ss;
        Socket soc;
        BufferedReader Input;
        PrintWriter Output;
        BufferedReader Player2Input;
        
        
        public GameEngineOnline(int width, int height)
        {
            super(width, height);
            
            try
            {
            ss = new ServerSocket(1996);
            soc = ss.accept();
            Input = new BufferedReader(new InputStreamReader(System.in));
            Output = new PrintWriter(soc.getOutputStream(), true);
            Player2Input = new BufferedReader(new InputStreamReader(soc.getInputStream()));
            }catch(IOException e){
            
                e.printStackTrace();
            }
        }

    /**
     *
     * @param width
     * @param height
     * @param host
     */
        public GameEngineOnline(int width, int height,String host){
           super(width, height);
           this.playerOneTurn= false;
                
            try
            {
                
            soc = new Socket(host,1996);
            Input = new BufferedReader(new InputStreamReader(System.in));
            Output = new PrintWriter(soc.getOutputStream(), true);
            Player2Input = new BufferedReader(new InputStreamReader(soc.getInputStream()));
            
            }catch(IOException e){
            
                e.printStackTrace();
            }
        }
        
        @Override
        public void mouseClicked(MouseEvent event)
        {

            super.mouseClicked(event);
            Output.println(pawnColPosition);
        }
        
        public String Listen()
        { 
            String Message=null;
            try{
            Message = Player2Input.readLine();
            }catch(IOException e)
            {
              e.printStackTrace();
            }
            return Message;
        }
    
}
