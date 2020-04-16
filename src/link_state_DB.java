import java.util.ArrayList;

public class link_state_DB {
    private ArrayList<ArrayList<packet>> data;
    private int currentRouterId;
    private final int num_routers=5;
    link_state_DB(circuit_DB circuit_db,int currentRouterId){
        this.data = new ArrayList<ArrayList<packet>>();
        this.currentRouterId=currentRouterId;
        int sender;
        int router_id;
        int link_id;
        int cost;
        int via;
        packet p;
        for(int j=0;j<num_routers;j++){
            data.add(new ArrayList<packet>());
        }
        for(int i= 0; i<circuit_db.getNumLinks();i++){
            sender = currentRouterId;
            router_id = currentRouterId;
            link_id = circuit_db.getLink(i);
            cost=circuit_db.getLinkCost(i);
            via = -1;
            p = new packet(sender,router_id,link_id,cost,via);
            data.get(currentRouterId-1).add(p);
        }
    }

    public int getSize(int i){
        return data.get(i).size();
    }

    public packet getData(int router, int index){
        return data.get(router).get(index);
    }

    public int sizeDB(){
        int size=0;
        for (int i = 0;i<num_routers;i++){
            size+=data.get(i).size();
        }
        return size;
    }

    public boolean addPacket(packet p){
        packet currentPacket;
        if(data.get(p.getRouter_id()-1).size()>0){
            for(int i=0;i<data.get(p.getRouter_id()-1).size();i++){
                currentPacket=data.get(p.getRouter_id()-1).get(i);
                if(currentPacket.getLink_id()==p.getLink_id()){
                    return false;
                }
            }
        }
        data.get(p.getRouter_id()-1).add(p);
        return true;
    }

    public String printDB(){
        String msg ="# Topology Database \n";
        for(int i=0;i<data.size();i++){
            if(data.get(i).size()>0){
                int set = i+1;
                msg+="R" + currentRouterId+ " -> R"+set+" nbr link " + data.get(i).size() + "\n";
                for (int j=0;j<data.get(i).size();j++){
                    msg+="R" + currentRouterId+ " -> R"+set+ " link "+ data.get(i).get(j).getLink_id()+ " cost "+data.get(i).get(j).getCost()+ "\n";
                }
            }
        }
        return msg;
    }
}
