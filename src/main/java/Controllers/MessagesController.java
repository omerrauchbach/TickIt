package Controllers;

import Model.Message;
import Model.MessageModel;
import View.AView;

import java.util.ArrayList;
import java.util.List;

public class MessagesController extends AController {



    MessageModel messageModel;
    AView view;

    public MessagesController(MessageModel model, AView view) {
        this.messageModel = model;
        this.view = view;
    }

    public boolean CreateMessage(String sender, String reciever, String text) {
            Message message=new Message(sender,reciever,text);
            return messageModel.CreateMessage(message);
    }

    public List<Message> getMessages(String reciever){

        List<Message> list = messageModel.searchByReciever(reciever);

        return list;
    }
    public void updateSeenToMessage(int id, int seen){
        messageModel.UpdateSeen(id,seen);
    }

    public List<Message> getRecieverMessages(String reciever){
        return messageModel.searchByReciever(reciever);
    }

    public void changeVacations(Message clickedRow) {
        messageModel.changeVacations(clickedRow);
    }

    public void changeOwnerOfVacation(Message clickedRow) {
        messageModel.changeOwnerOfVacation(clickedRow);
    }
}
