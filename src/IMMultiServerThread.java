import java.net.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class IMMultiServerThread extends Thread {
    private Socket socket = null;

    public IMMultiServerThread(Socket socket) {
        super("IMMultiServerThread");
        this.socket = socket;
    }

    public void run() {

        try
        {
            //initializing the Object Input and Output Streams
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            int cardNum;
            Random randNum = new Random();
            //initializing our strings to store data such as the output, input and name;
            String inputLine, outputLine, inputName;
            Card serverCard, clientCard;

            IMProtocol kkp = new IMProtocol();
            outputLine = kkp.processInput(null);

            //passing "Server" as the name of the server for our message object
            //Message message = new Message("Server", outputLine);

            //sending initial message to client (connection established)
            //out.writeObject(message);

            //distribute cards to server here
            //creating decks here
            ArrayList<Card> Deck =initDeck();
            ArrayList<Card> disDeck = new ArrayList<>();
            ArrayList<Card> serverHand = new ArrayList<Card> (7);
            ArrayList<Card> clientHand = new ArrayList<Card>(7);

            //total number of cards in deck
            System.out.println("Total cards in deck: " + Deck.size());

            //giving server their cards
            for(int i=0; i<7; i++)
                Draw(Deck, serverHand);

            //giving client their cards
            for(int i=0; i<7; i++)
                Draw(Deck, clientHand);

            //total cards left in deck
            System.out.println("Total cards in deck: " + Deck.size());
            for (int i = 0; i < serverHand.size(); i++)
                System.out.print("Card number: "+ serverHand.get(i).getCardNumber()  + " Card Color: " + serverHand.get(i).getCardColor() +"\n");

            //putting first card on discard deck (disDeck)

            //passing ArrayList object deck to client
            out.writeObject(clientHand);
            Draw(Deck,disDeck);
            System.out.println("Top card on discard deck: " + disDeck.get(disDeck.size()-1).getCardNumber() + " " +disDeck.get(disDeck.size()-1).getCardColor());

            out.writeObject(disDeck.get(disDeck.size()-1));

            //we assume the client will have the first play here
            Card playerCard = (Card) in.readObject();

            //String playerCardColor;
            //System.out.println("Player Card Color: " + playerCard.getCardColor());
            //playerCard.getCardNumber();
            //while(1>0)
            while((playerCard.getCardColor())  != null )
            {
                //playerCard = (Card) in.readObject();
                //before checking the validity
                System.out.println("* Player sent:" + playerCard.getCardNumber() + " "+ playerCard.getCardColor());
                if((processCard(disDeck.get(disDeck.size()-1), playerCard))==true)
                {
                    //displays after checking if its valid
                    System.out.println("** Player sent:" + playerCard.getCardNumber() + " "+ playerCard.getCardColor());
                    disDeck.add(playerCard);

                    //im sending an arraylist here
                    out.writeObject(disDeck);
                    System.out.println("Top card on discard deck: " + disDeck.get(disDeck.size()-1).getCardNumber() + " " +disDeck.get(disDeck.size()-1).getCardColor());

                }
                //else
                //{
                //   Message errorMessage = new Message("Server", "Invalid play, try again");
                //   out.writeObject(errorMessage);

                //}
                //if(processCard(disDeck.get(disDeck.size()-1),
                try {
                    playerCard = (Card) in.readObject();
                }
                catch(ClassNotFoundException cnfe)
                {
                    System.err.println("Problem reading object: class not found");
                    System.exit(1);
                }
            }
            try {
                playerCard = (Card) in.readObject();
            }catch(ClassNotFoundException cnfe)
            {
                System.err.println("Problem reading object: class not found");
                System.exit(1);
            }

            //Message inMessage = (Message) in.readObject();

            //checking if we have a response and a name from the client

            // while ((inputLine = inMessage.getResponse()) != null || (inputName = inMessage.getName()) !=null ){
            // System.out.println(inMessage.toString());
            // //determining the server's reply by calling the prcessInput() method

            // outputLine = kkp.processInput(inputLine);

            // //sending reply to client
            // message = new Message ("Server", outputLine);
            // out.writeObject(message);

            // //repeating steps until the server says "Bye."
            // if (outputLine.equalsIgnoreCase("Bye."))
            // break;

            // //need to introduce try and catch for reading the object
            // try {
            // inMessage = (Message) in.readObject();
            // }
            // catch(ClassNotFoundException cnfe)
            // {
            // System.err.println("Problem reading object: class not found");
            // System.exit(1);
            // }
            // }

            //socket.close();
            out.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException cnfe) {
            System.err.println("Problem reading object: class not found");
            System.exit(1);
        }

    }

    public ArrayList initDeck(){
        Random rand = new Random();
        ArrayList<Card> Deck = new ArrayList<Card> (80);
        //Random randomCardCol = new Random();

        int cardColor;
        List<String> cardCol = new ArrayList<String>();
        cardCol.add(0,"blue");
        cardCol.add(1,"red");
        cardCol.add(2,"yellow");
        cardCol.add(3,"green");

        //My version of creating the deck, not Giuseppes version
        Card card;
        for (int i=0; i< 10; i++)
        {
            for(int j=0; j<4; j++)
            {
                //if(i!=0){
                for(int k=0; k<2; k++)
                {
                    card = new Card (i, cardCol.get(j));
                    Deck.add(card);
                }

                //else {
                //      card =new Card (i, cardCol.get(j));
                //      Deck.add(card);
                // }

                //}
            }
        }
        return Deck;
    }

    public void Draw(ArrayList<Card> deckFrom, ArrayList<Card> deckTo)
    {
        int cardNum;
        Random randNum = new Random();
        cardNum =randNum.nextInt(deckFrom.size());
        //card = randDeckCard(Deck);
        deckTo.add(deckFrom.get(cardNum));
        deckFrom.remove(deckFrom.get(cardNum));

    }

    public boolean processCard(Card card1, Card card2)
    {
        boolean validPlay=false;
        String cardColor=card1.getCardColor();
        int cardNumber = card1.getCardNumber();
        String cardColor2=card2.getCardColor();
        int cardNumber2 = card2.getCardNumber();
        if(cardColor.equals(cardColor2) || cardNumber==cardNumber2)
        {
            validPlay= true;
            System.out.println("Valid Play");
        }

        return validPlay;
    }
}

