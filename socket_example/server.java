import javafx.application.Application;

import java.awt.*;
import java.io.*;
import java.net.*;

import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import  javafx.scene.control.TextArea;
import java.util.*;


public class server extends Application
{
    public void start(Stage stage)
    {
        TextArea ta=new TextArea();
        ScrollPane scrollPane=new ScrollPane(ta);
        Scene scene=new Scene(scrollPane,450,200);
        stage.setTitle("server");
        stage.setScene(scene);
        stage.show();
        new Thread(()->

        {

            try
            {
                ServerSocket serverSocket=new ServerSocket(7000);
                Platform.runLater(()-> ta.appendText("server start at "+new Date()+'\n'));

                Socket socket=serverSocket.accept();

                DataInputStream dataInputStream=new DataInputStream(socket.getInputStream());
                DataOutputStream dataOutputStream=new DataOutputStream(socket.getOutputStream());
                InetAddress inetAddress=socket.getInetAddress();
                Platform.runLater(()-> ta.appendText("client's ip addresss is "+inetAddress.getHostAddress()+'\n'));

                while(true)
                {
                    //double Radius=dataInputStream.readDouble();
                    //double Area=2*Math.PI*Radius;
                    float []s=new float[3];
                    for(int i=0;i<3;i++)
                        s[i]=dataInputStream.readFloat();

                    Platform.runLater(()->
                    {
                        ta.appendText("yaw pitch roll recived from client:"+s[0]+" "+s[1]+" "+s[2]+'\n');
                    });

                }

            }
            catch (IOException ex)
            {
                ex.printStackTrace();    // ？
            }

        }).start();



    }

}
