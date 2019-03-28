package Model;

public class Request {

    int requestId;
    private String sender;
    private String receiver;
    private boolean closed;
    // 0 not seen, replacement.
    // 1 seen and replace accepted by reciever.
    // 2 seen and replace canceled by reciever.
    // 3 not seen, pay cash.
    // 4 seen and accepted by reciever
    // 5 seen and canceled by reciever
    // 6 reciever wait for get his money from buyer
    // 7 don't do nothing
    private boolean isTrade;
    private int requestedTicketId;
    private int offeredTicketId;

    public Request(int requestId, String sender, String receiver, boolean closed, int requestedTicketId, int offeredTicketId, boolean isTrade) {
        this.sender = sender;
        this.receiver = receiver;
        this.isTrade = isTrade;
        this.closed = closed;
        this.requestedTicketId = requestedTicketId;
        if (isTrade)
            this.offeredTicketId = offeredTicketId;
        this.requestId = requestId;
    }

    public boolean getClosed() {
        return closed;
    }

    public String getSender() {
        return sender;
    }

    public String getReciever() {
        return receiver;
    }

    public boolean getisTrade() {
        return isTrade;
    }

    public int getrequestedTicketId() {
        return requestedTicketId;
    }

    public int getofferedTicketId() {
        return offeredTicketId;
    }
}