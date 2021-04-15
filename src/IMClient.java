/* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
import java.util.Scanner;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
public class IMClient {
    public static void main(String[] args) throws IOException {

       // if (args.length != 3) {
          //  System.err.println(
            //        "Usage: java EchoClient <host name> <port number>, <client name>");
       //     System.exit(1);
      //  }

        String hostName = "localhost";
        int portNumber = 4444;
        String clientName = "Joe";
        ArrayList<Card> disDeck = new ArrayList<>();
        Scanner scan = new Scanner(System.in);

        Socket kkSocket = null;
        ObjectOutputStream out = null;
        ObjectInputStream in = null;
        ArrayList<Card> clientHand = null;
        Card disDeckCard = null;
        //Message inMessage= null;
        //ObjectInputStream in=null;
        BufferedReader stdIn = null;
        try {
            //client initiates a connection request to server's IP address, port
            kkSocket = new Socket(hostName, portNumber);

            //initializing the Object Output and Input Stream
            out = new ObjectOutputStream(kkSocket.getOutputStream());

            in = new ObjectInputStream(kkSocket.getInputStream());

            stdIn = new BufferedReader(new InputStreamReader(System.in));



            //in is bound to the Socket InputStream
            //fromServer stores the message from the server
            //receiving message from server.
            //checking if we have a response and a name

            clientHand = (ArrayList) in.readObject();
            for (int i = 0; i < clientHand.size(); i++)
                System.out.print("Card number: "+ clientHand.get(i).getCardNumber()  + " Card Color: " + clientHand.get(i).getCardColor() +"\n");

            disDeckCard= (Card) in.readObject();
            //disDeck  = (Card) in.readObject(); 
            //Message inMessage;

            //Message inMessage = (Message) in.readObject();
            // while ((fromServer = inMessage.getResponse()) != null || (serverName = inMessage.getName()) != null) {
            // //printing message from server locally to the clients screen
            // System.out.println(inMessage.toString());

            // //prompting the client it is their turn to talk
            // System.out.print(clientName+": ");

            // //breaks if message from server is "Bye."
            // if (fromServer.equals("Bye."))
            // break;

            // //read response from client
            // fromUser = stdIn.readLine();

            // //breaks if message from client is "Bye."
            // if(fromUser.equals("Bye."))
            // break;

            // if (fromUser != null) {

            // //sending/printing on the server
            // out.writeObject(clientName + ": "+ fromUser);
            // }
            // try {
            // inMessage = (Message) in.readObject();
            // }
            // catch(ClassNotFoundException cnfe)
            // {
            // System.err.println("Problem reading object: class not found");
            // System.exit(1);
            // }
            // }

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                    hostName);
            e.printStackTrace();
            System.exit(1);
        }   catch (ClassNotFoundException cnfe) {
            System.err.println("Problem reading object: class not found");
            System.exit(1);
        }
        //Message fromServer;
        int fromUser;
        String serverName;
        Card playerCard;
        while(disDeckCard !=null)
        {
            System.out.println("Top card on deck: "+ disDeckCard.getCardNumber() + " " + disDeckCard.getCardColor());
            //System.out.println("Top card on discard deck: " + disDeck.get(disDeck.size()-1).getCardNumber() + " " +disDeck.get(disDeck.size()-1).getCardColor());
            System.out.println(clientName+ " " + "Enter your card as the numerical position it appears in your deck:" );

            fromUser = scan.nextInt();

            //String[] cardDetails = fromUser.split(" ");

            //Card cardChoice = new Card (Integer.parseInt(cardDetails[0]), cardDetails[1]);
            playerCard = clientHand.remove(fromUser);
            out.writeObject(playerCard);
            for (int i = 0; i < clientHand.size(); i++)
                System.out.print("Card number: "+ clientHand.get(i).getCardNumber()  + " Card Color: " + clientHand.get(i).getCardColor() +"\n");

            //if(inMessage=new Message readObject() !=null)
            //{
            //   System.out.println(inMessage.toString());
            //}
            // else
            // {
            //disDeckCard  = (Card) in.readObject();
            //out.writeObject(disDeckCard);
            System.out.println("Top card on discard deck: " + disDeckCard.getCardNumber() + " " +disDeckCard.getCardColor());
            // clientHand.remove(fromUser);
            //}
            try {
                disDeckCard=(Card) in.readObject();

            }
            catch(ClassNotFoundException cnfe)
            {
                System.err.println("Problem reading object: class not found");
                cnfe.printStackTrace();
                System.exit(1);
            }
        }

        out.close();
        in.close();
        kkSocket.close();
        stdIn.close();
    }
}