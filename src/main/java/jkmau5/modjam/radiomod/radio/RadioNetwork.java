package jkmau5.modjam.radiomod.radio;

import jkmau5.modjam.radiomod.tile.TileEntityBroadcaster;
import jkmau5.modjam.radiomod.tile.TileEntityCable;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Lordmau5
 * Date: 14.12.13
 * Time: 15:41
 * You are allowed to change this code,
 * however, not to publish it without my permission.
 */
public class RadioNetwork {
    private List<TileEntityCable> cables = new ArrayList<TileEntityCable>();
    //private List<TileEntityAntenna> antennas = new ArrayList<TileEntityAntenna>(); //TODO: Add tile entity for the antenna
    private TileEntityBroadcaster broadcaster;

    public RadioNetwork(TileEntityCable mainCable) {
        this.cables.clear();
        this.broadcaster = null;

        addCable(mainCable);
    }

    public RadioNetwork(TileEntityBroadcaster broadcaster) {
        this.cables.clear();
        this.broadcaster = broadcaster;
    }

    public void destroyNetwork() {
        this.cables = null;
        this.broadcaster = null;
    }

    public List<TileEntityCable> getCables() {
        return this.cables;
    }

    public TileEntityBroadcaster getBroadcaster() {
        return this.broadcaster;
    }

    public boolean setBroadcaster(TileEntityBroadcaster broadcaster){
        //System.out.println("1");
        if(broadcaster.getRadioNetwork() != null)
            return false;
        //System.out.println("2");
        if(this.broadcaster != null && this.broadcaster == broadcaster)
            return true;
        //System.out.println("3");
        if(this.broadcaster != null && this.broadcaster != broadcaster)
            return false;
        //System.out.println("4");
        this.broadcaster = broadcaster;
        broadcaster.setRadioNetwork(this);
        return true;
    }

    public boolean tryRemoveBroadcaster(TileEntityBroadcaster radio){
        /*if(radio != null && !radio.isConnectedToNetwork()) {
            this.broadcaster.destroyNetwork();
            this.broadcaster = null;
            return true;
        }*/
        return false;
    }

    public void addCable(TileEntityCable cable) {
        if(this.cables.contains(cable))
            return;
        this.cables.add(cable);
        cable.setNetwork(this);
    }

    public void removeCable(TileEntityCable cable) {
        if(!this.cables.contains(cable))
            return;
        this.cables.remove(cable);

        recalculateNetwork(this);
    }

    public void recalculateNetwork(RadioNetwork network) {
        for(TileEntityCable cable : network.getCables()) {
            cable.initiateNetwork();
        }

        if(this.broadcaster != null)
            this.broadcaster.destroyNetwork();
        this.broadcaster = null;

        /*if(this.broadcaster != null) {
            this.broadcaster.destroyNetwork();
            this.broadcaster = null;
        }*/
    }

    public void mergeWithNetwork(RadioNetwork otherNetwork) {
        if(otherNetwork == this)
            return;

        if(getBroadcaster() != null)
            System.out.println("First: " + getBroadcaster().toString());
        if(otherNetwork.getBroadcaster() != null)
            System.out.println("Second: " + otherNetwork.getBroadcaster().toString());

        for(TileEntityCable cable : otherNetwork.getCables())
            addCable(cable);

        //if(otherNetwork.getBroadcaster() != null)
        //    otherNetwork.getBroadcaster().setRadioNetwork(this);

        otherNetwork.destroyNetwork();
    }
}