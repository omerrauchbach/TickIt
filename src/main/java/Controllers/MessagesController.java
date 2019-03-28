package Controllers;

import Model.Message;
import Model.MessageModel;
import View.AView;

import java.util.List;

public class MessagesController extends AController {



    MessageModel messageModel;
    AView view;

    public MessagesController(MessageModel model, AView view) {
        this.messageModel = model;
        this.view = view;
    }

    public boolean CreateMessage(String sender, String reciever, int seen, int vacationIdSource, int vacationIdDest, int kind) {
            Message message=new Message(sender,reciever,seen,vacationIdSource,vacationIdDest,kind);
            return messageModel.CreateMessage(message);
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
