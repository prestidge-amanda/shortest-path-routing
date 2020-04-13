import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class packet {
    private String packet_type;
    private int router_id;
    private int link_id;
    private int sender;
    private int cost;
    private int via;

    packet(int router_id, int link_id){
        this.router_id=router_id;
        this.link_id=link_id;
        this.packet_type="HELLO";
        this.sender=-1;
        this.cost=-1;
        this.via=-1;
    }

    packet(int router_id){
        this.router_id=router_id;
        this.packet_type="INIT";
        this.link_id=-1;
        this.sender=-1;
        this.cost=-1;
        this.via=-1;
    }

    packet(int sender, int router_id,int link_id, int cost, int via){
        this.sender=sender;
        this.packet_type="LSPDU";
        this.router_id=router_id;
        this.link_id=link_id;
        this.cost=cost;
        this.via=via;
    }

    public int getCost() {
        return cost;
    }

    public int getRouter_id(){
        return router_id;
    }

    public int getLink_id(){
        return link_id;
    }

    public int getVia(){
        return via;
    }

    public int getSender(){
        return sender;
    }

    public String getPacket_type(){
        return packet_type;
    }

    public byte[] getUDPdata() {
        ByteBuffer buffer = ByteBuffer.allocate(512);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        if(packet_type=="INIT"){
            buffer.putInt(router_id);
        }else if (packet_type=="HELLO"){
            buffer.putInt(router_id);
            buffer.putInt(link_id);
        }else if(packet_type=="LSPDU"){
            buffer.putInt(sender);
            buffer.putInt(router_id);
            buffer.putInt(link_id);
            buffer.putInt(cost);
            buffer.putInt(via);
        }
        return buffer.array();
    }

    public static packet parseUDPdata(byte[] UDPdata) throws Exception {
        ByteBuffer buffer = ByteBuffer.wrap(UDPdata);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        if(buffer.array().length==2){
            int router_id = buffer.getInt();
            int link_id=buffer.getInt();
            return new packet(router_id,link_id);
        }else if (buffer.array().length==20){
            int sender=buffer.getInt();
            int router_id=buffer.getInt();
            int link_id=buffer.getInt();
            int cost=buffer.getInt();
            int via=buffer.getInt();
            return new packet(sender,router_id,link_id,cost,via);
        }else{
            return new packet(1);
        }
    }


}
